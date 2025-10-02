<% String ctx = request.getContextPath(); %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.classdata.ClassData" %>
<!DOCTYPE html>
<html>
<head><title>学科更新</title></head>
<body>
<h1>学科更新</h1>

<%
  String errorMessage = (String) request.getAttribute("errorMessage");
  if (errorMessage != null) { %>
    <p style="color:red;"><%= errorMessage %></p>
<% }
  ClassData cd = (ClassData) request.getAttribute("classData");
  if (cd == null) { %>
    <p style="color:red;">学科が見つかりませんでした。</p>
    <p><a href="<%= ctx %>/classes">一覧へ戻る</a></p>
<% } else { %>

<form action="<%= ctx %>/classdata/ClassUpdateServlet" method="post" accept-charset="UTF-8">
  <input type="hidden" name="class_id" value="<%= cd.getClassId() %>">

  学科名:  <input type="text"   name="class_name" value="<%= cd.getClassName() %>" required><br><br>
  コースID:<input type="number" name="course_id"  value="<%= cd.getCourseId() %>" required><br><br>
  学校ID:  <input type="number" name="school_id"  value="<%= cd.getSchoolId() %>" required><br><br>

  <input type="submit" value="更新">
  <a href="<%= ctx %>/classes" style="margin-left:10px;">一覧へ戻る</a>
</form>

<% } %>
</body>
</html>
