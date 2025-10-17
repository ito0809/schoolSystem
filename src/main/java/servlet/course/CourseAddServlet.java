package servlet.course;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.course.CourseAddDao;
import dao.school.SchoolListDao;
import model.course.CourseData;

@WebServlet("/coursedata/CourseAddServlet")
public class CourseAddServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    // 追加フォーム表示
    req.getRequestDispatcher("/course/course_add.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");

    // 入力バインド（必要に応じて）
    CourseData c = new CourseData();
    c.setCourseName(req.getParameter("course_name"));
    Integer schoolId = null;
    try { schoolId = Integer.valueOf(req.getParameter("school_id")); } catch (Exception ignore) {}
    c.setSchoolId(schoolId);

    try {
      // ★ 事前に学校IDの存在チェック
      if (schoolId == null || !new SchoolListDao().exists(schoolId)) {
        req.setAttribute("errorMessage", "学校ID " + req.getParameter("school_id") + " は存在しません。");
        req.setAttribute("courseData", c); // 入力値を戻す
        req.getRequestDispatcher("/course/course_add.jsp").forward(req, resp);
        return;
      }

      boolean ok = new CourseAddDao().insert(c);
      if (ok) {
        resp.sendRedirect(req.getContextPath() + "/courses?added=1");
        return;
      }

      req.setAttribute("errorMessage", "登録に失敗しました。");
      req.setAttribute("courseData", c);
      req.getRequestDispatcher("/course/course_add.jsp").forward(req, resp);

    } catch (java.sql.SQLIntegrityConstraintViolationException e) {
      // ★ FK違反（保険）: forward でフォームに戻す
      req.setAttribute("errorMessage", "学校IDが不正です（外部キー制約）。既存のIDを指定してください。");
      req.setAttribute("courseData", c);
      req.getRequestDispatcher("/course/course_add.jsp").forward(req, resp);

    } catch (java.sql.SQLException e) {
      throw new ServletException(e);
    }
  }
}
