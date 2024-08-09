package org.dev.iuh.common.dto;
import java.util.*;;
public class OverviewStatistics {
    private int totalCourses;
    private int totalSubjects;
    private int totalStudents;
    private Map<String, Integer> pointSpectrum;
    private Map<String, Integer> genderRatio;
    
    public int getTotalCourses() {
      return totalCourses;
    }
    public void setTotalCourses(int totalCourses) {
      this.totalCourses = totalCourses;
    }
    public int getTotalSubjects() {
      return totalSubjects;
    }
    public void setTotalSubjects(int totalSubjects) {
      this.totalSubjects = totalSubjects;
    }
    public int getTotalStudents() {
      return totalStudents;
    }
    public void setTotalStudents(int totalStudents) {
      this.totalStudents = totalStudents;
    }
    public Map<String, Integer> getPointSpectrum() {
      return pointSpectrum;
    }
    public void setPointSpectrum(Map<String, Integer> pointSpectrum) {
      this.pointSpectrum = pointSpectrum;
    }
    public Map<String, Integer> getGenderRatio() {
      return genderRatio;
    }
    public void setGenderRatio(Map<String, Integer> genderRatio) {
      this.genderRatio = genderRatio;
    }
    

}
