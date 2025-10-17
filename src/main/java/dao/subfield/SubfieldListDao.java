package dao.subfield;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import dao.base.BaseDao;
import model.subfield.SubfieldData;

public class SubfieldListDao extends BaseDao {

	public List<SubfieldData> findAll(Integer fieldId, String keyword, int limit, int offset) throws SQLException {
		
		    String sql = """
		        SELECT s.subfield_id, s.field_id, s.subfield_name,
		               f.field_name AS field_name
		        FROM subfields s
		        LEFT JOIN fields f ON s.field_id = f.field_id
		        WHERE (? IS NULL OR s.field_id = ?)
		          AND (? IS NULL
		               OR s.subfield_name LIKE CONCAT('%', ?, '%')
		               OR f.field_name     LIKE CONCAT('%', ?, '%'))
		        ORDER BY s.subfield_id ASC
		        LIMIT ? OFFSET ?
		    """;

		    try (Connection con = getConnection();
		         PreparedStatement ps = con.prepareStatement(sql)) {
		        int i = 1;

		        // fieldId 2個
		        if (fieldId == null) { ps.setNull(i++, Types.INTEGER); ps.setNull(i++, Types.INTEGER); }
		        else { ps.setInt(i++, fieldId); ps.setInt(i++, fieldId); }

		        // keyword は 3個 必ずセットする
		        if (keyword == null || keyword.isBlank()) {
		            ps.setNull(i++, Types.VARCHAR); // ? IS NULL 用
		            ps.setNull(i++, Types.VARCHAR); // LIKE1
		            ps.setNull(i++, Types.VARCHAR); // LIKE2
		        } else {
		            String kw = keyword.trim();
		            ps.setString(i++, kw); // ? IS NULL 用（NULL でないので FALSE になる）
		            ps.setString(i++, kw); // LIKE1
		            ps.setString(i++, kw); // LIKE2
		        }

		        ps.setInt(i++, limit);
		        ps.setInt(i,   offset);

		        List<SubfieldData> list = new ArrayList<>();
		        try (ResultSet rs = ps.executeQuery()) {
		            while (rs.next()) list.add(map(rs));
		        }
		        return list;
		    }
		}



	public int countAll(Integer fieldId, String keyword) throws SQLException {
	    String sql = """
	        SELECT COUNT(*)
	        FROM subfields s
	        LEFT JOIN fields f ON s.field_id = f.field_id
	        WHERE (? IS NULL OR s.field_id = ?)
	          AND (? IS NULL
	               OR s.subfield_name LIKE CONCAT('%', ?, '%')
	               OR f.field_name     LIKE CONCAT('%', ?, '%'))
	    """;

	    try (Connection con = getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {
	        int i = 1;

	        if (fieldId == null) { ps.setNull(i++, Types.INTEGER); ps.setNull(i++, Types.INTEGER); }
	        else { ps.setInt(i++, fieldId); ps.setInt(i++, fieldId); }

	        if (keyword == null || keyword.isBlank()) {
	            ps.setNull(i++, Types.VARCHAR);
	            ps.setNull(i++, Types.VARCHAR);
	            ps.setNull(i++, Types.VARCHAR);
	        } else {
	            String kw = keyword.trim();
	            ps.setString(i++, kw);
	            ps.setString(i++, kw);
	            ps.setString(i++, kw);
	        }

	        try (ResultSet rs = ps.executeQuery()) {
	            rs.next(); return rs.getInt(1);
	        }
	    }
	}


	public SubfieldData findById(int id) throws SQLException {
	    String sql = """
	        SELECT s.subfield_id, s.field_id, s.subfield_name,
	               f.field_name AS field_name
	        FROM subfields s
	        LEFT JOIN fields f ON s.field_id = f.field_id
	        WHERE s.subfield_id = ?
	    """;
	    try (Connection con = getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {
	        ps.setInt(1, id);
	        try (ResultSet rs = ps.executeQuery()) {
	            return rs.next() ? map(rs) : null;
	        }
	    }
	}


	private SubfieldData map(ResultSet rs) throws SQLException {
	    SubfieldData s = new SubfieldData();
	    s.setSubfieldId(rs.getInt("subfield_id"));
	    s.setFieldId(rs.getInt("field_id"));
	    s.setSubfieldName(rs.getString("subfield_name"));
	    s.setFieldName(rs.getString("field_name"));   // ★ 追加
	    return s;
	}
}
