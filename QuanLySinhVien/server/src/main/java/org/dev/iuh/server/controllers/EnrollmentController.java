package org.dev.iuh.server.controllers;

import org.dev.iuh.common.dto.EnrollmentDto;
import org.dev.iuh.common.dto.GeneralMessage;
import org.dev.iuh.common.dto.BasicTranscript;
import org.dev.iuh.common.dto.RegisterCourseDto;
import org.dev.iuh.server.services.EnrollmentService;

import jakarta.persistence.EntityManager;
import java.util.*;

public class EnrollmentController {
    private String requestCode;
    private EntityManager entityManager;
	private EnrollmentService enrollmentService;

    public EnrollmentController() {}

    public EnrollmentController(String requestCode, EntityManager entityManager) {
        this.requestCode = requestCode;
        this.entityManager = entityManager;
        this.enrollmentService = new EnrollmentService(this.entityManager);
    }

    public GeneralMessage<List<EnrollmentDto>> loadEnrollments(HashMap<String, Object> searchCiteria ) {
        return GeneralMessage.createSuccessMessage(enrollmentService.findEnrollments(searchCiteria), requestCode);
    }


    public GeneralMessage<String> enrollToACourse(RegisterCourseDto dto) {
        return GeneralMessage.createSuccessMessage(enrollmentService.enrollToACourse(dto), requestCode);
    }

    public GeneralMessage<List<BasicTranscript>> getBasicTranscript(int courseId) {
        return GeneralMessage.createSuccessMessage(enrollmentService.getBasicTranscript(courseId), requestCode);
    }

    public String getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(String requestCode) {
        this.requestCode = requestCode;
    }
}
