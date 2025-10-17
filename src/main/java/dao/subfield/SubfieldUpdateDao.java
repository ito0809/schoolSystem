// src/main/java/dao/subfield/SubfieldUpdateDao.java
package dao.subfield;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import dao.base.BaseDao;
import model.subfield.SubfieldData;

public class SubfieldUpdateDao extends BaseDao {
  public boolean update(SubfieldData s) throws SQLException {
    String sql = "UPDATE subfields SET field_id=?, subfield_name=? WHERE subfield_id=?";
    try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
      int i=1;
      if (s.getFieldId()==null) ps.setNull(i++, Types.INTEGER); else ps.setInt(i++, s.getFieldId());
      ps.setString(i++, s.getSubfieldName());
      ps.setInt(i++, s.getSubfieldId());
      return ps.executeUpdate()==1;
    }
  }
}
