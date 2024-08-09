package org.dev.iuh.common.dto;

public class AssessmentDto {
    private int assessmentValueId;
    private int weight;
    private String name;
    private double value;

    public AssessmentDto(){}

    public AssessmentDto(int assessmentValueId, int weight, String name, double value) {
        this.assessmentValueId = assessmentValueId;
        this.name = name;
        this.weight = weight;
        this.value = value;
    }

    public int getWeight() {
        return weight;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getAssessmentValueId() {
        return assessmentValueId;
    }

    public void setAssessmentValueId(int assessmentValueId) {
        this.assessmentValueId = assessmentValueId;
    }

}
