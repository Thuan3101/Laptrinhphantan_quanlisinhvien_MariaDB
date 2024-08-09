package org.dev.iuh.client.app;

import java.awt.EventQueue;
import java.awt.Font;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import org.dev.iuh.client.context.ApplicationContext;
import org.dev.iuh.client.context.SocketClientContext;
import org.dev.iuh.client.dto.SocketClient;
import org.dev.iuh.client.dto.StudentResponseDto;
import org.dev.iuh.client.services.RequestMaker;
import org.dev.iuh.client.ui.AuthenFrame;
import org.dev.iuh.common.dto.AssessmentDto;
import org.dev.iuh.common.dto.BasicTranscript;
import org.dev.iuh.common.dto.CourseDto;
import org.dev.iuh.common.dto.CourseOptionDto;
import org.dev.iuh.common.dto.EnrollmentDto;
import org.dev.iuh.common.dto.GeneralMessage;
import org.dev.iuh.common.dto.OverviewStatistics;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class App {
	private static Thread listenerThread;

	public static void main(String[] args) throws IOException {
		ExecutorService executorService = Executors.newCachedThreadPool();
		executorService.execute(() -> {
			var app = new App();
			app.runClient();
			app.connectToServer("localhost", 8888);
		});

	}

	private void runClient() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				        if ("Nimbus".equals(info.getName())) {
				            UIManager.setLookAndFeel(info.getClassName());
				            
				            break;
				        }
				    }
