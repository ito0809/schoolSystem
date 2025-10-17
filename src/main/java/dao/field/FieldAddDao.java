package dao.field;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import dao.base.BaseDao;
import model.field.FieldData;

public class FieldAddDao extends BaseDao {
  public boolean insert(FieldData f) throws SQLException {
    String sql = "INSERT INTO fields (field_name) VALUES (?)";
    try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
      ps.setString(1, f.getFieldName());
      return ps.executeUpdate()==1;
    }
  }
}
