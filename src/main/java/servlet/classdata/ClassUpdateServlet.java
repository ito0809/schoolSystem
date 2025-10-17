// src/main/java/servlet/classdata/ClassUpdateServlet.java
package servlet.classdata;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.classdata.ClassData;

@WebServlet("/classdata/ClassUpdateServlet")
public class ClassUpdateServlet extends HttpServlet {

  private void loadMasters(HttpServletRequest req) throws SQLException {
    req.setAttribute("courseList",
        new dao.course.CourseListDao().findAll(null, null, 10000, 0));
    req.setAttribute("schoolList",
        new dao.school.SchoolListDao().findAll());
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String idStr = req.getParameter("id");
    if (idStr == null || idStr.isBlank()) {
      resp.sendRedirect(req.getContextPath()+"/classes?badid=1");
      return;
    }
    try {
      int id = Integer.parseInt(idStr);
      var c = new dao.classdata.ClassListDao().findById(id);
      if (c == null) {
        resp.sendRedirect(req.getContextPath()+"/classes?notfound=1");
        return;
      }
      req.setAttribute("classData", c);
      loadMasters(req);
      req.getRequestDispatcher("/classdata/class_update.jsp").forward(req, resp);
    } catch (NumberFormatException e) {
      resp.sendRedirect(req.getContextPath()+"/classes?badid=1");
    } catch (SQLException e) {
      throw new ServletException(e);
    }
  }

  // doPost はそのままでOK



  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");

    String idStr     = req.getParameter("class_id");
    String name      = req.getParameter("class_name");
    String courseStr = req.getParameter("course_id"); // ← select
    String schoolStr = req.getParameter("school_id"); // ← select

    if (idStr==null || name==null || name.isBlank()
        || courseStr==null || courseStr.isBlank()
        || schoolStr==null || schoolStr.isBlank()) {
      req.setAttribute("errorMessage", "学科ID／学科名／コース／学校は必須です。");
      req.setAttribute("classData", bind(req));
      try { loadMasters(req); } catch (SQLException ignore) {}
      req.getRequestDispatcher("/classdata/class_update.jsp").forward(req, resp);
      return;
    }

    try {
      ClassData c = new ClassData();
      c.setClassId(Integer.parseInt(idStr));
      c.setClassName(name.trim());
      c.setCourseId(Integer.parseInt(courseStr));
      c.setSchoolId(Integer.parseInt(schoolStr));

      int updated = new dao.classdata.ClassUpdateDao().update(c);
      if (updated == 1) {
        resp.sendRedirect(req.getContextPath()+"/classes?updated="+c.getClassId());
      } else {
        req.setAttribute("errorMessage","更新対象が見つかりませんでした。");
        req.setAttribute("classData", c);
        loadMasters(req);
        req.getRequestDispatcher("/classdata/class_update.jsp").forward(req, resp);
      }

    } catch (SQLIntegrityConstraintViolationException e) {
      req.setAttribute("errorMessage","同じ学校に同名の学科が既に存在します。学科名を変更してください。");
      req.setAttribute("classData", bind(req));
      try { loadMasters(req); } catch (SQLException ignore) {}
      req.getRequestDispatcher("/classdata/class_update.jsp").forward(req, resp);

    } catch (NumberFormatException e) {
      req.setAttribute("errorMessage","ID／コース／学校は正しく選択してください。");
      req.setAttribute("classData", bind(req));
      try { loadMasters(req); } catch (SQLException ignore) {}
      req.getRequestDispatcher("/classdata/class_update.jsp").forward(req, resp);

    } catch (SQLException e) {
      throw new ServletException(e);
    }
  }

  private static ClassData bind(HttpServletRequest req){
    ClassData c = new ClassData();
    try { String v=req.getParameter("class_id"); if(v!=null&&!v.isBlank()) c.setClassId(Integer.parseInt(v)); } catch(Exception ignore){}
    c.setClassName(req.getParameter("class_name"));
    try { String v=req.getParameter("course_id"); if(v!=null&&!v.isBlank()) c.setCourseId(Integer.parseInt(v)); } catch(Exception ignore){}
    try { String v=req.getParameter("school_id"); if(v!=null&&!v.isBlank()) c.setSchoolId(Integer.parseInt(v)); } catch(Exception ignore){}
    return c;
  }
}
