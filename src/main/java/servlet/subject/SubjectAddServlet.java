package servlet.subject;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.subfield.SubfieldListDao;     // ← これだけ使う
import dao.subject.SubjectAddDao;
import model.subject.SubjectData;

@WebServlet("/subjectdata/SubjectAddServlet")
public class SubjectAddServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.getRequestDispatcher("/subject/subject_add.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");

    String name        = trim(req.getParameter("subject_name"));
    String subfieldStr = trim(req.getParameter("subfield_id"));
    String creditsStr  = trim(req.getParameter("credits"));

    if (name == null || name.isBlank()) {
      req.setAttribute("errorMessage","科目名は必須です。");
      req.setAttribute("subjectData", bind(req));
      req.getRequestDispatcher("/subject/subject_add.jsp").forward(req, resp);
      return;
    }

    try {
      SubjectData s = bind(req);
      s.setSubjectName(name);

      Integer sfId = null;
      if (subfieldStr != null && !subfieldStr.isBlank()) sfId = Integer.valueOf(subfieldStr);
      if (creditsStr  != null && !creditsStr.isBlank())  s.setCredits(new BigDecimal(creditsStr));
      s.setSubfieldId(sfId);

      // ★ Subfield の存在確認（SubfieldListDao の findById を利用）
      if (sfId != null && new SubfieldListDao().findById(sfId) == null) {
        req.setAttribute("errorMessage", "存在しないサブフィールドIDです。先にマスタへ登録するか、既存IDを指定してください。");
        req.setAttribute("subjectData", s);
        req.getRequestDispatcher("/subject/subject_add.jsp").forward(req, resp);
        return;
      }

      boolean ok = new SubjectAddDao().insert(s);
      if (ok) {
        resp.sendRedirect(req.getContextPath()+"/subjects?added=1");
      } else {
        req.setAttribute("errorMessage","登録に失敗しました。");
        req.setAttribute("subjectData", s);
        req.getRequestDispatcher("/subject/subject_add.jsp").forward(req, resp);
      }

    } catch (NumberFormatException e) {
      req.setAttribute("errorMessage","数値形式が正しくありません。");
      req.setAttribute("subjectData", bind(req));
      req.getRequestDispatcher("/subject/subject_add.jsp").forward(req, resp);

    } catch (SQLIntegrityConstraintViolationException e) {
      req.setAttribute("errorMessage","サブフィールドIDが不正です（外部キー制約に違反）。既存のIDを指定してください。");
      req.setAttribute("subjectData", bind(req));
      req.getRequestDispatcher("/subject/subject_add.jsp").forward(req, resp);

    } catch (SQLException e) {
      throw new ServletException(e);
    }
  }

  private static SubjectData bind(HttpServletRequest req){
    SubjectData s = new SubjectData();
    try { String id=req.getParameter("subject_id"); if(id!=null) s.setSubjectId(Integer.valueOf(id)); } catch(Exception ignore){}
    try { String sf=req.getParameter("subfield_id"); if(sf!=null && !sf.isBlank()) s.setSubfieldId(Integer.valueOf(sf)); } catch(Exception ignore){}
    s.setSubjectName(trim(req.getParameter("subject_name")));
    try { String cr=req.getParameter("credits"); if(cr!=null && !cr.isBlank()) s.setCredits(new java.math.BigDecimal(cr)); } catch(Exception ignore){}
    return s;
  }
  private static String trim(String s){ return s==null? null: s.trim(); }
}
