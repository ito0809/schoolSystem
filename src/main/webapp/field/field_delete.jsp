<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.field.FieldData" %>
<% String ctx = request.getContextPath();
   FieldData f = (FieldData)request.getAttribute("fieldData"); %>
<!DOCTYPE html>
<html><head><title>分野削除</title>
<style>table{border-collapse:collapse}th,td{border:1px solid #ccc;padding:6px}</style>
</head><body>
<h1>分野削除</h1>
<% if (f==null){ %>
  <p style="color:red;">分野が見つかりませんでした。</p>
  <p><a href="<%=ctx%>/fields">一覧へ戻る</a></p>
<% } else { %>
<p>以下の分野を削除します。よろしいですか？</p>
<table>
  <tr><th>ID</th><td><%= f.getFieldId() %></td></tr>
  <tr><th>分野名</th><td><%= f.getFieldName()==null?"":f.getFieldName() %></td></tr>
</table>
<form action="<%=ctx%>/fielddata/FieldDeleteServlet" method="post" style="margin-top:10px;">
  <input type="hidden" name="id" value="<%= f.getFieldId() %>">
  <input type="submit" value="削除する" onclick="return confirm('本当に削除しますか？');">
  <a href="<%=ctx%>/fields" style="margin-left:10px;">キャンセル</a>
</form>
<% } %>
</body></html>
