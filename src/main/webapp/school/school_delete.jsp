<!-- src/main/webapp/school/school_delete.jsp -->
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.school.SchoolData" %>
<%
  String ctx = request.getContextPath();
  SchoolData s = (SchoolData)request.getAttribute("school");
%>
<!DOCTYPE html><html><head><title>学校削除</title>
<style>table{border-collapse:collapse}th,td{border:1px solid #ccc;padding:6px}</style>
</head><body>
<h1>学校削除</h1>
<% if (s==null){ %>
  <p style="color:red;">データが見つかりません。</p>
  <p><a href="<%=ctx%>/schools">一覧へ戻る</a></p>
<% } else { %>
  <p>以下の学校を削除します。よろしいですか？</p>
  <table>
    <tr><th>ID</th><td><%= s.getSchoolId() %></td></tr>
    <tr><th>学校コード</th><td><%= s.getSchoolCode()==null?"":s.getSchoolCode() %></td></tr>
    <tr><th>学校名</th><td><%= s.getSchoolName()==null?"":s.getSchoolName() %></td></tr>
  </table>
  <form action="<%=ctx%>/schooldata/SchoolDeleteServlet" method="post" style="margin-top:10px;">
    <input type="hidden" name="id" value="<%= s.getSchoolId() %>">
    <input type="submit" value="削除する" onclick="return confirm('本当に削除しますか？');">
    <a href="<%=ctx%>/schools" style="margin-left:10px;">キャンセル</a>
  </form>
<% } %>
</body></html>
