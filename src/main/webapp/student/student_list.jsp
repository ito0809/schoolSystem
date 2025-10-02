<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*, model.student.StudentData" %>
<% String ctx = request.getContextPath(); %>
<!DOCTYPE html>
<html>
<head>
  <title>学生一覧</title>
  <style>
    table{border-collapse:collapse}
    th,td{border:1px solid #ccc;padding:6px;vertical-align:top}
    .nowrap{white-space:nowrap}
  </style>
</head>
<body>
<h1>学生一覧（全項目）</h1>

<h3>学生IDを入力して編集／削除</h3>
<form method="get">
  学生ID: <input type="number" name="id" required>
  <button type="submit" formaction="<%=ctx%>/studentdata/StudentUpdateServlet" formmethod="get">編集</button>
  <button type="submit" formaction="<%=ctx%>/studentdata/StudentDeleteServlet" formmethod="get">削除</button>
</form>
<br>

<table>
  <tr class="nowrap">
    <th>ID</th><th>学籍番号</th><th>姓</th><th>名</th>
    <th>セイ</th><th>メイ</th><th>生年月日</th><th>性別ID</th>
    <th>郵便番号</th><th>都道府県</th><th>市区町村</th><th>番地等</th><th>TEL</th>
    <th>学校ID</th><th>入学日</th><th>卒業日</th><th>作成日時</th><th>更新日時</th>
  </tr>
<%
  Object attr = request.getAttribute("studentList");
  List<?> rows = (attr instanceof List<?>) ? (List<?>)attr : null;

  if (rows == null || rows.isEmpty()) {
%>
  <tr><td colspan="18">データが存在しません</td></tr>
<%
  } else {
    for (Object o : rows) {
      StudentData s = (StudentData)o;
%>
  <tr>
    <td><%= s.getStudentId() %></td>
    <td><%= s.getStudentNumber()==null?"":s.getStudentNumber() %></td>
    <td><%= s.getLastName()==null?"":s.getLastName() %></td>
    <td><%= s.getFirstName()==null?"":s.getFirstName() %></td>
    <td><%= s.getLastNameKana()==null?"":s.getLastNameKana() %></td>
    <td><%= s.getFirstNameKana()==null?"":s.getFirstNameKana() %></td>
    <td><%= s.getBirthDate()==null?"":s.getBirthDate() %></td>
    <td><%= s.getGenderId()==null?"":s.getGenderId() %></td>
    <td><%= s.getPostalCode()==null?"":s.getPostalCode() %></td>
    <td><%= s.getPrefecture()==null?"":s.getPrefecture() %></td>
    <td><%= s.getCity()==null?"":s.getCity() %></td>
    <td><%= s.getAddressLine()==null?"":s.getAddressLine() %></td>
    <td><%= s.getTel()==null?"":s.getTel() %></td>
    <td><%= s.getSchoolId() %></td>
    <td><%= s.getEnrollmentDate()==null?"":s.getEnrollmentDate() %></td>
    <td><%= s.getGraduationDate()==null?"":s.getGraduationDate() %></td>
    <td><%= s.getCreatedAt()==null?"":s.getCreatedAt() %></td>
    <td><%= s.getUpdatedAt()==null?"":s.getUpdatedAt() %></td>
  </tr>
<%
    }
  }
%>
</table>

<form action="<%=ctx%>/studentdata/StudentAddServlet" method="get" style="margin-top:10px;">
  <input type="submit" value="追加">
</form>
</body>
</html>
