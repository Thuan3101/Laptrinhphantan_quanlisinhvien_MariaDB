package org.dev.iuh.server.dto;

import org.dev.iuh.server.entities.Course;
import org.dev.iuh.server.entities.Subject;

public class CourseResponseDto {
    private int courseId;
	private String courseCode;
	private String courseName;
	private String room;
	private String academicYear;
	private int semesterNo;
    private int subjectId;
    private String subjectCode;
    private String subjectName;

    public CourseResponseDto(Course c, Subject s) {
        courseId = c.getId();
        courseCode = c.getCode();
        courseName = c.getName();
        room = c.getRoom();
        academicYear = c.getAcademicYear();
        semesterNo = c.getSemesterNo();
        subjectId = s.getId();
        subjectCode = s.getCode();
        subjectName = s.getName();
    }

    public CourseResponseDto() {
        
    }
    public int getCourseId() {
        return courseId;
    }
    public void setCourseId(int courseId) {
        this.courseId = courseId;
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
    public String getRoom() {
        return room;
    }
    public void setRoom(String room) {
        this.room = room;
    }
    public String getAcademicYear() {
        return academicYear;
    }
    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }
    public int getSemesterNo() {
        return semesterNo;
    }
    public void setSemesterNo(int semesterNo) {
        this.semesterNo = semesterNo;
    }
    public int getSubjectId() {
        return subjectId;
    }
    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }
    public String getSubjectCode() {
        return subjectCode;
    }
    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }
    public String getSubjectName() {
        return subjectName;
    }
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
