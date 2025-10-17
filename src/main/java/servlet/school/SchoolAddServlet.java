// src/main/java/servlet/school/SchoolAddServlet.java
package servlet.school;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.school.SchoolAddDao;
import model.school.SchoolData;

@WebServlet("/schooldata/SchoolAddServlet")
public class SchoolAddServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.getRequestDispatcher("/school/school_add.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");
    String code = trim(req.getParameter("school_code"));   // null/空OK
    String name = trim(req.getParameter("school_name"));

    if (name==null || name.isBlank()) {
      req.setAttribute("errorMessage","学校名は必須です。");
      req.setAttribute("school", bind(req));
      req.getRequestDispatcher("/school/school_add.jsp").forward(req, resp);
      return;
    }
    try {
      SchoolData s = new SchoolData();
      s.setSchoolCode(code);
      s.setSchoolName(name);
      int newId = new SchoolAddDao().insert(s);
      if (newId>0) resp.sendRedirect(req.getContextPath()+"/schools?added=1");
      else {
        req.setAttribute("errorMessage","登録に失敗しました。");
        req.setAttribute("school", s);
        req.getRequestDispatcher("/school/school_add.jsp").forward(req, resp);
      }
    } catch (SQLException e) { throw new ServletException(e); }
  }

  private static SchoolData bind(HttpServletRequest req){
    SchoolData s = new SchoolData();
    s.setSchoolCode(req.getParameter("school_code"));
    s.setSchoolName(req.getParameter("school_name"));
    return s;
  }
  private static String trim(String s){ return s==null? null: s.trim(); }
}
