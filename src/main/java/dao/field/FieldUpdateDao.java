package dao.field;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import dao.base.BaseDao;
import model.field.FieldData;

public class FieldUpdateDao extends BaseDao {
  public boolean update(FieldData f) throws SQLException {
    String sql = "UPDATE fields SET field_name = ? WHERE field_id = ?";
    try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
      ps.setString(1, f.getFieldName());
      ps.setInt(2, f.getFieldId());
      return ps.executeUpdate()==1;
    }
  }
}
