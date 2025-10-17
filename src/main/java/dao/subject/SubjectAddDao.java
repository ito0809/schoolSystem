package dao.subject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import dao.base.BaseDao;
import model.subject.SubjectData;

public class SubjectAddDao extends BaseDao {
  public boolean insert(SubjectData s) throws SQLException {
    String sql = "INSERT INTO subjects (subfield_id, subject_name, credits) VALUES (?, ?, ?)";
    try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
      int i=1;
      if (s.getSubfieldId()==null) ps.setNull(i++, Types.INTEGER); else ps.setInt(i++, s.getSubfieldId());
      ps.setString(i++, s.getSubjectName());
      if (s.getCredits()==null) ps.setNull(i++, Types.DECIMAL); else ps.setBigDecimal(i++, s.getCredits());
      return ps.executeUpdate()==1;
    }
  }
}
