package servlet.field;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.field.FieldListDao;
import dao.field.FieldUpdateDao;
import model.field.FieldData;

@WebServlet("/fielddata/FieldUpdateServlet")
public class FieldUpdateServlet extends HttpServlet {

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
      req.getRequestDispatcher("/field/field_update.jsp").forward(req, resp);
    } catch (NumberFormatException e) {
      resp.sendRedirect(req.getContextPath()+"/fields?badid=1");
    } catch (SQLException e) { throw new ServletException(e); }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");
    String idStr = req.getParameter("field_id");
    String name  = req.getParameter("field_name");

    if (idStr==null || name==null || name.isBlank()) {
      req.setAttribute("errorMessage","必須項目（ID／分野名）が未入力です。");
      req.setAttribute("fieldData", bind(req));
      req.getRequestDispatcher("/field/field_update.jsp").forward(req, resp);
      return;
    }

    try {
      FieldData f = new FieldData();
      f.setFieldId(Integer.parseInt(idStr.trim()));
      f.setFieldName(name.trim());

      boolean ok = new FieldUpdateDao().update(f);
      if (!ok) {
        req.setAttribute("errorMessage","更新対象が見つかりませんでした。");
        req.setAttribute("fieldData", f);
        req.getRequestDispatcher("/field/field_update.jsp").forward(req, resp);
        return;
      }
      resp.sendRedirect(req.getContextPath()+"/fields?updated="+f.getFieldId());

    } catch (NumberFormatException e) {
      req.setAttribute("errorMessage","IDは数値で入力してください。");
      req.setAttribute("fieldData", bind(req));
      req.getRequestDispatcher("/field/field_update.jsp").forward(req, resp);
    } catch (SQLException e) { throw new ServletException(e); }
  }

  private static FieldData bind(HttpServletRequest req){
    FieldData f = new FieldData();
    try { String id=req.getParameter("field_id"); if(id!=null) f.setFieldId(Integer.valueOf(id)); } catch(Exception ignore){}
    f.setFieldName(req.getParameter("field_name"));
    return f;
  }
}
