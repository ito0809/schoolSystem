package servlet.student;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.master.GenderListDao;          // ★ 追加
import dao.student.StudentListDao;
import dao.student.StudentUpdateDao;
import model.student.StudentData;

@WebServlet("/studentdata/StudentUpdateServlet")
public class StudentUpdateServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String idStr = req.getParameter("id");
    if (idStr == null) { resp.sendRedirect(req.getContextPath()+"/students"); return; }
    try {
      int id = Integer.parseInt(idStr);
      StudentData s = new StudentListDao().findById(id);
      if (s == null) { resp.sendRedirect(req.getContextPath()+"/students?notfound=1"); return; }
      req.setAttribute("studentData", s);
      loadMasters(req);                                      // ★ 性別マスタ
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

    String idStr      = req.getParameter("student_id");
    String lastName   = req.getParameter("last_name");
    String firstName  = req.getParameter("first_name");
    String schoolIdS  = req.getParameter("school_id");

    if (idStr == null || lastName == null || lastName.isBlank()
        || firstName == null || firstName.isBlank()
        || schoolIdS == null || schoolIdS.isBlank()) {
      req.setAttribute("errorMessage", "必須項目（学生ID／姓／名／学校ID）が未入力です。");
      req.setAttribute("studentData", bindFromRequest(req));
      try { loadMasters(req); } catch (SQLException ignore) {}
      req.getRequestDispatcher("/student/student_update.jsp").forward(req, resp);
      return;
    }

    try {
      StudentData s = bindFromRequest(req);
      s.setStudentId(Integer.parseInt(idStr.trim()));
      s.setLastName(lastName.trim());
      s.setFirstName(firstName.trim());
      s.setSchoolId(Integer.parseInt(schoolIdS.trim()));     // 学校はIDのまま

      boolean ok = new StudentUpdateDao().update(s);
      if (!ok) {
        req.setAttribute("errorMessage", "更新対象が見つかりませんでした。");
        req.setAttribute("studentData", s);
        loadMasters(req);
        req.getRequestDispatcher("/student/student_update.jsp").forward(req, resp);
        return;
      }
      resp.sendRedirect(req.getContextPath()+"/students?updated="+s.getStudentId());
    } catch (NumberFormatException e) {
      req.setAttribute("errorMessage", "数値項目（学生ID／学校ID／性別IDなど）の形式が正しくありません。");
      req.setAttribute("studentData", bindFromRequest(req));
      try { loadMasters(req); } catch (SQLException ignore) {}
      req.getRequestDispatcher("/student/student_update.jsp").forward(req, resp);
    } catch (SQLException e) {
      throw new ServletException(e);
    }
  }

  /* -------- helpers -------- */

  private void loadMasters(HttpServletRequest req) throws SQLException {
    req.setAttribute("genderList", new GenderListDao().findAll()); // ★ 性別のみ
  }

  private static StudentData bindFromRequest(HttpServletRequest req) {
    StudentData s = new StudentData();
    s.setStudentNumber(trim(req.getParameter("student_number")));
    s.setLastName(trim(req.getParameter("last_name")));
    s.setFirstName(trim(req.getParameter("first_name")));
    s.setLastNameKana(trim(req.getParameter("last_name_kana")));
    s.setFirstNameKana(trim(req.getParameter("first_name_kana")));
    s.setBirthDate(parseLocalDate(req.getParameter("birth_date")));
    s.setGenderId(parseInteger(req.getParameter("gender_id"))); // selectの値
    s.setPostalCode(trim(req.getParameter("postal_code")));
    s.setPrefecture(trim(req.getParameter("prefecture")));
    s.setCity(trim(req.getParameter("city")));
    s.setAddressLine(trim(req.getParameter("address_line")));
    s.setTel(trim(req.getParameter("tel")));
    Integer sid = parseInteger(req.getParameter("school_id")); if (sid!=null) s.setSchoolId(sid);
    s.setEnrollmentDate(parseLocalDate(req.getParameter("enrollment_date")));
    s.setGraduationDate(parseLocalDate(req.getParameter("graduation_date")));
    try { String idS=req.getParameter("student_id"); if(idS!=null) s.setStudentId(Integer.parseInt(idS.trim())); } catch(Exception ignore){}
    return s;
  }

  private static Integer parseInteger(String s){ try{ return (s==null||s.isBlank())? null: Integer.valueOf(s.trim()); }catch(Exception e){ return null; } }
  private static LocalDate parseLocalDate(String s){ try{ return (s==null||s.isBlank())? null: LocalDate.parse(s.trim()); }catch(Exception e){ return null; } }
  private static String trim(String s){ return s==null? null: s.trim(); }
}
