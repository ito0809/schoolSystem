<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*, model.field.FieldData" %>

<%
  String ctx = request.getContextPath();
  @SuppressWarnings("unchecked")
  List<FieldData> list = (List<FieldData>) request.getAttribute("fieldList");
  String q = (String) request.getAttribute("q");
  Integer curPage = (Integer) request.getAttribute("page");   // ← 変数名変更
  Integer pages   = (Integer) request.getAttribute("pages");
%>

<!DOCTYPE html>
<html>
<head><title>分野一覧</title>
<style>table{border-collapse:collapse}th,td{border:1px solid #ccc;padding:6px}</style>
</head>
<body>
<h1>分野一覧</h1>

<form method="get" action="<%=ctx%>/fields" style="margin-bottom:8px;">
  キーワード: <input type="text" name="q" value="<%= q==null?"":q %>">
  <button type="submit">検索</button>
  <a href="<%=ctx%>/fielddata/FieldAddServlet" style="margin-left:10px;">追加</a>
</form>

<form method="get" style="margin-bottom:12px;">
  分野ID: <input type="number" name="id" required>
  <button type="submit" formaction="<%=ctx%>/fielddata/FieldUpdateServlet">編集</button>
  <button type="submit" formaction="<%=ctx%>/fielddata/FieldDeleteServlet">削除</button>
</form>

<table>
  <tr><th>ID</th><th>分野名</th></tr>
<% if (list!=null && !list.isEmpty()) { for (FieldData f : list) { %>
  <tr>
    <td><%= f.getFieldId() %></td>
    <td><%= f.getFieldName()==null?"":f.getFieldName() %></td>
  </tr>
<% }} else { %>
  <tr><td colspan="2">データが存在しません</td></tr>
<% } %>
</table>

<% if (pages != null && pages > 1) { %>
  <div style="margin-top:8px;">
    <% for (int p = 1; p <= pages; p++) { %>
      <a href="<%= ctx %>/fields?page=<%= p %><%= (q != null && !q.isBlank()) ? "&q=" + q : "" %>"
         style="margin-right:6px;<%= (curPage != null && p == curPage) ? "font-weight:bold;" : "" %>">
        <%= p %>
      </a>
    <% } %>
  </div>
<% } %>

</body>
</html>
