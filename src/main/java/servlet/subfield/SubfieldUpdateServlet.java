package servlet.subfield;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.field.FieldListDao;
import dao.subfield.SubfieldListDao;
import dao.subfield.SubfieldUpdateDao;
import model.subfield.SubfieldData;

@WebServlet("/subfielddata/SubfieldUpdateServlet")
public class SubfieldUpdateServlet extends HttpServlet {

  /** 分野マスタを JSP に渡す（共通化） */
  private void loadMasters(HttpServletRequest req) throws SQLException {
    req.setAttribute("fieldList", new FieldListDao().findAll());
  }

  // 編集画面表示
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String idStr = req.getParameter("id");
    if (idStr == null || idStr.isBlank()) {
      resp.sendRedirect(req.getContextPath()+"/subfields");
      return;
    }
    try {
      int id = Integer.parseInt(idStr);
      SubfieldData s = new SubfieldListDao().findById(id);
      if (s == null) {
        resp.sendRedirect(req.getContextPath()+"/subfields?notfound=1");
        return;
      }
      req.setAttribute("subfieldData", s);
      loadMasters(req); // セレクト用
      req.getRequestDispatcher("/subfield/subfield_update.jsp").forward(req, resp);
    } catch (Exception e) {
      resp.sendRedirect(req.getContextPath()+"/subfields?badid=1");
    }
  }

  // 更新処理
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");

    String idStr    = req.getParameter("subfield_id");
    String name     = req.getParameter("subfield_name");
    String fieldStr = req.getParameter("field_id");

    if (idStr==null || name==null || name.isBlank() || fieldStr==null || fieldStr.isBlank()) {
      req.setAttribute("errorMessage", "分野／サブフィールド名は必須です。");
      req.setAttribute("subfieldData", bind(req));
      try { loadMasters(req); } catch (SQLException ignore) {}
      req.getRequestDispatcher("/subfield/subfield_update.jsp").forward(req, resp);
      return;
    }

    try {
      int id = Integer.parseInt(idStr);
      int fieldId = Integer.parseInt(fieldStr);

      // FK 先存在チェック
      if (!new FieldListDao().exists(fieldId)) {
        req.setAttribute("errorMessage", "存在しない分野です。");
        req.setAttribute("subfieldData", bind(req));
        loadMasters(req);
        req.getRequestDispatcher("/subfield/subfield_update.jsp").forward(req, resp);
        return;
      }

      SubfieldData s = new SubfieldData();
      s.setSubfieldId(id);
      s.setFieldId(fieldId);
      s.setSubfieldName(name.trim());

      boolean ok = new SubfieldUpdateDao().update(s);
      if (ok) {
        resp.sendRedirect(req.getContextPath()+"/subfields?updated="+id);
      } else {
        req.setAttribute("errorMessage", "更新対象が見つかりません。");
        req.setAttribute("subfieldData", s);
        loadMasters(req);
        req.getRequestDispatcher("/subfield/subfield_update.jsp").forward(req, resp);
      }
    } catch (NumberFormatException | SQLException e) {
      req.setAttribute("errorMessage","入力値が不正です。");
      req.setAttribute("subfieldData", bind(req));
      try { loadMasters(req); } catch (SQLException ignore) {}
      req.getRequestDispatcher("/subfield/subfield_update.jsp").forward(req, resp);
    }
  }

  private static SubfieldData bind(HttpServletRequest req){
    SubfieldData s = new SubfieldData();
    try { String id=req.getParameter("subfield_id"); if(id!=null) s.setSubfieldId(Integer.parseInt(id)); } catch(Exception ignore){}
    try { String f=req.getParameter("field_id"); if(f!=null && !f.isBlank()) s.setFieldId(Integer.parseInt(f)); } catch(Exception ignore){}
    s.setSubfieldName(req.getParameter("subfield_name"));
    return s;
  }
}
