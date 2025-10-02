<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.classdata.ClassData" %>
<!DOCTYPE html>
<html>
<head>
  <title>学科削除</title>
  <style>
    table{border-collapse:collapse}
    th,td{border:1px solid #ccc; padding:6px;}
  </style>
</head>
<body>
<h1>学科削除</h1>

<%
  ClassData cd = (ClassData) request.getAttribute("classData");
  if (cd == null) {
%>
  <p style="color:red;">学科が見つかりませんでした。</p>
  <p><a href="<%= request.getContextPath() %>/classes">一覧へ戻る</a></p>
<%
  } else {
%>
  <p>以下の学科を削除します。よろしいですか？</p>
  <table>
    <tr><th>学科ID</th><td><%= cd.getClassId() %></td></tr>
    <tr><th>学科名</th><td><%= cd.getClassName() %></td></tr>
    <tr><th>コースID</th><td><%= cd.getCourseId() %></td></tr>
    <tr><th>学校ID</th><td><%= cd.getSchoolId() %></td></tr>
  </table>
  <br>

  <!-- ★ 削除実行は POST のみ -->
 <form action="<%= request.getContextPath() %>/classdata/ClassDeleteServlet" method="post">
  <input type="hidden" name="id" value="<%= cd.getClassId() %>">
  <input type="submit" value="削除する" onclick="return confirm('本当に削除しますか？');">
  <a href="<%= request.getContextPath() %>/classes">キャンセル</a>
</form>

<%
  }
%>
</body>
</html>
