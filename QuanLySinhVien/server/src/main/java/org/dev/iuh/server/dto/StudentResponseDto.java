package org.dev.iuh.server.dto;

import java.io.Serializable;
import java.time.LocalDate;

import org.dev.iuh.common.enums.Gender;
import org.dev.iuh.server.entities.AcademicDetails;
import org.dev.iuh.server.entities.User;


public class StudentResponseDto implements Serializable {
    private int academicDetailsId;
    private int userId;
	private String firstName;
	private String lastName;
	private LocalDate dob;
	private Gender gender;
	private String phoneNumber;
	private String email;
	private String address; 
    private String admissionYear;
    private String faculty;
    private String klass;
    private String studentCode;

    public StudentResponseDto() {

    }

    public StudentResponseDto(AcademicDetails academicDetails, User user) {
        this.academicDetailsId = academicDetails.getId();
        this.userId = user.getId();
        this.address = user.getAddress();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.phoneNumber = user.getPhoneNumber();
        this.dob = user.getDob();
        this.gender = user.getGender();
        this.email = user.getEmail();
        this.admissionYear = academicDetails.getAdmissionYear();
        this.faculty = academicDetails.getFaculty();
        this.klass = academicDetails.getKlass();
        this.studentCode = academicDetails.getStudentCode();
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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
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

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    
}
