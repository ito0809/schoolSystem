package servlet.course;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.course.CourseListDao;
import model.course.CourseData;

@WebServlet("/courses")
public class CourseListServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    Integer schoolId = parseInt(req.getParameter("school_id"));
    String q = req.getParameter("q");

    int page = Math.max(1, parseInt(req.getParameter("page"), 1));
    int size = Math.max(1, parseInt(req.getParameter("size"), 20));
    int offset = (page - 1) * size;

    try {
      CourseListDao dao = new CourseListDao();
      int total = dao.countAll(schoolId, q);
      List<CourseData> list = dao.findAll(schoolId, q, size, offset);

      req.setAttribute("courseList", list);
      req.setAttribute("total", total);
      req.setAttribute("page", page);
      req.setAttribute("size", size);
      req.setAttribute("q", q);
      req.setAttribute("school_id", schoolId);

      req.getRequestDispatcher("/course/course_list.jsp").forward(req, resp);
    } catch (SQLException e) {
      throw new ServletException(e);
    }
  }

  private Integer parseInt(String s) {
    try { return (s == null || s.isBlank()) ? null : Integer.valueOf(s); }
    catch (NumberFormatException e) { return null; }
  }
  private int parseInt(String s, int def) {
    try { return (s == null || s.isBlank()) ? def : Integer.parseInt(s); }
    catch (NumberFormatException e) { return def; }
  }
}
