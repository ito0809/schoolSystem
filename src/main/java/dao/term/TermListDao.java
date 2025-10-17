package dao.term;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import dao.base.BaseDao;
import model.term.TermData;

public class TermListDao extends BaseDao {

  public List<TermData> findAll(String keyword, int limit, int offset) throws SQLException {
    String sql = """
      SELECT term_id, term_name, term_order
      FROM terms
      WHERE (? IS NULL OR term_name LIKE CONCAT('%', ?, '%'))
      ORDER BY term_id ASC
      LIMIT ? OFFSET ?
    """;
    try (Connection con = getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
      int i = 1;
      if (keyword == null || keyword.isBlank()) {
        ps.setNull(i++, Types.VARCHAR); ps.setNull(i++, Types.VARCHAR);
      } else {
        String kw = keyword.trim();
        ps.setString(i++, kw); ps.setString(i++, kw);
      }
      ps.setInt(i++, limit);
      ps.setInt(i,   offset);

      List<TermData> list = new ArrayList<>();
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) list.add(map(rs));
      }
      return list;
    }
  }

  public int countAll(String keyword) throws SQLException {
    String sql = """
      SELECT COUNT(*) FROM terms
      WHERE (? IS NULL OR term_name LIKE CONCAT('%', ?, '%'))
    """;
    try (Connection con = getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
      int i = 1;
      if (keyword == null || keyword.isBlank()) {
        ps.setNull(i++, Types.VARCHAR); ps.setNull(i++, Types.VARCHAR);
      } else {
        String kw = keyword.trim();
        ps.setString(i++, kw); ps.setString(i++, kw);
      }
      try (ResultSet rs = ps.executeQuery()) { rs.next(); return rs.getInt(1); }
    }
  }

  public TermData findById(int id) throws SQLException {
    String sql = "SELECT term_id, term_name, term_order FROM terms WHERE term_id = ?";
    try (Connection con = getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
      ps.setInt(1, id);
      try (ResultSet rs = ps.executeQuery()) {
        return rs.next() ? map(rs) : null;
      }
    }
  }

  private TermData map(ResultSet rs) throws SQLException {
    TermData t = new TermData();
    t.setTermId(rs.getInt("term_id"));
    t.setTermName(rs.getString("term_name"));
    int ord = rs.getInt("term_order");
    t.setTermOrder(rs.wasNull() ? null : ord);
    return t;
  }
}
