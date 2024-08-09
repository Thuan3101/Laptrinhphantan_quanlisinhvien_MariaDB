package org.dev.iuh.server.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Table (name = "enrollment")
@Entity
public class Enrollment implements Serializable {

	private static final long serialVersionUID = 7302530995596219692L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "student_id")
	private AcademicDetails user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "course_id")
	private Course course;

	@Column(name = "avg_grade", columnDefinition = "FLOAT DEFAULT NULL")
	private Float avgGrade;
	
	@OneToMany (fetch = FetchType.LAZY, mappedBy = "enrollment", cascade = CascadeType.ALL)
	private List<AssessmentValue> componentScoreValues = new ArrayList<AssessmentValue>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public AcademicDetails getUser() {
		return user;
	}

	public void setUser(AcademicDetails user) {
		this.user = user;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public List<AssessmentValue> getComponentScoreValues() {
		return componentScoreValues;
	}

	public void setComponentScoreValues(List<AssessmentValue> componentScoreValues) {
		this.componentScoreValues = componentScoreValues;
	}

	public Float getAvgGrade() {
		return avgGrade;
	}

	public void setAvgGrade(Float avgGrade) {
		this.avgGrade = avgGrade;
	}

	public Enrollment() {
	}
	
}
