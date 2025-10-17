package servlet.teacher;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.teacher.TeacherListDao;
import dao.teacher.TeacherUpdateDao;
import model.teacher.TeacherData;

@WebServlet("/teacherdata/TeacherUpdateServlet")
public class TeacherUpdateServlet extends HttpServlet {

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
      req.getRequestDispatcher("/teacher/teacher_update.jsp").forward(req, resp);
    } catch (NumberFormatException e) {
      resp.sendRedirect(req.getContextPath()+"/teachers?badid=1");
    } catch (SQLException e) { throw new ServletException(e); }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");
    String idStr = req.getParameter("teacher_id");
    String name  = req.getParameter("teacher_name");
    if (idStr == null || name == null || name.isBlank()) {
      req.setAttribute("errorMessage", "教員IDと教員名は必須です。");
      req.setAttribute("teacher", bind(req));
      req.getRequestDispatcher("/teacher/teacher_update.jsp").forward(req, resp);
      return;
    }
    try {
      TeacherData t = bind(req);
      t.setTeacherId(Integer.parseInt(idStr));
      t.setTeacherName(name.trim());
      boolean ok = new TeacherUpdateDao().update(t);
      if (!ok) {
        req.setAttribute("errorMessage", "更新対象が見つかりませんでした。");
        req.setAttribute("teacher", t);
        req.getRequestDispatcher("/teacher/teacher_update.jsp").forward(req, resp);
        return;
      }
      resp.sendRedirect(req.getContextPath()+"/teachers?updated="+t.getTeacherId());
    } catch (SQLException | NumberFormatException e) { throw new ServletException(e); }
  }

  private TeacherData bind(HttpServletRequest req) {
    TeacherData t = new TeacherData();
    try { t.setTeacherId(Integer.parseInt(req.getParameter("teacher_id"))); } catch(Exception ignore){}
    t.setTeacherName(req.getParameter("teacher_name"));
    return t;
  }
}
