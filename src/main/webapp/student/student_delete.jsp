<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.student.StudentData" %>
<% String ctx = request.getContextPath();
   StudentData s = (StudentData)request.getAttribute("studentData"); %>
<!DOCTYPE html>
<html>
<head>
  <title>学生削除</title>
  <style>
    table{border-collapse:collapse} th,td{border:1px solid #ccc;padding:6px}
  </style>
</head>
<body>
<h1>学生削除</h1>

<% if (s == null) { %>
  <p style="color:red;">学生が見つかりませんでした。</p>
  <p><a href="<%=ctx%>/students">一覧へ戻る</a></p>
<% } else { %>

<p>以下の学生を削除します。よろしいですか？</p>
<table>
  <tr><th>ID</th><td><%= s.getStudentId() %></td></tr>
  <tr><th>学籍番号</th><td><%= nv(s.getStudentNumber()) %></td></tr>
  <tr><th>姓</th><td><%= nv(s.getLastName()) %></td></tr>
  <tr><th>名</th><td><%= nv(s.getFirstName()) %></td></tr>
  <tr><th>セイ</th><td><%= nv(s.getLastNameKana()) %></td></tr>
  <tr><th>メイ</th><td><%= nv(s.getFirstNameKana()) %></td></tr>
  <tr><th>生年月日</th><td><%= s.getBirthDate()==null?"":s.getBirthDate() %></td></tr>

  <!-- ★ 性別は名前表示 -->
  <tr><th>性別</th><td><%= s.getGenderName()==null?"":s.getGenderName() %></td></tr>

  <tr><th>郵便番号</th><td><%= nv(s.getPostalCode()) %></td></tr>
  <tr><th>都道府県</th><td><%= nv(s.getPrefecture()) %></td></tr>
  <tr><th>市区町村</th><td><%= nv(s.getCity()) %></td></tr>
  <tr><th>番地等</th><td><%= nv(s.getAddressLine()) %></td></tr>
  <tr><th>TEL</th><td><%= nv(s.getTel()) %></td></tr>

  <!-- ★ 学校はID表示のまま -->
  <tr><th>学校ID</th><td><%= s.getSchoolId() %></td></tr>

  <tr><th>入学日</th><td><%= s.getEnrollmentDate()==null?"":s.getEnrollmentDate() %></td></tr>
  <tr><th>卒業日</th><td><%= s.getGraduationDate()==null?"":s.getGraduationDate() %></td></tr>
</table>

<form action="<%=ctx%>/studentdata/StudentDeleteServlet" method="post" style="margin-top:10px;">
  <input type="hidden" name="id" value="<%= s.getStudentId() %>">
  <input type="submit" value="削除する" onclick="return confirm('本当に削除しますか？');">
  <a href="<%=ctx%>/students" style="margin-left:10px;">キャンセル</a>
</form>

<% } %>

<%! private String nv(String v){ return v==null? "": v; } %>
</body>
</html>
