package org.dev.iuh.client.ui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.dev.iuh.client.dto.CbxCourseModel;
import org.dev.iuh.client.services.RequestMaker;
import org.dev.iuh.common.dto.AssessmentDto;
import org.dev.iuh.common.dto.CourseOptionDto;
import org.dev.iuh.common.dto.EnrollmentDto;
import org.dev.iuh.common.dto.EnrollmentEdit;

import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;




public class GradeManagement extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField txtMSSV;
	private JTextField txtCourseName;
	private JTextField txtFirstName;
	private JTextField txtLastName;
	private static final String[] GRADE_COLS_NAME = { "ID", "Tên cột điểm", "Trọng số (%)", "Điểm" };
	private static final String[] ENROLLMENT_COLS_NAME = { "ID", "Tên Khoá Học", "MSSV", "Họ", "Tên", "Mã lớp học phần",
			"Kỳ học", "Năm học", "Phòng học", "Mã môn học", "ID môn học", "Trung bình" };
	private JTable tblEditGrade;
	private JTable tblEnrollment;
	private JTextField txtAcademicYear;
	private JTextField txtSemesterNo;
	private JTextField txtRoom;
	private JTextField txtEnrollmentId;
	private JTextField txtCourseCode;
	private JTextField txtSubjectCode;
	private JButton btnUpdateGrade;
	private JLabel txtAvgGrade10;
	private JComboBox<CourseOptionDto> selectCourse;
	private CourseOptionDto[] courseDtos;

	/**
	 * Create the panel.
	 */

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

	private void hideColumns(JTable tbl, int startIdx, int colsLength) {
		for (int i = startIdx; i < colsLength; i++) {

			tbl.getColumnModel().getColumn(i).setMaxWidth(0);
			tbl.getColumnModel().getColumn(i).setMinWidth(0);
			tbl.getColumnModel().getColumn(i).setPreferredWidth(0);
		}
	}

	public void loadEnrollments(List<EnrollmentDto> dtos) {
		List<Object[]> objects = new ArrayList<Object[]>();
		for (EnrollmentDto dto : dtos) {
			objects.add(new Object[] { dto.getId(), dto.getCourseName(), dto.getStudentCode(), dto.getFirstName(),
					dto.getLastName(), dto.getCourseCode(), dto.getSemesterNo(), dto.getAcademicyear(), dto.getRoom(),
					dto.getSubjectCode(), dto.getSubjectId(), dto.getAvgGrade() });
		}
		renderTableUI(tblEnrollment, objects.toArray(new Object[objects.size()][]));
		clearFormData();
	}

	public void loadAssessments(List<AssessmentDto> dtos) {
		List<Object[]> objects = new ArrayList<Object[]>();
		for (AssessmentDto dto : dtos) {
			objects.add(new Object[] { dto.getAssessmentValueId(), dto.getName(), dto.getWeight(), dto.getValue() });
		}
		renderTableUI(tblEditGrade, objects.toArray(new Object[objects.size()][]));
	}

	public void render() {
		removeAll();
		setLayout(null);

		JLabel lblQunLim = new JLabel("Quản lý điểm");
		lblQunLim.setHorizontalAlignment(SwingConstants.CENTER);
		lblQunLim.setForeground(new Color(56, 114, 250));
		lblQunLim.setFont(new Font("Segoe UI", Font.BOLD, 22));
		lblQunLim.setBackground(new Color(56, 114, 250));
		lblQunLim.setBounds(0, 0, 1168, 40);
		add(lblQunLim);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Th\u00F4ng tin ghi danh", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(10, 80, 627, 285);
		add(panel);
		panel.setLayout(null);

		JLabel lblH = new JLabel("Họ");
		lblH.setBounds(329, 46, 75, 30);
		panel.add(lblH);

		txtFirstName = new JTextField();
		txtFirstName.setBounds(403, 47, 200, 30);
		panel.add(txtFirstName);
		txtFirstName.setColumns(10);

		JLabel lblTn = new JLabel("Tên");
		lblTn.setBounds(329, 88, 75, 30);
		panel.add(lblTn);

		txtLastName = new JTextField();
		txtLastName.setBounds(403, 89, 200, 30);
		panel.add(txtLastName);
		txtLastName.setColumns(10);

		JLabel lblKHc_1 = new JLabel("Kỳ Học");
		lblKHc_1.setBounds(329, 129, 75, 30);
		panel.add(lblKHc_1);

		txtAcademicYear = new JTextField();
		txtAcademicYear.setColumns(10);
		txtAcademicYear.setBounds(403, 171, 200, 30);
		panel.add(txtAcademicYear);

		JLabel lblNmHc = new JLabel("Năm học");
		lblNmHc.setBounds(329, 170, 75, 30);
		panel.add(lblNmHc);

		txtSemesterNo = new JTextField();
		txtSemesterNo.setColumns(10);
		txtSemesterNo.setBounds(403, 130, 200, 30);
		panel.add(txtSemesterNo);

		txtCourseName = new JTextField();
		txtCourseName.setBounds(90, 222, 200, 30);
		panel.add(txtCourseName);
		txtCourseName.setColumns(10);

		JLabel lblLpHcPhn = new JLabel("Lớp Học Phần");
		lblLpHcPhn.setBounds(10, 221, 75, 30);
		panel.add(lblLpHcPhn);

		JLabel lblNewLabel = new JLabel("MSSV");
		lblNewLabel.setBounds(10, 79, 75, 30);
		panel.add(lblNewLabel);

		txtMSSV = new JTextField();
		txtMSSV.setBounds(90, 89, 200, 30);
		panel.add(txtMSSV);
		txtMSSV.setColumns(10);

		txtRoom = new JTextField();
		txtRoom.setColumns(10);
		txtRoom.setBounds(403, 222, 200, 30);
		panel.add(txtRoom);

		JLabel lblPhngHc = new JLabel("Phòng học");
		lblPhngHc.setBounds(329, 222, 75, 30);
		panel.add(lblPhngHc);

		JLabel lblUd = new JLabel("ID");
		lblUd.setBounds(10, 46, 75, 30);
		panel.add(lblUd);

		txtEnrollmentId = new JTextField();
		txtEnrollmentId.setColumns(10);
		txtEnrollmentId.setBounds(90, 47, 200, 30);
		panel.add(txtEnrollmentId);

		JLabel lblMLp = new JLabel("Mã Lớp");
		lblMLp.setBounds(10, 173, 75, 30);
		panel.add(lblMLp);

		txtCourseCode = new JTextField();
		txtCourseCode.setColumns(10);
		txtCourseCode.setBounds(90, 174, 200, 30);
		panel.add(txtCourseCode);

		JLabel lblMMnHc = new JLabel("Mã môn học");
		lblMMnHc.setBounds(10, 120, 75, 30);
		panel.add(lblMMnHc);

		txtSubjectCode = new JTextField();
		txtSubjectCode.setColumns(10);
		txtSubjectCode.setBounds(90, 130, 200, 30);
		panel.add(txtSubjectCode);
		DefaultTableModel gradeEditModel = new DefaultTableModel(0, 0);
		gradeEditModel.setColumnIdentifiers(GRADE_COLS_NAME);

		tblEnrollment = new JTable();
		DefaultTableModel enrollmentModel = new DefaultTableModel(0, 0);
		enrollmentModel.setColumnIdentifiers(ENROLLMENT_COLS_NAME);
		tblEnrollment.setModel(enrollmentModel);
		JScrollPane listEnrollmentPane = new JScrollPane(tblEnrollment);
		listEnrollmentPane.setBounds(640, 115, 528, 530);
		add(listEnrollmentPane);

		JPanel panel_3 = new JPanel();
		panel_3.setBounds(10, 375, 624, 275);
		add(panel_3);
		panel_3.setBorder(new TitledBorder(null, "Nhập liệu điểm", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panel_3.setLayout(null);

		btnUpdateGrade = new JButton("Cập nhật");
		btnUpdateGrade.setBounds(514, 222, 100, 30);
		panel_3.add(btnUpdateGrade);

		tblEditGrade = new JTable();
		tblEditGrade.setModel(gradeEditModel);
		JScrollPane gradePane = new JScrollPane(tblEditGrade);
		gradePane.setBounds(10, 22, 604, 150);
		panel_3.add(gradePane);

		JLabel lblNewLabel_1 = new JLabel("Điểm trung bình hệ 10:");
		lblNewLabel_1.setForeground(new Color(56, 114, 250));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.ITALIC, 11));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setBounds(363, 182, 200, 30);
		panel_3.add(lblNewLabel_1);

		txtAvgGrade10 = new JLabel("8.5");
		txtAvgGrade10.setForeground(new Color(56, 114, 250));
		txtAvgGrade10.setBounds(573, 182, 41, 30);
		panel_3.add(txtAvgGrade10);

		hideColumns(tblEnrollment, 5, ENROLLMENT_COLS_NAME.length);
		courseDtos = new CourseOptionDto[1];
		CbxCourseModel courseModel = new CbxCourseModel(courseDtos);
		selectCourse = new JComboBox<CourseOptionDto>(courseModel);
		selectCourse.setBounds(10, 40, 250, 30);
		selectCourse.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				var selectedCourse = (CourseOptionDto) e.getItem();

				if (selectedCourse.getId() != -1 && e.getStateChange() == ItemEvent.SELECTED) {
					try {
						RequestMaker.createRequest("FILTER_ENROLLMENT_BY_COURSE", selectedCourse.getId());
						return;
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}

			}
		});
		add(selectCourse);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"In \u1EA5n", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(647, 51, 435, 53);
		add(panel_1);

		
		
		JButton btnXuatExcel = new JButton("Xuất Excel lớp học phần");
		btnXuatExcel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exportToExcel();
			}
		});
		panel_1.setLayout(null);
		btnXuatExcel.setBounds(22, 19, 175, 23);
		panel_1.add(btnXuatExcel);
		
		JButton btnXutExcelTheo = new JButton("Xuất Excel theo sinh viên");
		btnXutExcelTheo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exportToExcelSV();
			}
		});
		btnXutExcelTheo.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnXutExcelTheo.setBounds(234, 19, 175, 23);
		panel_1.add(btnXutExcelTheo);

		/**
		 * --------------------------------------------------------------- ACTION
		 * LISTENER ---------------------------------------------------
		 */
		tblEnrollment.addMouseListener(new MouseAdapter() {
			// dto.getId(), dto.getCourseName(), dto.getStudentCode(), dto.getFirstName(),
			// dto.getLastName(), dto.getCourseCode(),
			// dto.getSemesterNo(), dto.getAcademicyear(), dto.getRoom()
			@Override
			public void mouseClicked(MouseEvent e) {
				DefaultTableModel model = (DefaultTableModel) tblEnrollment.getModel();
				int selectedRow = tblEnrollment.getSelectedRow();
				if (selectedRow >= 0) {
					txtEnrollmentId.setText("" + model.getValueAt(selectedRow, 0));
					txtCourseName.setText((String) model.getValueAt(selectedRow, 1));
					txtMSSV.setText((String) model.getValueAt(selectedRow, 2));
					txtFirstName.setText((String) model.getValueAt(selectedRow, 3));
					txtLastName.setText((String) model.getValueAt(selectedRow, 4));
					txtCourseCode.setText((String) model.getValueAt(selectedRow, 5));
					txtSemesterNo.setText("" + model.getValueAt(selectedRow, 6));
					txtAcademicYear.setText((String) model.getValueAt(selectedRow, 7));
					txtRoom.setText((String) model.getValueAt(selectedRow, 8));
					txtSubjectCode.setText((String) model.getValueAt(selectedRow, 9));
					txtAvgGrade10.setText("" + model.getValueAt(selectedRow, 11));
					try {
						RequestMaker.createRequest("GET_ASSESSMENT_BY_ENROLLMENT_ID",
								(int) model.getValueAt(selectedRow, 0));
					} catch (Exception e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, "Vui lòng khởi động server", "Lỗi",
								JOptionPane.ERROR_MESSAGE);
					}

				}
			}
		});

		btnUpdateGrade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					var enrollmentEdit = new EnrollmentEdit();
					enrollmentEdit.setEnrollmentId(Integer.parseInt(txtEnrollmentId.getText()));
					var assessments = new LinkedList<EnrollmentEdit.AssessmentValue>();
					for (int i = 0; i < tblEditGrade.getRowCount(); i++) { // Loop through the rows
						var grade = new EnrollmentEdit.AssessmentValue();
						grade.setId((int) tblEditGrade.getValueAt(i, 0));
						double temp = Double.parseDouble(tblEditGrade.getValueAt(i, 3).toString());
						grade.setValue(temp);
						assessments.add(grade);
					}
					enrollmentEdit.setAssesments(assessments);
					// tính điểm trung bình
					float sum = 0f;
					int totalFactor = 0;
					for (int i = 0; i < tblEditGrade.getRowCount(); i++) { // Loop through the rows
						int factor = (int) tblEditGrade.getValueAt(i, 2);
						float grade = Float.parseFloat(tblEditGrade.getValueAt(i, 3).toString());
						if (grade != -1 && (grade < 0 || grade > 10))
							throw new IllegalArgumentException("Điểm nhập vào trong phạm vi từ 0 - 10");
						if (grade >= 0) {
							sum += factor * grade;
							totalFactor += factor;
						}
					}
					float avg = sum / (float) totalFactor;
					enrollmentEdit.setAvgGrade(avg);
					RequestMaker.createRequest("UPDATE_GRADE", enrollmentEdit);
				} catch (Exception e1) {
					e1.printStackTrace();
					if (e1 instanceof ClassCastException) {
						JOptionPane.showMessageDialog(null, "Điểm nhập vào phải là số", "Lỗi",
								JOptionPane.ERROR_MESSAGE);
					} else if (e1 instanceof IllegalArgumentException) {
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Lỗi",
								JOptionPane.ERROR_MESSAGE);
					}
					else {
						JOptionPane.showMessageDialog(null, "Vui lòng khởi động server", "Lỗi",
								JOptionPane.ERROR_MESSAGE);
					}

				}
			}
		});
	}
	
	private void exportToExcel() {
	    try {
	        Workbook workbook = new XSSFWorkbook();
	        Sheet sheet = workbook.createSheet("Student Data");
	        DefaultTableModel model = (DefaultTableModel) tblEnrollment.getModel();
	        CourseOptionDto selectedCourse = (CourseOptionDto) selectCourse.getSelectedItem();

	        // Kiểm tra xem lựa chọn có hợp lệ không
	        if (selectedCourse == null || selectedCourse.getId() == -1) {
	            JOptionPane.showMessageDialog(null, "Vui lòng chọn một lớp học phần", "Lỗi", JOptionPane.ERROR_MESSAGE);
	            return;
	        }
	        // Add column headers
	        Row headerRow = sheet.createRow(0);
	        for (int col = 0; col < model.getColumnCount(); col++) {
	            headerRow.createCell(col).setCellValue(model.getColumnName(col));
	        }

	        // Add data rows
	        for (int row = 0; row < model.getRowCount(); row++) {
	            Row excelRow = sheet.createRow(row + 1);
	            for (int col = 0; col < model.getColumnCount(); col++) {
	                Object value = model.getValueAt(row, col);
	                if (value != null) {
	                    excelRow.createCell(col).setCellValue(value.toString());
	                } else {
	                    excelRow.createCell(col).setCellValue("");
	                }
	            }
	        }

	        // Save the workbook to a file
	        try (FileOutputStream fileOut = new FileOutputStream("student_data_grade.xlsx")) {
	            workbook.write(fileOut);
	            JOptionPane.showMessageDialog(this, "Dữ liệu đã được xuất thành công vào file Excel.", "Xuất Excel",
	                    JOptionPane.INFORMATION_MESSAGE);
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi xuất dữ liệu sang Excel.", "Lỗi",
	                JOptionPane.ERROR_MESSAGE);
	    }
	}
	private void exportToExcelSV() {
		try {
			Workbook workbook = new XSSFWorkbook();
			Sheet sheet = workbook.createSheet("Student Data");
			DefaultTableModel model = (DefaultTableModel) tblEditGrade.getModel();
			CourseOptionDto selectedCourse = (CourseOptionDto) selectCourse.getSelectedItem();

			// Kiểm tra xem lựa chọn có hợp lệ không
			if (selectedCourse == null || selectedCourse.getId() == -1) {
				JOptionPane.showMessageDialog(null, "Vui lòng chọn một lớp học phần", "Lỗi", JOptionPane.ERROR_MESSAGE);
				return;
			}
			// Add column headers
			Row headerRow = sheet.createRow(0);
			for (int col = 0; col < model.getColumnCount(); col++) {
				headerRow.createCell(col).setCellValue(model.getColumnName(col));
			}

			// Add data rows
			for (int row = 0; row < model.getRowCount(); row++) {
				Row excelRow = sheet.createRow(row + 1);
				for (int col = 0; col < model.getColumnCount(); col++) {
					excelRow.createCell(col).setCellValue(model.getValueAt(row, col).toString());
				}
			}

			try (FileOutputStream fileOut = new FileOutputStream("student_data_grade_SV.xlsx")) {
				workbook.write(fileOut);
				JOptionPane.showMessageDialog(this, "Dữ liệu đã được xuất thành công vào file Excel.", "Xuất Excel",
						JOptionPane.INFORMATION_MESSAGE);
			}

		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi xuất dữ liệu sang Excel.", "Lỗi",
					JOptionPane.ERROR_MESSAGE);
		}
	}



	private void clearFormData() {
		txtEnrollmentId.setText("");
		txtAcademicYear.setText("");
		txtSubjectCode.setText("");
		txtMSSV.setText("");
		txtSubjectCode.setText("");
		txtCourseCode.setText("");
		txtCourseName.setText("");
		txtFirstName.setText("");
		txtLastName.setText("");
		txtSemesterNo.setText("");
		txtRoom.setText("");
	}

	public GradeManagement() {
		render();
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
