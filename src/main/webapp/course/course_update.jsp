<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List, model.course.CourseData, model.school.SchoolData" %>
<%
  String ctx = request.getContextPath();
  String msg = (String)request.getAttribute("errorMessage");
  CourseData cd = (CourseData)request.getAttribute("courseData");
  List<SchoolData> schoolList = (List<SchoolData>)request.getAttribute("schoolList");
  Integer selSchoolId = (cd!=null)? cd.getSchoolId() : null;
%>
<!DOCTYPE html>
<html>
<head>
  <title>コース更新</title>
  <style>label{display:inline-block;width:90px;margin-top:6px}</style>
</head>
<body>
<h1>コース更新</h1>

<% if (msg != null) { %><p style="color:red;"><%= msg %></p><% } %>

<form action="<%=ctx%>/coursedata/CourseUpdateServlet" method="post">
  <input type="hidden" name="course_id" value="<%= cd.getCourseId() %>">

  <div>
    <label>コース名:</label>
    <input type="text" name="course_name"
           value="<%= cd.getCourseName()==null? "" : cd.getCourseName() %>" required>
  </div>

  <div>
    <label>学校:</label>
    <select name="school_id" required>
      <option value="">-- 選択 --</option>
      <% if (schoolList != null) {
           for (SchoolData s : schoolList) {
             boolean sel = (selSchoolId != null && selSchoolId.equals(s.getSchoolId()));
      %>
        <option value="<%= s.getSchoolId() %>" <%= sel ? "selected" : "" %>>
          <%= s.getSchoolName() %>
        </option>
      <% } } %>
    </select>
  </div>

  <div style="margin-top:10px;">
    <input type="submit" value="更新">
    <a href="<%=ctx%>/courses" style="margin-left:10px;">一覧へ戻る</a>
  </div>
</form>
</body>
</html>
