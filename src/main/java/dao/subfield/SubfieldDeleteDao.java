// src/main/java/dao/subfield/SubfieldDeleteDao.java
package dao.subfield;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import dao.base.BaseDao;

public class SubfieldDeleteDao extends BaseDao {
	  public boolean deleteById(int id) throws SQLException {
	    String sql = "DELETE FROM subfields WHERE subfield_id = ?";
	    try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
	      ps.setInt(1, id);
	      return ps.executeUpdate() == 1;
	    }
	  }
	}
