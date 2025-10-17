package servlet.subfield;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.subfield.SubfieldListDao;
import model.subfield.SubfieldData;

@WebServlet("/subfields")
public class SubfieldListServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String q = req.getParameter("q");
    Integer field = null;
    try { String f = req.getParameter("field"); if (f!=null && !f.isBlank()) field = Integer.valueOf(f); } catch(Exception ignore){}

    int pageNo = 1, size = 50;
    try { pageNo = Math.max(1, Integer.parseInt(req.getParameter("page"))); } catch (Exception ignore) {}
    int offset = (pageNo - 1) * size;

    try {
      SubfieldListDao dao = new SubfieldListDao();
      List<SubfieldData> list = dao.findAll(field, q, size, offset);
      int total = dao.countAll(field, q);

      req.setAttribute("subfieldList", list);
      req.setAttribute("q", q);
   // servlet/subfield/SubfieldListServlet.java の doGet 内、forward 前に追加
      req.setAttribute("fieldList", new dao.field.FieldListDao().findAll());
      req.setAttribute("field", field);
      req.setAttribute("page", pageNo);
      req.setAttribute("pages", (int)Math.ceil(total/(double)size));
      req.getRequestDispatcher("/subfield/subfield_list.jsp").forward(req, resp);
    } catch (SQLException e) { throw new ServletException(e); }
  }
}
