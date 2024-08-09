package org.dev.iuh.client.dto;

import javax.swing.DefaultComboBoxModel;

import org.dev.iuh.common.dto.CourseOptionDto;

public class CbxCourseModel extends DefaultComboBoxModel<CourseOptionDto>{
	private static final long serialVersionUID = 16666666666L;

	public CbxCourseModel(CourseOptionDto[] items) {
        super(items);
    }
}
