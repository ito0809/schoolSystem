package servlet.student;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.master.GenderListDao;          // ★ 追加
import dao.school.SchoolListDao;
import dao.student.StudentListDao;
import dao.student.StudentUpdateDao;
import model.student.StudentData;

@WebServlet("/studentdata/StudentUpdateServlet")
public class StudentUpdateServlet extends HttpServlet {

  /** 性別・学校の候補をJSPへ */
  private void loadMasters(HttpServletRequest req) throws SQLException {
    req.setAttribute("genderList", new GenderListDao().findAll());
    req.setAttribute("schoolList", new SchoolListDao().findAll()); // ★学校一覧
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String idStr = req.getParameter("id");
    if (idStr == null || idStr.isBlank()) {
      resp.sendRedirect(req.getContextPath()+"/students?badid=1"); return;
    }
    try {
      int id = Integer.parseInt(idStr);
      StudentData s = new StudentListDao().findById(id);
      if (s == null) { resp.sendRedirect(req.getContextPath()+"/students?notfound=1"); return; }

      req.setAttribute("studentData", s);
      loadMasters(req); // ★性別と学校を渡す
      req.getRequestDispatcher("/student/student_update.jsp").forward(req, resp);

    } catch (NumberFormatException e) {
      resp.sendRedirect(req.getContextPath()+"/students?badid=1");
    } catch (SQLException e) {
      throw new ServletException(e);
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");

    try {
      StudentData s = bind(req);

      // ▼ここを boolean で受ける
      boolean ok = new StudentUpdateDao().update(s);

      if (ok) {
        resp.sendRedirect(req.getContextPath() + "/students?updated=" + s.getStudentId());
      } else {
        req.setAttribute("errorMessage", "更新対象が見つかりません。");
        req.setAttribute("studentData", s);
        loadMasters(req);
        req.getRequestDispatcher("/student/student_update.jsp").forward(req, resp);
      }
    } catch (NumberFormatException e) {
      req.setAttribute("errorMessage","数値項目の形式が不正です。");
      req.setAttribute("studentData", bindSafe(req));
      try { loadMasters(req); } catch (SQLException ignore) {}
      req.getRequestDispatcher("/student/student_update.jsp").forward(req, resp);
    } catch (SQLException e) {
      throw new ServletException(e);
    }
  }


  /* ---- helpers ---- */
  private static StudentData bind(HttpServletRequest req) {
    StudentData s = bindSafe(req);
    // school_id は <select> から送られるので数値に
    s.setSchoolId(Integer.valueOf(req.getParameter("school_id")));
    return s;
  }
  private static StudentData bindSafe(HttpServletRequest req) {
    StudentData s = new StudentData();
    // 既存の項目バインドをそのまま（学籍番号/氏名/カナ/日付/住所/TEL/性別など）
    // 例：
    try { s.setStudentId(Integer.valueOf(req.getParameter("student_id"))); } catch(Exception ignore){}
    s.setStudentNumber(req.getParameter("student_number"));
    s.setLastName(req.getParameter("last_name"));
    s.setFirstName(req.getParameter("first_name"));
    s.setLastNameKana(req.getParameter("last_name_kana"));
    s.setFirstNameKana(req.getParameter("first_name_kana"));
    // birth_date, enrollment_date, graduation_date も既存通りにパース
    try { s.setGenderId(Integer.valueOf(req.getParameter("gender_id"))); } catch(Exception ignore){}
    s.setPostalCode(req.getParameter("postal_code"));
    s.setPrefecture(req.getParameter("prefecture"));
    s.setCity(req.getParameter("city"));
    s.setAddressLine(req.getParameter("address_line"));
    s.setTel(req.getParameter("tel"));
    try { s.setSchoolId(Integer.valueOf(req.getParameter("school_id"))); } catch(Exception ignore){}
    return s;
  }
}