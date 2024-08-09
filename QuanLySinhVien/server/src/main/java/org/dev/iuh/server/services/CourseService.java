package org.dev.iuh.server.services;

import org.dev.iuh.server.dto.CourseResponseDto;
import java.util.*;

import jakarta.persistence.EntityManager;

public class CourseService {
    private EntityManager entityManager;

    public CourseService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public CourseResponseDto findCourseById(int courseId) {
        return entityManager.createNamedQuery("Course.findById", CourseResponseDto.class)
                .setParameter("courseId", courseId)
                .getResultList().get(0);
    }

    public List<CourseResponseDto> findCourseByLecturerId(int lecturerId) {
        return entityManager.createNamedQuery("Course.findByLecturerId", CourseResponseDto.class)
                .setParameter("lecturerId", lecturerId)
                .getResultList();
    }

}
