package org.dev.iuh.common.dto;

public class BasicTranscript {
    private String firstName;
    private String lastName;
    private String studentCode;
    private Float avgGrade;

    public BasicTranscript() {}

    public BasicTranscript(String firstName, String lastName, String studentCode, Float avgGrade) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentCode = studentCode;
        this.avgGrade = avgGrade;
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
    public String getStudentCode() {
        return studentCode;
    }
    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }
    public Float getAvgGrade() {
        return avgGrade;
    }
    public void setAvgGrade(Float avgGrade) {
        this.avgGrade = avgGrade;
    }

    
}
