package org.dev.iuh.server.controllers;

import java.util.List;

import org.dev.iuh.common.dto.GeneralMessage;
import org.dev.iuh.common.dto.LoginRequestDto;
import org.dev.iuh.server.dto.StudentResponseDto;
import org.dev.iuh.server.services.StudentService;

import jakarta.persistence.EntityManager;

public class UserController {
    private String requestCode;
    private EntityManager entityManager;
	private StudentService studentService;

    public UserController() {}

    public UserController(String requestCode, EntityManager entityManager) {
        this.requestCode = requestCode;
        this.entityManager = entityManager;
        this.studentService = new StudentService(this.entityManager);
    }

    public GeneralMessage<List<StudentResponseDto>> getListStudent() {
        var result = studentService.findAllStudents();
        return GeneralMessage.createSuccessMessage(result, this.requestCode);
    }

    public String getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(String requestCode) {
        this.requestCode = requestCode;
    }

    public GeneralMessage<String> login(LoginRequestDto dto) {
        return GeneralMessage.createSuccessMessage(studentService.login(dto), requestCode);
    }

    public GeneralMessage<Boolean> addNewStudent(StudentResponseDto dto) {
        return GeneralMessage.createSuccessMessage(studentService.addNewStudent(dto), requestCode);
    }

    public GeneralMessage<Boolean> editStudent(StudentResponseDto dto) {
        return GeneralMessage.createSuccessMessage(studentService.updateStudent(dto), requestCode);
    }

    public GeneralMessage<Boolean> deleteStudent(StudentResponseDto dto) {
        return GeneralMessage.createSuccessMessage(studentService.deleteStudent(dto), requestCode);
    }
}
