<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List,
                 model.student.StudentData,
                 model.master.Gender,
                 model.school.SchoolData" %>

<%
  String ctx = request.getContextPath();
  String errorMessage = (String) request.getAttribute("errorMessage");

  StudentData cd = (StudentData) request.getAttribute("studentData");

  List<Gender> genderList = (List<Gender>) request.getAttribute("genderList");
  Integer selGenderId = (cd != null) ? cd.getGenderId() : null;

  List<SchoolData> schoolList =
      (List<SchoolData>) request.getAttribute("schoolList");
  Integer selSchoolId = (cd != null) ? cd.getSchoolId() : null;   // ← sd ではなく cd
%>
<!DOCTYPE html>
<html>
<head>
  <title>学生更新</title>
  <style>label{display:inline-block;width:140px;margin-top:6px}</style>
</head>
<body>
<h1>学生更新</h1>

<% if (errorMessage != null) { %>
  <p style="color:red;"><%= errorMessage %></p>
<% } %>

<% if (cd == null) { %>
  <p style="color:red;">学生が見つかりませんでした。</p>
  <p><a href="<%= ctx %>/students">一覧へ戻る</a></p>
<% } else { %>
<form action="<%= ctx %>/studentdata/StudentUpdateServlet" method="post">
  <input type="hidden" name="student_id" value="<%= cd.getStudentId() %>">

  <div><label>学籍番号</label>
    <input type="text" name="student_number"
           value="<%= cd.getStudentNumber()==null? "": cd.getStudentNumber() %>">
  </div>

  <div><label>姓</label>
    <input type="text" name="last_name"
           value="<%= cd.getLastName()==null? "": cd.getLastName() %>" required>
  </div>

  <div><label>名</label>
    <input type="text" name="first_name"
           value="<%= cd.getFirstName()==null? "": cd.getFirstName() %>" required>
  </div>

  <div><label>セイ</label>
    <input type="text" name="last_name_kana"
           value="<%= cd.getLastNameKana()==null? "": cd.getLastNameKana() %>">
  </div>

  <div><label>メイ</label>
    <input type="text" name="first_name_kana"
           value="<%= cd.getFirstNameKana()==null? "": cd.getFirstNameKana() %>">
  </div>

  <div><label>生年月日</label>
    <input type="date" name="birth_date"
           value="<%= cd.getBirthDate()==null? "": cd.getBirthDate() %>">
  </div>

  <!-- 性別：名前で選択 -->
  <div><label>性別</label>
    <select name="gender_id">
      <option value="">--選択--</option>
      <% if (genderList != null) {
           for (Gender g : genderList) {
             boolean sel = (selGenderId != null && selGenderId.intValue() == g.getGenderId()); %>
        <option value="<%= g.getGenderId() %>" <%= sel ? "selected" : "" %>>
          <%= g.getGenderName() %>
        </option>
      <%   }
         } %>
    </select>
  </div>

  <div><label>郵便番号</label>
    <input type="text" name="postal_code"
           value="<%= cd.getPostalCode()==null? "": cd.getPostalCode() %>">
  </div>

  <div><label>都道府県</label>
    <input type="text" name="prefecture"
           value="<%= cd.getPrefecture()==null? "": cd.getPrefecture() %>">
  </div>

  <div><label>市区町村</label>
    <input type="text" name="city"
           value="<%= cd.getCity()==null? "": cd.getCity() %>">
  </div>

  <div><label>番地等</label>
    <input type="text" name="address_line"
           value="<%= cd.getAddressLine()==null? "": cd.getAddressLine() %>">
  </div>

  <div><label>TEL</label>
    <input type="text" name="tel"
           value="<%= cd.getTel()==null? "": cd.getTel() %>">
  </div>

  <!-- 学校：名前で選択（送信値は school_id） -->
  <div><label>学校</label>
    <select name="school_id" required>
      <option value="">--選択--</option>
      <% if (schoolList != null) {
           for (SchoolData sc : schoolList) {
             boolean selected = (selSchoolId != null && selSchoolId.equals(sc.getSchoolId())); %>
        <option value="<%= sc.getSchoolId() %>" <%= selected ? "selected" : "" %>>
          <%= sc.getSchoolName() %>
        </option>
      <%   }
         } %>
    </select>
  </div>

  <div><label>入学日</label>
    <input type="date" name="enrollment_date"
           value="<%= cd.getEnrollmentDate()==null? "": cd.getEnrollmentDate() %>">
  </div>

  <div><label>卒業日</label>
    <input type="date" name="graduation_date"
           value="<%= cd.getGraduationDate()==null? "": cd.getGraduationDate() %>">
  </div>

  <div style="margin-top:10px;">
    <input type="submit" value="更新">
    <a href="<%= ctx %>/students" style="margin-left:10px;">一覧へ戻る</a>
  </div>
</form>
<% } %>
</body>
</html>
