package model.course;

public class CourseData {
  private int courseId;
  private String courseName;
  private int schoolId;

  public int getCourseId() { return courseId; }
  public void setCourseId(int courseId) { this.courseId = courseId; }

  public String getCourseName() { return courseName; }
  public void setCourseName(String courseName) { this.courseName = courseName; }

  public int getSchoolId() { return schoolId; }
  public void setSchoolId(int schoolId) { this.schoolId = schoolId; }
}
