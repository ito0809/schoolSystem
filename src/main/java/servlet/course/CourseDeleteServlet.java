package servlet.course;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.course.CourseDeleteDao;
import dao.course.CourseListDao;
import model.course.CourseData;

@WebServlet("/coursedata/CourseDeleteServlet")
public class CourseDeleteServlet extends HttpServlet {
  @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String idStr = req.getParameter("id");
    if (idStr == null) { resp.sendRedirect(req.getContextPath()+"/courses"); return; }
    try {
      CourseData c = new CourseListDao().findById(Integer.parseInt(idStr));
      if (c == null) { resp.sendRedirect(req.getContextPath()+"/courses?notfound=1"); return; }
      req.setAttribute("courseData", c);
      req.getRequestDispatcher("/course/course_delete.jsp").forward(req, resp);
    } catch (Exception e) { throw new ServletException(e); }
  }

  @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    try {
      boolean ok = new CourseDeleteDao().delete(Integer.parseInt(req.getParameter("id")));
      resp.sendRedirect(req.getContextPath()+"/courses?deleted="+(ok? req.getParameter("id"): "0"));
    } catch (SQLException e) { throw new ServletException(e); }
  }
}
