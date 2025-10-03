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
			  SELECT
			    s.student_id, s.student_number, s.last_name, s.first_name, s.last_name_kana, s.first_name_kana,
			    s.birth_date, s.gender_id, s.postal_code, s.prefecture, s.city, s.address_line, s.tel,
			    s.school_id, s.enrollment_date, s.graduation_date, s.created_at, s.updated_at,
			    g.gender_name   AS gender_name,      -- ★ 追加
			    sc.school_name  AS school_name       -- ★ 追加
			  FROM `students` s
			  LEFT JOIN `genders` g ON s.gender_id = g.gender_id           -- ★ 性別マスタ
			  LEFT JOIN `schools` sc ON s.school_id = sc.school_id         -- ★ 学校マスタ
			  WHERE (? IS NULL OR s.school_id = ?)
			    AND ( ? IS NULL
			          OR s.last_name       LIKE CONCAT('%', ?, '%')
			          OR s.first_name      LIKE CONCAT('%', ?, '%')
			          OR s.student_number  LIKE CONCAT('%', ?, '%')
			          OR s.last_name_kana  LIKE CONCAT('%', ?, '%')
			          OR s.first_name_kana LIKE CONCAT('%', ?, '%')
			        )
			  ORDER BY s.student_id ASC
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
//dao.student.StudentListDao

public StudentData findById(int id) throws SQLException {
 String sql = """
   SELECT
     s.student_id, s.student_number, s.last_name, s.first_name, s.last_name_kana, s.first_name_kana,
     s.birth_date, s.gender_id, s.postal_code, s.prefecture, s.city, s.address_line, s.tel,
     s.school_id, s.enrollment_date, s.graduation_date, s.created_at, s.updated_at,
     g.gender_name  AS gender_name,       -- ★ 追加
     sc.school_name AS school_name        -- ★ 追加
   FROM `students` s
   LEFT JOIN `genders` g ON s.gender_id = g.gender_id       -- ★ 性別マスタ
   LEFT JOIN `schools` sc ON s.school_id = sc.school_id     -- ★ 学校マスタ
   WHERE s.student_id = ?
 """;
 try (Connection con = getConnection();
      PreparedStatement ps = con.prepareStatement(sql)) {
   ps.setInt(1, id);
   try (ResultSet rs = ps.executeQuery()) {
     return rs.next() ? map(rs) : null;
   }
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
    s.setGenderName(rs.getString("gender_name"));   // 追加
    s.setSchoolName(rs.getString("school_name"));   // 追加

    return s;
  }
}
