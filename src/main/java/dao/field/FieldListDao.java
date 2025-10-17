package dao.field;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import dao.base.BaseDao;
import model.field.FieldData;

public class FieldListDao extends BaseDao {

	 public boolean exists(int fieldId) throws SQLException {
		    String sql = "SELECT 1 FROM fields WHERE field_id = ?";
		    try (Connection con = getConnection();
		         PreparedStatement ps = con.prepareStatement(sql)) {
		      ps.setInt(1, fieldId);
		      try (ResultSet rs = ps.executeQuery()) {
		        return rs.next();
		      }
		    }
		  }

		  // これを DAO に配置
		  public List<FieldData> findAll() throws SQLException {
		    String sql = "SELECT field_id, field_name FROM fields ORDER BY field_id ASC";
		    try (Connection con = getConnection();
		         PreparedStatement ps = con.prepareStatement(sql);
		         ResultSet rs = ps.executeQuery()) {
		      List<FieldData> list = new ArrayList<>();
		      while (rs.next()) {
		        FieldData f = new FieldData();
		        f.setFieldId(rs.getInt("field_id"));
		        f.setFieldName(rs.getString("field_name"));
		        list.add(f);
		      }
		      return list;
		    }
		  }

  public List<FieldData> findAll(String keyword, int limit, int offset) throws SQLException {
    String sql = """
      SELECT field_id, field_name
      FROM fields
      WHERE (? IS NULL OR field_name LIKE CONCAT('%', ?, '%'))
      ORDER BY field_id ASC
      LIMIT ? OFFSET ?
    """;
    try (Connection con = getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

      int i = 1;
      if (keyword==null || keyword.isBlank()) {
        ps.setNull(i++, Types.VARCHAR);
        ps.setNull(i++, Types.VARCHAR);
      } else {
        String kw = keyword.trim();
        ps.setString(i++, kw);
        ps.setString(i++, kw);
      }
      ps.setInt(i++, limit);
      ps.setInt(i,   offset);

      List<FieldData> list = new ArrayList<>();
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) list.add(map(rs));
      }
      return list;
    }
  }

  public int countAll(String keyword) throws SQLException {
    String sql = """
      SELECT COUNT(*)
      FROM fields
      WHERE (? IS NULL OR field_name LIKE CONCAT('%', ?, '%'))
    """;
    try (Connection con = getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

      int i = 1;
      if (keyword==null || keyword.isBlank()) {
        ps.setNull(i++, Types.VARCHAR);
        ps.setNull(i++, Types.VARCHAR);
      } else {
        String kw = keyword.trim();
        ps.setString(i++, kw);
        ps.setString(i++, kw);
      }
      try (ResultSet rs = ps.executeQuery()) { rs.next(); return rs.getInt(1); }
    }
  }

  public FieldData findById(int id) throws SQLException {
    String sql = "SELECT field_id, field_name FROM fields WHERE field_id = ?";
    try (Connection con = getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
      ps.setInt(1, id);
      try (ResultSet rs = ps.executeQuery()) { return rs.next() ? map(rs) : null; }
    }
  }

  private FieldData map(ResultSet rs) throws SQLException {
    FieldData f = new FieldData();
    f.setFieldId(rs.getInt("field_id"));
    f.setFieldName(rs.getString("field_name"));
    return f;
  }
}
