package org.dev.iuh.client.ui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingConstants;
import javax.swing.JComboBox;

import org.dev.iuh.client.dto.CbxCourseModel;
import org.dev.iuh.client.services.RequestMaker;
import org.dev.iuh.common.dto.CourseDto;
import org.dev.iuh.common.dto.CourseOptionDto;
import org.dev.iuh.common.dto.EnrollmentDto;
import org.dev.iuh.common.dto.RegisterCourseDto;

import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class CourseManagement extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField txtCourseId;
	private JTextField txtSubjectCode;
	private JTextField txtCourseCode;
	private JTextField txtCourseName;
	private JTextField txtSemesterNo;
	private JTextField txtAcademicYear;
	private JTextField txtRoom;
	private JTextField txtStudentCode;
	private JTable tblEnrollment;
	private JButton btnRegisterCourse;
	private JComboBox<CourseOptionDto> selectCourse;
	private CourseOptionDto[] courseDtos;
	private int selectedCourseId;
	private static final String[] ENROLLMENT_COLS_NAME = { "ID", "Tên Khoá Học", "MSSV", "Họ", "Tên", "Mã lớp học phần",
			"Kỳ học", "Năm học", "Phòng học", "Mã môn học", "ID môn học", "Trung bình" };

	/**
	 * Create the panel.
	 */
	public CourseManagement() {

		render();
	}

	public void render() {
		selectedCourseId = -1;
		removeAll();
		setLayout(null);

		JLabel lblQunLKho = new JLabel("Đăng ký khoá học");
		lblQunLKho.setHorizontalAlignment(SwingConstants.CENTER);
		lblQunLKho.setForeground(new Color(56, 114, 250));
		lblQunLKho.setFont(new Font("Segoe UI", Font.BOLD, 22));
		lblQunLKho.setBackground(new Color(56, 114, 250));
		lblQunLKho.setBounds(0, 11, 1151, 40);
		add(lblQunLKho);

		JPanel panelSearchStudent = new JPanel();
		panelSearchStudent.setBounds(10, 64, 545, 573);
		add(panelSearchStudent);
		panelSearchStudent.setLayout(null);

//		courseDtos = ApplicationContext.getInstance().getMyCourses().stream()
//				.map(course -> {
//					return new CourseOptionDto(course.getCourseId(), course.getCourseName(), course.getCourseCode());
//				}).toArray(CourseOptionDto[]::new);
		courseDtos = new CourseOptionDto[1];
		CbxCourseModel courseModel = new CbxCourseModel(courseDtos);


		selectCourse = new JComboBox<CourseOptionDto>(courseModel);
		selectCourse.setBounds(10, 11, 250, 30);
		panelSearchStudent.add(selectCourse);

		selectCourse.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				var selectedCourse = (CourseOptionDto) e.getItem();
				if (selectedCourse.getId() != -1 && e.getStateChange() == ItemEvent.SELECTED) {
					selectedCourseId = selectedCourse.getId();
					try {
						RequestMaker.createRequest("GET_COURSE_INFO", selectedCourse.getId());
						RequestMaker.createRequest("GET_COURSE_ENROLLMENT", selectedCourse.getId());
						return;
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}

			}
		});

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Th\u00F4ng tin l\u1EDBp h\u1ECDc ph\u1EA7n", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel.setBounds(10, 67, 525, 373);
		panelSearchStudent.add(panel);
		panel.setLayout(null);

		JLabel lblUd = new JLabel("Id Lớp học phần");
		lblUd.setBounds(20, 28, 103, 30);
		panel.add(lblUd);

		txtCourseId = new JTextField();
		txtCourseId.setColumns(10);
		txtCourseId.setBounds(121, 28, 250, 30);
		panel.add(txtCourseId);

		JLabel lblMMnHc = new JLabel("Mã môn học");
		lblMMnHc.setBounds(20, 69, 103, 30);
		panel.add(lblMMnHc);

		txtSubjectCode = new JTextField();
		txtSubjectCode.setColumns(10);
		txtSubjectCode.setBounds(121, 69, 250, 30);
		panel.add(txtSubjectCode);

		JLabel lblMLp = new JLabel("Mã Lớp");
		lblMLp.setBounds(20, 122, 103, 30);
		panel.add(lblMLp);

		txtCourseCode = new JTextField();
		txtCourseCode.setColumns(10);
		txtCourseCode.setBounds(121, 122, 250, 30);
		panel.add(txtCourseCode);

		JLabel lblLpHcPhn = new JLabel("Lớp Học Phần");
		lblLpHcPhn.setBounds(20, 171, 103, 30);
		panel.add(lblLpHcPhn);

		txtCourseName = new JTextField();
		txtCourseName.setColumns(10);
		txtCourseName.setBounds(121, 171, 250, 30);
		panel.add(txtCourseName);

		JLabel lblKHc_1 = new JLabel("Kỳ Học");
		lblKHc_1.setBounds(20, 212, 103, 30);
		panel.add(lblKHc_1);

		txtSemesterNo = new JTextField();
		txtSemesterNo.setColumns(10);
		txtSemesterNo.setBounds(121, 212, 250, 30);
		panel.add(txtSemesterNo);

		JLabel lblNmHc = new JLabel("Năm học");
		lblNmHc.setBounds(20, 253, 103, 30);
		panel.add(lblNmHc);

		txtAcademicYear = new JTextField();
		txtAcademicYear.setColumns(10);
		txtAcademicYear.setBounds(121, 253, 250, 30);
		panel.add(txtAcademicYear);

		JLabel lblPhngHc = new JLabel("Phòng học");
		lblPhngHc.setBounds(20, 305, 103, 30);
		panel.add(lblPhngHc);

		txtRoom = new JTextField();
		txtRoom.setColumns(10);
		txtRoom.setBounds(121, 305, 250, 30);
		panel.add(txtRoom);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(
				new TitledBorder(null, "Ghi danh sinh vi\u00EAn", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 451, 525, 85);
		panelSearchStudent.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblNewLabel = new JLabel("Mã số sinh viên");
		lblNewLabel.setBounds(10, 38, 100, 30);
		panel_1.add(lblNewLabel);

		txtStudentCode = new JTextField();
		txtStudentCode.setBounds(120, 38, 175, 30);
		panel_1.add(txtStudentCode);
		txtStudentCode.setColumns(10);

		btnRegisterCourse = new JButton("Ghi danh vào lớp");
		btnRegisterCourse.setBounds(305, 38, 150, 30);
		panel_1.add(btnRegisterCourse);
		btnRegisterCourse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String studentCode = txtStudentCode.getText();
				if (selectedCourseId == -1) {
					JOptionPane.showMessageDialog(null, "Cần chọn lớp học phần trước khi thực hiện thao tác này", "Lưu ý",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				
				if (studentCode.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Vui lòng nhập mã sinh viên cần ghi danh", "Lưu ý",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				
				var registerDto = new RegisterCourseDto();
				registerDto.setCourseId(selectedCourseId);
				registerDto.setStudentCode(studentCode);
				try {
					RequestMaker.createRequest("REGISTER_TO_COURSE", registerDto);
					return;
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		JPanel panelSearchStudent_1 = new JPanel();
		panelSearchStudent_1.setBorder(new TitledBorder(null, "Danh s\u00E1ch sinh vi\u00EAn trong l\u1EDBp",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelSearchStudent_1.setBounds(560, 64, 586, 573);
		add(panelSearchStudent_1);
		panelSearchStudent_1.setLayout(null);

		tblEnrollment = new JTable();
		DefaultTableModel enrollmentModel = new DefaultTableModel(0, 0);
		enrollmentModel.setColumnIdentifiers(ENROLLMENT_COLS_NAME);
		tblEnrollment.setModel(enrollmentModel);
		JScrollPane listEnrollmentPane = new JScrollPane(tblEnrollment);
		listEnrollmentPane.setBounds(10, 29, 566, 532);
		panelSearchStudent_1.add(listEnrollmentPane);

		hideColumns(tblEnrollment, 5, ENROLLMENT_COLS_NAME.length);

	}

	public void loadCourseInfo(CourseDto courseDto) {
		txtCourseId.setText("" + courseDto.getCourseId());
		txtCourseCode.setText(courseDto.getCourseCode());
		txtCourseName.setText(courseDto.getCourseName());
		txtSubjectCode.setText(courseDto.getSubjectCode());
		txtRoom.setText(courseDto.getRoom());
		txtAcademicYear.setText(courseDto.getAcademicYear());
		txtSemesterNo.setText("" + courseDto.getSemesterNo());
	}

	public void loadEnrollments(List<EnrollmentDto> dtos) {
		List<Object[]> objects = new ArrayList<Object[]>();
		
		for (EnrollmentDto dto : dtos) {
			objects.add(new Object[] { dto.getId(), dto.getCourseName(), dto.getStudentCode(), dto.getFirstName(),
					dto.getLastName(), dto.getCourseCode(), dto.getSemesterNo(), dto.getAcademicyear(), dto.getRoom(),
					dto.getSubjectCode(), dto.getSubjectId(), dto.getAvgGrade() });
		}
		renderTableUI(tblEnrollment, objects.toArray(new Object[objects.size()][]));
	}

	public void renderTableUI(JTable table, Object[][] data) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
		if (data == null) {
			return;
		}
		for (Object[] r : data) {
			model.addRow(r);
		}
	}

	// do dữ liệu trả về có rất nhiều cột, ta chỉ muốn hiện thị 1 số cột dữ liệu
	// trên bảng
	private void hideColumns(JTable tbl, int startIdx, int colsLength) {
		for (int i = startIdx; i < colsLength; i++) {

			tbl.getColumnModel().getColumn(i).setMaxWidth(0);
			tbl.getColumnModel().getColumn(i).setMinWidth(0);
			tbl.getColumnModel().getColumn(i).setPreferredWidth(0);
		}
	}
	
	
	public void doAfterAddSuccessfully() throws Exception {
//		clearForm();
		RequestMaker.createRequest("GET_COURSE_ENROLLMENT", selectedCourseId);
		JOptionPane.showMessageDialog(this, "Đã thêm sinh viên vào lớp học phần", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
	}

	public CourseOptionDto[] getCourseDtos() {
		return courseDtos;
	}

	public void setCourseDtos(CourseOptionDto[] courseDtos) {
		this.courseDtos = courseDtos;
		CbxCourseModel courseModel = new CbxCourseModel(courseDtos);
		selectCourse.setModel(courseModel);
	}
	
	
}
