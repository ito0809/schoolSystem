<!-- src/main/webapp/classdata/class_update.jsp -->
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.classdata.ClassData, java.util.List, model.course.CourseData, model.school.SchoolData" %>
<%
  String ctx  = request.getContextPath();
  String err  = (String)request.getAttribute("errorMessage");
  ClassData c = (ClassData)request.getAttribute("classData");
  List<CourseData> courseList = (List<CourseData>)request.getAttribute("courseList");
  List<SchoolData> schoolList = (List<SchoolData>)request.getAttribute("schoolList"); // ★ 追加
%>
<!DOCTYPE html>
<html>
<head><title>学科更新</title>
<style>label{display:inline-block;width:140px;margin-top:6px}</style>
</head>
<body>
<h1>学科更新</h1>
<% if (err!=null){ %><p style="color:red;"><%= err %></p><% } %>

<% if (c == null) { %>
  <p style="color:red;">データが見つかりませんでした。</p>
  <p><a href="<%=ctx%>/classes">一覧へ戻る</a></p>
<% } else { %>
<form action="<%=request.getContextPath()%>/classdata/ClassUpdateServlet" method="post">
  <input type="hidden" name="class_id" value="<%= c.getClassId() %>">

  <div>
    <label>学科名</label>
    <input type="text" name="class_name" value="<%= c.getClassName()==null?"":c.getClassName() %>" required>
  </div>

  <div>
    <label>コース</label>
    <select name="course_id" required>
      <option value="">--選択--</option>
      <% if (courseList!=null) {
           Integer sel = c.getCourseId();
           for (CourseData co : courseList) {
             boolean s = (sel!=null && sel.equals(co.getCourseId())); %>
        <option value="<%=co.getCourseId()%>" <%= s?"selected":"" %>>
          <%= co.getCourseName() %>
        </option>
      <% }} %>
    </select>
  </div>

  <div>
    <label>学校</label>
    <select name="school_id" required>
      <option value="">--選択--</option>
      <% if (schoolList!=null) {
           Integer selS = c.getSchoolId();
           for (SchoolData sc : schoolList) {
             boolean s = (selS!=null && selS.equals(sc.getSchoolId())); %>
        <option value="<%=sc.getSchoolId()%>" <%= s?"selected":"" %>>
          <%= sc.getSchoolName() %>
        </option>
      <% }} %>
    </select>
  </div>

  <div style="margin-top:10px;">
    <input type="submit" value="更新">
    <a href="<%=ctx%>/classes" style="margin-left:10px;">一覧へ戻る</a>
  </div>
</form>
<% } %>
</body>
</html>
