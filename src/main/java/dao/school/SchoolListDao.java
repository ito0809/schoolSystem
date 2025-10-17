// src/main/java/dao/school/SchoolListDao.java
package dao.school;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import dao.base.BaseDao;
import model.school.SchoolData;

public class SchoolListDao extends BaseDao {

  /** 学校マスタ全件（名前順）— 既存の簡易版 */
  public List<SchoolData> findAll() throws SQLException {
    String sql = "SELECT school_id, school_code, school_name, created_at, updated_at " +
                 "FROM schools ORDER BY school_name ASC, school_id ASC";
    try (Connection con = getConnection();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
      List<SchoolData> list = new ArrayList<>();
      while (rs.next()) list.add(map(rs));
      return list;
    }
  }

  /** 一覧（キーワード検索＋ページング）— サーブレットが呼ぶ想定 */
  public List<SchoolData> findAll(String keyword, int limit, int offset) throws SQLException {
    String sql = """
      SELECT school_id, school_code, school_name, created_at, updated_at
      FROM schools
      WHERE (? IS NULL
             OR school_name LIKE CONCAT('%', ?, '%')
             OR school_code LIKE CONCAT('%', ?, '%'))
      ORDER BY school_name ASC, school_id ASC
      LIMIT ? OFFSET ?
    """;
    try (Connection con = getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
      int i = 1;
      if (keyword == null || keyword.isBlank()) {
        ps.setNull(i++, Types.VARCHAR);
        ps.setNull(i++, Types.VARCHAR);
        ps.setNull(i++, Types.VARCHAR);
      } else {
        String kw = keyword.trim();
        ps.setString(i++, kw);
        ps.setString(i++, kw);
        ps.setString(i++, kw);
      }
      ps.setInt(i++, limit);
      ps.setInt(i,   offset);

      List<SchoolData> list = new ArrayList<>();
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) list.add(map(rs));
      }
      return list;
    }
  }

  /** 件数（同条件）— サーブレットが呼ぶ想定 */
  public int countAll(String keyword) throws SQLException {
    String sql = """
      SELECT COUNT(*)
      FROM schools
      WHERE (? IS NULL
             OR school_name LIKE CONCAT('%', ?, '%')
             OR school_code LIKE CONCAT('%', ?, '%'))
    """;
    try (Connection con = getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
      int i = 1;
      if (keyword == null || keyword.isBlank()) {
        ps.setNull(i++, Types.VARCHAR);
        ps.setNull(i++, Types.VARCHAR);
        ps.setNull(i,   Types.VARCHAR);
      } else {
        String kw = keyword.trim();
        ps.setString(i++, kw);
        ps.setString(i++, kw);
        ps.setString(i,   kw);
      }
      try (ResultSet rs = ps.executeQuery()) {
        rs.next(); return rs.getInt(1);
      }
    }
  }

  /** 単体取得（更新・削除の確認画面用）— サーブレットが呼ぶ想定 */
  public SchoolData findById(int id) throws SQLException {
    String sql = """
      SELECT school_id, school_code, school_name, created_at, updated_at
      FROM schools WHERE school_id = ?
    """;
    try (Connection con = getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
      ps.setInt(1, id);
      try (ResultSet rs = ps.executeQuery()) {
        return rs.next() ? map(rs) : null;
      }
    }
  }

  /** 存在チェック（必要に応じて使用） */
  public boolean exists(int schoolId) throws SQLException {
    String sql = "SELECT 1 FROM schools WHERE school_id = ?";
    try (Connection con = getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
      ps.setInt(1, schoolId);
      try (ResultSet rs = ps.executeQuery()) {
        return rs.next();
      }
    }
  }

  /** 共通マッピング */
  private SchoolData map(ResultSet rs) throws SQLException {
    SchoolData s = new SchoolData();
    s.setSchoolId(rs.getInt("school_id"));
    // school_code はNULL許容なので getString のまま
    try { s.setSchoolCode(rs.getString("school_code")); } catch (SQLException ignore) {}
    s.setSchoolName(rs.getString("school_name"));
    try {
      Timestamp cat = rs.getTimestamp("created_at");
      Timestamp uat = rs.getTimestamp("updated_at");
      if (cat != null) s.setCreatedAt(cat.toLocalDateTime());
      if (uat != null) s.setUpdatedAt(uat.toLocalDateTime());
    } catch (SQLException ignore) {}
    return s;
  }
}
