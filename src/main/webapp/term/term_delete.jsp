<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.term.TermData" %>
<% String ctx = request.getContextPath();
   TermData t = (TermData)request.getAttribute("termData"); %>
<!DOCTYPE html>
<html><head><title>学期削除</title>
<style>table{border-collapse:collapse}th,td{border:1px solid #ccc;padding:6px}</style>
</head><body>
<h1>学期削除</h1>
<% if (t==null){ %>
  <p style="color:red;">学期が見つかりませんでした。</p>
  <p><a href="<%=ctx%>/terms">一覧へ戻る</a></p>
<% } else { %>
<p>以下の学期を削除します。よろしいですか？</p>
<table>
  <tr><th>ID</th><td><%= t.getTermId() %></td></tr>
  <tr><th>学期名</th><td><%= t.getTermName()==null?"":t.getTermName() %></td></tr>
  <tr><th>表示順</th><td><%= t.getTermOrder()==null?"":t.getTermOrder() %></td></tr>
</table>
<form action="<%=ctx%>/termdata/TermDeleteServlet" method="post" style="margin-top:10px;">
  <input type="hidden" name="id" value="<%= t.getTermId() %>">
  <input type="submit" value="削除する" onclick="return confirm('本当に削除しますか？');">
  <a href="<%=ctx%>/terms" style="margin-left:10px;">キャンセル</a>
</form>
<% } %>
</body></html>
