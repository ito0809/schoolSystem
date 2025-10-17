package dao.teacher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import dao.base.BaseDao;
import model.teacher.TeacherData;

public class TeacherAddDao extends BaseDao {
  public boolean insert(TeacherData t) throws SQLException {
    String sql = "INSERT INTO teachers (teacher_name) VALUES (?)";
    try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
      ps.setString(1, t.getTeacherName());
      return ps.executeUpdate() == 1;
    }
  }
}
