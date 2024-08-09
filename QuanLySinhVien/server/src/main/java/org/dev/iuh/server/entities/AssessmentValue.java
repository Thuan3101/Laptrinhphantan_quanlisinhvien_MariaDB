package org.dev.iuh.server.entities;

import java.io.Serializable;

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

@Table (name = "assessment_value")
@Entity
@NamedQueries({
	@NamedQuery(name = "AssessmentValue.findByEnrollmentId", query = "SELECT NEW org.dev.iuh.common.dto.AssessmentDto(avlue.id, a.weight, a.name, avlue.value) FROM AssessmentValue avlue JOIN avlue.assessment a WHERE avlue.enrollment.id = :enrollmentId")
})
public class AssessmentValue implements Serializable{
	private static final long serialVersionUID = 5323400440340688124L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private double value;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "assessment_id")
	private Assessment assessment;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "enrollment_id")
	private Enrollment enrollment;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
	
	public AssessmentValue() {
	}

	public Assessment getAssessment() {
		return assessment;
	}

	public void setAssessment(Assessment assessment) {
		this.assessment = assessment;
	}

	public Enrollment getEnrollment() {
		return enrollment;
	}

	public void setEnrollment(Enrollment enrollment) {
		this.enrollment = enrollment;
	}

	
}
