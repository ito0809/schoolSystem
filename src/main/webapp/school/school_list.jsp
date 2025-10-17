<!-- src/main/webapp/school/school_list.jsp -->
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*, model.school.SchoolData" %>
<%
  String ctx = request.getContextPath();
  List<SchoolData> list = (List<SchoolData>)request.getAttribute("schools");
  String q = (String)request.getAttribute("q");
  Integer pageNo = (Integer)request.getAttribute("page");
  Integer pages  = (Integer)request.getAttribute("pages");
%>
<!DOCTYPE html><html><head><title>学校一覧</title>
<style>table{border-collapse:collapse}th,td{border:1px solid #ccc;padding:6px}</style>
</head><body>
<h1>学校一覧</h1>

<form method="get" action="<%=ctx%>/schools" style="margin-bottom:8px;">
  キーワード: <input type="text" name="q" value="<%= q==null?"":q %>">
  <button>検索</button>
  <a href="<%=ctx%>/schooldata/SchoolAddServlet" style="margin-left:10px;">追加</a>
</form>

<form method="get" style="margin-bottom:10px;">
  学校ID: <input type="number" name="id" required>
  <button type="submit" formaction="<%=ctx%>/schooldata/SchoolUpdateServlet">編集</button>
  <button type="submit" formaction="<%=ctx%>/schooldata/SchoolDeleteServlet">削除</button>
</form>

<table>
  <tr><th>ID</th><th>学校コード</th><th>学校名</th></tr>
  <% if (list!=null && !list.isEmpty()) {
       for (SchoolData s : list) { %>
    <tr>
      <td><%= s.getSchoolId() %></td>
      <td><%= s.getSchoolCode()==null?"":s.getSchoolCode() %></td>
      <td><%= s.getSchoolName()==null?"":s.getSchoolName() %></td>
    </tr>
  <% }} else { %>
    <tr><td colspan="3">データが存在しません</td></tr>
  <% } %>
</table>

<% if (pages!=null && pages>1) { %>
<div style="margin-top:8px;">
  <% for (int p=1; p<=pages; p++){ %>
    <a href="<%=ctx%>/schools?page=<%=p%><%= (q!=null&&!q.isBlank())? "&q="+q:"" %>"
       style="margin-right:6px;<%= (pageNo!=null && p==pageNo)?"font-weight:bold;":"" %>"><%=p%></a>
  <% } %>
</div>
<% } %>
</body></html>
