package servlet.subject;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.subject.SubjectDeleteDao;
import dao.subject.SubjectListDao;
import model.subject.SubjectData;

@WebServlet("/subjectdata/SubjectDeleteServlet")
public class SubjectDeleteServlet extends HttpServlet {

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
      req.getRequestDispatcher("/subject/subject_delete.jsp").forward(req, resp);
    } catch (NumberFormatException e) {
      resp.sendRedirect(req.getContextPath()+"/subjects?badid=1");
    } catch (SQLException e) { throw new ServletException(e); }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String idStr = req.getParameter("id");
    if (idStr == null) { resp.sendRedirect(req.getContextPath()+"/subjects"); return; }
    try {
      int id = Integer.parseInt(idStr);
      boolean ok = new SubjectDeleteDao().delete(id);
      resp.sendRedirect(req.getContextPath()+"/subjects?deleted="+(ok? id : "0"));
    } catch (Exception e) { throw new ServletException(e); }
  }
}
