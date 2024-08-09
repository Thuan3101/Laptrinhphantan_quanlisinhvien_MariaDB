package org.dev.iuh.client.ui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import org.dev.iuh.client.dto.CbxCourseModel;
import org.dev.iuh.client.services.RequestMaker;
import org.dev.iuh.common.dto.BasicTranscript;
import org.dev.iuh.common.dto.CourseOptionDto;
import org.dev.iuh.common.dto.OverviewStatistics;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;

public class OverviewUI extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel lbltotalCourses;
	private JLabel lblTotalSubjects;
	private JLabel lblTotalStudents;
	private ChartPanel pieChartPanel;
	private JPanel pieChartArea;
	private JComboBox<CourseOptionDto> selectCourse1;
	private JComboBox<CourseOptionDto> selectCourse2;
	private CourseOptionDto[] courseDtos;
	private JTable tblCourseTranscript;
	private static final String[] COLS_NAME = { "MSSV", "Họ", "Tên", "Điểm trung bình" };

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

	public void render() {
		removeAll();
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		JLabel lblTngQuan = new JLabel("Tổng quan");
		lblTngQuan.setHorizontalAlignment(SwingConstants.CENTER);
		lblTngQuan.setFont(new Font("Segoe UI", Font.BOLD, 18));
		add(lblTngQuan);

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane);

		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(1120, 700));
		scrollPane.setPreferredSize(new Dimension(1160, 680));
		scrollPane.setColumnHeaderView(panel);
		panel.setLayout(null);

		JPanel card1 = new JPanel();
		card1.setBounds(15, 11, 275, 150);
		card1.setBackground(new Color(26, 77, 46));
		panel.add(card1);
		card1.setLayout(null);

		lbltotalCourses = new JLabel("10");
		lbltotalCourses.setForeground(Color.WHITE);
		lbltotalCourses.setFont(new Font("Tahoma", Font.BOLD, 32));
		lbltotalCourses.setBounds(134, 42, 47, 30);
		card1.add(lbltotalCourses);

		JLabel lblNewLabel_2_1 = new JLabel("Lớp học phần");
		lblNewLabel_2_1.setForeground(Color.WHITE);
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_2_1.setBounds(134, 71, 168, 30);
		card1.add(lblNewLabel_2_1);

		JLabel lblCourseIcon = new JLabel("");
		lblCourseIcon.setIcon(new ImageIcon(OverviewUI.class.getResource("/org/dev/iuh/client/assets/course.png")));
		lblCourseIcon.setBounds(21, 32, 80, 80);
		card1.add(lblCourseIcon);

		JPanel card3 = new JPanel();
		card3.setBounds(585, 11, 275, 150);
		card3.setBackground(new Color(116, 105, 182));
		panel.add(card3);
		card3.setLayout(null);

		JLabel lblNewLabel_1_1 = new JLabel("");
		lblNewLabel_1_1.setIcon(new ImageIcon(OverviewUI.class.getResource("/org/dev/iuh/client/assets/student.png")));
		lblNewLabel_1_1.setBounds(20, 33, 80, 80);
		card3.add(lblNewLabel_1_1);

		lblTotalStudents = new JLabel("100");
		lblTotalStudents.setForeground(Color.WHITE);
		lblTotalStudents.setFont(new Font("Tahoma", Font.BOLD, 32));
		lblTotalStudents.setBounds(124, 50, 96, 30);
		card3.add(lblTotalStudents);

		JLabel lblNewLabel_2_1_1_1 = new JLabel("Tổng số sinh viên");
		lblNewLabel_2_1_1_1.setForeground(Color.WHITE);
		lblNewLabel_2_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_2_1_1_1.setBounds(124, 79, 207, 30);
		card3.add(lblNewLabel_2_1_1_1);

		JPanel card2 = new JPanel();
		card2.setBounds(300, 11, 275, 150);
		card2.setBackground(new Color(236, 134, 94));
		panel.add(card2);
		card2.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(OverviewUI.class.getResource("/org/dev/iuh/client/assets/book-1.png")));
		lblNewLabel_1.setBounds(20, 30, 106, 80);
		card2.add(lblNewLabel_1);

		JLabel lblNewLabel_2_1_1 = new JLabel("Môn học");
		lblNewLabel_2_1_1.setForeground(Color.WHITE);
		lblNewLabel_2_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_2_1_1.setBounds(148, 72, 207, 30);
		card2.add(lblNewLabel_2_1_1);

		lblTotalSubjects = new JLabel("6");
		lblTotalSubjects.setForeground(Color.WHITE);
		lblTotalSubjects.setFont(new Font("Tahoma", Font.BOLD, 32));
		lblTotalSubjects.setBounds(148, 43, 47, 30);
		card2.add(lblTotalSubjects);

		pieChartArea = new JPanel();
		pieChartArea.setBounds(681, 232, 470, 362);
		panel.add(pieChartArea);
		pieChartArea.setLayout(new BorderLayout(0, 0));

