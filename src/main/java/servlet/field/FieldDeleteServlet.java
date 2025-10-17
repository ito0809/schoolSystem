package servlet.field;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.field.FieldDeleteDao;
import dao.field.FieldListDao;
import model.field.FieldData;

@WebServlet("/fielddata/FieldDeleteServlet")
public class FieldDeleteServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String idStr = req.getParameter("id");
    if (idStr == null) { resp.sendRedirect(req.getContextPath()+"/fields"); return; }
    try {
      int id = Integer.parseInt(idStr);
      FieldData f = new FieldListDao().findById(id);
      if (f == null) { resp.sendRedirect(req.getContextPath()+"/fields?notfound=1"); return; }
      req.setAttribute("fieldData", f);
      req.getRequestDispatcher("/field/field_delete.jsp").forward(req, resp);
    } catch (NumberFormatException e) {
      resp.sendRedirect(req.getContextPath()+"/fields?badid=1");
    } catch (SQLException e) { throw new ServletException(e); }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String idStr = req.getParameter("id");
    if (idStr == null) { resp.sendRedirect(req.getContextPath()+"/fields"); return; }
    try {
      int id = Integer.parseInt(idStr);
      boolean ok = new FieldDeleteDao().delete(id);
      resp.sendRedirect(req.getContextPath()+"/fields?deleted="+(ok? id : "0"));
    } catch (SQLIntegrityConstraintViolationException e) {
      // subfields 参照中など
      resp.sendRedirect(req.getContextPath()+"/fields?fkviolation=1");
    } catch (Exception e) { throw new ServletException(e); }
  }
}
