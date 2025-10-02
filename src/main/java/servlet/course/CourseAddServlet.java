package servlet.course;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.course.CourseAddDao;
import model.course.CourseData;

@WebServlet("/coursedata/CourseAddServlet")
public class CourseAddServlet extends HttpServlet {
  @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    req.getRequestDispatcher("/course/course_add.jsp").forward(req, resp);
  }
  @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");
    CourseData c = new CourseData();
    c.setCourseName(req.getParameter("course_name"));
    c.setSchoolId(Integer.parseInt(req.getParameter("school_id")));
    try {
      new CourseAddDao().insert(c);
      resp.sendRedirect(req.getContextPath()+"/courses?added=1");
    } catch (SQLException e) { throw new ServletException(e); }
  }
}
