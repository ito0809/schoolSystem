// src/main/java/servlet/classdata/ClassAddServlet.java
package servlet.classdata;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.classdata.ClassAddDao;
import model.classdata.ClassData;

@WebServlet("/classdata/ClassAddServlet")
public class ClassAddServlet extends HttpServlet {

  /** コース＆学校のマスタをJSPへ */
  private void loadMasters(HttpServletRequest req) throws SQLException {
    req.setAttribute("courseList",
        new dao.course.CourseListDao().findAll(null, null, 10000, 0)); // 既存どおり
    req.setAttribute("schoolList",
        new dao.school.SchoolListDao().findAll());                     // ★ 追加
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    try {
      loadMasters(req);
      req.getRequestDispatcher("/classdata/class_add.jsp").forward(req, resp);
    } catch (SQLException e) {
      throw new ServletException(e);
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");

    String name      = trim(req.getParameter("class_name"));
    String courseStr = trim(req.getParameter("course_id")); // select
    String schoolStr = trim(req.getParameter("school_id")); // ★ select に変更

    if (name==null || name.isBlank() ||
        courseStr==null || courseStr.isBlank() ||
        schoolStr==null || schoolStr.isBlank()) {
      req.setAttribute("errorMessage", "学科名／コース／学校は必須です。");
      req.setAttribute("classData", bind(req));
      try { loadMasters(req); } catch (SQLException ignore) {}
      req.getRequestDispatcher("/classdata/class_add.jsp").forward(req, resp);
      return;
    }

    try {
      ClassData c = new ClassData();
      c.setClassName(name.trim());
      c.setCourseId(Integer.parseInt(courseStr));
      c.setSchoolId(Integer.parseInt(schoolStr));

      int newId = new ClassAddDao().insert(c);
      if (newId > 0) {
        resp.sendRedirect(req.getContextPath()+"/classes?added=1");
      } else {
        req.setAttribute("errorMessage", "登録に失敗しました。");
        req.setAttribute("classData", c);
        loadMasters(req);
        req.getRequestDispatcher("/classdata/class_add.jsp").forward(req, resp);
      }

    } catch (SQLIntegrityConstraintViolationException e) {
      req.setAttribute("errorMessage", "同じ学校に同名の学科が既に存在します。学科名を変更してください。");
      req.setAttribute("classData", bind(req));
      try { loadMasters(req); } catch (SQLException ignore) {}
      req.getRequestDispatcher("/classdata/class_add.jsp").forward(req, resp);

    } catch (NumberFormatException e) {
      req.setAttribute("errorMessage","コース／学校の選択が不正です。");
      req.setAttribute("classData", bind(req));
      try { loadMasters(req); } catch (SQLException ignore) {}
      req.getRequestDispatcher("/classdata/class_add.jsp").forward(req, resp);

    } catch (SQLException e) {
      throw new ServletException(e);
    }
  }

  private static ClassData bind(HttpServletRequest req){
    ClassData c = new ClassData();
    c.setClassName(req.getParameter("class_name"));
    try { String v=req.getParameter("course_id"); if(v!=null&&!v.isBlank()) c.setCourseId(Integer.parseInt(v)); } catch(Exception ignore){}
    try { String v=req.getParameter("school_id"); if(v!=null&&!v.isBlank()) c.setSchoolId(Integer.parseInt(v)); } catch(Exception ignore){}
    return c;
  }
  private static String trim(String s){ return s==null? null: s.trim(); }
}
