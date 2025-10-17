<%@ page contentType="text/html; charset=UTF-8" %>
<% String ctx = request.getContextPath();
   String errorMessage = (String)request.getAttribute("errorMessage"); %>
<!DOCTYPE html>
<html><head><title>学期追加</title>
<style>label{display:inline-block;width:120px;margin-top:6px}</style>
</head><body>
<h1>学期追加</h1>
<% if (errorMessage!=null){ %><p style="color:red;"><%= errorMessage %></p><% } %>
<form action="<%=ctx%>/termdata/TermAddServlet" method="post">
  <div><label>学期名</label>
    <input type="text" name="term_name" required>
  </div>
  <div><label>表示順</label>
    <input type="number" name="term_order" min="0" max="127" step="1">
  </div>
  <div style="margin-top:10px;">
    <input type="submit" value="登録">
    <a href="<%=ctx%>/terms" style="margin-left:10px;">一覧へ戻る</a>
  </div>
</form>
</body></html>
