package org.dev.iuh.server.services;

import org.dev.iuh.common.dto.AssessmentDto;
import org.dev.iuh.common.dto.EnrollmentEdit;
import org.dev.iuh.common.dto.OverviewStatistics;
import org.dev.iuh.server.context.ServerContext;
import org.dev.iuh.server.dao.AssessmentDao;
import org.dev.iuh.server.entities.Enrollment;

import java.util.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;

public class AssessmentService implements AssessmentDao {
    private EntityManager entityManager;

    public AssessmentService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<AssessmentDto> getAssessments(int enrollmentId) {
        return entityManager.createNamedQuery("AssessmentValue.findByEnrollmentId", AssessmentDto.class)
                .setParameter("enrollmentId", enrollmentId)
                .getResultList();
    }

    public boolean updateGrade(EnrollmentEdit dto) {
        EntityTransaction trans = entityManager.getTransaction();
        try {
            trans.begin();
            // AssessmentValue aValue =
            for (EnrollmentEdit.AssessmentValue v : dto.getAssesments()) {
                var assesmentValueDb = entityManager.find(org.dev.iuh.server.entities.AssessmentValue.class, v.getId());
                var object = new org.dev.iuh.server.entities.AssessmentValue();
                object.setId(v.getId());
                object.setValue(v.getValue());
                object.setAssessment(assesmentValueDb.getAssessment());
                object.setEnrollment(assesmentValueDb.getEnrollment());
                entityManager.merge(object);
            }
            var enrollment = entityManager.find(Enrollment.class, dto.getEnrollmentId());
            enrollment.setAvgGrade(dto.getAvgGrade());
            entityManager.merge(enrollment);
            trans.commit();
            return true;
        } catch (Exception e) {
            if (trans.isActive()) {
                trans.rollback();
            }
            e.printStackTrace();
        }
        return false;

    }

    @SuppressWarnings("unchecked")
    private Map<String, Integer> getGenderRatioByCourseId(int courseId, int lecturerId) {
        Map<String, Integer> genderRatio = new LinkedHashMap<>();
        String sqlQuery = "SELECT " +
                "COUNT(CASE WHEN u.gender = 'MALE' THEN 1 END) AS maleCount, " +
                "COUNT(CASE WHEN u.gender = 'FEMALE' THEN 1 END) AS femaleCount, " +
                "COUNT(CASE WHEN u.gender = 'OTHER' THEN 1 END) AS otherCount " +
                "FROM enrollment e " +
                "JOIN course c ON e.course_id = c.id " +
                "JOIN user u ON u.id = e.student_id " +
                "WHERE c.id = :courseId AND c.lecturer_id = :lecturerId";
        Query query = entityManager.createNativeQuery(sqlQuery);
        query.setParameter("courseId", courseId);
        query.setParameter("lecturerId", lecturerId);
        List<Object[]> results = query.getResultList();

        if (!results.isEmpty()) {
            Object[] result = results.get(0);
            genderRatio.put("MALE", ((Long) result[0]).intValue());
            genderRatio.put("FEMALE", ((Long) result[1]).intValue());
            genderRatio.put("OTHER", ((Long) result[2]).intValue());
        }
        return genderRatio;
    }

