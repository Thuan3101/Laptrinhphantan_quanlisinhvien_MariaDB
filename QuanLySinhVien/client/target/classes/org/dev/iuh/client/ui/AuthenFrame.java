package org.dev.iuh.client.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.dev.iuh.client.services.RequestMaker;
import org.dev.iuh.common.dto.LoginRequestDto;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.ImageIcon;

public class AuthenFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtEmail;
	private JTextField txtPassword;
	private JButton btnLogin;

	public void dispose() {
		this.setVisible(false);
	}

	public AuthenFrame() {

		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 598, 379);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));

		JPanel panel = new JPanel();
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Sử dụng tài khoản giản viên để đăng nhập");
		lblNewLabel.setBounds(10, 65, 271, 20);
		panel.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Ứng dụng quản lý sinh viên");
		lblNewLabel_1.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblNewLabel_1.setBounds(10, 15, 261, 49);
		panel.add(lblNewLabel_1);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(10, 110, 261, 179);
		panel.add(panel_2);
		panel_2.setLayout(null);

		JLabel lblNewLabel_2 = new JLabel("Email");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_2.setBounds(0, 5, 261, 20);
		panel_2.add(lblNewLabel_2);

		txtEmail = new JTextField();
		txtEmail.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtEmail.setBounds(1, 26, 260, 25);
		panel_2.add(txtEmail);
		txtEmail.setColumns(10);

		txtPassword = new JTextField();
		txtPassword.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtPassword.setColumns(10);
		txtPassword.setBounds(1, 82, 260, 25);
		panel_2.add(txtPassword);

		JLabel lblNewLabel_2_1 = new JLabel("Mật khẩu");
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_2_1.setBounds(0, 61, 261, 20);
		panel_2.add(lblNewLabel_2_1);

		btnLogin = new JButton("Đăng nhập");
		btnLogin.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		btnLogin.setBounds(0, 118, 261, 35);
		panel_2.add(btnLogin);
		
		JLabel lblVoHThng = new JLabel("vào hệ thống");
		lblVoHThng.setBounds(10, 85, 271, 20);
		panel.add(lblVoHThng);

		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblNewLabel_3 = new JLabel("");
		var image = new ImageIcon(AuthenFrame.class.getResource("/org/dev/iuh/client/assets/login-1.png"))
				.getImage().getScaledInstance(286, 286, Image.SCALE_SMOOTH);
		lblNewLabel_3.setIcon(new ImageIcon(image));
		lblNewLabel_3.setBounds(0, 5, 286, 326);
		panel_1.add(lblNewLabel_3);

		btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String email = txtEmail.getText();
				String password = txtPassword.getText();
				var request = new LoginRequestDto(email, password);
				try {
					RequestMaker.createRequest("LOGIN", request);
				} catch (Exception e1) {
					e1.printStackTrace();
					if (e1 instanceof NullPointerException) {
						JOptionPane.showMessageDialog(null, "Vui lòng khởi chạy server", "Lỗi",
								JOptionPane.ERROR_MESSAGE);
					}
				}

			}
		});
		
		setLocationRelativeTo(null); 
	}
}
