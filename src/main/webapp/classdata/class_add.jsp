<!-- src/main/webapp/classdata/class_add.jsp -->
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*, model.classdata.ClassData, model.course.CourseData, model.school.SchoolData" %>
<%
  String ctx = request.getContextPath();
  String err = (String)request.getAttribute("errorMessage");
  ClassData cd = (ClassData)request.getAttribute("classData");
  List<CourseData> courseList = (List<CourseData>)request.getAttribute("courseList");
  List<SchoolData> schoolList = (List<SchoolData>)request.getAttribute("schoolList"); // ★
%>
<!DOCTYPE html>
<html>
<head><title>学科追加</title>
<style>label{display:inline-block;width:140px;margin-top:6px}</style>
</head>
<body>
<h1>学科追加</h1>
<% if (err!=null){ %><p style="color:red;"><%= err %></p><% } %>

<form action="<%=ctx%>/classdata/ClassAddServlet" method="post">
  <div>
    <label>学科名</label>
    <input type="text" name="class_name"
           value="<%= cd!=null && cd.getClassName()!=null ? cd.getClassName() : "" %>" required>
  </div>

  <div>
    <label>コース</label>
    <select name="course_id" required>
      <option value="">--選択--</option>
      <% if (courseList!=null) {
           Integer sel = cd!=null ? cd.getCourseId() : null;
           for (CourseData co : courseList) {
             boolean s = (sel!=null && sel.equals(co.getCourseId())); %>
        <option value="<%=co.getCourseId()%>" <%= s ? "selected" : "" %>>
          <%= co.getCourseName() %>
        </option>
      <% }} %>
    </select>
  </div>

  <!-- ★ 学校をセレクトに変更 -->
  <div>
    <label>学校</label>
    <select name="school_id" required>
      <option value="">--選択--</option>
      <% if (schoolList!=null) {
           Integer sel = cd!=null ? cd.getSchoolId() : null;
           for (SchoolData sc : schoolList) {
             boolean s = (sel!=null && sel.equals(sc.getSchoolId())); %>
        <option value="<%=sc.getSchoolId()%>" <%= s ? "selected" : "" %>>
          <%= sc.getSchoolName() %>
        </option>
      <% }} %>
    </select>
  </div>

  <div style="margin-top:10px;">
    <input type="submit" value="登録">
    <a href="<%=ctx%>/classes" style="margin-left:10px;">一覧へ戻る</a>
  </div>
</form>
</body>
</html>
