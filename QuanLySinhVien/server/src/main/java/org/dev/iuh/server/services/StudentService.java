package org.dev.iuh.server.services;

import java.util.List;

import org.dev.iuh.common.dto.LoginRequestDto;
import org.dev.iuh.common.enums.UserRole;
import org.dev.iuh.server.context.ServerContext;
import org.dev.iuh.server.dao.UserDao;
import org.dev.iuh.server.dto.StudentResponseDto;
import org.dev.iuh.server.entities.AcademicDetails;
import org.dev.iuh.server.entities.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class StudentService implements UserDao {
    private EntityManager entityManager;

    public StudentService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<StudentResponseDto> findAllStudents() {
        return entityManager.createNamedQuery("AcademicDetails.findAll", StudentResponseDto.class).getResultList();
    }

    @Override
    public boolean addNewStudent(StudentResponseDto dto) {
        EntityTransaction trans = entityManager.getTransaction();
        try {
            trans.begin();

            var user = new User();
            user.setAddress(dto.getAddress());
            user.setDob(dto.getDob());
            user.setEmail(dto.getEmail());
            user.setGender(dto.getGender());
            user.setRole(UserRole.STUDENT);
            user.setFirstName(dto.getFirstName());
            user.setLastName(dto.getLastName());
            user.setPhoneNumber(dto.getPhoneNumber());

            var academicDetails = new AcademicDetails();
            academicDetails.setUser(user);
            academicDetails.setStudentCode(dto.getStudentCode());
            academicDetails.setFaculty(dto.getFaculty());
            academicDetails.setKlass(dto.getKlass());
            academicDetails.setAdmissionYear(dto.getAdmissionYear());
            entityManager.persist(academicDetails);

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

    @Override
    public boolean updateStudent(StudentResponseDto dto) {
        EntityTransaction trans = entityManager.getTransaction();
        try {
            trans.begin();
            AcademicDetails academicDetailsDb = entityManager.find(AcademicDetails.class, dto.getAcademicDetailsId());
            academicDetailsDb.setAdmissionYear(dto.getAdmissionYear());
            academicDetailsDb.setFaculty(dto.getFaculty());
            academicDetailsDb.setKlass(dto.getKlass());
            academicDetailsDb.setStudentCode(dto.getStudentCode());

            var user = academicDetailsDb.getUser();
            // user.setId(dto.getUserId());
            user.setAddress(dto.getAddress());
            user.setDob(dto.getDob());
            user.setEmail(dto.getEmail());
            user.setGender(dto.getGender());
            user.setRole(UserRole.STUDENT);
            user.setFirstName(dto.getFirstName());
            user.setLastName(dto.getLastName());
            user.setPhoneNumber(dto.getPhoneNumber());
            academicDetailsDb.setUser(user);

            entityManager.merge(academicDetailsDb);

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

    @Override
    public boolean deleteStudent(StudentResponseDto dto) {
        EntityTransaction trans = entityManager.getTransaction();
        try {
            trans.begin();
            AcademicDetails academicDetailsDb = entityManager.find(AcademicDetails.class, dto.getAcademicDetailsId());
            entityManager.remove(academicDetailsDb);
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

    @Override
    public String login(LoginRequestDto dto) {
        var results = entityManager.createNamedQuery("User.login", User.class)
                .setParameter("email", dto.getEmail())
                .setParameter("password", dto.getPassword())
                .getResultList();
        if (results == null || results.isEmpty()) {
            return "Email hoặc mật khẩu không chính xác";
        } else {
            var user = results.get(0);
            var role = user.getRole().name();
            if (role == UserRole.STUDENT.name()) {
                return "Bạn không có quyền truy cập vào ứng dụng";
            }
            ServerContext.getInstance().setUserId(user.getId());
            StringBuilder sb = new StringBuilder();
            sb.append("OK").append(",")
                    .append(user.getId()).append(",")
                    .append(user.getFirstName()).append(",")
                    .append(user.getLastName());
            return sb.toString();
        }
    }
}
