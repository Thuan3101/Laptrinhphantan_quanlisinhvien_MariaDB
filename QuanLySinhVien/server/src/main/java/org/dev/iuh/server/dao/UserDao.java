package org.dev.iuh.server.dao;

import java.util.List;

import org.dev.iuh.common.dto.LoginRequestDto;
import org.dev.iuh.server.dto.StudentResponseDto;

public interface UserDao {
    List<StudentResponseDto> findAllStudents();
    boolean addNewStudent(StudentResponseDto dto);
    boolean updateStudent(StudentResponseDto dto);
    boolean deleteStudent(StudentResponseDto dto);
    String login (LoginRequestDto dto);
}