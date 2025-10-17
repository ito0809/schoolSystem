package dao.term;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import dao.base.BaseDao;
import model.term.TermData;

public class TermAddDao extends BaseDao {
  public boolean insert(TermData t) throws SQLException {
    String sql = "INSERT INTO terms (term_name, term_order) VALUES (?, ?)";
    try (Connection con = getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
      int i = 1;
      ps.setString(i++, t.getTermName());
      if (t.getTermOrder() == null) ps.setNull(i, Types.TINYINT); else ps.setInt(i, t.getTermOrder());
      return ps.executeUpdate() == 1;
    }
  }
}
