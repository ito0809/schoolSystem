package dao.teacher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import dao.base.BaseDao;
import model.teacher.TeacherData;

public class TeacherListDao extends BaseDao {

  public List<TeacherData> findAll(String keyword, int limit, int offset) throws SQLException {
    String sql = """
      SELECT teacher_id, teacher_name, created_at, updated_at
      FROM teachers
      WHERE (? IS NULL OR teacher_name LIKE CONCAT('%', ?, '%'))
      ORDER BY teacher_id ASC
      LIMIT ? OFFSET ?
    """;
    try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
      int i = 1;
      if (keyword == null || keyword.isBlank()) { ps.setNull(i++, Types.VARCHAR); ps.setNull(i++, Types.VARCHAR); }
      else { String kw = keyword.trim(); ps.setString(i++, kw); ps.setString(i++, kw); }
      ps.setInt(i++, limit);
      ps.setInt(i, offset);

      List<TeacherData> list = new ArrayList<>();
      try (ResultSet rs = ps.executeQuery()) { while (rs.next()) list.add(map(rs)); }
      return list;
    }
  }

  public int countAll(String keyword) throws SQLException {
    String sql = """
      SELECT COUNT(*) FROM teachers
      WHERE (? IS NULL OR teacher_name LIKE CONCAT('%', ?, '%'))
    """;
    try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
      int i = 1;
      if (keyword == null || keyword.isBlank()) { ps.setNull(i++, Types.VARCHAR); ps.setNull(i, Types.VARCHAR); }
      else { String kw = keyword.trim(); ps.setString(i++, kw); ps.setString(i, kw); }
      try (ResultSet rs = ps.executeQuery()) { rs.next(); return rs.getInt(1); }
    }
  }

  public TeacherData findById(int id) throws SQLException {
    String sql = "SELECT teacher_id, teacher_name, created_at, updated_at FROM teachers WHERE teacher_id = ?";
    try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
      ps.setInt(1, id);
      try (ResultSet rs = ps.executeQuery()) { return rs.next() ? map(rs) : null; }
    }
  }

  private TeacherData map(ResultSet rs) throws SQLException {
    TeacherData t = new TeacherData();
    t.setTeacherId(rs.getInt("teacher_id"));
    t.setTeacherName(rs.getString("teacher_name"));
    Timestamp c = rs.getTimestamp("created_at"), u = rs.getTimestamp("updated_at");
    t.setCreatedAt(c != null ? c.toLocalDateTime() : null);
    t.setUpdatedAt(u != null ? u.toLocalDateTime() : null);
    return t;
  }
}
