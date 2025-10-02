<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.student.StudentData" %>
<% String ctx = request.getContextPath(); %>
<!DOCTYPE html>
<html>
<head><title>学生更新</title>
<style>label{display:inline-block;width:140px;margin-top:6px}</style>
</head>
<body>
<h1>学生更新</h1>
<%
  String msg = (String)request.getAttribute("errorMessage");
  if (msg != null) { out.print("<p style='color:red;'>"+msg+"</p>"); }
  StudentData s = (StudentData)request.getAttribute("studentData");
  if (s == null) {
%>
  <p style="color:red;">学生が見つかりませんでした。</p>
  <p><a href="<%=ctx%>/students">一覧へ戻る</a></p>
<% } else { %>
<form action="<%=ctx%>/studentdata/StudentUpdateServlet" method="post">
  <input type="hidden" name="student_id" value="<%=s.getStudentId()%>">

  <div><label>学籍番号</label><input type="text"  name="student_number" value="<%=s.getStudentNumber()==null?"":s.getStudentNumber()%>"></div>
  <div><label>姓</label><input type="text" name="last_name"  value="<%=s.getLastName()==null?"":s.getLastName()%>"></div>
  <div><label>名</label><input type="text" name="first_name" value="<%=s.getFirstName()==null?"":s.getFirstName()%>"></div>
  <div><label>セイ</label><input type="text" name="last_name_kana"  value="<%=s.getLastNameKana()==null?"":s.getLastNameKana()%>"></div>
  <div><label>メイ</label><input type="text" name="first_name_kana" value="<%=s.getFirstNameKana()==null?"":s.getFirstNameKana()%>"></div>
  <div><label>生年月日</label><input type="date" name="birth_date" value="<%=s.getBirthDate()==null?"":s.getBirthDate()%>"></div>
  <div><label>性別ID</label><input type="number" name="gender_id" value="<%=s.getGenderId()==null?"":s.getGenderId()%>"></div>
  <div><label>郵便番号</label><input type="text" name="postal_code" value="<%=s.getPostalCode()==null?"":s.getPostalCode()%>"></div>
  <div><label>都道府県</label><input type="text" name="prefecture" value="<%=s.getPrefecture()==null?"":s.getPrefecture()%>"></div>
  <div><label>市区町村</label><input type="text" name="city" value="<%=s.getCity()==null?"":s.getCity()%>"></div>
  <div><label>番地等</label><input type="text" name="address_line" value="<%=s.getAddressLine()==null?"":s.getAddressLine()%>"></div>
  <div><label>TEL</label><input type="text" name="tel" value="<%=s.getTel()==null?"":s.getTel()%>"></div>
  <div><label>学校ID</label><input type="number" name="school_id" value="<%=s.getSchoolId()%>"></div>
  <div><label>入学日</label><input type="date" name="enrollment_date" value="<%=s.getEnrollmentDate()==null?"":s.getEnrollmentDate()%>"></div>
  <div><label>卒業日</label><input type="date" name="graduation_date" value="<%=s.getGraduationDate()==null?"":s.getGraduationDate()%>"></div>

  <div style="margin-top:10px;">
    <input type="submit" value="更新">
    <a href="<%=ctx%>/students" style="margin-left:10px;">一覧へ戻る</a>
  </div>
</form>
<% } %>
</body>
</html>
