// src/main/java/dao/subfield/SubfieldAddDao.java
package dao.subfield;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import dao.base.BaseDao;
import model.subfield.SubfieldData;

public class SubfieldAddDao extends BaseDao {
  public boolean insert(SubfieldData s) throws SQLException {
    String sql = "INSERT INTO subfields (field_id, subfield_name) VALUES (?, ?)";
    try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
      int i=1;
      if (s.getFieldId()==null) ps.setNull(i++, Types.INTEGER); else ps.setInt(i++, s.getFieldId());
      ps.setString(i++, s.getSubfieldName());
      return ps.executeUpdate()==1;
    }
  }
}
