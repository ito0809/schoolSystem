<%@ page contentType="text/html; charset=UTF-8" %>
<% String ctx = request.getContextPath(); %>
<% String errorMessage = (String)request.getAttribute("errorMessage"); %>
<% if (errorMessage != null) { %>
  <p style="color:red;"><%= errorMessage %></p>
<% } %>

<!DOCTYPE html><html><head><title>コース追加</title></head><body>
<h1>コース追加</h1>
<form action="<%=ctx%>/coursedata/CourseAddServlet" method="post">
  コース名: <input type="text" name="course_name" required><br><br>
  学校ID:   <input type="number" name="school_id" required><br><br>
  <input type="submit" value="登録">
  <a href="<%=ctx%>/courses" style="margin-left:10px;">一覧へ戻る</a>
</form>
</body></html>
