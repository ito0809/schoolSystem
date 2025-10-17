// src/main/java/servlet/subfield/SubfieldDeleteServlet.java
package servlet.subfield;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.subfield.SubfieldListDao;
import model.subfield.SubfieldData;

@WebServlet("/subfielddata/SubfieldDeleteServlet")
public class SubfieldDeleteServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String idStr = req.getParameter("id");
    if (idStr == null || idStr.isBlank()) {
      resp.sendRedirect(req.getContextPath() + "/subfields");
      return;
    }
    try {
      int id = Integer.parseInt(idStr);
      SubfieldData s = new SubfieldListDao().findById(id); // ← field_name 付き
      req.setAttribute("subfieldData", s);
      req.getRequestDispatcher("/subfield/subfield_delete.jsp").forward(req, resp);
    } catch (Exception e) {
      resp.sendRedirect(req.getContextPath() + "/subfields?badid=1");
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String idStr = req.getParameter("id");
    if (idStr == null || idStr.isBlank()) {
      resp.sendRedirect(req.getContextPath() + "/subfields");
      return;
    }
    try {
      int id = Integer.parseInt(idStr);
      boolean ok = new dao.subfield.SubfieldDeleteDao().deleteById(id);
      if (ok) {
        resp.sendRedirect(req.getContextPath() + "/subfields?deleted=" + id);
      } else {
        req.setAttribute("errorMessage", "削除対象が見つかりません。");
        req.setAttribute("subfieldData", new SubfieldListDao().findById(id));
        req.getRequestDispatcher("/subfield/subfield_delete.jsp").forward(req, resp);
      }
    } catch (java.sql.SQLIntegrityConstraintViolationException e) {
      // 参照されていて消せない場合
      req.setAttribute("errorMessage", "このサブフィールドは他のデータから参照されています。先に関連データを削除してください。");
      try { req.setAttribute("subfieldData", new SubfieldListDao().findById(Integer.parseInt(idStr))); } catch (Exception ignore) {}
      req.getRequestDispatcher("/subfield/subfield_delete.jsp").forward(req, resp);
    } catch (Exception e) {
      throw new ServletException(e);
    }
  }
}
