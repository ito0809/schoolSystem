// src/main/java/dao/school/SchoolDeleteDao.java
package dao.school;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import dao.base.BaseDao;

public class SchoolDeleteDao extends BaseDao {
  public int deleteById(int id) throws SQLException {
    try (Connection con = getConnection();
         PreparedStatement ps = con.prepareStatement("DELETE FROM schools WHERE school_id=?")) {
      ps.setInt(1, id);
      return ps.executeUpdate();
    }
  }
}
