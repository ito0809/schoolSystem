package dao.student;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import dao.base.BaseDao;
import model.student.StudentData;

public class StudentUpdateDao extends BaseDao {
  public boolean update(StudentData s) throws SQLException {
    String sql = """
      UPDATE `students`
      SET student_number=?, last_name=?, first_name=?, last_name_kana=?, first_name_kana=?,
          birth_date=?, gender_id=?, postal_code=?, prefecture=?, city=?, address_line=?, tel=?,
          school_id=?, enrollment_date=?, graduation_date=?
      WHERE student_id=?
    """;
    try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
      int i=1;
      ps.setString(i++, s.getStudentNumber());
      ps.setString(i++, s.getLastName());
      ps.setString(i++, s.getFirstName());
      ps.setString(i++, s.getLastNameKana());
      ps.setString(i++, s.getFirstNameKana());
      if (s.getBirthDate()==null) ps.setNull(i++, Types.DATE); else ps.setDate(i++, Date.valueOf(s.getBirthDate()));
      if (s.getGenderId()==null) ps.setNull(i++, Types.INTEGER); else ps.setInt(i++, s.getGenderId());
      ps.setString(i++, s.getPostalCode());
      ps.setString(i++, s.getPrefecture());
      ps.setString(i++, s.getCity());
      ps.setString(i++, s.getAddressLine());
      ps.setString(i++, s.getTel());
      ps.setInt(i++, s.getSchoolId());
      if (s.getEnrollmentDate()==null) ps.setNull(i++, Types.DATE); else ps.setDate(i++, Date.valueOf(s.getEnrollmentDate()));
      if (s.getGraduationDate()==null) ps.setNull(i++, Types.DATE); else ps.setDate(i++, Date.valueOf(s.getGraduationDate()));
      ps.setInt(i, s.getStudentId());
      return ps.executeUpdate()==1;
    }
  }
}
