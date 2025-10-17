package dao.term;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import dao.base.BaseDao;
import model.term.TermData;

public class TermUpdateDao extends BaseDao {
  public boolean update(TermData t) throws SQLException {
    String sql = "UPDATE terms SET term_name = ?, term_order = ? WHERE term_id = ?";
    try (Connection con = getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
      int i = 1;
      ps.setString(i++, t.getTermName());
      if (t.getTermOrder() == null) ps.setNull(i++, Types.TINYINT); else ps.setInt(i++, t.getTermOrder());
      ps.setInt(i, t.getTermId());
      return ps.executeUpdate() == 1;
    }
  }
}
