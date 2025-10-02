package dao.course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dao.base.BaseDao;
import model.course.CourseData;

public class CourseAddDao extends BaseDao {

  /** 追加（true=1件追加） */
  public boolean insert(CourseData c) throws SQLException {
    String sql = "INSERT INTO course (course_name, school_id) VALUES (?, ?)";
    try (Connection con = getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
      ps.setString(1, c.getCourseName());
      ps.setInt(2, c.getSchoolId());
      return ps.executeUpdate() == 1;
    }
  }

  /** 追加して採番IDを返す（必要ならこちらを利用） */
  public Integer insertAndReturnId(CourseData c) throws SQLException {
    String sql = "INSERT INTO course (course_name, school_id) VALUES (?, ?)";
    try (Connection con = getConnection();
         PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      ps.setString(1, c.getCourseName());
      ps.setInt(2, c.getSchoolId());
      if (ps.executeUpdate() != 1) return null;
      try (ResultSet rs = ps.getGeneratedKeys()) {
        return rs.next() ? rs.getInt(1) : null;
      }
    }
  }
}
