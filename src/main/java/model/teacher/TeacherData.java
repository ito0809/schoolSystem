package model.teacher;

import java.time.LocalDateTime;

public class TeacherData {
  private Integer teacherId;
  private String teacherName;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public Integer getTeacherId() { return teacherId; }
  public void setTeacherId(Integer teacherId) { this.teacherId = teacherId; }

  public String getTeacherName() { return teacherName; }
  public void setTeacherName(String teacherName) { this.teacherName = teacherName; }

  public LocalDateTime getCreatedAt() { return createdAt; }
  public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

  public LocalDateTime getUpdatedAt() { return updatedAt; }
  public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
