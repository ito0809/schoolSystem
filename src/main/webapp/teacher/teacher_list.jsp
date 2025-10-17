<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*, model.teacher.TeacherData" %>
<%
  String ctx = request.getContextPath();
  List<TeacherData> list = (List<TeacherData>) request.getAttribute("teacherList");
  String q = (String) request.getAttribute("q");
  Integer pageNo = (Integer) request.getAttribute("page");       // ← ここをリネーム
  Integer totalPages = (Integer) request.getAttribute("pages");  // ← ここをリネーム
%>
<!DOCTYPE html>
<html>
<head>
  <title>教員一覧</title>
  <style>table{border-collapse:collapse}th,td{border:1px solid #ccc;padding:6px}</style>
</head>
<body>
<h1>教員一覧</h1>

<form method="get" action="<%=ctx%>/teachers" style="margin-bottom:8px;">
  キーワード: <input type="text" name="q" value="<%= q==null? "": q %>">
  <button type="submit">検索</button>
  <a href="<%=ctx%>/teacherdata/TeacherAddServlet" style="margin-left:10px;">追加</a>
</form>

<form method="get" style="margin-bottom:12px;">
  教員ID: <input type="number" name="id" required>
  <button type="submit" formaction="<%=ctx%>/teacherdata/TeacherUpdateServlet">編集</button>
  <button type="submit" formaction="<%=ctx%>/teacherdata/TeacherDeleteServlet">削除</button>
</form>

<table>
  <tr><th>ID</th><th>教員名</th><th>作成日時</th><th>更新日時</th></tr>
<% if (list!=null && !list.isEmpty()) { for (TeacherData t : list) { %>
  <tr>
    <td><%= t.getTeacherId() %></td>
    <td><%= t.getTeacherName()==null? "": t.getTeacherName() %></td>
    <td><%= t.getCreatedAt()==null? "": t.getCreatedAt() %></td>
    <td><%= t.getUpdatedAt()==null? "": t.getUpdatedAt() %></td>
  </tr>
<% }} else { %>
  <tr><td colspan="4">データが存在しません</td></tr>
<% } %>
</table>

<% if (totalPages!=null && totalPages>1) { %>
  <div style="margin-top:8px;">
    <% for (int p=1; p<=totalPages; p++) { %>
      <a href="<%=ctx%>/teachers?page=<%=p%><%= (q!=null && !q.isBlank()) ? "&q="+q : "" %>"
         style="margin-right:6px;<%= (pageNo!=null && p==pageNo) ? "font-weight:bold;" : "" %>"><%= p %></a>
    <% } %>
  </div>
<% } %>
</body>
</html>
