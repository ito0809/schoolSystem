package servlet.subject;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.subfield.SubfieldListDao;
import dao.subject.SubjectAddDao;   // 既存
import model.subject.SubjectData;  // 既存

@WebServlet("/subjectdata/SubjectAddServlet")
public class SubjectAddServlet extends HttpServlet {

  private void loadMasters(HttpServletRequest req) throws SQLException {
    // プルダウン用：全サブフィールド
    req.setAttribute("subfieldList",
        new SubfieldListDao().findAll(null, null, 10_000, 0));
  }

  @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    try { loadMasters(req); } catch (SQLException e) { throw new ServletException(e); }
    req.getRequestDispatcher("/subject/subject_add.jsp").forward(req, resp);
  }

  @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");

    String subfieldId = req.getParameter("subfield_id");
    String subjectName = req.getParameter("subject_name");
    String credits = req.getParameter("credits");

    if (subfieldId==null || subfieldId.isBlank()
        || subjectName==null || subjectName.isBlank()
        || credits==null || credits.isBlank()) {
      req.setAttribute("errorMessage", "サブフィールド・科目名・単位は必須です。");
      keepInput(req);
      try { loadMasters(req); } catch (SQLException ignore) {}
      req.getRequestDispatcher("/subject/subject_add.jsp").forward(req, resp);
      return;
    }

    try {
      SubjectData s = new SubjectData();
      s.setSubfieldId(Integer.valueOf(subfieldId));
      s.setSubjectName(subjectName.trim());
      s.setCredits(new java.math.BigDecimal(credits.trim()));

      boolean ok = new SubjectAddDao().insert(s);
      if (ok) {
        resp.sendRedirect(req.getContextPath()+"/subjects?added=1");
      } else {
        req.setAttribute("errorMessage", "登録に失敗しました。");
        keepInput(req);
        loadMasters(req);
        req.getRequestDispatcher("/subject/subject_add.jsp").forward(req, resp);
      }
    } catch (Exception e) {
      req.setAttribute("errorMessage", "入力値の形式が不正です。");
      keepInput(req);
      try { loadMasters(req); } catch (SQLException ignore) {}
      req.getRequestDispatcher("/subject/subject_add.jsp").forward(req, resp);
    }
  }

  // 入力保持
  private static void keepInput(HttpServletRequest req){
    SubjectData s = new SubjectData();
    try { s.setSubfieldId(Integer.valueOf(req.getParameter("subfield_id"))); } catch(Exception ignore){}
    s.setSubjectName(req.getParameter("subject_name"));
    try { s.setCredits(new java.math.BigDecimal(req.getParameter("credits"))); } catch(Exception ignore){}
    req.setAttribute("subjectData", s);
  }
}
