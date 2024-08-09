package org.dev.iuh.common.dto;

import java.io.Serializable;

public class EnrollmentDto implements Serializable{
	private static final long serialVersionUID = 1222L;
	private int id;
	private String studentCode;
	private String firstName;
	private String lastName;
	private String courseCode;
	private String courseName;
	private int semesterNo;
	private String academicyear;
	private String room;
	private String subjectCode;
	private int subjectId;
	private Float avgGrade;
	
	public Float getAvgGrade() {
		return avgGrade;
	}

	public void setAvgGrade(Float avgGrade) {
		this.avgGrade = avgGrade;
	}

	public EnrollmentDto() {
	}
	
	public EnrollmentDto(int id, String studentCode, String firstName, String lastName, String courseCode, String courseName,
			int semesterNo, String academicyear, String room, String subjectCode, int subjectId, Float avgGrade) {
		this.id = id;
		this.studentCode = studentCode;
		this.firstName = firstName;
		this.lastName = lastName;
		this.courseCode = courseCode;
		this.courseName = courseName;
		this.semesterNo = semesterNo;
		this.academicyear = academicyear;
		this.room = room;
		this.subjectCode = subjectCode;
		this.subjectId = subjectId;
		this.avgGrade = avgGrade;
	}


	public String getStudentCode() {
		return studentCode;
	}
	public void setStudentCode(String studentCode) {
		this.studentCode = studentCode;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getCourseCode() {
		return courseCode;
	}
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public int getSemesterNo() {
		return semesterNo;
	}
	public void setSemesterNo(int semesterNo) {
		this.semesterNo = semesterNo;
	}
	public String getAcademicyear() {
		return academicyear;
	}
	public void setAcademicyear(String academicyear) {
		this.academicyear = academicyear;
	}
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	@Override
	public String toString() {
		return "EnrollmentDto [id=" + id + ", studentCode=" + studentCode + ", firstName=" + firstName + ", lastName="
				+ lastName + ", courseCode=" + courseCode + ", courseName=" + courseName + ", semesterNo=" + semesterNo
				+ ", academicyear=" + academicyear + ", room=" + room + ", subjectCode=" + subjectCode + ", subjectId="
				+ subjectId + ", avgGrade=" + avgGrade + "]";
	}	
	

	
}

