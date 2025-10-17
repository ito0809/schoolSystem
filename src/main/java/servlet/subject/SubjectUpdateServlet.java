package servlet.subject;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.subject.SubjectListDao;
import dao.subject.SubjectUpdateDao;
import model.subject.SubjectData;

@WebServlet("/subjectdata/SubjectUpdateServlet")
public class SubjectUpdateServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String idStr = req.getParameter("id");
    if (idStr == null) { resp.sendRedirect(req.getContextPath()+"/subjects"); return; }
    try {
      int id = Integer.parseInt(idStr);
      SubjectData s = new SubjectListDao().findById(id);
      if (s == null) { resp.sendRedirect(req.getContextPath()+"/subjects?notfound=1"); return; }
      req.setAttribute("subjectData", s);
      req.getRequestDispatcher("/subject/subject_update.jsp").forward(req, resp);
    } catch (NumberFormatException e) {
      resp.sendRedirect(req.getContextPath()+"/subjects?badid=1");
    } catch (SQLException e) { throw new ServletException(e); }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");
    String idStr = req.getParameter("subject_id");
    String name  = req.getParameter("subject_name");

    if (idStr==null || name==null || name.isBlank()) {
      req.setAttribute("errorMessage","必須項目（科目ID／科目名）が未入力です。");
      req.setAttribute("subjectData", bind(req));
      req.getRequestDispatcher("/subject/subject_update.jsp").forward(req, resp);
      return;
    }

    try {
      SubjectData s = bind(req);
      s.setSubjectId(Integer.parseInt(idStr.trim()));
      s.setSubjectName(name.trim());

      boolean ok = new SubjectUpdateDao().update(s);
      if (!ok) {
        req.setAttribute("errorMessage","更新対象が見つかりませんでした。");
        req.setAttribute("subjectData", s);
        req.getRequestDispatcher("/subject/subject_update.jsp").forward(req, resp);
        return;
      }
      resp.sendRedirect(req.getContextPath()+"/subjects?updated="+s.getSubjectId());
    } catch (NumberFormatException e) {
      req.setAttribute("errorMessage","数値形式が正しくありません。");
      req.setAttribute("subjectData", bind(req));
      req.getRequestDispatcher("/subject/subject_update.jsp").forward(req, resp);
    } catch (SQLException e) { throw new ServletException(e); }
  }

  private static SubjectData bind(HttpServletRequest req){
    SubjectData s = new SubjectData();
    try { String id=req.getParameter("subject_id"); if(id!=null) s.setSubjectId(Integer.valueOf(id)); } catch(Exception ignore){}
    try { String sf=req.getParameter("subfield_id"); if(sf!=null && !sf.isBlank()) s.setSubfieldId(Integer.valueOf(sf)); } catch(Exception ignore){}
    s.setSubjectName(req.getParameter("subject_name"));
    try { String cr=req.getParameter("credits"); if(cr!=null && !cr.isBlank()) s.setCredits(new BigDecimal(cr)); } catch(Exception ignore){}
    return s;
  }
}
