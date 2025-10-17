<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.teacher.TeacherData" %>
<% String ctx = request.getContextPath();
   String errorMessage = (String)request.getAttribute("errorMessage");
   TeacherData t = (TeacherData)request.getAttribute("teacher"); %>
<!DOCTYPE html><html><head><title>教員更新</title></head><body>
<h1>教員更新</h1>
<% if (errorMessage!=null){ %><p style="color:red;"><%=errorMessage%></p><% } %>
<% if (t==null){ %>
  <p style="color:red;">教員が見つかりませんでした。</p>
  <p><a href="<%=ctx%>/teachers">一覧へ戻る</a></p>
<% } else { %>
<form action="<%=ctx%>/teacherdata/TeacherUpdateServlet" method="post">
  <input type="hidden" name="teacher_id" value="<%= t.getTeacherId() %>">
  教員名: <input type="text" name="teacher_name" value="<%= t.getTeacherName()==null?"":t.getTeacherName() %>" required>
  <input type="submit" value="更新">
  <a href="<%=ctx%>/teachers" style="margin-left:8px;">一覧へ戻る</a>
</form>
<% } %>
</body></html>
