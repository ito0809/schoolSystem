package servlet.classdata;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.classdata.ClassDeleteDao;   // ←実装済みのDAO名に合わせて
import dao.classdata.ClassListDao;
import model.classdata.ClassData;

@WebServlet("/classdata/ClassDeleteServlet")
public class ClassDeleteServlet extends HttpServlet { 


  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String idStr = req.getParameter("id"); // ← パラメータ名は id に統一
    if (idStr == null) { resp.sendRedirect(req.getContextPath()+"/classes"); return; }
    try {
      int id = Integer.parseInt(idStr);
      ClassData c = new ClassListDao().findById(id);
      if (c == null) { resp.sendRedirect(req.getContextPath()+"/classes?notfound=1"); return; }
      req.setAttribute("classData", c);
      req.getRequestDispatcher("/classdata/class_delete.jsp").forward(req, resp);
    } catch (NumberFormatException e) {
      resp.sendRedirect(req.getContextPath()+"/classes?badid=1");
    } catch (SQLException e) {
      throw new ServletException(e);
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String idStr = req.getParameter("id");
    if (idStr == null) { resp.sendRedirect(req.getContextPath()+"/classes"); return; }
    try {
      int id = Integer.parseInt(idStr);
      boolean ok = new ClassDeleteDao().delete(id); // ←DAOに合わせて
      resp.sendRedirect(req.getContextPath()+"/classes?deleted="+(ok? id : "0"));
    } catch (Exception e) {
      throw new ServletException(e);
    }
  }
}
