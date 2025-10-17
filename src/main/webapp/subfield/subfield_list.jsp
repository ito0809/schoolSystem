<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*, model.subfield.SubfieldData" %>
<%
  String ctx = request.getContextPath();
  List<SubfieldData> list = (List<SubfieldData>)request.getAttribute("subfieldList");
  String q = (String)request.getAttribute("q");
  Integer field = (Integer)request.getAttribute("field");
  Integer pageNo = (Integer)request.getAttribute("page");
  Integer totalPages = (Integer)request.getAttribute("pages");
%>
<!DOCTYPE html>
<html>
<head><title>サブフィールド一覧</title>
<style>table{border-collapse:collapse}th,td{border:1px solid #ccc;padding:6px}</style>
</head>
<body>
<h1>サブフィールド一覧</h1>

<form method="get" action="<%=ctx%>/subfields" style="margin-bottom:8px;">
  分野名: <input type="number" name="field" value="<%= field==null?"":field %>" style="width:90px;">
  キーワード: <input type="text" name="q" value="<%= q==null?"":q %>">
  <button type="submit">検索</button>
  <a href="<%=ctx%>/subfielddata/SubfieldAddServlet" style="margin-left:10px;">追加</a>
</form>

<form method="get" style="margin-bottom:12px;">
  サブフィールドID: <input type="number" name="id" required>
  <button type="submit" formaction="<%=ctx%>/subfielddata/SubfieldUpdateServlet">編集</button>
  <button type="submit" formaction="<%=ctx%>/subfielddata/SubfieldDeleteServlet">削除</button>
</form>

<table>
  <tr><th>ID</th><th>分野名</th><th>サブフィールド名</th></tr>
<% if (list!=null && !list.isEmpty()) { for (SubfieldData s : list) { %>
  <tr>
    <td><%= s.getSubfieldId() %></td>
    <td><%= s.getFieldName() != null ? s.getFieldName() : ("ID:" + s.getFieldId()) %></td>
    <td><%= s.getSubfieldName()==null?"":s.getSubfieldName() %></td>
  </tr>
<% }} else { %>
  <tr><td colspan="3">データが存在しません</td></tr>
<% } %>
</table>

<% if (totalPages!=null && totalPages>1) { %>
  <div style="margin-top:8px;">
    <% for (int p=1; p<=totalPages; p++) { %>
      <a href="<%=ctx%>/subfields?page=<%=p%><%= (q!=null && !q.isBlank())? "&q="+q : "" %><%= (field!=null? "&field="+field : "") %>"
         style="margin-right:6px;<%= (pageNo!=null && p==pageNo) ? "font-weight:bold;" : "" %>"><%= p %></a>
    <% } %>
  </div>
<% } %>
</body>
</html>
