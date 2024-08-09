package org.dev.iuh.server.app;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import org.dev.iuh.server.controllers.ClientHandler;
import org.dev.iuh.server.utils.EntityManagerFactoryUtil;
import jakarta.persistence.EntityManager;
import java.awt.Color;

public class Server extends JFrame {
	private static final long serialVersionUID = 1L;
	private JButton startServerButton;  
	private JButton stopServerButton;
	private JTextArea logArea;
	private ServerSocket serverSocket;
	private List<ClientHandler> clients = new CopyOnWriteArrayList<ClientHandler>();
	private EntityManagerFactoryUtil mangerFactoryUtil;
	private EntityManager entityManager;

	public Server() {

		setTitle("QLSV Server");
		logArea = new JTextArea(16, 50);
		logArea.setEditable(false);
		startServerButton = new JButton("Start Server");
		startServerButton.setBackground(Color.RED);
		stopServerButton = new JButton("Stop Server");
		stopServerButton.setBackground(Color.RED);
		stopServerButton.setEnabled(false);

		startServerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startServer();
			}
		});

		stopServerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopServer();
			}
		});

		JPanel panel = new JPanel(new BorderLayout());
		panel.add(new JScrollPane(logArea), BorderLayout.CENTER);

		getContentPane().add(panel, BorderLayout.CENTER);
		getContentPane().add(startServerButton, BorderLayout.NORTH);
		getContentPane().add(stopServerButton, BorderLayout.WEST);
		pack();
	}

	private void startServer() {
		new Thread(() -> {
			final int PORT = 3101;
			try {
				this.mangerFactoryUtil = new EntityManagerFactoryUtil();
				this.entityManager = this.mangerFactoryUtil.getEnManager();
				serverSocket = new ServerSocket(8888);
				logArea.append("Server is listening on port " + PORT + "\n");
				startServerButton.setEnabled(false);
				stopServerButton.setEnabled(true);
				System.out.println("Server started on port 3101");
				ExecutorService executorService = Executors.newCachedThreadPool();

				while (true) {
					Socket clientSocket = serverSocket.accept();

					logArea.append("Listen from: " + clientSocket.getInetAddress().getHostAddress() + "\n");
					executorService.execute(new ClientHandler(clientSocket, logArea));
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();
	}

	private void stopServer() {
		try {
			for (ClientHandler client : clients) {
				client.closeConnection();
			}
			serverSocket.close();
			startServerButton.setEnabled(true);
			stopServerButton.setEnabled(false);
			logArea.append("Server stopped.\n");
		} catch (IOException e) {
			logArea.append("Could not stop the server.\n");
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				var server = new Server();
				server.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				server.setVisible(true);
			}
		});
	}

}
