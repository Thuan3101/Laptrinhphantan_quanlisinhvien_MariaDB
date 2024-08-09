package org.dev.iuh.server.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@NamedQueries({
	@NamedQuery(name = "Course.findById", query = "SELECT c, s FROM Course c JOIN c.subject s WHERE c.id = :courseId"),
	@NamedQuery(name = "Course.findByLecturerId", query = "SELECT c, s FROM Course c JOIN c.subject s WHERE c.lecturer.id = :lecturerId")
})
@Table (name = "course")
@Entity
public class Course implements Serializable{
	private static final long serialVersionUID = 340298834765763623L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String code;
	private String name;
	private String room;
	@Column (name = "academic_year")
	private String academicYear;
	@Column(name = "semester_no")
	private int semesterNo;
	
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn(name = "lecturer_id")
	private User lecturer;
	
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn(name = "subject_id")
	private Subject subject;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public User getLecturer() {
		return lecturer;
	}

	public void setLecturer(User lecturer) {
		this.lecturer = lecturer;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public int getSemesterNo() {
		return semesterNo;
	}

	public void setSemesterNo(int semesterNo) {
		this.semesterNo = semesterNo;
	}

	public Course() {
	}
	

}
