package model.classdata;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ClassData implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer classId;        // PK
    private String  className;      // 学科名
    private Integer courseId;       // コースID
    private Integer schoolId;       // 学校ID
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // ★ 表示用の名称
    private String  courseName;     // コース名（JOINで取得）
    private String  schoolName;     // 学校名（JOINで取得）

    public ClassData() {}
    public ClassData(Integer classId, String className, Integer courseId, Integer schoolId) {
        this.classId = classId;
        this.className = className;
        this.courseId = courseId;
        this.schoolId = schoolId;
    }

    // --- getter / setter ---
    public Integer getClassId() { return classId; }
    public void setClassId(Integer classId) { this.classId = classId; }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }

    public Integer getCourseId() { return courseId; }
    public void setCourseId(Integer courseId) { this.courseId = courseId; }

    public Integer getSchoolId() { return schoolId; }
    public void setSchoolId(Integer schoolId) { this.schoolId = schoolId; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    // --- 表示用名称 ---
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public String getSchoolName() { return schoolName; }
    public void setSchoolName(String schoolName) { this.schoolName = schoolName; }

    @Override
    public String toString() {
        return "ClassData{" +
                "classId=" + classId +
                ", className='" + className + '\'' +
                ", courseId=" + courseId +
                ", schoolId=" + schoolId +
                ", courseName='" + courseName + '\'' +
                ", schoolName='" + schoolName + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
