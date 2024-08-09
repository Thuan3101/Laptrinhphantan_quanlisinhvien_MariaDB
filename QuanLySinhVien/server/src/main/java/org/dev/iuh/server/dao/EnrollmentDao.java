package org.dev.iuh.server.dao;
import java.util.*;
import org.dev.iuh.common.dto.EnrollmentDto;

public interface EnrollmentDao { 
    List<EnrollmentDto> findEnrollments(HashMap<String, Object> searchCiteria);
}
