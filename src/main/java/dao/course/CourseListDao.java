package dao.course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import dao.base.BaseDao;
import model.course.CourseData;

public class CourseListDao extends BaseDao {

    /** 一覧（学校ID・キーワード・ページング） */
    public List<CourseData> findAll(Integer schoolId, String keyword,
                                    int limit, int offset) throws SQLException {
    	String sql = """
    			  SELECT c.course_id, c.course_name, c.school_id,
    			         s.school_name
    			  FROM course c
    			  LEFT JOIN schools s ON s.school_id = c.school_id
    			  WHERE (? IS NULL OR c.school_id = ?)
    			    AND (? IS NULL OR c.course_name LIKE CONCAT('%', ?, '%'))
    			  ORDER BY c.course_id
    			  LIMIT ? OFFSET ?
    			""";
    	
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            int i = 1;
            if (schoolId == null) { ps.setNull(i++, Types.INTEGER); ps.setNull(i++, Types.INTEGER); }
            else { ps.setInt(i++, schoolId); ps.setInt(i++, schoolId); }

            if (keyword == null || keyword.isBlank()) { ps.setNull(i++, Types.VARCHAR); ps.setNull(i++, Types.VARCHAR); }
            else { String kw = keyword.trim(); ps.setString(i++, kw); ps.setString(i++, kw); }

            ps.setInt(i++, limit);
            ps.setInt(i, offset);

            List<CourseData> list = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
            return list;
        }
    }

    /** 件数（同条件） */
    public int countAll(Integer schoolId, String keyword) throws SQLException {
        String sql = """
            SELECT COUNT(*)
            FROM `course`
            WHERE (? IS NULL OR school_id = ?)
              AND (? IS NULL OR course_name LIKE CONCAT('%', ?, '%'))
        """;

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            int i = 1;
            if (schoolId == null) { ps.setNull(i++, Types.INTEGER); ps.setNull(i++, Types.INTEGER); }
            else { ps.setInt(i++, schoolId); ps.setInt(i++, schoolId); }

            if (keyword == null || keyword.isBlank()) { ps.setNull(i++, Types.VARCHAR); ps.setNull(i++, Types.VARCHAR); }
            else { String kw = keyword.trim(); ps.setString(i++, kw); ps.setString(i++, kw); }

            try (ResultSet rs = ps.executeQuery()) {
                rs.next(); return rs.getInt(1);
            }
        }
    }

    /** 主キー単体取得 */
    public CourseData findById(int courseId) throws SQLException {
        String sql = """
            SELECT c.course_id, c.course_name, c.school_id,
         s.school_name
  FROM course c
  LEFT JOIN schools s ON s.school_id = c.school_id
  WHERE c.course_id = ?
""";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, courseId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        }
    }

    /** 1行マッピング */
    private CourseData map(ResultSet rs) throws SQLException {
    	  CourseData c = new CourseData();
    	  c.setCourseId(rs.getInt("course_id"));
    	  c.setCourseName(rs.getString("course_name"));
    	  int sid = rs.getInt("school_id");
    	  c.setSchoolId(rs.wasNull() ? null : sid);
    	  try { c.setSchoolName(rs.getString("school_name")); } catch (SQLException ignore) {}
    	  return c;
    	}
}