//		courseDtos = new CourseOptionDto[] { new CourseOptionDto(-1, "Chọn lớp học phần", "Chọn lớp học phần"),
//				new CourseOptionDto(1, "Systems Analysis and Design L01", "S2101625HK2520241L01"),
//				new CourseOptionDto(2, "Systems Analysis and Design L02", "S2101625HK2520241L02"),
//				new CourseOptionDto(3, "Distributed Programming with Java Technology L02", "S2101558HK2520241L02"),
//				new CourseOptionDto(4, "Distributed Programming with Java Technology L01", "S2101558HK2520241L01"), };
		courseDtos = new CourseOptionDto[1];
		
		CbxCourseModel courseModel1 = new CbxCourseModel(courseDtos);
		CbxCourseModel courseModel2 = new CbxCourseModel(courseDtos);

		selectCourse1 = new JComboBox<CourseOptionDto>(courseModel1);
		selectCourse1.setBounds(325, 187, 250, 30);
		panel.add(selectCourse1);
		selectCourse1.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				var selectedCourse = (CourseOptionDto) e.getItem();
				if (selectedCourse.getId() != -1 && e.getStateChange() == ItemEvent.SELECTED) {
					try {
						RequestMaker.createRequest("GET_BASIC_TRANSCRIPT_FOR_COURSE", selectedCourse.getId());
						return;
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}

			}
		});

		selectCourse2 = new JComboBox<CourseOptionDto>(courseModel2);
		selectCourse2.setBounds(681, 187, 250, 30);
		selectCourse2.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				var selectedCourse = (CourseOptionDto) e.getItem();
				if (selectedCourse.getId() != -1 && e.getStateChange() == ItemEvent.SELECTED) {
					try {
						RequestMaker.createRequest("VIEW_GENDER_RATIO_BY_COURSE", selectedCourse.getId());
						return;
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}

			}
		});
		panel.add(selectCourse2);

		tblCourseTranscript = new JTable();
		DefaultTableModel defaultTableModel = new DefaultTableModel(0, 0);
		defaultTableModel.setColumnIdentifiers(COLS_NAME);
		tblCourseTranscript.setModel(defaultTableModel);

		JScrollPane scrollPane_1 = new JScrollPane(tblCourseTranscript);
		scrollPane_1.setBounds(15, 232, 656, 362);
		panel.add(scrollPane_1);

		JLabel lblNewLabel_2 = new JLabel("Điểm trung bình theo lớp học phần");
		lblNewLabel_2.setBounds(15, 185, 290, 30);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel.add(lblNewLabel_2);
		
		JButton btnDangxuat = new JButton("Đăng xuất");
		btnDangxuat.setBackground(Color.CYAN);
		btnDangxuat.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnDangxuat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleLogout();
			}
		});
		btnDangxuat.setBounds(1040, 36, 100, 23);
		panel.add(btnDangxuat);
	}

	public OverviewUI() {
		render();

	}

	public JFreeChart createBarChart(CategoryDataset dataset) {
		JFreeChart barChart = ChartFactory.createBarChart("Phổ điểm trung bình môn học", "Thang điểm", "Số lượng",
				dataset, PlotOrientation.VERTICAL, false, false, false);
		return barChart;
	}

	public JFreeChart createPieChart(PieDataset<String> dataset) {
		JFreeChart chart = ChartFactory.createPieChart("Phân loại sinh viên", dataset, true, true, true);
		return chart;
	}

	public void loadStatistic(OverviewStatistics stats) {
		loadGenderRatio(stats.getGenderRatio());

		lbltotalCourses.setText("" + stats.getTotalCourses());
		lblTotalStudents.setText("" + stats.getTotalStudents());
		lblTotalSubjects.setText("" + stats.getTotalSubjects());

	}

	public void loadBasicTranscriptByCourse(List<BasicTranscript> transcripts) {
		List<Object[]> objects = new ArrayList<Object[]>();
		for (BasicTranscript transcript : transcripts) {
			objects.add(new Object[] { transcript.getStudentCode(), transcript.getFirstName(), transcript.getLastName(),
					transcript.getAvgGrade() });
		}
		renderTableUI(tblCourseTranscript, objects.toArray(new Object[objects.size()][]));
	}

	public void loadGenderRatio(Map<String, Integer> mapData) {
		DefaultPieDataset<String> genderRatio = new DefaultPieDataset<String>();
		for (Map.Entry<String, Integer> entry : mapData.entrySet()) {
			genderRatio.setValue(entry.getKey(), entry.getValue());
		}

		SwingUtilities.invokeLater(() -> {
			pieChartArea.removeAll();
			pieChartArea.revalidate();
			var aChart = createPieChart(genderRatio);
			aChart.removeLegend();
			pieChartPanel = new ChartPanel(aChart);
			pieChartArea.add(pieChartPanel, BorderLayout.CENTER);
			pieChartArea.repaint();
		});
	}

	public CourseOptionDto[] getCourseDtos() {
		return courseDtos;
	}

	public void setCourseDtos(CourseOptionDto[] courseDtos) {
		this.courseDtos = courseDtos;
		CbxCourseModel courseModel1 = new CbxCourseModel(courseDtos);
		CbxCourseModel courseModel2 = new CbxCourseModel(courseDtos);
		selectCourse1.setModel(courseModel1);
		selectCourse2.setModel(courseModel2);
		
	}
	// Phương thức xử lý đăng xuất
	private void handleLogout() {
	    // Đưa ra cảnh báo xác nhận đăng xuất
	    int choice = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn đăng xuất?", "Xác nhận đăng xuất", JOptionPane.YES_NO_OPTION);
	    if (choice == JOptionPane.YES_OPTION) {
	        // Xử lý đăng xuất ở đây
	        // Ví dụ: Chuyển hướng đến giao diện đăng nhập
	        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this); // Get the JFrame containing OverviewUI
	        frame.dispose(); // Dispose the JFrame
	        AuthenFrame loginForm = new AuthenFrame(); // Khởi tạo giao diện đăng nhập
	        loginForm.setVisible(true); // Hiển thị giao diện đăng nhập
	    }
	}

	
	
	
}
