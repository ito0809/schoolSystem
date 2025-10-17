package servlet.subject;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.subject.SubjectListDao;
import model.subject.SubjectData;

@WebServlet("/subjects")
public class SubjectListServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String q = req.getParameter("q");
    Integer subfield = null;
    try { String sf = req.getParameter("subfield"); if (sf!=null && !sf.isBlank()) subfield = Integer.valueOf(sf); } catch(Exception ignore){}

    int pageNo = 1, size = 50;
    try { pageNo = Math.max(1, Integer.parseInt(req.getParameter("page"))); } catch (Exception ignore) {}
    int offset = (pageNo - 1) * size;

    try {
      SubjectListDao dao = new SubjectListDao();
      List<SubjectData> list = dao.findAll(subfield, q, size, offset);
      int total = dao.countAll(subfield, q);
      req.setAttribute("subjectList", list);
      req.setAttribute("q", q);
      req.setAttribute("subfield", subfield);
      req.setAttribute("page", pageNo);
      req.setAttribute("total", total);
      req.setAttribute("pages", (int)Math.ceil(total/(double)size));
      req.getRequestDispatcher("/subject/subject_list.jsp").forward(req, resp);
    } catch (SQLException e) { throw new ServletException(e); }
  }
}
