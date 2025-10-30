package servlet.course;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.course.CourseAddDao;
import model.course.CourseData;

@WebServlet("/coursedata/CourseAddServlet")
public class CourseAddServlet extends HttpServlet {

  /** 学校マスタをJSPへ渡す */
  private void loadMasters(HttpServletRequest req) throws SQLException {
    req.setAttribute("schoolList",
        new dao.school.SchoolListDao().findAll()); // 全件でOKの想定
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    try { loadMasters(req); } catch (SQLException e) { throw new ServletException(e); }
    req.getRequestDispatcher("/course/course_add.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");

    String courseName = req.getParameter("course_name");
    String schoolIdS  = req.getParameter("school_id");

    // 必須チェック
    if (courseName == null || courseName.isBlank() ||
        schoolIdS == null || schoolIdS.isBlank()) {
      req.setAttribute("errorMessage", "コース名・学校は必須です。");
      req.setAttribute("course_name", courseName);   // 入力保持
      req.setAttribute("school_id",  schoolIdS);
      try { loadMasters(req); } catch (SQLException ignore) {}
      req.getRequestDispatcher("/course/course_add.jsp").forward(req, resp);
      return;
    }

    try {
      CourseData c = new CourseData();
      c.setCourseName(courseName.trim());
      c.setSchoolId(Integer.parseInt(schoolIdS));

      boolean ok = new CourseAddDao().insert(c);
      if (ok) {
        resp.sendRedirect(req.getContextPath()+"/courses?added=1");
      } else {
        req.setAttribute("errorMessage", "登録に失敗しました。");
        req.setAttribute("course_name", courseName);
        req.setAttribute("school_id",  schoolIdS);
        loadMasters(req);
        req.getRequestDispatcher("/course/course_add.jsp").forward(req, resp);
      }
    } catch (NumberFormatException e) {
      req.setAttribute("errorMessage", "学校の選択が不正です。");
      req.setAttribute("course_name", courseName);
      req.setAttribute("school_id",  schoolIdS);
      try { loadMasters(req); } catch (SQLException ignore) {}
      req.getRequestDispatcher("/course/course_add.jsp").forward(req, resp);
    } catch (SQLException e) {
      throw new ServletException(e);
    }
  }
}