package dao.classdata;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import dao.base.BaseDao;
import model.classdata.ClassData;

public class ClassUpdateDao extends BaseDao {

  /** 戻り値: 更新件数(1で成功) */
  public int update(ClassData c) throws SQLException {
    String sql = """
      UPDATE classes
         SET class_name = ?, course_id = ?, school_id = ?
       WHERE class_id = ?
    """;
    try (Connection con = getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
      int i=1;
      ps.setString(i++, c.getClassName());
      ps.setInt(i++, c.getCourseId());
      ps.setInt(i++, c.getSchoolId());
      ps.setInt(i++, c.getClassId());
      return ps.executeUpdate();
    }
  }
}
