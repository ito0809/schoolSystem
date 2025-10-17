// src/main/java/dao/classdata/ClassDeleteDao.java
package dao.classdata;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import dao.base.BaseDao;

public class ClassDeleteDao extends BaseDao {

  /** id で削除。削除件数を返す(0=対象なし) */
  public int deleteById(int classId) throws SQLException {
    String sql = "DELETE FROM classes WHERE class_id = ?";
    try (Connection con = getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
      ps.setInt(1, classId);
      return ps.executeUpdate();
    }
  }
}
