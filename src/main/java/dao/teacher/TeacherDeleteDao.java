package dao.teacher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import dao.base.BaseDao;

public class TeacherDeleteDao extends BaseDao {
  public boolean delete(int id) throws SQLException {
    String sql = "DELETE FROM teachers WHERE teacher_id = ?";
    try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
      ps.setInt(1, id);
      return ps.executeUpdate() == 1;
    }
  }
}
