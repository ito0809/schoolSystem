package dao.student;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import dao.base.BaseDao;
import model.student.StudentData;

public class StudentListDao extends BaseDao {

  /** 一覧（学校ID / キーワード / ページング） */
  public List<StudentData> findAll(Integer schoolId, String keyword, int limit, int offset) throws SQLException {
    String sql = """
      SELECT student_id, student_number, last_name, first_name, last_name_kana, first_name_kana,
             birth_date, gender_id, postal_code, prefecture, city, address_line, tel,
             school_id, enrollment_date, graduation_date, created_at, updated_at
      FROM `students`
      WHERE (? IS NULL OR school_id = ?)
        AND ( ? IS NULL
              OR last_name       LIKE CONCAT('%', ?, '%')
              OR first_name      LIKE CONCAT('%', ?, '%')
              OR student_number  LIKE CONCAT('%', ?, '%')
              OR last_name_kana  LIKE CONCAT('%', ?, '%')
              OR first_name_kana LIKE CONCAT('%', ?, '%')
            )
      ORDER BY student_id ASC
      LIMIT ? OFFSET ?
    """;

    try (Connection con = getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

      int i = 1;
      if (schoolId == null) { ps.setNull(i++, Types.INTEGER); ps.setNull(i++, Types.INTEGER); }
      else { ps.setInt(i++, schoolId); ps.setInt(i++, schoolId); }

      if (keyword == null || keyword.isBlank()) {
        for (int k=0; k<6; k++) ps.setNull(i++, Types.VARCHAR);
      } else {
        String kw = keyword.trim();
        ps.setString(i++, kw); // ? IS NULL 用
        ps.setString(i++, kw);
        ps.setString(i++, kw);
        ps.setString(i++, kw);
        ps.setString(i++, kw);
        ps.setString(i++, kw);
      }

      ps.setInt(i++, limit);
      ps.setInt(i,   offset);

      List<StudentData> list = new ArrayList<>();
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) list.add(map(rs));
      }
      return list;
    }
  }

  /** 件数 */
  public int countAll(Integer schoolId, String keyword) throws SQLException {
    String sql = """
      SELECT COUNT(*)
      FROM `students`
      WHERE (? IS NULL OR school_id = ?)
        AND ( ? IS NULL
              OR last_name       LIKE CONCAT('%', ?, '%')
              OR first_name      LIKE CONCAT('%', ?, '%')
              OR student_number  LIKE CONCAT('%', ?, '%')
              OR last_name_kana  LIKE CONCAT('%', ?, '%')
              OR first_name_kana LIKE CONCAT('%', ?, '%')
            )
    """;

    try (Connection con = getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

      int i = 1;
      if (schoolId == null) { ps.setNull(i++, Types.INTEGER); ps.setNull(i++, Types.INTEGER); }
      else { ps.setInt(i++, schoolId); ps.setInt(i++, schoolId); }

      if (keyword == null || keyword.isBlank()) {
        for (int k=0; k<6; k++) ps.setNull(i++, Types.VARCHAR);
      } else {
        String kw = keyword.trim();
        ps.setString(i++, kw); // ? IS NULL 用
        ps.setString(i++, kw); ps.setString(i++, kw); ps.setString(i++, kw);
        ps.setString(i++, kw); ps.setString(i++, kw);
      }

      try (ResultSet rs = ps.executeQuery()) { rs.next(); return rs.getInt(1); }
    }
  }

  /** 主キー取得 */
  public StudentData findById(int id) throws SQLException {
    String sql = """
      SELECT student_id, student_number, last_name, first_name, last_name_kana, first_name_kana,
             birth_date, gender_id, postal_code, prefecture, city, address_line, tel,
             school_id, enrollment_date, graduation_date, created_at, updated_at
      FROM `students` WHERE student_id = ?
    """;
    try (Connection con = getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
      ps.setInt(1, id);
      try (ResultSet rs = ps.executeQuery()) { return rs.next() ? map(rs) : null; }
    }
  }

  private StudentData map(ResultSet rs) throws SQLException {
    StudentData s = new StudentData();
    s.setStudentId(rs.getInt("student_id"));
    s.setStudentNumber(rs.getString("student_number"));
    s.setLastName(rs.getString("last_name"));
    s.setFirstName(rs.getString("first_name"));
    s.setLastNameKana(rs.getString("last_name_kana"));
    s.setFirstNameKana(rs.getString("first_name_kana"));
    Date bd = rs.getDate("birth_date");
    s.setBirthDate(bd != null ? bd.toLocalDate() : null);
    int g = rs.getInt("gender_id"); s.setGenderId(rs.wasNull() ? null : g);
    s.setPostalCode(rs.getString("postal_code"));
    s.setPrefecture(rs.getString("prefecture"));
    s.setCity(rs.getString("city"));
    s.setAddressLine(rs.getString("address_line"));
    s.setTel(rs.getString("tel"));
    s.setSchoolId(rs.getInt("school_id"));
    Date ed = rs.getDate("enrollment_date");
    s.setEnrollmentDate(ed != null ? ed.toLocalDate() : null);
    Date gd = rs.getDate("graduation_date");
    s.setGraduationDate(gd != null ? gd.toLocalDate() : null);
    Timestamp cat = rs.getTimestamp("created_at");
    Timestamp uat = rs.getTimestamp("updated_at");
    s.setCreatedAt(cat != null ? cat.toLocalDateTime() : null);
    s.setUpdatedAt(uat != null ? uat.toLocalDateTime() : null);
    return s;
  }
}
