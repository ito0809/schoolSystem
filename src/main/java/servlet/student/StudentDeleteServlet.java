package servlet.student;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.student.StudentDeleteDao;
import dao.student.StudentListDao;
import model.student.StudentData;

@WebServlet("/studentdata/StudentDeleteServlet")
public class StudentDeleteServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String idStr = req.getParameter("id");
    if (idStr == null) { resp.sendRedirect(req.getContextPath()+"/students"); return; }
    try {
      int id = Integer.parseInt(idStr);
      // ★ findById は gender_name / school_name を返す版を使用済み
      StudentData s = new StudentListDao().findById(id);
      if (s == null) { resp.sendRedirect(req.getContextPath()+"/students?notfound=1"); return; }
      req.setAttribute("studentData", s);
      req.getRequestDispatcher("/student/student_delete.jsp").forward(req, resp);
    } catch (NumberFormatException e) {
      resp.sendRedirect(req.getContextPath()+"/students?badid=1");
    } catch (SQLException e) {
      throw new ServletException(e);
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String idStr = req.getParameter("id");
    if (idStr == null) { resp.sendRedirect(req.getContextPath()+"/students"); return; }
    try {
      int id = Integer.parseInt(idStr);
      boolean ok = new StudentDeleteDao().delete(id);
      resp.sendRedirect(req.getContextPath()+"/students?deleted="+(ok? id : "0"));
    } catch (Exception e) {
      throw new ServletException(e);
    }
  }
}
