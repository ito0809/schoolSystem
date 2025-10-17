package servlet.field;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.field.FieldAddDao;
import model.field.FieldData;

@WebServlet("/fielddata/FieldAddServlet")
public class FieldAddServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.getRequestDispatcher("/field/field_add.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");
    String name = trim(req.getParameter("field_name"));

    if (name==null || name.isBlank()) {
      req.setAttribute("errorMessage", "分野名は必須です。");
      req.getRequestDispatcher("/field/field_add.jsp").forward(req, resp);
      return;
    }
    try {
      FieldData f = new FieldData();
      f.setFieldName(name.trim());
      boolean ok = new FieldAddDao().insert(f);
      if (ok) resp.sendRedirect(req.getContextPath()+"/fields?added=1");
      else {
        req.setAttribute("errorMessage", "登録に失敗しました。");
        req.getRequestDispatcher("/field/field_add.jsp").forward(req, resp);
      }
    } catch (SQLException e) { throw new ServletException(e); }
  }

  private static String trim(String s){ return s==null? null : s.trim(); }
}
