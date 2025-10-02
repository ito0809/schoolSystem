package servlet.course;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.course.CourseListDao;
import dao.course.CourseUpdateDao;
import model.course.CourseData;

@WebServlet("/coursedata/CourseUpdateServlet")
public class CourseUpdateServlet extends HttpServlet {
  @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String idStr = req.getParameter("id");
    if (idStr == null) { resp.sendRedirect(req.getContextPath()+"/courses"); return; }
    try {
      CourseData c = new CourseListDao().findById(Integer.parseInt(idStr));
      if (c == null) { resp.sendRedirect(req.getContextPath()+"/courses?notfound=1"); return; }
      req.setAttribute("courseData", c);
      req.getRequestDispatcher("/course/course_update.jsp").forward(req, resp);
    } catch (Exception e) { throw new ServletException(e); }
  }

  @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");
    try {
      CourseData c = new CourseData();
      c.setCourseId(Integer.parseInt(req.getParameter("course_id")));
      c.setCourseName(req.getParameter("course_name"));
      c.setSchoolId(Integer.parseInt(req.getParameter("school_id")));
      boolean ok = new CourseUpdateDao().update(c);
      if (!ok) {
        req.setAttribute("errorMessage","更新対象が見つかりませんでした。");
        req.setAttribute("courseData", c);
        req.getRequestDispatcher("/course/course_update.jsp").forward(req, resp);
        return;
      }
      resp.sendRedirect(req.getContextPath()+"/courses?updated="+c.getCourseId());
    } catch (Exception e) { throw new ServletException(e); }
  }
}
