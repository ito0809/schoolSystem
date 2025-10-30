// src/main/java/servlet/course/CourseUpdateServlet.java
package servlet.course;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.course.CourseListDao;
import dao.course.CourseUpdateDao;
import dao.school.SchoolListDao;
import model.course.CourseData;

@WebServlet("/coursedata/CourseUpdateServlet")
public class CourseUpdateServlet extends HttpServlet {

  /** 学校マスタをJSPへ渡す */
  private void loadMasters(HttpServletRequest req) throws SQLException {
    req.setAttribute("schoolList", new SchoolListDao().findAll()); // 全件
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String idStr = req.getParameter("id");
    if (idStr == null || idStr.isBlank()) {
      resp.sendRedirect(req.getContextPath()+"/courses?badid=1");
      return;
    }
    try {
      int id = Integer.parseInt(idStr);
      CourseData c = new CourseListDao().findById(id);
      if (c == null) {
        resp.sendRedirect(req.getContextPath()+"/courses?notfound=1");
        return;
      }
      req.setAttribute("courseData", c);
      loadMasters(req); // ← 学校候補を渡す
      req.getRequestDispatcher("/course/course_update.jsp").forward(req, resp);
    } catch (NumberFormatException e) {
      resp.sendRedirect(req.getContextPath()+"/courses?badid=1");
    } catch (SQLException e) { throw new ServletException(e); }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");

    String idStr      = req.getParameter("course_id");
    String name       = req.getParameter("course_name");
    String schoolIdS  = req.getParameter("school_id"); // ← selectの値

    if (idStr==null||idStr.isBlank() || name==null||name.isBlank() ||
        schoolIdS==null||schoolIdS.isBlank()) {
      req.setAttribute("errorMessage","コース名・学校は必須です。");
      bindBackAndForward(req, resp);
      return;
    }

    try {
      CourseData c = new CourseData();
      c.setCourseId(Integer.parseInt(idStr));
      c.setCourseName(name.trim());
      c.setSchoolId(Integer.parseInt(schoolIdS));     // ← 数値化

      boolean ok = new CourseUpdateDao().update(c);
      if (ok) {
        resp.sendRedirect(req.getContextPath()+"/courses?updated="+c.getCourseId());
      } else {
        req.setAttribute("errorMessage","更新対象が見つかりません。");
        req.setAttribute("courseData", c);
        loadMasters(req);
        req.getRequestDispatcher("/course/course_update.jsp").forward(req, resp);
      }
    } catch (Exception e) {
      req.setAttribute("errorMessage","入力値の形式が不正です。");
      bindBackAndForward(req, resp);
    }
  }

  /** 入力保持＋マスタ再読込＋更新画面へ戻す */
  private void bindBackAndForward(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    CourseData c = new CourseData();
    try { c.setCourseId(Integer.parseInt(req.getParameter("course_id"))); } catch(Exception ignore){}
    c.setCourseName(req.getParameter("course_name"));
    try { c.setSchoolId(Integer.parseInt(req.getParameter("school_id"))); } catch(Exception ignore){}
    req.setAttribute("courseData", c);
    try { loadMasters(req); } catch (SQLException ignore) {}
    req.getRequestDispatcher("/course/course_update.jsp").forward(req, resp);
  }
}
