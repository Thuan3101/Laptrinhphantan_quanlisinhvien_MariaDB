package org.dev.iuh.common.dto;

import java.util.*;;
/**
 * EnrollmentEdit
 */
public class EnrollmentEdit {
    private int enrollmentId;
    private Float avgGrade;
    private List<AssessmentValue> assesments;
    
    public int getEnrollmentId() {
        return enrollmentId;
    }
    public void setEnrollmentId(int enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public List<AssessmentValue> getAssesments() {
        return assesments;
    }
    public void setAssesments(List<AssessmentValue> assesments) {
        this.assesments = assesments;
    }

    public EnrollmentEdit () {}
    
    public static class AssessmentValue {
        private int id;
        private int assessmentId;
        private int enrollmentId;
        private double value;
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
		public int getAssessmentId() {
			return assessmentId;
		}
		public void setAssessmentId(int assessmentId) {
			this.assessmentId = assessmentId;
		}
		public int getEnrollmentId() {
			return enrollmentId;
		}
		public void setEnrollmentId(int enrollmentId) {
			this.enrollmentId = enrollmentId;
		}   
        
    }

    public Float getAvgGrade() {
        return avgGrade;
    }
    public void setAvgGrade(Float avgGrade) {
        this.avgGrade = avgGrade;
    }
}