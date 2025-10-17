<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.field.FieldData" %>
<% String ctx = request.getContextPath();
   String errorMessage = (String)request.getAttribute("errorMessage");
   FieldData f = (FieldData)request.getAttribute("fieldData"); %>
<!DOCTYPE html>
<html><head><title>分野更新</title>
<style>label{display:inline-block;width:120px;margin-top:6px}</style>
</head><body>
<h1>分野更新</h1>
<% if (errorMessage!=null){ %><p style="color:red;"><%= errorMessage %></p><% } %>
<% if (f==null){ %>
  <p style="color:red;">分野が見つかりませんでした。</p>
  <p><a href="<%=ctx%>/fields">一覧へ戻る</a></p>
<% } else { %>
<form action="<%=ctx%>/fielddata/FieldUpdateServlet" method="post">
  <input type="hidden" name="field_id" value="<%= f.getFieldId() %>">
  <div><label>分野名</label>
    <input type="text" name="field_name" value="<%= f.getFieldName()==null?"":f.getFieldName() %>" required>
  </div>
  <div style="margin-top:10px;">
    <input type="submit" value="更新">
    <a href="<%=ctx%>/fields" style="margin-left:10px;">一覧へ戻る</a>
  </div>
</form>
<% } %>
</body></html>
