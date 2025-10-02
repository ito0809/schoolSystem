package servlet.student;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/students")
public class StudentListServlet extends HttpServlet {
  @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    Integer schoolId = parseInt(req.getParameter("school_id"));
    String q = req.getParameter("q");
    int page = Math.max(1, parseIntOr(req.getParameter("page"), 1));
    int size = Math.max(1, parseIntOr(req.getParameter("size"), 20));
    int offset = (page - 1) * size;

    try {
      var dao = new dao.student.StudentListDao();
      int total = dao.countAll(schoolId, q);
      var list  = dao.findAll(schoolId, q, size, offset);
      req.setAttribute("studentList", list);
      req.setAttribute("total", total);
      req.setAttribute("page", page);
      req.setAttribute("size", size);
      req.setAttribute("q", q);
      req.setAttribute("school_id", schoolId);
      req.getRequestDispatcher("/student/student_list.jsp").forward(req, resp);
    } catch (SQLException e) { throw new ServletException(e); }
  }
  private Integer parseInt(String s){ try{return (s==null||s.isBlank())?null:Integer.valueOf(s);}catch(Exception e){return null;}}
  private int parseIntOr(String s,int def){ try{return (s==null||s.isBlank())?def:Integer.parseInt(s);}catch(Exception e){return def;}}
}
