package dao.course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import dao.base.BaseDao;

public class CourseDeleteDao extends BaseDao {

  /** 削除（true=1件削除） */
  public boolean delete(int courseId) throws SQLException {
    String sql = "DELETE FROM course WHERE course_id = ?";
    try (Connection con = getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
      ps.setInt(1, courseId);
      return ps.executeUpdate() == 1;
    }
  }
}
