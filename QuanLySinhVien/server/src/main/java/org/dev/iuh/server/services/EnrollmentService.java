package org.dev.iuh.server.services;

import java.util.*;

import org.dev.iuh.common.dto.BasicTranscript;
import org.dev.iuh.common.dto.EnrollmentDto;
import org.dev.iuh.common.dto.RegisterCourseDto;
import org.dev.iuh.server.dao.EnrollmentDao;
import org.dev.iuh.server.entities.AcademicDetails;
import org.dev.iuh.server.entities.Assessment;
import org.dev.iuh.server.entities.AssessmentValue;
import org.dev.iuh.server.entities.Course;
import org.dev.iuh.server.entities.Enrollment;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;

public class EnrollmentService implements EnrollmentDao {
    private EntityManager entityManager;

    public EnrollmentService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<EnrollmentDto> findEnrollments(HashMap<String, Object> searchCriteria) {
        StringBuilder sb = new StringBuilder(
                "SELECT e.id AS id, ac.student_code AS studentCode, u.first_name AS firstName, u.last_name AS lastName, ")
                .append("c.code AS courseCode, c.name AS courseName, c.semester_no AS semesterNo, c.academic_year AS academicYear, c.room AS room, ")
                .append("s.code AS subjectCode, s.id AS subjectId, e.avg_grade AS avgGrade ")
                .append("FROM academic_details ac ")
                .append("JOIN user u ON ac.student_id = u.id ")
                .append("JOIN enrollment e ON ac.id = e.student_id ")
                .append("JOIN course c ON e.course_id = c.id ")
                .append("JOIN subject s ON c.subject_id = s.id ");
        // If search criteria provided, add WHERE clause
        if (searchCriteria != null && !searchCriteria.isEmpty()) {
            sb.append("WHERE ");
            StringJoiner joiner = new StringJoiner(" AND ");
            for (Map.Entry<String, Object> entry : searchCriteria.entrySet()) {
                if (entry.getValue() instanceof String) {
                    joiner.add(entry.getKey() + " = '" + entry.getValue() + "'");
                } else if (entry.getValue() instanceof Integer) {
                    joiner.add(entry.getKey() + " = " + entry.getValue());
                } else {
                    // Handle other data types if needed
                }
            }
            sb.append(joiner.toString());
        }

        sb.append(";");

        String sql = sb.toString();

        @SuppressWarnings("unchecked")
        List<EnrollmentDto> enrollmentDtos = entityManager.createNativeQuery(sql)
                .unwrap(org.hibernate.query.Query.class)
                .setTupleTransformer((tuple, aliases) -> {
                    var dto = new EnrollmentDto();
                    dto.setId((int) tuple[0]);
                    dto.setStudentCode((String) tuple[1]);
                    dto.setFirstName((String) tuple[2]);
                    dto.setLastName((String) tuple[3]);
                    dto.setCourseCode((String) tuple[4]);
                    dto.setCourseName((String) tuple[5]);
                    dto.setSemesterNo((int) tuple[6]);
                    dto.setAcademicyear((String) tuple[7]);
                    dto.setRoom((String) tuple[8]);
                    dto.setSubjectCode((String) tuple[9]);
                    dto.setSubjectId((int) tuple[10]);
                    dto.setAvgGrade((Float) tuple[11]);
                    return dto;
                })
                .getResultList();

        return enrollmentDtos;

    }

    public String enrollToACourse(RegisterCourseDto dto) {
        EntityTransaction trans = entityManager.getTransaction();
        try {
            trans.begin();

            // tìm thông tin sinh viên
            AcademicDetails student = entityManager.createQuery(
                    "SELECT ac FROM AcademicDetails ac WHERE ac.studentCode = :studentCode", AcademicDetails.class)
                    .setParameter("studentCode", dto.getStudentCode())
                    .getSingleResult();
            // không tìm thấy sinh viên
            if (student == null) {
                return "Không tìm thấy sinh viên trong hệ thống";
            }
            // sinh viên đã đăng ký trước đó
            if (isStudentEnrolledInCourse(student.getId(), dto.getCourseId())) {
                return "Sinh viên đã đăng ký lớp học phần này";
            } else {
                Course course = entityManager.find(Course.class, dto.getCourseId());
                if (student != null && course != null) {
                    // tạo đăng ký mới
                    Enrollment enrollment = new Enrollment();
                    enrollment.setUser(student);
                    enrollment.setCourse(course);
                    List<Assessment> assessments = course.getSubject().getAssessments();
                    List<AssessmentValue> assessmentValues = new LinkedList<AssessmentValue>();
                    // khởi tạo các cột điểm mặc định là -1
                    for (var assesment : assessments) {
                        var value = new org.dev.iuh.server.entities.AssessmentValue();
                        value.setAssessment(assesment);
                        value.setEnrollment(enrollment);
                        value.setValue(-1);
                        assessmentValues.add(value);
                    }
                    enrollment.setComponentScoreValues(assessmentValues);
                    entityManager.persist(enrollment);
                    // entityManager.persist(assessmentValues);
                } else {
                    return "Không tồn tại sinh viên hoặc khoá học";
                }
            }

            trans.commit();
            return "OK";
        } catch (Exception e) {
            if (trans.isActive()) {
                trans.rollback();
            }
            e.printStackTrace();
            if (e instanceof NoResultException) {
                return "Không tìm thấy sinh viên mã: " + dto.getStudentCode();
            }

        }
        // return false;
        return "Đã có lỗi xảy ra";
    }

    public boolean isStudentEnrolledInCourse(int studentId, int courseId) {
        return entityManager.createQuery(
                "SELECT COUNT(e) FROM Enrollment e WHERE e.user.id = :studentId AND e.course.id = :courseId",
                Long.class)
                .setParameter("studentId", studentId)
                .setParameter("courseId", courseId)
                .getSingleResult() > 0;
    }

    @SuppressWarnings("unchecked")
    public List<BasicTranscript> getBasicTranscript(int courseId) {
        String strSql = "SELECT u.first_name, u.last_name, ac.student_code, e.avg_grade " +
                "FROM academic_details ac " +
                "JOIN user u ON ac.student_id = u.id " +
                "JOIN enrollment e ON e.student_id = ac.id " +
                "JOIN course c ON e.course_id = c.id " +
                "WHERE c.id = :courseId " +
                "ORDER BY e.avg_grade DESC, u.last_name ASC";

        Query query = entityManager.createNativeQuery(strSql, BasicTranscript.class);
        query.setParameter("courseId", courseId);
        return query.getResultList();
    }
}
