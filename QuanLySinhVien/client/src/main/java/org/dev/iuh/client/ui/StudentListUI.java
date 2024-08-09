package org.dev.iuh.client.ui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.HeadlessException;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import org.dev.iuh.client.dto.StudentResponseDto;
import org.dev.iuh.client.services.RequestMaker;
import org.dev.iuh.common.converter.DateLabelFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.border.EtchedBorder;
import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class StudentListUI extends JPanel {

	private static final long serialVersionUID = 155555L;
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	private JTextField txtId;
	private JTextField txtFName;
	private JTextField txtLName;
	private JTextField txtPhone;
	private JTextField txtEmail;
	private JTextField txtStdCode;
	private JTextField txtAdmissionYear;
	private JTextField txtFaculty;
	private JTextField txtClass;
	private ButtonGroup genderGroup;
	private JTextArea txtAddress;
	private JRadioButton rbtnMale;
	private JRadioButton rbtnFemale;
	private JRadioButton rbtnOther;
	private int userId;

	private static final String[] COLS_NAME = { "MSSV", "Họ", "Tên", "Email", "Ngày sinh", "Số điện thoại", "Địa chỉ",
			"Id", "Lớp sinh hoạt", "Năm nhập học", "Khoa", "Giới tính", "UserId" };
	private JTable tblStudents;
	private JDatePickerImpl txtDob;
	private JDatePanelImpl datePanel;
	private JButton btnRefresh;

	private void hideColumns(JTable tbl, int startIdx, int colsLength) {
		for (int i = startIdx; i < colsLength; i++) {

			tbl.getColumnModel().getColumn(i).setMaxWidth(0);
			tbl.getColumnModel().getColumn(i).setMinWidth(0);
			tbl.getColumnModel().getColumn(i).setPreferredWidth(0);
		}
	}

	public void renderStudentForm(StudentResponseDto dto) throws ParseException {
		txtId.setText("" + dto.getAcademicDetailsId());
		txtFName.setText(dto.getFirstName());
		txtLName.setText(dto.getLastName());
		txtPhone.setText(dto.getPhoneNumber());
		txtEmail.setText(dto.getEmail());
		LocalDate today = LocalDate.now();

		txtDob.getModel().setDate(today.getYear(), today.getMonthValue() - 1, today.getDayOfMonth());
		datePanel.getModel().setDate(today.getYear(), today.getMonthValue() - 1, today.getDayOfMonth());

		txtClass.setText(dto.getKlass());
		txtAdmissionYear.setText(dto.getAdmissionYear());
		txtFaculty.setText(dto.getFaculty());
		txtStdCode.setText(dto.getStudentCode());

	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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

	public void renderStudentTable(List<StudentResponseDto> studentList) throws IOException {
		List<Object[]> objects = new ArrayList<Object[]>();
		for (StudentResponseDto dto : studentList) {
			objects.add(new Object[] { dto.getStudentCode(), dto.getFirstName(), dto.getLastName(), dto.getEmail(),
					dto.getDob().format(FORMATTER), dto.getPhoneNumber(), dto.getAcademicDetailsId(), dto.getAddress(),
					dto.getAdmissionYear(), dto.getFaculty(), dto.getKlass(), dto.getGender(), dto.getUserId() });
		}
		renderTableUI(tblStudents, objects.toArray(new Object[objects.size()][]));

	}

	public void clearForm() {
		txtAddress.setText("");
//		txtDob.setDate(new Date());
		txtEmail.setText("");
		txtFaculty.setText("");
		txtFName.setText("");
		txtLName.setText("");
		txtAdmissionYear.setText("");
		txtAddress.setText("");
		txtStdCode.setText("");
		txtPhone.setText("");
		txtId.setText("");
		txtStdCode.setEditable(true);
	}

	public void doAfterAddSuccessfully() throws Exception {
		clearForm();
		RequestMaker.createRequest("VIEW_LIST_STUDENT", "");
		JOptionPane.showMessageDialog(this, "Đã thêm mới sinh viên", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

	}

	public void doAfterEditSuccessfully() throws Exception {
		clearForm();
		RequestMaker.createRequest("VIEW_LIST_STUDENT", "");
		JOptionPane.showMessageDialog(this, "Đã cập nhật thông tin sinh viên", "Thông báo",
				JOptionPane.INFORMATION_MESSAGE);
	}

	public void doAfterDeleteSuccessfully() throws Exception {
		clearForm();
		RequestMaker.createRequest("VIEW_LIST_STUDENT", "");
		JOptionPane.showMessageDialog(this, "Đã xoá thông tin sinh viên", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
	}

	public void render() {
		removeAll();
//		setBounds(new Rectangle(0, 0, 1180, 700));
		setLayout(null);
		JLabel lblNewLabel = new JLabel("Quản lý sinh viên");
		lblNewLabel.setForeground(new Color(56, 114, 250));
		lblNewLabel.setBackground(new Color(56, 114, 250));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 0, 1168, 50);
		lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
		add(lblNewLabel);

		JPanel studentFromPanel = new JPanel();
		studentFromPanel.setBorder(new TitledBorder(null, "Thông tin cá nhân", TitledBorder.LEADING, TitledBorder.TOP,
				null, new Color(56, 114, 250)));
		studentFromPanel.setBounds(10, 50, 370, 387);
		add(studentFromPanel);
		studentFromPanel.setLayout(null);

		JLabel lblID = new JLabel("ID");
		lblID.setBounds(20, 38, 82, 30);
		studentFromPanel.add(lblID);

		txtId = new JTextField();
		txtId.setBounds(115, 38, 150, 30);
		studentFromPanel.add(txtId);
		txtId.setColumns(10);
		txtId.setEditable(false);

		JLabel lblfFName = new JLabel("Họ");
		lblfFName.setBounds(20, 74, 82, 30);
		studentFromPanel.add(lblfFName);

		txtFName = new JTextField();
		txtFName.setColumns(10);
		txtFName.setBounds(115, 74, 150, 30);
		studentFromPanel.add(txtFName);

		JLabel lblLName = new JLabel("Tên");
		lblLName.setBounds(20, 110, 85, 30);
		studentFromPanel.add(lblLName);

		txtLName = new JTextField();
		txtLName.setColumns(10);
		txtLName.setBounds(115, 110, 150, 30);
		studentFromPanel.add(txtLName);

		JLabel lblPhone = new JLabel("Số điện thoại");
		lblPhone.setBounds(20, 146, 85, 30);
		studentFromPanel.add(lblPhone);

		txtPhone = new JTextField();
		txtPhone.setColumns(10);
		txtPhone.setBounds(115, 146, 150, 30);
		studentFromPanel.add(txtPhone);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(20, 182, 85, 30);
		studentFromPanel.add(lblEmail);

		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		txtEmail.setBounds(115, 182, 150, 30);
		studentFromPanel.add(txtEmail);

		JLabel lblGender = new JLabel("Giới tính");
		lblGender.setBounds(20, 215, 85, 30);
		studentFromPanel.add(lblGender);

		rbtnMale = new JRadioButton("Nam");
		rbtnMale.setBounds(115, 218, 50, 30);
		studentFromPanel.add(rbtnMale);

		rbtnFemale = new JRadioButton("Nữ");
		rbtnFemale.setBounds(170, 218, 50, 30);
		studentFromPanel.add(rbtnFemale);

		rbtnOther = new JRadioButton("Khác");
		rbtnOther.setBounds(220, 218, 76, 30);

		genderGroup = new ButtonGroup();
		genderGroup.add(rbtnMale);
		genderGroup.add(rbtnFemale);
		genderGroup.add(rbtnOther);
		studentFromPanel.add(rbtnOther);

		JLabel lblAddress = new JLabel("Địa chỉ");
		lblAddress.setBounds(20, 288, 85, 30);
		studentFromPanel.add(lblAddress);

		txtAddress = new JTextArea();
		txtAddress.setRows(5);
		txtAddress.setBounds(115, 288, 224, 75);
		studentFromPanel.add(txtAddress);

		JLabel lblDob = new JLabel("Ngày sinh");
		lblDob.setBounds(20, 251, 85, 30);
		studentFromPanel.add(lblDob);

		JPanel panel = new JPanel();
		panel.setBounds(381, 53, 789, 556);
		add(panel);
		panel.setLayout(null);

		tblStudents = new JTable();
		tblStudents.addMouseListener(new MouseAdapter() {
			// objects.add(new Object[]{dto.getStudentCode(), dto.getFirstName(),
			// dto.getLastName(), dto.getEmail(), dto.getDob(), dto.getPhoneNumber(),
			// dto.getId(), dto.getAddress(), dto.getAdmissionYear(), dto.getFaculty(),
			// dto.getKlass() });
			@Override
			public void mouseClicked(MouseEvent e) {
				DefaultTableModel model = (DefaultTableModel) tblStudents.getModel();
				int selectedRow = tblStudents.getSelectedRow();
				if (selectedRow > 0) {
					txtStdCode.setEditable(false);
				}
				txtStdCode.setText((String) model.getValueAt(selectedRow, 0));
				txtFName.setText((String) model.getValueAt(selectedRow, 1));
				txtLName.setText((String) model.getValueAt(selectedRow, 2));
				txtEmail.setText((String) model.getValueAt(selectedRow, 3));

				LocalDate lDate = LocalDate.parse((String) model.getValueAt(selectedRow, 4), FORMATTER);
				txtDob.getModel().setDate(lDate.getYear(), lDate.getMonthValue() - 1, lDate.getDayOfMonth());
				datePanel.getModel().setDate(lDate.getYear(), lDate.getMonthValue() - 1, lDate.getDayOfMonth());
				txtPhone.setText((String) model.getValueAt(selectedRow, 5));
				txtId.setText("" + model.getValueAt(selectedRow, 6));
				txtAddress.setText((String) model.getValueAt(selectedRow, 7));
				txtAdmissionYear.setText((String) model.getValueAt(selectedRow, 8));
				txtFaculty.setText((String) model.getValueAt(selectedRow, 9));
				txtClass.setText((String) model.getValueAt(selectedRow, 10));

				switch ((String) model.getValueAt(selectedRow, 11)) {
				case "MALE":
					rbtnMale.setSelected(true);
					break;
				case "FEMALE":
					rbtnFemale.setSelected(true);
					break;
				case "OTHER":
					rbtnOther.setSelected(true);
					break;
				}
				setUserId((int) model.getValueAt(selectedRow, 12));
			}
		});

		DefaultTableModel defaultTableModel = new DefaultTableModel(0, 0);
		defaultTableModel.setColumnIdentifiers(COLS_NAME);
		tblStudents.setModel(defaultTableModel);
		hideColumns(tblStudents, 6, COLS_NAME.length);
		JScrollPane scrollPane = new JScrollPane(tblStudents);

		scrollPane.setBounds(10, 71, 769, 485);
		panel.add(scrollPane);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(10, 10, 465, 56);
		panel.add(panel_2);
		panel_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_2.setLayout(null);

		JButton btnAdd = new JButton("Thêm");
		btnAdd.setBounds(25, 15, 94, 30);
		panel_2.add(btnAdd);

		JButton btnDelete = new JButton("Xoá");
		btnDelete.setBounds(141, 15, 94, 30);
		panel_2.add(btnDelete);

		JButton btnEdit = new JButton("Sửa");

		btnEdit.setBounds(245, 15, 90, 30);
		panel_2.add(btnEdit);
		
		btnRefresh = new JButton("Làm mới");
		btnRefresh.setBounds(345, 15, 90, 30);
		panel_2.add(btnRefresh);
		
		JPanel panel_2_1 = new JPanel();
		panel_2_1.setLayout(null);
		panel_2_1.setBorder(new TitledBorder(null, "In \u1EA5n", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2_1.setBounds(485, 10, 275, 56);
		panel.add(panel_2_1);
		
		JButton btnExportExcel = new JButton("Xuất Excel");
		btnExportExcel.setBounds(25, 15, 94, 30);
		panel_2_1.add(btnExportExcel);

		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				var dto = new StudentResponseDto();
				dto.setAcademicDetailsId(Integer.parseInt(txtId.getText()));
				dto.setAddress(txtAddress.getText());
				Date selectedDate = (Date) txtDob.getModel().getValue();
				dto.setDob(selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
				dto.setEmail(txtEmail.getText());
				dto.setFaculty(txtFaculty.getText());
				dto.setFirstName(txtFName.getText());
				dto.setLastName(txtLName.getText());
				dto.setUserId(getUserId());
				if (rbtnMale.isSelected()) {
					dto.setGender("MALE");
				} else if (rbtnFemale.isSelected()) {
					dto.setGender("FEMALE");
				} else if (rbtnOther.isSelected()) {
					dto.setGender("OTHER");
				}
				dto.setAdmissionYear(txtAdmissionYear.getText());
				dto.setKlass(txtClass.getText());
				dto.setStudentCode(txtStdCode.getText());
				dto.setPhoneNumber(txtPhone.getText());
				try {
					RequestMaker.createRequest("EDIT_STUDENT", dto);
				} catch (Exception ex) {
					ex.printStackTrace();
					if (ex instanceof SocketException) {
						JOptionPane.showMessageDialog(null, "Vui lòng bật server", "Lỗi", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				var dto = new StudentResponseDto();
				dto.setAddress(txtAddress.getText());
				Date selectedDate = (Date) txtDob.getModel().getValue();
				dto.setDob(selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
				dto.setEmail(txtEmail.getText());
				dto.setFaculty(txtFaculty.getText());
				dto.setFirstName(txtFName.getText());
				dto.setLastName(txtLName.getText());
				if (rbtnMale.isSelected()) {
					dto.setGender("MALE");
				} else if (rbtnFemale.isSelected()) {
					dto.setGender("FEMALE");
				} else if (rbtnOther.isSelected()) {
					dto.setGender("OTHER");
				}
				dto.setAdmissionYear(txtAdmissionYear.getText());
				dto.setKlass(txtClass.getText());
				dto.setStudentCode(txtStdCode.getText());
				dto.setPhoneNumber(txtPhone.getText());
				try {
					RequestMaker.createRequest("ADD_STUDENT", dto);
				} catch (Exception e1) {
					e1.printStackTrace();
					if (e1 instanceof SocketException) {
						JOptionPane.showMessageDialog(null, "Vui lòng bật server", "Lỗi", JOptionPane.ERROR_MESSAGE);
					}
				}

			}
		});

		btnDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tblStudents.getSelectedRow();
				if (selectedRow < 0) {
					JOptionPane.showMessageDialog(null, "Vui lòng chọn sinh viên cần xoá", "Lưu ý",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				int result = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xoá sinh viên này?", "Xác nhận",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (result == JOptionPane.YES_OPTION) {
					var dto = new StudentResponseDto();
					dto.setAcademicDetailsId(Integer.parseInt(txtId.getText()));
					dto.setUserId(getUserId());
					try {
						RequestMaker.createRequest("DELETE_STUDENT", dto);
					} catch (Exception e1) {
						e1.printStackTrace();
						if (e1 instanceof SocketException) {
							JOptionPane.showMessageDialog(null, "Vui lòng khởi chạy server", "Lỗi",
									JOptionPane.ERROR_MESSAGE);
						}
					}
				} else {

				}

			}
		});
		
		btnRefresh.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				clearForm();
			}
		});
		
		

		JPanel studentFromPanel_1 = new JPanel();
		studentFromPanel_1.setBounds(10, 437, 370, 177);
		add(studentFromPanel_1);
		studentFromPanel_1.setLayout(null);
		studentFromPanel_1.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Th\u00F4ng tin h\u1ECDc t\u1EADp", TitledBorder.LEADING, TitledBorder.TOP, null,
				new Color(56, 114, 250)));

		JLabel lblStdCode = new JLabel("MSSV");
		lblStdCode.setBounds(20, 30, 85, 22);
		studentFromPanel_1.add(lblStdCode);

		txtStdCode = new JTextField();
		txtStdCode.setColumns(10);
		txtStdCode.setBounds(115, 27, 150, 30);
		studentFromPanel_1.add(txtStdCode);

		JLabel lblAdmissionYear = new JLabel("Năm nhập học");
		lblAdmissionYear.setBounds(20, 66, 85, 22);
		studentFromPanel_1.add(lblAdmissionYear);

		txtAdmissionYear = new JTextField();
		txtAdmissionYear.setColumns(10);
		txtAdmissionYear.setBounds(115, 63, 150, 30);
		studentFromPanel_1.add(txtAdmissionYear);

		JLabel lblFaculty = new JLabel("Khoa");
		lblFaculty.setBounds(20, 102, 85, 22);
		studentFromPanel_1.add(lblFaculty);

		txtFaculty = new JTextField();
		txtFaculty.setColumns(10);
		txtFaculty.setBounds(115, 99, 150, 30);
		studentFromPanel_1.add(txtFaculty);

		JLabel lblClass = new JLabel("Lớp sinh hoạt");
		lblClass.setBounds(20, 138, 85, 22);
		studentFromPanel_1.add(lblClass);

		txtClass = new JTextField();
		txtClass.setColumns(10);
		txtClass.setBounds(115, 135, 150, 30);
		studentFromPanel_1.add(txtClass);

		UtilDateModel model = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		datePanel = new JDatePanelImpl(model, p);
		txtDob = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		model.setSelected(true);
		txtDob.setBounds(115, 251, 193, 30);
		studentFromPanel.add(txtDob);
		
		btnExportExcel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				exportToExcel();
			}
		});
	}

	public StudentListUI() throws IOException {
		render();
		txtStdCode.setEditable(true);
	}
	
	private void exportToExcel() {
		try (Workbook workbook = new XSSFWorkbook()) {
			Sheet sheet = workbook.createSheet("Student Data");
			DefaultTableModel model = (DefaultTableModel) tblStudents.getModel();

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

			// Save the workbook to a file
			try (FileOutputStream fileOut = new FileOutputStream("student_data.xlsx")) {
				workbook.write(fileOut);
				JOptionPane.showMessageDialog(this, "Dữ liệu đã được xuất thành công vào file Excel.", "Xuất Excel",
						JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi xuất dữ liệu sang Excel.", "Lỗi",
					JOptionPane.ERROR_MESSAGE);
		} catch (HeadlessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
