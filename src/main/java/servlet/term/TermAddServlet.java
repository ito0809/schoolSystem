package servlet.term;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.term.TermAddDao;
import model.term.TermData;

@WebServlet("/termdata/TermAddServlet")
public class TermAddServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.getRequestDispatcher("/term/term_add.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");

    String name   = trim(req.getParameter("term_name"));
    String orderS = trim(req.getParameter("term_order"));

    if (name == null || name.isBlank()) {
      req.setAttribute("errorMessage", "学期名は必須です。");
      req.getRequestDispatcher("/term/term_add.jsp").forward(req, resp);
      return;
    }

    try {
      TermData t = new TermData();
      t.setTermName(name.trim());

      if (orderS != null && !orderS.isBlank()) {
        int ord = Integer.parseInt(orderS);
        if (ord < 0 || ord > 127) throw new NumberFormatException();
        t.setTermOrder(ord);
      }

      boolean ok = new TermAddDao().insert(t);
      if (ok) resp.sendRedirect(req.getContextPath()+"/terms?added=1");
      else {
        req.setAttribute("errorMessage", "登録に失敗しました。");
        req.getRequestDispatcher("/term/term_add.jsp").forward(req, resp);
      }
    } catch (NumberFormatException e) {
      req.setAttribute("errorMessage", "表示順は 0〜127 の数値で入力してください。");
      req.getRequestDispatcher("/term/term_add.jsp").forward(req, resp);
    } catch (SQLException e) { throw new ServletException(e); }
  }

  private static String trim(String s){ return s==null ? null : s.trim(); }
}
