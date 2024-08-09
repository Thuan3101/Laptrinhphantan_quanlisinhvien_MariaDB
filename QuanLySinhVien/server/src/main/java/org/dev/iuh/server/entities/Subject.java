package org.dev.iuh.server.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Table (name = "subject")
@Entity
public class Subject implements Serializable{
	private static final long serialVersionUID = 2231637180381333378L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String code;
	private int credits;

	@OneToMany (fetch = FetchType.LAZY, mappedBy = "subject")
	private List<Assessment> assessments = new ArrayList<Assessment>();
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getCredits() {
		return credits;
	}
	public void setCredits(int credits) {
		this.credits = credits;
	}
	public Subject() {
	}
	public List<Assessment> getAssessments() {
		return assessments;
	}
	public void setAssessments(List<Assessment> assessments) {
		this.assessments = assessments;
	}

}
