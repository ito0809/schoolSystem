package dao.teacher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import dao.base.BaseDao;
import model.teacher.TeacherData;

public class TeacherUpdateDao extends BaseDao {
  public boolean update(TeacherData t) throws SQLException {
    String sql = "UPDATE teachers SET teacher_name = ?, updated_at = CURRENT_TIMESTAMP WHERE teacher_id = ?";
    try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
      ps.setString(1, t.getTeacherName());
      ps.setInt(2, t.getTeacherId());
      return ps.executeUpdate() == 1;
    }
  }
}
