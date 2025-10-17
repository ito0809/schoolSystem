// src/main/java/dao/school/SchoolUpdateDao.java
package dao.school;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import dao.base.BaseDao;
import model.school.SchoolData;

public class SchoolUpdateDao extends BaseDao {
  public int update(SchoolData s) throws SQLException {
    String sql = "UPDATE schools SET school_code=?, school_name=? WHERE school_id=?";
    try (Connection con = getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
      ps.setString(1, s.getSchoolCode());
      ps.setString(2, s.getSchoolName());
      ps.setInt(3, s.getSchoolId());
      return ps.executeUpdate();
    }
  }
}
