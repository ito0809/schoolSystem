package dao.course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import dao.base.BaseDao;
import model.course.CourseData;

public class CourseUpdateDao extends BaseDao {

  /** 更新（true=1件更新） */
  public boolean update(CourseData c) throws SQLException {
    String sql = "UPDATE course SET course_name = ?, school_id = ? WHERE course_id = ?";
    try (Connection con = getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
      ps.setString(1, c.getCourseName());
      ps.setInt(2, c.getSchoolId());
      ps.setInt(3, c.getCourseId());
      return ps.executeUpdate() == 1;
    }
  }
}
