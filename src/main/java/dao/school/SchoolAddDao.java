// src/main/java/dao/school/SchoolAddDao.java
package dao.school;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dao.base.BaseDao;
import model.school.SchoolData;

public class SchoolAddDao extends BaseDao {
  public int insert(SchoolData s) throws SQLException {
    String sql = "INSERT INTO schools (school_code, school_name) VALUES (?, ?)";
    try (Connection con = getConnection();
         PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      ps.setString(1, s.getSchoolCode());
      ps.setString(2, s.getSchoolName());
      int af = ps.executeUpdate();
      if (af>0) try (ResultSet rs = ps.getGeneratedKeys()) { if (rs.next()) return rs.getInt(1); }
      return 0;
    }
  }
}
