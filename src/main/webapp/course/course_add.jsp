<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List, model.school.SchoolData" %>
<%
  String ctx   = request.getContextPath();
  String msg   = (String) request.getAttribute("errorMessage");
  String cname = (String) request.getAttribute("course_name");
  String sel   = (String) request.getAttribute("school_id"); // 文字列で保持
  List<SchoolData> schools = (List<SchoolData>) request.getAttribute("schoolList");
%>
<!DOCTYPE html>
<html>
<head>
  <title>コース追加</title>
  <style>label{display:inline-block;width:120px;margin-top:6px}</style>
</head>
<body>
<h1>コース追加</h1>

<% if (msg != null) { %><p style="color:red;"><%= msg %></p><% } %>

<form action="<%=ctx%>/coursedata/CourseAddServlet" method="post">
  <div>
    <label>コース名:</label>
    <input type="text" name="course_name" value="<%= cname==null? "" : cname %>" required>
  </div>

  <div>
    <label>学校:</label>
    <select name="school_id" required>
      <option value="">-- 選択 --</option>
      <% if (schools != null) {
           for (SchoolData s : schools) {
             String v = String.valueOf(s.getSchoolId());
             boolean selected = (sel != null && sel.equals(v));
      %>
        <option value="<%= v %>" <%= selected ? "selected" : "" %>>
          <%= s.getSchoolName() %>
        </option>
      <%   }
         } %>
    </select>
  </div>

  <div style="margin-top:10px;">
    <input type="submit" value="登録">
    <a href="<%=ctx%>/courses" style="margin-left:10px;">一覧へ戻る</a>
  </div>
</form>
</body>
</html>
