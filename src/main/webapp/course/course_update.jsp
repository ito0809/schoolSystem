<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.course.CourseData" %>
<% String ctx = request.getContextPath(); %>
<!DOCTYPE html><html><head><title>コース更新</title></head><body>
<h1>コース更新</h1>
<%
  CourseData cd = (CourseData)request.getAttribute("courseData");
  if (cd == null) { %>
    <p style="color:red;">コースが見つかりませんでした。</p>
    <p><a href="<%=ctx%>/courses">一覧へ戻る</a></p>
<% } else { %>
<form action="<%=ctx%>/coursedata/CourseUpdateServlet" method="post">
  <input type="hidden" name="course_id" value="<%=cd.getCourseId()%>">
  コース名: <input type="text" name="course_name" value="<%=cd.getCourseName()%>" required><br><br>
  学校ID:   <input type="number" name="school_id" value="<%=cd.getSchoolId()%>" required><br><br>
  <input type="submit" value="更新">
  <a href="<%=ctx%>/courses" style="margin-left:10px;">一覧へ戻る</a>
</form>
<% } %>
</body></html>
