<%@ page contentType="text/html; charset=UTF-8" %>
<% String ctx = request.getContextPath();
   String errorMessage = (String)request.getAttribute("errorMessage"); %>
<!DOCTYPE html><html><head><title>教員追加</title></head><body>
<h1>教員追加</h1>
<% if (errorMessage!=null){ %><p style="color:red;"><%=errorMessage%></p><% } %>
<form action="<%=ctx%>/teacherdata/TeacherAddServlet" method="post">
  教員名: <input type="text" name="teacher_name" required>
  <input type="submit" value="登録">
  <a href="<%=ctx%>/teachers" style="margin-left:8px;">一覧へ戻る</a>
</form>
</body></html>
