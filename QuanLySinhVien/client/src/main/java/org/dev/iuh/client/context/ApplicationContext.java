package org.dev.iuh.client.context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.dev.iuh.client.ui.AuthenFrame;
import org.dev.iuh.client.ui.CourseManagement;
import org.dev.iuh.client.ui.GradeManagement;
import org.dev.iuh.client.ui.MainFrame;
import org.dev.iuh.client.ui.OverviewUI;
import org.dev.iuh.client.ui.StudentListUI;
import org.dev.iuh.common.dto.CourseDto;
/**
 * ApplicationContext: lưu trữ các đối tượng UI của ứung dụng để quản lý. Vì các đối tượng này chỉ nên là duy nhất
 * trong suốt quá trình chạy app, dùng singleton pattern
 */
public class ApplicationContext {
	private static final ApplicationContext INSTANCE = new ApplicationContext();
	private StudentListUI studentListUI;
	private OverviewUI overviewUI;
	private MainFrame mainFrame;
	private AuthenFrame authenFrame;
	private GradeManagement gradeManagementUI;
	private CourseManagement courseManagementUI;
	private List<CourseDto> myCourses;
	
	private ApplicationContext() {
        System.out.println("Created Application client context ");
        try {
        	myCourses = new ArrayList<CourseDto>();
        	authenFrame = new AuthenFrame();
			studentListUI = new StudentListUI();
			overviewUI = new OverviewUI();
			gradeManagementUI = new GradeManagement();
			courseManagementUI = new CourseManagement();
			mainFrame = new MainFrame();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

	public void initComponents () {
		try {
			myCourses = new ArrayList<CourseDto>();
			this.authenFrame = new AuthenFrame();
			this.mainFrame = new MainFrame();
			this.studentListUI = new StudentListUI();
			this.overviewUI = new OverviewUI();
			this.gradeManagementUI = new GradeManagement();
			this.courseManagementUI = new CourseManagement();
			
		}catch (Exception e) {
		}

	}
    public static ApplicationContext getInstance() {
        return INSTANCE;
    }

	public StudentListUI getStudentListUI() {
		return studentListUI;
	}

	public OverviewUI getOverviewUI() {
		return overviewUI;
	}

	public MainFrame getMainFrame() {
		return mainFrame;
	}

	public AuthenFrame getAuthenFrame() {
		return authenFrame;
	}

	public GradeManagement getGradeManagementUI() {
		return gradeManagementUI;
	}

	public CourseManagement getCourseManagementUI() {
		return courseManagementUI;
	}

	public List<CourseDto> getMyCourses() {
		return myCourses;
	}

	public void setMyCourses(List<CourseDto> myCourses) {
		this.myCourses = myCourses;
	}
	
	
}
