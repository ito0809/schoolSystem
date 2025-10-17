// src/main/java/servlet/school/SchoolUpdateServlet.java
package servlet.school;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.school.SchoolListDao;
import dao.school.SchoolUpdateDao;
import model.school.SchoolData;

@WebServlet("/schooldata/SchoolUpdateServlet")
public class SchoolUpdateServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String idStr = req.getParameter("id");
    if (idStr==null || idStr.isBlank()) { resp.sendRedirect(req.getContextPath()+"/schools"); return; }
    try {
      int id = Integer.parseInt(idStr);
      SchoolData s = new SchoolListDao().findById(id);
      if (s==null) { resp.sendRedirect(req.getContextPath()+"/schools?notfound=1"); return; }
      req.setAttribute("school", s);
      req.getRequestDispatcher("/school/school_update.jsp").forward(req, resp);
    } catch (Exception e) {
      resp.sendRedirect(req.getContextPath()+"/schools?badid=1");
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");
    String idStr = req.getParameter("school_id");
    String code  = req.getParameter("school_code");
    String name  = req.getParameter("school_name");

    if (idStr==null || name==null || name.isBlank()) {
      req.setAttribute("errorMessage","学校ID／学校名は必須です。");
      req.setAttribute("school", bind(req));
      req.getRequestDispatcher("/school/school_update.jsp").forward(req, resp);
      return;
    }
    try {
      SchoolData s = new SchoolData();
      s.setSchoolId(Integer.parseInt(idStr));
      s.setSchoolCode(code);
      s.setSchoolName(name.trim());
      int updated = new SchoolUpdateDao().update(s);
      if (updated==1) resp.sendRedirect(req.getContextPath()+"/schools?updated="+s.getSchoolId());
      else {
        req.setAttribute("errorMessage","更新対象が見つかりません。");
        req.setAttribute("school", s);
        req.getRequestDispatcher("/school/school_update.jsp").forward(req, resp);
      }
    } catch (NumberFormatException e) {
      req.setAttribute("errorMessage","学校IDが不正です。");
      req.setAttribute("school", bind(req));
      req.getRequestDispatcher("/school/school_update.jsp").forward(req, resp);
    } catch (SQLException e) { throw new ServletException(e); }
  }

  private static SchoolData bind(HttpServletRequest req){
    SchoolData s = new SchoolData();
    try { String v=req.getParameter("school_id"); if(v!=null&&!v.isBlank()) s.setSchoolId(Integer.parseInt(v)); } catch(Exception ignore){}
    s.setSchoolCode(req.getParameter("school_code"));
    s.setSchoolName(req.getParameter("school_name"));
    return s;
  }
}
