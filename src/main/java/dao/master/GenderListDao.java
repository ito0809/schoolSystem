package dao.master;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.base.BaseDao;
import model.master.Gender;

public class GenderListDao extends BaseDao {
  public List<Gender> findAll() throws SQLException {
    String sql = "SELECT gender_id, gender_name FROM `genders` ORDER BY gender_id";
    try (Connection con = getConnection();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
      List<Gender> list = new ArrayList<>();
      while (rs.next()) {
        Gender g = new Gender();
        g.setGenderId(rs.getInt("gender_id"));
        g.setGenderName(rs.getString("gender_name"));
        list.add(g);
      }
      return list;
    }
  }
}
