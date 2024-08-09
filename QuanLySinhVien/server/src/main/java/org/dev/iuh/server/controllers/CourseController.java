package org.dev.iuh.server.controllers;

import org.dev.iuh.common.dto.GeneralMessage;
import org.dev.iuh.server.dto.CourseResponseDto;
import org.dev.iuh.server.services.CourseService;

import jakarta.persistence.EntityManager;
import java.util.*;

public class CourseController {
    private String requestCode;
    private EntityManager entityManager;
    private CourseService courseService;

    public CourseController() {
    }

    public CourseController(String requestCode, EntityManager entityManager) {
        this.requestCode = requestCode;
        this.entityManager = entityManager;
        this.courseService = new CourseService(this.entityManager);
    }

    public GeneralMessage<CourseResponseDto> findCourseById(int courseId) {
        return GeneralMessage.createSuccessMessage(courseService.findCourseById(courseId), requestCode);
    }

    public GeneralMessage<List<CourseResponseDto>> findCourseByLecturerId(int lecturerId) {
        return GeneralMessage.createSuccessMessage(courseService.findCourseByLecturerId(lecturerId), requestCode);
    }

    public String getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(String requestCode) {
        this.requestCode = requestCode;
    }
}
