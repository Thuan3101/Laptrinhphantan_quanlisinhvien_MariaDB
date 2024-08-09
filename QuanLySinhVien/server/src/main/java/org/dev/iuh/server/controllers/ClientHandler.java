package org.dev.iuh.server.controllers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

import javax.swing.JTextArea;

import org.dev.iuh.common.dto.EnrollmentEdit;
import org.dev.iuh.common.dto.GeneralRequest;
import org.dev.iuh.common.dto.LoginRequestDto;
import org.dev.iuh.common.dto.RegisterCourseDto;
import org.dev.iuh.server.dto.StudentResponseDto;
import org.dev.iuh.server.utils.EntityManagerFactoryUtil;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.persistence.EntityManager;

public class ClientHandler implements Runnable {
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private UserController userController;
	private EnrollmentController enrollmentController;
	private CourseController courseController;
	private EntityManagerFactoryUtil mangerFactoryUtil;
	private AssessmentController assessmentController;
	private EntityManager entityManager;
	private JTextArea logArea;

	public ClientHandler(Socket socket, JTextArea logArea) {
		this.socket = socket;
		this.logArea = logArea;
		this.mangerFactoryUtil = new EntityManagerFactoryUtil();
		this.entityManager = mangerFactoryUtil.getEnManager();
		this.userController = new UserController("", this.entityManager);
		this.enrollmentController = new EnrollmentController("", this.entityManager);
		this.assessmentController = new AssessmentController("", this.entityManager);
		this.courseController = new CourseController("", this.entityManager);
	}

