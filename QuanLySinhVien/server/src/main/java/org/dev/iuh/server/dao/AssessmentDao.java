package org.dev.iuh.server.dao;

import java.util.List;

import org.dev.iuh.common.dto.AssessmentDto;

public interface AssessmentDao {
    List<AssessmentDto> getAssessments(int subjectId);
}
