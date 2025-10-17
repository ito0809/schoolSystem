package dao.subject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import dao.base.BaseDao;
import model.subject.SubjectData;

public class SubjectListDao extends BaseDao {

  // 検索: サブフィールドID任意, キーワード(科目名), ページング
  public List<SubjectData> findAll(Integer subfieldId, String keyword, int limit, int offset) throws SQLException {
    String sql = """
      SELECT subject_id, subfield_id, subject_name, credits
      FROM subjects
      WHERE (? IS NULL OR subfield_id = ?)
        AND (? IS NULL OR subject_name LIKE CONCAT('%', ?, '%'))
      ORDER BY subject_id ASC
      LIMIT ? OFFSET ?
    """;
    try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
      int i = 1;
      if (subfieldId == null) { ps.setNull(i++, Types.INTEGER); ps.setNull(i++, Types.INTEGER); }
      else { ps.setInt(i++, subfieldId); ps.setInt(i++, subfieldId); }

      if (keyword == null || keyword.isBlank()) { ps.setNull(i++, Types.VARCHAR); ps.setNull(i++, Types.VARCHAR); }
      else { String kw = keyword.trim(); ps.setString(i++, kw); ps.setString(i++, kw); }

      ps.setInt(i++, limit);
      ps.setInt(i, offset);

      List<SubjectData> list = new ArrayList<>();
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) list.add(map(rs));
      }
      return list;
    }
  }

  public int countAll(Integer subfieldId, String keyword) throws SQLException {
    String sql = """
      SELECT COUNT(*) FROM subjects
      WHERE (? IS NULL OR subfield_id = ?)
        AND (? IS NULL OR subject_name LIKE CONCAT('%', ?, '%'))
    """;
    try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
      int i = 1;
      if (subfieldId == null) { ps.setNull(i++, Types.INTEGER); ps.setNull(i++, Types.INTEGER); }
      else { ps.setInt(i++, subfieldId); ps.setInt(i++, subfieldId); }

      if (keyword == null || keyword.isBlank()) { ps.setNull(i++, Types.VARCHAR); ps.setNull(i++, Types.VARCHAR); }
      else { String kw = keyword.trim(); ps.setString(i++, kw); ps.setString(i++, kw); }

      try (ResultSet rs = ps.executeQuery()) { rs.next(); return rs.getInt(1); }
    }
  }

  public SubjectData findById(int id) throws SQLException {
    String sql = "SELECT subject_id, subfield_id, subject_name, credits FROM subjects WHERE subject_id = ?";
    try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
      ps.setInt(1, id);
      try (ResultSet rs = ps.executeQuery()) {
        return rs.next() ? map(rs) : null;
      }
    }
  }

  private SubjectData map(ResultSet rs) throws SQLException {
    SubjectData s = new SubjectData();
    s.setSubjectId(rs.getInt("subject_id"));
    int sf = rs.getInt("subfield_id"); s.setSubfieldId(rs.wasNull() ? null : sf);
    s.setSubjectName(rs.getString("subject_name"));
    java.math.BigDecimal cr = rs.getBigDecimal("credits"); s.setCredits(cr); // null 可
    return s;
  }
}