    @SuppressWarnings("unchecked")
    private Map<String, Integer> getPointSpectrumByCourseId(int courseId, int lecturerId) {
        Map<String, Integer> pointSpectrum = new LinkedHashMap<>();

        String queryStr = "SELECT " +
                "COUNT(CASE " +
                "WHEN e.avg_grade >= 0 AND e.avg_grade < 1 THEN 1 END) AS '0-1', " +
                "COUNT(CASE WHEN e.avg_grade >= 1 AND e.avg_grade < 2 THEN 1 END) AS '1-2', " +
                "COUNT(CASE WHEN e.avg_grade >= 2 AND e.avg_grade < 3 THEN 1 END) AS '2-3', " +
                "COUNT(CASE WHEN e.avg_grade >= 3 AND e.avg_grade < 4 THEN 1 END) AS '3-4', " +
                "COUNT(CASE WHEN e.avg_grade >= 4 AND e.avg_grade < 5 THEN 1 END) AS '4-5', " +
                "COUNT(CASE WHEN e.avg_grade >= 5 AND e.avg_grade < 6 THEN 1 END) AS '5-6', " +
                "COUNT(CASE WHEN e.avg_grade >= 6 AND e.avg_grade < 7 THEN 1 END) AS '6-7', " +
                "COUNT(CASE WHEN e.avg_grade >= 7 AND e.avg_grade < 8 THEN 1 END) AS '7-8', " +
                "COUNT(CASE WHEN e.avg_grade >= 8 AND e.avg_grade < 9 THEN 1 END) AS '8-9', " +
                "COUNT(CASE WHEN e.avg_grade >= 9 AND e.avg_grade <= 10 THEN 1 END) AS '9-10' " +
                "FROM enrollment e " +
                "JOIN course c ON e.course_id = c.id " +
                "WHERE c.id = :courseId AND c.lecturer_id = :lecturerId";

        Query query = entityManager.createNativeQuery(queryStr);
        query.setParameter("courseId", courseId);
        query.setParameter("lecturerId", lecturerId);
        List<Object[]> results = query.getResultList();

        if (!results.isEmpty()) {
            Object[] result = results.get(0);
            for (int i = 0; i < 10; i++) {
                pointSpectrum.put("" + i + "-" + (i + 1), ((Long) result[i]).intValue());
            }
        }
        return pointSpectrum;
    }

    @SuppressWarnings("unchecked")
    private Map<String, Integer> getSummarize(int lecturerId) {

        Map<String, Integer> summary = new LinkedHashMap<>();
        String sqlQuery = "SELECT COUNT(*) AS total FROM course c WHERE c.lecturer_id = :lecturerId " +
                "UNION ALL " +
                "SELECT COUNT(DISTINCT c.subject_id) FROM course c WHERE c.lecturer_id = :lecturerId " +
                "UNION ALL " +
                "SELECT COUNT(DISTINCT e.student_id) FROM course c JOIN enrollment e ON c.id = e.course_id WHERE c.lecturer_id = :lecturerId";
        Query query = entityManager.createNativeQuery(sqlQuery);
        query.setParameter("lecturerId", lecturerId);
        List<Object> results = query.getResultList();
        if (results.size() == 3) {
            summary.put("totalCourses", ((Long) results.get(0)).intValue());
            summary.put("totalSubjects", ((Long) results.get(1)).intValue());
            summary.put("totalStudents", ((Long) results.get(2)).intValue());
        }
        return summary;

    }

    public OverviewStatistics getOverviewStatistic(int courseId) {
        int lecturerId = ServerContext.getInstance().getUserId();
        var pointSpectrum = getPointSpectrumByCourseId(courseId, lecturerId);
        var genderRatio = getGenderRatioByCourseId(courseId, lecturerId);
        var summary = getSummarize(lecturerId);
        var stats = new OverviewStatistics();
        stats.setGenderRatio(genderRatio);
        stats.setPointSpectrum(pointSpectrum);
        stats.setTotalCourses(summary.get("totalCourses"));
        stats.setTotalStudents(summary.get("totalStudents"));
        stats.setTotalSubjects(summary.get("totalSubjects"));
        return stats;
    }

    public Map<String, Integer> getPointSpectrum(int courseId) {
        int lecturerId = ServerContext.getInstance().getUserId();
        System.out.println("lec id: " + lecturerId);
        var res = getPointSpectrumByCourseId(courseId, lecturerId);
        return res;
    }

    public Map<String, Integer> getGenderRatio(int courseId) {
        int lecturerId = ServerContext.getInstance().getUserId();
        System.out.println("lec id: " + lecturerId);
        var res = getGenderRatioByCourseId(courseId, lecturerId);
        return res;
    }

}
