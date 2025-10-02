package servlet.student;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.student.StudentAddDao;
import model.student.StudentData;

@WebServlet("/studentdata/StudentAddServlet")
public class StudentAddServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.getRequestDispatcher("/student/student_add.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    req.setCharacterEncoding("UTF-8");

    // 必須チェック
    String lastName  = trim(req.getParameter("last_name"));
    String firstName = trim(req.getParameter("first_name"));
    String schoolIdS = trim(req.getParameter("school_id"));
    if (isEmpty(lastName) || isEmpty(firstName) || isEmpty(schoolIdS)) {
      req.setAttribute("errorMessage", "姓・名・学校IDは必須です。");
      req.setAttribute("studentData", bindFromRequest(req)); // 入力値戻し
      req.getRequestDispatcher("/student/student_add.jsp").forward(req, resp);
      return;
    }

    try {
      StudentData s = bindFromRequest(req);
      s.setLastName(lastName);
      s.setFirstName(firstName);
      s.setSchoolId(Integer.parseInt(schoolIdS));

      boolean ok = new StudentAddDao().insert(s);
      if (!ok) {
        req.setAttribute("errorMessage", "登録に失敗しました。");
        req.setAttribute("studentData", s);
        req.getRequestDispatcher("/student/student_add.jsp").forward(req, resp);
        return;
      }
      resp.sendRedirect(req.getContextPath() + "/students?added=1");
    } catch (NumberFormatException e) {
      req.setAttribute("errorMessage", "数値項目の形式が正しくありません。（学校ID／性別IDなど）");
      req.setAttribute("studentData", bindFromRequest(req));
      req.getRequestDispatcher("/student/student_add.jsp").forward(req, resp);
    } catch (SQLException e) {
      throw new ServletException(e);
    }
  }

  // ---- helpers ----
  private static StudentData bindFromRequest(HttpServletRequest req) {
    StudentData s = new StudentData();
    s.setStudentNumber(trim(req.getParameter("student_number")));
    s.setLastName(trim(req.getParameter("last_name")));
    s.setFirstName(trim(req.getParameter("first_name")));
    s.setLastNameKana(trim(req.getParameter("last_name_kana")));
    s.setFirstNameKana(trim(req.getParameter("first_name_kana")));
    s.setBirthDate(parseLocalDate(req.getParameter("birth_date")));
    s.setGenderId(parseInteger(req.getParameter("gender_id")));
    s.setPostalCode(trim(req.getParameter("postal_code")));
    s.setPrefecture(trim(req.getParameter("prefecture")));
    s.setCity(trim(req.getParameter("city")));
    s.setAddressLine(trim(req.getParameter("address_line")));
    s.setTel(trim(req.getParameter("tel")));
    Integer sid = parseInteger(req.getParameter("school_id"));
    if (sid != null) s.setSchoolId(sid);
    s.setEnrollmentDate(parseLocalDate(req.getParameter("enrollment_date")));
    s.setGraduationDate(parseLocalDate(req.getParameter("graduation_date")));
    return s;
  }
  private static Integer parseInteger(String s){ try{ return isEmpty(s)? null: Integer.valueOf(s.trim()); }catch(Exception e){ throw new NumberFormatException(); } }
  private static LocalDate parseLocalDate(String s){ try{ return isEmpty(s)? null: LocalDate.parse(s.trim()); }catch(Exception e){ return null; } }
  private static boolean isEmpty(String s){ return s==null || s.isBlank(); }
  private static String trim(String s){ return s==null? null: s.trim(); }
}
