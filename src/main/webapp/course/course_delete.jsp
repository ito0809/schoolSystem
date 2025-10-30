<!-- /webapp/course/course_delete.jsp -->
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.course.CourseData" %>
<%
  String ctx = request.getContextPath();
  CourseData c = (CourseData) request.getAttribute("courseData");
%>
<!DOCTYPE html>
<html><head><title>コース削除</title></head>
<body>
<h1>コース削除</h1>

<% if (c == null) { %>
  <p style="color:red;">コースが見つかりませんでした。</p>
  <p><a href="<%=ctx%>/courses">一覧へ戻る</a></p>
<% } else { %>
  <p>以下のコースを削除します。よろしいですか？</p>
  <ul>
    <li>ID: <%= c.getCourseId() %></li>
    <li>コース名: <%= c.getCourseName() %></li>
    <!-- ★学校名があれば表示、なければIDのフォールバック -->
    <li>学校:
  <%= (c.getSchoolName() != null && !c.getSchoolName().isEmpty())
        ? c.getSchoolName()
        : ("ID:" + String.valueOf(c.getSchoolId())) %>
</li>

  </ul>

  <form action="<%=ctx%>/coursedata/CourseDeleteServlet" method="post">
    <input type="hidden" name="id" value="<%= c.getCourseId() %>">
    <input type="submit" value="削除する" onclick="return confirm('本当に削除しますか？');">
    <a href="<%=ctx%>/courses" style="margin-left:10px;">キャンセル</a>
  </form>
<% } %>
</body></html>
