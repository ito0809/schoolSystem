// src/main/java/model/school/SchoolData.java
package model.school;

import java.time.LocalDateTime;

public class SchoolData {
  private Integer schoolId;
  private String  schoolCode;
  private String  schoolName;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public Integer getSchoolId() { return schoolId; }
  public void setSchoolId(Integer schoolId) { this.schoolId = schoolId; }
  public String getSchoolCode() { return schoolCode; }
  public void setSchoolCode(String schoolCode) { this.schoolCode = schoolCode; }
  public String getSchoolName() { return schoolName; }
  public void setSchoolName(String schoolName) { this.schoolName = schoolName; }
  public LocalDateTime getCreatedAt() { return createdAt; }
  public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
  public LocalDateTime getUpdatedAt() { return updatedAt; }
  public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
