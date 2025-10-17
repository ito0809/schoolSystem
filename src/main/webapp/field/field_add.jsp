<%@ page contentType="text/html; charset=UTF-8" %>
<% String ctx = request.getContextPath();
   String errorMessage = (String)request.getAttribute("errorMessage"); %>
<!DOCTYPE html>
<html><head><title>分野追加</title>
<style>label{display:inline-block;width:120px;margin-top:6px}</style>
</head><body>
<h1>分野追加</h1>
<% if (errorMessage!=null){ %><p style="color:red;"><%= errorMessage %></p><% } %>
<form action="<%=ctx%>/fielddata/FieldAddServlet" method="post">
  <div><label>分野名</label>
    <input type="text" name="field_name" required>
  </div>
  <div style="margin-top:10px;">
    <input type="submit" value="登録">
    <a href="<%=ctx%>/fields" style="margin-left:10px;">一覧へ戻る</a>
  </div>
</form>
</body></html>
