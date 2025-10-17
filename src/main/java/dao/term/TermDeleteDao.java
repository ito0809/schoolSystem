package dao.term;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import dao.base.BaseDao;

public class TermDeleteDao extends BaseDao {
  public boolean delete(int id) throws SQLException {
    String sql = "DELETE FROM terms WHERE term_id = ?";
    try (Connection con = getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
      ps.setInt(1, id);
      return ps.executeUpdate() == 1;
    }
  }
}
