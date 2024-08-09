package org.dev.iuh.client.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;

import javax.swing.JOptionPane;

import java.util.HashMap;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.dev.iuh.client.context.ApplicationContext;
import org.dev.iuh.client.services.RequestMaker;
import javax.swing.JTabbedPane;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTabbedPane tabbedPane;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
						if ("Nimbus".equals(info.getName())) {
							UIManager.setLookAndFeel(info.getClassName());
							break;
						}
					}
					// UIManager.setLookAndFeel(new FlatLightLaf());
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void initialTab() {
		var overviewUI = ApplicationContext.getInstance().getOverviewUI();
		overviewUI.setBounds(40, 40, 1180, 650);
		overviewUI.render();
		
		var studentMngm = ApplicationContext.getInstance().getStudentListUI();
		studentMngm.setBounds(40, 40, 1180, 650);
		studentMngm.render();
		
		var gradeMngm = ApplicationContext.getInstance().getGradeManagementUI();
		gradeMngm.setBounds(40, 40, 1180, 650);
		gradeMngm.render();
		
		var registerCourse = ApplicationContext.getInstance().getCourseManagementUI();
		registerCourse.setBounds(40, 40, 1180, 650);
		registerCourse.render();
		
		tabbedPane.addTab("Tổng quan",null, overviewUI, null);
		tabbedPane.addTab("Quản lý sinh viên", null, studentMngm, null);
		tabbedPane.addTab("Nhập liệu điểm", null, gradeMngm, null);
		tabbedPane.addTab("Đăng ký môn học", null, registerCourse, null);
		
		tabbedPane.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				try {
					if (e.getSource() instanceof JTabbedPane) {
	                    JTabbedPane pane = (JTabbedPane) e.getSource();
	                    switch (pane.getSelectedIndex()) {
	                    // overview 
						case 0: 
							RequestMaker.createRequest("GET_STATISTICS", -1);
							break;
						// student
						case 1:
							RequestMaker.createRequest("VIEW_LIST_STUDENT", "");
							break;
						// grade management
						case 2:
							RequestMaker.createRequest("FILTER_ENROLLMENT_BY_COURSE", -1);
							break;
							
						// enrollment
						case 3:
							break;
						default:
							throw new IllegalArgumentException("Unexpected value: " + pane);
						}
	                }
				}catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Vui lòng khởi động server", "Lỗi", JOptionPane.ERROR_MESSAGE);
				}
				
				
			}
		});
		
		
		try {
			RequestMaker.createRequest("GET_STATISTICS", 2);
		} catch (Exception e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Vui lòng khởi động server", "Lỗi", JOptionPane.ERROR_MESSAGE);
		}
		
	}

	public void run() {
		setLocationRelativeTo(null); // cho ra giữa màn hình
		setVisible(true);
		initialTab();
	}

	/**
	 * Create the frame.
	 */

	public MainFrame() {
		setTitle("Phần mềm quản lý sinh viên");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setBounds(0, 0, 1200, 750);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBounds(0, 0, 1200, 700);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 10, 1200, 700);
		contentPane.add(tabbedPane);
		
		setLocationRelativeTo(null);

	}
}
