<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.term.TermData" %>
<% String ctx = request.getContextPath();
   String errorMessage = (String)request.getAttribute("errorMessage");
   TermData t = (TermData)request.getAttribute("termData"); %>
<!DOCTYPE html>
<html><head><title>学期更新</title>
<style>label{display:inline-block;width:120px;margin-top:6px}</style>
</head><body>
<h1>学期更新</h1>
<% if (errorMessage!=null){ %><p style="color:red;"><%= errorMessage %></p><% } %>
<% if (t==null){ %>
  <p style="color:red;">学期が見つかりませんでした。</p>
  <p><a href="<%=ctx%>/terms">一覧へ戻る</a></p>
<% } else { %>
<form action="<%=ctx%>/termdata/TermUpdateServlet" method="post">
  <input type="hidden" name="term_id" value="<%= t.getTermId() %>">
  <div><label>学期名</label>
    <input type="text" name="term_name" value="<%= t.getTermName()==null?"":t.getTermName() %>" required>
  </div>
  <div><label>表示順</label>
    <input type="number" name="term_order"
           value="<%= t.getTermOrder()==null ? "" : t.getTermOrder() %>"
           min="0" max="127" step="1">
  </div>
  <div style="margin-top:10px;">
    <input type="submit" value="更新">
    <a href="<%=ctx%>/terms" style="margin-left:10px;">一覧へ戻る</a>
  </div>
</form>
<% } %>
</body></html>
