<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.classdata.ClassData" %>
<%
  String ctx = request.getContextPath();
  String err = (String)request.getAttribute("errorMessage");
  ClassData c = (ClassData)request.getAttribute("classData");
%>
<!DOCTYPE html>
<html>
<head><title>学科削除</title>
<style>table{border-collapse:collapse}th,td{border:1px solid #ccc;padding:6px}</style>
</head>
<body>
<h1>学科削除</h1>
<% if (err!=null){ %><p style="color:red;"><%= err %></p><% } %>

<% if (c==null) { %>
  <p style="color:red;">学科が見つかりませんでした。</p>
  <p><a href="<%=ctx%>/classes">一覧へ戻る</a></p>
<% } else { %>
  <p>以下の学科を削除します。よろしいですか？</p>
  <table>
    <tr><th>学科ID</th><td><%= c.getClassId() %></td></tr>
    <tr><th>学科名</th><td><%= c.getClassName() %></td></tr>
    <tr><th>コース名</th><td><%= c.getCourseName()!=null? c.getCourseName() : ("ID:"+c.getCourseId()) %></td></tr>
    <tr><th>学校名</th><td><%= c.getSchoolName()!=null? c.getSchoolName() : ("ID:"+c.getSchoolId()) %></td></tr>
  </table>

  <form action="<%=ctx%>/classdata/ClassDeleteServlet" method="post" style="margin-top:10px;">
    <input type="hidden" name="id" value="<%= c.getClassId() %>">
    <input type="submit" value="削除する" onclick="return confirm('本当に削除しますか？');">
    <a href="<%=ctx%>/classes" style="margin-left:10px;">キャンセル</a>
  </form>
<% } %>
</body>
</html>
