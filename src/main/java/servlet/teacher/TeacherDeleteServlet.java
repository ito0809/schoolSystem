package servlet.teacher;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.teacher.TeacherDeleteDao;
import dao.teacher.TeacherListDao;
import model.teacher.TeacherData;

@WebServlet("/teacherdata/TeacherDeleteServlet")
public class TeacherDeleteServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String idStr = req.getParameter("id");
    if (idStr == null) { resp.sendRedirect(req.getContextPath()+"/teachers"); return; }
    try {
      int id = Integer.parseInt(idStr);
      TeacherData t = new TeacherListDao().findById(id);
      if (t == null) { resp.sendRedirect(req.getContextPath()+"/teachers?notfound=1"); return; }
      req.setAttribute("teacher", t);
      req.getRequestDispatcher("/teacher/teacher_delete.jsp").forward(req, resp);
    } catch (NumberFormatException e) {
      resp.sendRedirect(req.getContextPath()+"/teachers?badid=1");
    } catch (SQLException e) { throw new ServletException(e); }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String idStr = req.getParameter("id");
    if (idStr == null) { resp.sendRedirect(req.getContextPath()+"/teachers"); return; }
    try {
      int id = Integer.parseInt(idStr);
      boolean ok = new TeacherDeleteDao().delete(id);
      resp.sendRedirect(req.getContextPath()+"/teachers?deleted="+(ok? id : "0"));
    } catch (Exception e) { throw new ServletException(e); }
  }
}
