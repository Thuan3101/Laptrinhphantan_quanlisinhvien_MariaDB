package org.dev.iuh.server.controllers;

import org.dev.iuh.common.dto.AssessmentDto;
import org.dev.iuh.common.dto.EnrollmentEdit;
import org.dev.iuh.common.dto.GeneralMessage;
import org.dev.iuh.common.dto.OverviewStatistics;
import org.dev.iuh.server.services.AssessmentService;

import jakarta.persistence.EntityManager;

import java.util.*;

public class AssessmentController {
    private String requestCode;
    private EntityManager entityManager;
	private AssessmentService assessmentService;

    public AssessmentController() {}

    public AssessmentController(String requestCode, EntityManager entityManager) {
        this.requestCode = requestCode;
        this.entityManager = entityManager;
        this.assessmentService = new AssessmentService(this.entityManager);
    }

    public String getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(String requestCode) {
        this.requestCode = requestCode;
    }

    public GeneralMessage<List<AssessmentDto>> loadAssessments(int enrollmentId) {
        return GeneralMessage.createSuccessMessage(assessmentService.getAssessments(enrollmentId), requestCode);
    }

    public GeneralMessage<Boolean> updateGrade(EnrollmentEdit dto) {
        return GeneralMessage.createSuccessMessage(assessmentService.updateGrade(dto), requestCode);
    }

    public GeneralMessage<OverviewStatistics> getOverviewStats(int courseId) {
        return GeneralMessage.createSuccessMessage(assessmentService.getOverviewStatistic(courseId), requestCode);
    } 

    public GeneralMessage<Map<String, Integer>> getPointSpectrumByCourse(int courseId) {
        return GeneralMessage.createSuccessMessage(assessmentService.getPointSpectrum(courseId), requestCode);
    }

    public GeneralMessage<Map<String, Integer>> getGenderRatioByCourse(int courseId) {
        return GeneralMessage.createSuccessMessage(assessmentService.getGenderRatio(courseId), requestCode);
    }
}
