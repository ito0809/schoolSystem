package servlet.term;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.term.TermListDao;
import dao.term.TermUpdateDao;
import model.term.TermData;

@WebServlet("/termdata/TermUpdateServlet")
public class TermUpdateServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String idStr = req.getParameter("id");
    if (idStr == null) { resp.sendRedirect(req.getContextPath()+"/terms"); return; }
    try {
      int id = Integer.parseInt(idStr);
      TermData t = new TermListDao().findById(id);
      if (t == null) { resp.sendRedirect(req.getContextPath()+"/terms?notfound=1"); return; }
      req.setAttribute("termData", t);
      req.getRequestDispatcher("/term/term_update.jsp").forward(req, resp);
    } catch (NumberFormatException e) {
      resp.sendRedirect(req.getContextPath()+"/terms?badid=1");
    } catch (SQLException e) { throw new ServletException(e); }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");
    String idStr   = req.getParameter("term_id");
    String name    = req.getParameter("term_name");
    String orderS  = req.getParameter("term_order");

    if (idStr==null || name==null || name.isBlank()) {
      req.setAttribute("errorMessage","必須項目（ID／学期名）が未入力です。");
      req.setAttribute("termData", bind(req));
      req.getRequestDispatcher("/term/term_update.jsp").forward(req, resp);
      return;
    }

    try {
      TermData t = new TermData();
      t.setTermId(Integer.parseInt(idStr.trim()));
      t.setTermName(name.trim());

      if (orderS != null && !orderS.isBlank()) {
        int ord = Integer.parseInt(orderS.trim());
        if (ord < 0 || ord > 127) throw new NumberFormatException();
        t.setTermOrder(ord);
      } else {
        t.setTermOrder(null);
      }

      boolean ok = new TermUpdateDao().update(t);
      if (!ok) {
        req.setAttribute("errorMessage","更新対象が見つかりませんでした。");
        req.setAttribute("termData", t);
        req.getRequestDispatcher("/term/term_update.jsp").forward(req, resp);
        return;
      }
      resp.sendRedirect(req.getContextPath()+"/terms?updated="+t.getTermId());

    } catch (NumberFormatException e) {
      req.setAttribute("errorMessage","ID／表示順は数値で入力してください（表示順は 0〜127）。");
      req.setAttribute("termData", bind(req));
      req.getRequestDispatcher("/term/term_update.jsp").forward(req, resp);
    } catch (SQLException e) { throw new ServletException(e); }
  }

  private static TermData bind(HttpServletRequest req){
    TermData t = new TermData();
    try { String id=req.getParameter("term_id"); if(id!=null) t.setTermId(Integer.valueOf(id)); } catch(Exception ignore){}
    t.setTermName(req.getParameter("term_name"));
    try { String o=req.getParameter("term_order"); if(o!=null && !o.isBlank()) t.setTermOrder(Integer.valueOf(o)); } catch(Exception ignore){}
    return t;
  }
}
