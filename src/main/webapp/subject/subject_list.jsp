<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*, model.subject.SubjectData, model.subfield.SubfieldData" %>
<%
  String ctx = request.getContextPath();
  List<SubjectData> list = (List<SubjectData>)request.getAttribute("subjectList");
  List<SubfieldData> subfieldList = (List<SubfieldData>)request.getAttribute("subfieldList");
  Integer subfield = (Integer)request.getAttribute("subfield");
  String q = (String)request.getAttribute("q");
%>
<!DOCTYPE html>
<html>
<head><title>科目一覧</title>
<style>table{border-collapse:collapse}th,td{border:1px solid #ccc;padding:6px}</style>
</head>
<body>
<h1>科目一覧</h1>

<form method="get" action="<%=ctx%>/subjects" style="margin-bottom:8px;">
  サブフィールド:
  <select name="subfield" style="width:180px;">
    <option value="">-- すべて --</option>
    <% if (subfieldList!=null) {
         for (SubfieldData sf : subfieldList) {
           boolean sel = (subfield!=null && subfield.equals(sf.getSubfieldId())); %>
      <option value="<%= sf.getSubfieldId() %>" <%= sel?"selected":"" %>><%= sf.getSubfieldName() %></option>
    <% }} %>
  </select>
  キーワード: <input type="text" name="q" value="<%= q==null?"":q %>">
  <button type="submit">検索</button>
  <a href="<%=ctx%>/subjectdata/SubjectAddServlet" style="margin-left:10px;">追加</a>
</form>

<form method="get" style="margin-bottom:12px;">
  科目ID: <input type="number" name="id" required>
  <button type="submit" formaction="<%=ctx%>/subjectdata/SubjectUpdateServlet">編集</button>
  <button type="submit" formaction="<%=ctx%>/subjectdata/SubjectDeleteServlet">削除</button>
</form>

<table>
  <tr><th>ID</th><th>サブフィールド</th><th>科目名</th><th>単位</th></tr>
<% if (list!=null && !list.isEmpty()) { for (SubjectData s : list) { %>
  <tr>
    <td><%= s.getSubjectId() %></td>
    <td><%= (s.getSubfieldName()!=null)? s.getSubfieldName() : ("ID:"+s.getSubfieldId()) %></td>
    <td><%= s.getSubjectName()==null?"":s.getSubjectName() %></td>
    <td><%= s.getCredits()==null?"":s.getCredits() %></td>
  </tr>
<% }} else { %>
  <tr><td colspan="4">データが存在しません</td></tr>
<% } %>
</table>
</body>
</html>