//					UIManager.setLookAndFeel(new FlatAtomOneLightIJTheme());
					ApplicationContext.getInstance().initComponents();
					UIManager.getLookAndFeelDefaults().put("defaultFont", new Font("Inter", Font.PLAIN, 12));
					AuthenFrame frame = ApplicationContext.getInstance().getAuthenFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void connectToServer(String serverAddress, int serverPort) {
		try {
			var socket = new Socket(serverAddress, serverPort);
			var out = new ObjectOutputStream(socket.getOutputStream());
			var in = new ObjectInputStream(socket.getInputStream());
			var socketCilent = new SocketClient(socket, in, out);
			SocketClientContext.getInstance().setSocketClient(socketCilent);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.registerModule(new JavaTimeModule());
			objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

			listenerThread = new Thread(() -> {
				try {
					while (!SocketClientContext.getInstance().getSocketClient().getSocket().isClosed()) {
						var fromServer = SocketClientContext.getInstance().getSocketClient().getFromServer();
						String message = fromServer.readUTF();
						var response = objectMapper.readValue(message, GeneralMessage.class);
						String json = "";
						switch (response.getRequestCode()) {

						case "LOGIN":
							json = objectMapper.writeValueAsString(response.getResult());
							String msg = objectMapper.readValue(json, String.class);
							if (msg.contains("OK")) {
								String lecturerId = msg.split(",")[1];
								RequestMaker.createRequest("GET_MY_COURSES", lecturerId);
								ApplicationContext.getInstance().getAuthenFrame().dispose();
								ApplicationContext.getInstance().getMainFrame().run();
								
							} else {
								JOptionPane.showMessageDialog(ApplicationContext.getInstance().getStudentListUI(), msg,
										"Thông báo", JOptionPane.ERROR_MESSAGE);
							}
							break;
						
						case "GET_ME":
							break;
							
						case "GET_MY_COURSES":
							json = objectMapper.writeValueAsString(response.getResult());
							List<CourseDto> myCourses = objectMapper.readValue(json,
									new TypeReference<List<CourseDto>>() {
									});
							
							ApplicationContext.getInstance().setMyCourses(myCourses);
							var listCourseOptions = ApplicationContext.getInstance().getMyCourses().stream()
									.map(course -> {
										return new CourseOptionDto(course.getCourseId(), course.getCourseName(), course.getCourseCode());
									}).collect(Collectors.toList());
							
							listCourseOptions.add(0, new CourseOptionDto(-1, "Chọn lớp học phần", ""));
							var courseDtos = listCourseOptions.toArray(CourseOptionDto[]::new);
							System.out.println(json);
							System.out.println(courseDtos);
							ApplicationContext.getInstance().getOverviewUI().setCourseDtos(courseDtos);
							ApplicationContext.getInstance().getCourseManagementUI().setCourseDtos(courseDtos);
							ApplicationContext.getInstance().getGradeManagementUI().setCourseDtos(courseDtos);
							break;

						case "VIEW_LIST_STUDENT":

							json = objectMapper.writeValueAsString(response.getResult());
							List<StudentResponseDto> result = objectMapper.readValue(json,
									new TypeReference<List<StudentResponseDto>>() {
									});
							ApplicationContext.getInstance().getStudentListUI().renderStudentTable(result);
							break;

						case "ADD_STUDENT":
							json = objectMapper.writeValueAsString(response.getResult());
							boolean isStudentAdded = objectMapper.readValue(json, Boolean.class);
							if (isStudentAdded == true) {
								ApplicationContext.getInstance().getStudentListUI().doAfterAddSuccessfully();
							} else {
								JOptionPane.showMessageDialog(ApplicationContext.getInstance().getStudentListUI(),
										"Email, mã sinh viên và số điện thoại phải là duy nhất", "Thông báo", JOptionPane.ERROR_MESSAGE);
							}
							break;

						case "EDIT_STUDENT":
							json = objectMapper.writeValueAsString(response.getResult());
							boolean isStudentEdited = objectMapper.readValue(json, Boolean.class);
							if (isStudentEdited == true) {
								ApplicationContext.getInstance().getStudentListUI().doAfterEditSuccessfully();
							} else {
								JOptionPane.showMessageDialog(ApplicationContext.getInstance().getStudentListUI(),
										"Email và số điện thoại phải là duy nhất", "Thông báo", JOptionPane.ERROR_MESSAGE);
							}
							break;

						case "DELETE_STUDENT":
							json = objectMapper.writeValueAsString(response.getResult());
							boolean isStudentRemoved = objectMapper.readValue(json, Boolean.class);
							if (isStudentRemoved == true) {
								ApplicationContext.getInstance().getStudentListUI().doAfterDeleteSuccessfully();
							} else {
								JOptionPane.showMessageDialog(ApplicationContext.getInstance().getStudentListUI(),
										"Đã có lỗi xảy ra", "Thông báo", JOptionPane.ERROR_MESSAGE);
							}
							break;

						case "VIEW_ENROLLMENTS":
							json = objectMapper.writeValueAsString(response.getResult());

							List<EnrollmentDto> enrollmentDtos = objectMapper.readValue(json,
									new TypeReference<List<EnrollmentDto>>() {
									});
							ApplicationContext.getInstance().getGradeManagementUI().loadEnrollments(enrollmentDtos);
							break;

						case "GET_ASSESSMENT_BY_ENROLLMENT_ID":
							json = objectMapper.writeValueAsString(response.getResult());
							List<AssessmentDto> assessmentDtos = objectMapper.readValue(json,
									new TypeReference<List<AssessmentDto>>() {
									});
							ApplicationContext.getInstance().getGradeManagementUI().loadAssessments(assessmentDtos);
							break;

						case "GET_STATISTICS":
							json = objectMapper.writeValueAsString(response.getResult());
							OverviewStatistics stats = objectMapper.readValue(json,
									new TypeReference<OverviewStatistics>() {
									});
							ApplicationContext.getInstance().getOverviewUI().loadStatistic(stats);
							break;

						case "VIEW_GENDER_RATIO_BY_COURSE":
							json = objectMapper.writeValueAsString(response.getResult());
							Map<String, Integer> genderRatio = objectMapper.readValue(json,
									new TypeReference<Map<String, Integer>>() {
									});
							ApplicationContext.getInstance().getOverviewUI().loadGenderRatio(genderRatio);
							break;

						case "FILTER_ENROLLMENT_BY_COURSE":
							json = objectMapper.writeValueAsString(response.getResult());

							List<EnrollmentDto> enrollmentDtos1 = objectMapper.readValue(json,
									new TypeReference<List<EnrollmentDto>>() {
									});
							ApplicationContext.getInstance().getGradeManagementUI().loadEnrollments(enrollmentDtos1);
							break;
							
						case "GET_COURSE_INFO":
							json = objectMapper.writeValueAsString(response.getResult());

							CourseDto courseDto = objectMapper.readValue(json,
									new TypeReference<CourseDto>() {
									});
							ApplicationContext.getInstance().getCourseManagementUI().loadCourseInfo(courseDto);
							break;
						
						case "GET_COURSE_ENROLLMENT":
							json = objectMapper.writeValueAsString(response.getResult());

							List<EnrollmentDto> enrollmentDtos2 = objectMapper.readValue(json,
									new TypeReference<List<EnrollmentDto>>() {
									});
							ApplicationContext.getInstance().getCourseManagementUI().loadEnrollments(enrollmentDtos2);
							break;
						
						case "REGISTER_TO_COURSE":
							json = objectMapper.writeValueAsString(response.getResult());
							String enrollStatus = objectMapper.readValue(json, String.class);
							if (enrollStatus.equalsIgnoreCase("OK")) {
								ApplicationContext.getInstance().getCourseManagementUI().doAfterAddSuccessfully();
							} else {
								JOptionPane.showMessageDialog(ApplicationContext.getInstance().getStudentListUI(),
										enrollStatus, "Thông báo", JOptionPane.ERROR_MESSAGE);
							}
							break;
							
						case "GET_BASIC_TRANSCRIPT_FOR_COURSE":
							json = objectMapper.writeValueAsString(response.getResult());
							List<BasicTranscript> transcripts = objectMapper.readValue(json,
									new TypeReference<List<BasicTranscript>>() {
									});
							ApplicationContext.getInstance().getOverviewUI().loadBasicTranscriptByCourse(transcripts);
							break;
			
						default:
							break;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					if (e instanceof SocketException) {
						JOptionPane.showMessageDialog(null, "Vui lòng khởi động server server", "Lỗi", JOptionPane.ERROR_MESSAGE);
					}
				} finally {
				}
			});
			listenerThread.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
