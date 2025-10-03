<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List, model.student.StudentData, model.master.Gender" %>
<%
  String ctx = request.getContextPath();
  StudentData sd = (StudentData) request.getAttribute("studentData");
  List<Gender> genderList = (List<Gender>) request.getAttribute("genderList");
  Integer selGenderId = (sd != null) ? sd.getGenderId() : null;
  String msg = (String) request.getAttribute("errorMessage");
%>
<!DOCTYPE html>
<html>
<head>
  <title>学生追加</title>
  <style>label{display:inline-block;width:140px;margin-top:6px}</style>
</head>
<body>
<h1>学生追加</h1>

<% if (msg != null) { %><p style="color:red;"><%= msg %></p><% } %>

<form action="<%=ctx%>/studentdata/StudentAddServlet" method="post">
  <div><label>学籍番号</label>
    <input type="text" name="student_number"
           value="<%= sd!=null&&sd.getStudentNumber()!=null?sd.getStudentNumber():"" %>">
  </div>

  <div><label>姓</label>
    <input type="text" name="last_name"
           value="<%= sd!=null&&sd.getLastName()!=null?sd.getLastName():"" %>" required>
  </div>

  <div><label>名</label>
    <input type="text" name="first_name"
           value="<%= sd!=null&&sd.getFirstName()!=null?sd.getFirstName():"" %>" required>
  </div>

  <div><label>セイ</label><input type="text" name="last_name_kana"
           value="<%= sd!=null&&sd.getLastNameKana()!=null?sd.getLastNameKana():"" %>"></div>
  <div><label>メイ</label><input type="text" name="first_name_kana"
           value="<%= sd!=null&&sd.getFirstNameKana()!=null?sd.getFirstNameKana():"" %>"></div>
  <div><label>生年月日</label><input type="date" name="birth_date"
           value="<%= sd!=null&&sd.getBirthDate()!=null?sd.getBirthDate():"" %>"></div>

  <!-- 性別：名前で選択（送信値は gender_id） -->
  <div><label>性別</label>
    <select name="gender_id">
      <option value="">--選択--</option>
      <%
        if (genderList != null) {
          for (Gender g : genderList) {
            boolean sel = (selGenderId != null && selGenderId.intValue() == g.getGenderId());
      %>
        <option value="<%= g.getGenderId() %>" <%= sel ? "selected" : "" %>>
          <%= g.getGenderName() %>
        </option>
      <%
          }
        }
      %>
    </select>
  </div>

  <div><label>郵便番号</label><input type="text" name="postal_code"
           value="<%= sd!=null&&sd.getPostalCode()!=null?sd.getPostalCode():"" %>"></div>
  <div><label>都道府県</label><input type="text" name="prefecture"
           value="<%= sd!=null&&sd.getPrefecture()!=null?sd.getPrefecture():"" %>"></div>
  <div><label>市区町村</label><input type="text" name="city"
           value="<%= sd!=null&&sd.getCity()!=null?sd.getCity():"" %>"></div>
  <div><label>番地等</label><input type="text" name="address_line"
           value="<%= sd!=null&&sd.getAddressLine()!=null?sd.getAddressLine():"" %>"></div>
  <div><label>TEL</label><input type="text" name="tel"
           value="<%= sd!=null&&sd.getTel()!=null?sd.getTel():"" %>"></div>

  <!-- 学校はID入力のまま -->
  <div><label>学校ID</label>
    <input type="number" name="school_id" required
           value="<%= sd!=null ? sd.getSchoolId() : "" %>">
  </div>

  <div><label>入学日</label><input type="date" name="enrollment_date"
           value="<%= sd!=null&&sd.getEnrollmentDate()!=null?sd.getEnrollmentDate():"" %>"></div>
  <div><label>卒業日</label><input type="date" name="graduation_date"
           value="<%= sd!=null&&sd.getGraduationDate()!=null?sd.getGraduationDate():"" %>"></div>

  <div style="margin-top:10px;">
    <input type="submit" value="登録">
    <a href="<%=ctx%>/students" style="margin-left:10px;">一覧へ戻る</a>
  </div>
</form>
</body>
</html>
