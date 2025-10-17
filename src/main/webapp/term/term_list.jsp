<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*, model.term.TermData" %>
<%
  String ctx = request.getContextPath();
  @SuppressWarnings("unchecked")
  List<TermData> list = (List<TermData>)request.getAttribute("termList");
  String q = (String)request.getAttribute("q");
  Integer curPage = (Integer)request.getAttribute("page");
  Integer pages   = (Integer)request.getAttribute("pages");
%>
<!DOCTYPE html>
<html>
<head><title>学期一覧</title>
<style>table{border-collapse:collapse}th,td{border:1px solid #ccc;padding:6px}</style>
</head>
<body>
<h1>学期一覧</h1>

<form method="get" action="<%=ctx%>/terms" style="margin-bottom:8px;">
  キーワード: <input type="text" name="q" value="<%= q==null ? "" : q %>">
  <button type="submit">検索</button>
  <a href="<%=ctx%>/termdata/TermAddServlet" style="margin-left:10px;">追加</a>
</form>

<form method="get" style="margin-bottom:12px;">
  学期ID: <input type="number" name="id" required>
  <button type="submit" formaction="<%=ctx%>/termdata/TermUpdateServlet">編集</button>
  <button type="submit" formaction="<%=ctx%>/termdata/TermDeleteServlet">削除</button>
</form>

<table>
  <tr><th>ID</th><th>学期名</th><th>表示順</th></tr>
  <% if (list != null && !list.isEmpty()) {
       for (TermData t : list) { %>
    <tr>
      <td><%= t.getTermId() %></td>
      <td><%= t.getTermName()==null ? "" : t.getTermName() %></td>
      <td><%= t.getTermOrder()==null ? "" : t.getTermOrder() %></td>
    </tr>
  <% }} else { %>
    <tr><td colspan="3">データが存在しません</td></tr>
  <% } %>
</table>

<% if (pages != null && pages > 1) { %>
<div style="margin-top:8px;">
  <% for (int p=1; p<=pages; p++) { %>
    <a href="<%=ctx%>/terms?page=<%=p%><%= (q!=null && !q.isBlank())? "&q="+q : "" %>"
       style="margin-right:6px;<%= (curPage!=null && p==curPage) ? "font-weight:bold;" : "" %>"><%= p %></a>
  <% } %>
</div>
<% } %>
</body>
</html>
