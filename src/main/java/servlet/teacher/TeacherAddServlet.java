package servlet.teacher;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.teacher.TeacherAddDao;
import model.teacher.TeacherData;

@WebServlet("/teacherdata/TeacherAddServlet")
public class TeacherAddServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.getRequestDispatcher("/teacher/teacher_add.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");
    String name = req.getParameter("teacher_name");
    if (name == null || name.isBlank()) {
      req.setAttribute("errorMessage", "教員名を入力してください。");
      req.getRequestDispatcher("/teacher/teacher_add.jsp").forward(req, resp);
      return;
    }
    try {
      TeacherData t = new TeacherData(); t.setTeacherName(name.trim());
      boolean ok = new TeacherAddDao().insert(t);
      if (ok) resp.sendRedirect(req.getContextPath()+"/teachers?added=1");
      else { req.setAttribute("errorMessage","登録に失敗しました。");
             req.getRequestDispatcher("/teacher/teacher_add.jsp").forward(req, resp); }
    } catch (SQLException e) { throw new ServletException(e); }
  }
}
