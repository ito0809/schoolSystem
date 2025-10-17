// src/main/java/servlet/school/SchoolDeleteServlet.java
package servlet.school;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.school.SchoolDeleteDao;
import dao.school.SchoolListDao;
import model.school.SchoolData;

@WebServlet("/schooldata/SchoolDeleteServlet")
public class SchoolDeleteServlet extends HttpServlet {

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
      req.getRequestDispatcher("/school/school_delete.jsp").forward(req, resp);
    } catch (Exception e) {
      resp.sendRedirect(req.getContextPath()+"/schools?badid=1");
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String idStr = req.getParameter("id");
    try {
      int id = Integer.parseInt(idStr);
      int del = new SchoolDeleteDao().deleteById(id);
      if (del==1) resp.sendRedirect(req.getContextPath()+"/schools?deleted="+id);
      else resp.sendRedirect(req.getContextPath()+"/schools?notfound=1");
    } catch (Exception e) {
      resp.sendRedirect(req.getContextPath()+"/schools?badid=1");
    }
  }
}
