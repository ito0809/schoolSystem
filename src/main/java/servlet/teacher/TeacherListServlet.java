package servlet.teacher;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.teacher.TeacherListDao;
import model.teacher.TeacherData;

@WebServlet("/teachers")
public class TeacherListServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String q = req.getParameter("q");
    int page = 1, size = 50;
    try { page = Math.max(1, Integer.parseInt(req.getParameter("page"))); } catch (Exception ignore) {}
    int offset = (page - 1) * size;

    try {
      TeacherListDao dao = new TeacherListDao();
      List<TeacherData> list = dao.findAll(q, size, offset);
      int total = dao.countAll(q);
      req.setAttribute("teacherList", list);
      req.setAttribute("q", q);
      req.setAttribute("page", page);
      req.setAttribute("total", total);
      req.setAttribute("pages", (int)Math.ceil(total/(double)size));
      req.getRequestDispatcher("/teacher/teacher_list.jsp").forward(req, resp);
    } catch (SQLException e) { throw new ServletException(e); }
  }
}
