<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.teacher.TeacherData" %>
<% String ctx = request.getContextPath();
   TeacherData t = (TeacherData)request.getAttribute("teacher"); %>
<!DOCTYPE html><html><head><title>教員削除</title>
<style>table{border-collapse:collapse}th,td{border:1px solid #ccc;padding:6px}</style>
</head><body>
<h1>教員削除</h1>
<% if (t==null){ %>
  <p style="color:red;">教員が見つかりませんでした。</p>
  <p><a href="<%=ctx%>/teachers">一覧へ戻る</a></p>
<% } else { %>
<p>以下の教員を削除します。よろしいですか？</p>
<table>
  <tr><th>ID</th><td><%= t.getTeacherId() %></td></tr>
  <tr><th>教員名</th><td><%= t.getTeacherName()==null?"":t.getTeacherName() %></td></tr>
  <tr><th>作成日時</th><td><%= t.getCreatedAt()==null?"":t.getCreatedAt() %></td></tr>
  <tr><th>更新日時</th><td><%= t.getUpdatedAt()==null?"":t.getUpdatedAt() %></td></tr>
</table>
<form action="<%=ctx%>/teacherdata/TeacherDeleteServlet" method="post" style="margin-top:10px;">
  <input type="hidden" name="id" value="<%= t.getTeacherId() %>">
  <input type="submit" value="削除する" onclick="return confirm('本当に削除しますか？');">
  <a href="<%=ctx%>/teachers" style="margin-left:10px;">キャンセル</a>
</form>
<% } %>
</body></html>
