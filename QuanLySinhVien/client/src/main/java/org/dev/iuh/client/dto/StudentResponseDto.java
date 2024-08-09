package org.dev.iuh.client.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StudentResponseDto {
    private int academicDetailsId;
    private int userId;
	private String firstName;
	private String lastName;
	private LocalDate dob;
	private String gender;
	private String phoneNumber;
	private String email;
	private String address; 
    private String admissionYear;
    private String faculty;
    private String klass;
    private String studentCode;
    
    public StudentResponseDto () {}
    
	public StudentResponseDto(
			@JsonProperty("academicDetailsId") int academicDetailsId, 
			@JsonProperty("userId") int userId, 
			@JsonProperty("firstName") String firstName, 
			@JsonProperty("lastName") String lastName, 
			@JsonProperty("dob") LocalDate dob, 
			@JsonProperty("gender") String gender, 
			@JsonProperty("phoneNumber") String phoneNumber,
			@JsonProperty("email") String email, 
			@JsonProperty("address") String address, 
			@JsonProperty("admissionYear") String admissionYear, 
			@JsonProperty("faculty") String faculty, 
			@JsonProperty("klass") String klass, 
			@JsonProperty("studentCode") String studentCode) {
		
		this.academicDetailsId = academicDetailsId;
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dob = dob;
		this.gender = gender;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.address = address;
		this.admissionYear = admissionYear;
		this.faculty = faculty;
		this.klass = klass;
		this.studentCode = studentCode;
	}

	
	public int getAcademicDetailsId() {
		return academicDetailsId;
	}

	public void setAcademicDetailsId(int academicDetailsId) {
		this.academicDetailsId = academicDetailsId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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

	
	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAdmissionYear() {
		return admissionYear;
	}
	public void setAdmissionYear(String admissionYear) {
		this.admissionYear = admissionYear;
	}
	public String getFaculty() {
		return faculty;
	}
	public void setFaculty(String faculty) {
		this.faculty = faculty;
	}
	public String getKlass() {
		return klass;
	}
	public void setKlass(String klass) {
		this.klass = klass;
	}
	public String getStudentCode() {
		return studentCode;
	}
	public void setStudentCode(String studentCode) {
		this.studentCode = studentCode;
	}
    
    
}
