// src/main/java/servlet/subject/SubjectUpdateServlet.java
package servlet.subject;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.subfield.SubfieldListDao;
import dao.subject.SubjectListDao;
import dao.subject.SubjectUpdateDao;
import model.subject.SubjectData;

@WebServlet("/subjectdata/SubjectUpdateServlet")
public class SubjectUpdateServlet extends HttpServlet {

  private void loadMasters(HttpServletRequest req) throws SQLException {
    // プルダウン用に全サブフィールド
    req.setAttribute("subfieldList",
        new SubfieldListDao().findAll(null, null, 10_000, 0));
  }

  @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String idStr = req.getParameter("id");
    if (idStr == null || idStr.isBlank()) {
      resp.sendRedirect(req.getContextPath()+"/subjects?badid=1"); return;
    }
    try {
      int id = Integer.parseInt(idStr);
      SubjectData s = new SubjectListDao().findById(id);
      if (s == null) { resp.sendRedirect(req.getContextPath()+"/subjects?notfound=1"); return; }

      req.setAttribute("subjectData", s);
      loadMasters(req);
      req.getRequestDispatcher("/subject/subject_update.jsp").forward(req, resp);

    } catch (NumberFormatException e) {
      resp.sendRedirect(req.getContextPath()+"/subjects?badid=1");
    } catch (SQLException e) { throw new ServletException(e); }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");

    String idStr       = req.getParameter("subject_id");
    String subfieldId  = req.getParameter("subfield_id");
    String subjectName = req.getParameter("subject_name");
    String creditsStr  = req.getParameter("credits");

    // 必須チェック
    if (idStr==null || idStr.isBlank() ||
        subfieldId==null || subfieldId.isBlank() ||
        subjectName==null || subjectName.isBlank() ||
        creditsStr==null || creditsStr.isBlank()) {
      req.setAttribute("errorMessage","サブフィールド・科目名・単位は必須です。");
      keepInput(req);
      try { loadMasters(req); } catch (SQLException ignore) {}
      req.getRequestDispatcher("/subject/subject_update.jsp").forward(req, resp);
      return;
    }

    try {
      SubjectData s = new SubjectData();
      s.setSubjectId(Integer.parseInt(idStr));
      s.setSubfieldId(Integer.parseInt(subfieldId));   // select の値
      s.setSubjectName(subjectName.trim());
      s.setCredits(new java.math.BigDecimal(creditsStr.trim()));

      // ★ DAO が boolean を返す想定
      boolean ok = new SubjectUpdateDao().update(s);
      if (ok) {
        resp.sendRedirect(req.getContextPath() + "/subjects?updated=" + s.getSubjectId());
        return;
      } else {
        req.setAttribute("errorMessage","更新対象が見つかりません。");
        req.setAttribute("subjectData", s);
        loadMasters(req);
        req.getRequestDispatcher("/subject/subject_update.jsp").forward(req, resp);
        return;
      }

    } catch (Exception e) {
      req.setAttribute("errorMessage","入力値の形式が不正です。");
      keepInput(req);
      try { loadMasters(req); } catch (SQLException ignore) {}
      req.getRequestDispatcher("/subject/subject_update.jsp").forward(req, resp);
    }
  }


  private static void keepInput(HttpServletRequest req){
    SubjectData s = new SubjectData();
    try { s.setSubjectId(Integer.valueOf(req.getParameter("subject_id"))); } catch(Exception ignore){}
    try { s.setSubfieldId(Integer.valueOf(req.getParameter("subfield_id"))); } catch(Exception ignore){}
    s.setSubjectName(req.getParameter("subject_name"));
    try { s.setCredits(new java.math.BigDecimal(req.getParameter("credits"))); } catch(Exception ignore){}
    req.setAttribute("subjectData", s);
  }
}