	@Override
	public void run() {
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			ObjectWriter writer = new ObjectMapper().registerModule(new JavaTimeModule()).writer()
					.withDefaultPrettyPrinter();
			ObjectMapper mapper = new ObjectMapper();
			mapper.registerModule(new JavaTimeModule());
			while (true) {
				String jsonRequest = in.readUTF();
				var request = mapper.readValue(jsonRequest, GeneralRequest.class);
				String json = "";
				switch (request.getRequestCode()) {
					case "LOGIN":
						userController.setRequestCode(request.getRequestCode());
						var loginDto = mapper.readValue(writer.writeValueAsString(request.getPayload()),
								LoginRequestDto.class);
						var loginResult = userController.login(loginDto);
						json = writer.writeValueAsString(loginResult);
						sendMessage(json);
						break;

					case "GET_ME":
						break;

					case "GET_MY_COURSES":
						courseController.setRequestCode(request.getRequestCode());
						int lecId = mapper.readValue(writer.writeValueAsString(request.getPayload()),
								Integer.class);
						var myCourses = courseController.findCourseByLecturerId(lecId);
						json = writer.writeValueAsString(myCourses);
						sendMessage(json);
						break;

					case "VIEW_LIST_STUDENT":
						userController.setRequestCode(request.getRequestCode());
						var result = userController.getListStudent();
						json = writer.writeValueAsString(result);
						sendMessage(json);
						break;

					case "ADD_STUDENT":
						userController.setRequestCode(request.getRequestCode());
						var payload = mapper.readValue(writer.writeValueAsString(request.getPayload()),
								StudentResponseDto.class);
						var isAdded = userController.addNewStudent(payload);
						json = writer.writeValueAsString(isAdded);
						sendMessage(json);
						break;

					case "EDIT_STUDENT":
						userController.setRequestCode(request.getRequestCode());
						var updatedInfo = mapper.readValue(writer.writeValueAsString(request.getPayload()),
								StudentResponseDto.class);
						var isEditedStudent = userController.editStudent(updatedInfo);
						json = writer.writeValueAsString(isEditedStudent);
						sendMessage(json);
						break;

					case "DELETE_STUDENT":
						userController.setRequestCode(request.getRequestCode());
						var deleteInfo = mapper.readValue(writer.writeValueAsString(request.getPayload()),
								StudentResponseDto.class);
						var isStudentDeleted = userController.deleteStudent(deleteInfo);
						json = writer.writeValueAsString(isStudentDeleted);
						sendMessage(json);
						break;

					case "VIEW_ENROLLMENTS":
						enrollmentController.setRequestCode(request.getRequestCode());
						TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {
						};
						String payload1 = writer.writeValueAsString(request.getPayload());
						HashMap<String, Object> searchMap = mapper.readValue(payload1, typeRef);
						var enrollments = enrollmentController.loadEnrollments(searchMap);
						json = writer.writeValueAsString(enrollments);
						sendMessage(json);
						break;

					case "GET_ASSESSMENT_BY_ENROLLMENT_ID":
						assessmentController.setRequestCode(request.getRequestCode());
						int enrollmentId = mapper.readValue(writer.writeValueAsString(request.getPayload()),
								Integer.class);
						// break;
						var assesments = assessmentController.loadAssessments(enrollmentId);
						json = writer.writeValueAsString(assesments);
						sendMessage(json);
						break;

					case "UPDATE_GRADE":
						assessmentController.setRequestCode(request.getRequestCode());
						TypeReference<EnrollmentEdit> typeRef1 = new TypeReference<EnrollmentEdit>() {
						};
						EnrollmentEdit data = mapper.readValue(writer.writeValueAsString(request.getPayload()),
								typeRef1);
						var isEdited = assessmentController.updateGrade(data);
						json = writer.writeValueAsString(isEdited);
						sendMessage(json);
						break;

					case "GET_STATISTICS":
						assessmentController.setRequestCode(request.getRequestCode());
						int courseId = mapper.readValue(writer.writeValueAsString(request.getPayload()),
								Integer.class);
						var stats = assessmentController.getOverviewStats(courseId);
						json = writer.writeValueAsString(stats);
						sendMessage(json);
						break;

					case "VIEW_POINT_SPECTRUM_BY_COURSE":
						assessmentController.setRequestCode(request.getRequestCode());
						int cId1 = mapper.readValue(writer.writeValueAsString(request.getPayload()),
								Integer.class);
						var res = assessmentController.getPointSpectrumByCourse(cId1);
						json = writer.writeValueAsString(res);
						sendMessage(json);
						break;

					case "VIEW_GENDER_RATIO_BY_COURSE":
						assessmentController.setRequestCode(request.getRequestCode());
						int cId2 = mapper.readValue(writer.writeValueAsString(request.getPayload()),
								Integer.class);
						var res2 = assessmentController.getGenderRatioByCourse(cId2);
						json = writer.writeValueAsString(res2);
						sendMessage(json);
						break;

					case "FILTER_ENROLLMENT_BY_COURSE":
					case "GET_COURSE_ENROLLMENT":
						enrollmentController.setRequestCode(request.getRequestCode());
						int cId3 = mapper.readValue(writer.writeValueAsString(request.getPayload()),
								Integer.class);
						var searchQueries = new HashMap<String, Object>();
						searchQueries.put("c.id", cId3);
						var res3 = enrollmentController.loadEnrollments(searchQueries);
						json = writer.writeValueAsString(res3);
						sendMessage(json);
						break;

					case "GET_COURSE_INFO":
						courseController.setRequestCode(request.getRequestCode());
						int cId4 = mapper.readValue(writer.writeValueAsString(request.getPayload()),
								Integer.class);
						var res4 = courseController.findCourseById(cId4);
						json = writer.writeValueAsString(res4);
						sendMessage(json);
						break;

					case "REGISTER_TO_COURSE":
						enrollmentController.setRequestCode(request.getRequestCode());
						TypeReference<RegisterCourseDto> registerDtoRef = new TypeReference<RegisterCourseDto>() {
						};
						String payload2 = writer.writeValueAsString(request.getPayload());
						RegisterCourseDto dto = mapper.readValue(payload2, registerDtoRef);
						var result1 = enrollmentController.enrollToACourse(dto);
						json = writer.writeValueAsString(result1);
						sendMessage(json);
						break;

					case "GET_BASIC_TRANSCRIPT_FOR_COURSE":
						enrollmentController.setRequestCode(request.getRequestCode());
						int cId5 = mapper.readValue(writer.writeValueAsString(request.getPayload()),
								Integer.class);
						var res5 = enrollmentController.getBasicTranscript(cId5);
						json = writer.writeValueAsString(res5);
						sendMessage(json);
						break;
					default:
						break;
				}
				logArea.append("Receive request: " + request.getRequestCode() + " from client!!!\n");
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

	}

	public void sendMessage(String message) {
		try {
			// System.out.println(message);
			out.writeUTF(message);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void closeConnection() {
		try {
			if (out != null)
				out.close();
			if (in != null)
				in.close();
			if (socket != null)
				socket.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}