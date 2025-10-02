<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*, model.course.CourseData" %>
<% String ctx = request.getContextPath(); %>
<!DOCTYPE html><html><head>
<title>コース一覧</title>
<style>table{border-collapse:collapse}th,td{border:1px solid #ccc;padding:6px}</style>
</head><body>
<h1>コース一覧</h1>

<h3>コースIDを入力して編集／削除</h3>
<form method="get">
  コースID: <input type="number" name="id" required><br><br>
  <button type="submit" formaction="<%=ctx%>/coursedata/CourseUpdateServlet" formmethod="get">編集</button>
  <button type="submit" formaction="<%=ctx%>/coursedata/CourseDeleteServlet" formmethod="get">削除</button>
</form>
<br>

<%!
@SuppressWarnings("unchecked")
private java.util.List<model.course.CourseData> getCourseList(
        javax.servlet.http.HttpServletRequest req) {

    Object o = req.getAttribute("courseList");
    if (o instanceof java.util.List<?>) {
        try {
            return (java.util.List<model.course.CourseData>) o;
        } catch (ClassCastException ignore) {}
    }
    return java.util.Collections.emptyList();
}
%>
<table>
<tr><th>ID</th><th>コース名</th><th>学校ID</th></tr>
<%
  java.util.List<model.course.CourseData> list = getCourseList(request);
  for (model.course.CourseData c : list) {
%>
  <tr>
    <td><%= c.getCourseId() %></td>
    <td><%= c.getCourseName() %></td>
    <td><%= c.getSchoolId() %></td>
  </tr>
<% } %>

</table>

<form action="<%=ctx%>/coursedata/CourseAddServlet" method="get" style="margin-top:10px;">
  <input type="submit" value="追加">
</form>
</body></html>
