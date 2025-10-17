// src/main/java/servlet/subfield/SubfieldAddServlet.java
package servlet.subfield;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.field.FieldListDao;
import dao.subfield.SubfieldAddDao;
import model.subfield.SubfieldData;

@WebServlet("/subfielddata/SubfieldAddServlet")
public class SubfieldAddServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    try {
      req.setAttribute("fieldList", new FieldListDao().findAll()); // ★ 追加
    } catch (SQLException e) { throw new ServletException(e); }
    req.getRequestDispatcher("/subfield/subfield_add.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");
    String name = trim(req.getParameter("subfield_name"));
    String fieldStr = trim(req.getParameter("field_id"));

    if (name==null || name.isBlank() || fieldStr==null || fieldStr.isBlank()) {
      req.setAttribute("errorMessage", "分野名とサブフィールド名は必須です。");
      req.setAttribute("subfieldData", bind(req));
      try { req.setAttribute("fieldList", new FieldListDao().findAll()); } catch (SQLException ignore) {}
      req.getRequestDispatcher("/subfield/subfield_add.jsp").forward(req, resp);
      return;
    }

    try {
      int fieldId = Integer.parseInt(fieldStr);
      if (!new FieldListDao().exists(fieldId)) {
        req.setAttribute("errorMessage","存在しない分野です。先に分野を作成するか既存を選択してください。");
        req.setAttribute("subfieldData", bind(req));
        req.setAttribute("fieldList", new FieldListDao().findAll()); // ★ 再セット
        req.getRequestDispatcher("/subfield/subfield_add.jsp").forward(req, resp);
        return;
      }

      SubfieldData s = new SubfieldData();
      s.setFieldId(fieldId);
      s.setSubfieldName(name.trim());

      boolean ok = new SubfieldAddDao().insert(s);
      if (ok) resp.sendRedirect(req.getContextPath()+"/subfields?added=1");
      else {
        req.setAttribute("errorMessage","登録に失敗しました。");
        req.setAttribute("subfieldData", s);
        req.setAttribute("fieldList", new FieldListDao().findAll());
        req.getRequestDispatcher("/subfield/subfield_add.jsp").forward(req, resp);
      }
    } catch (NumberFormatException e) {
      req.setAttribute("errorMessage","分野の選択が不正です。");
      req.setAttribute("subfieldData", bind(req));
      try { req.setAttribute("fieldList", new FieldListDao().findAll()); } catch (SQLException ignore) {}
      req.getRequestDispatcher("/subfield/subfield_add.jsp").forward(req, resp);
    } catch (SQLException e) {
      throw new ServletException(e);
    }
  }

  private static SubfieldData bind(HttpServletRequest req){
    SubfieldData s = new SubfieldData();
    try { String f=req.getParameter("field_id"); if(f!=null && !f.isBlank()) s.setFieldId(Integer.valueOf(f)); } catch(Exception ignore){}
    s.setSubfieldName(trim(req.getParameter("subfield_name")));
    return s;
  }
  private static String trim(String s){ return s==null? null: s.trim(); }
}
