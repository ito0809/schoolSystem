<!-- src/main/webapp/subfield/subfield_delete.jsp -->
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.subfield.SubfieldData" %>
<%
  String ctx = request.getContextPath();
  String err = (String)request.getAttribute("errorMessage");
  SubfieldData s = (SubfieldData)request.getAttribute("subfieldData");
%>
<!DOCTYPE html>
<html>
<head><title>サブフィールド削除</title></head>
<body>
<h1>サブフィールド削除</h1>
<% if (err != null) { %><p style="color:red;"><%= err %></p><% } %>

<% if (s == null) { %>
  <p style="color:red;">サブフィールドが見つかりませんでした。</p>
  <p><a href="<%=ctx%>/subfields">一覧へ戻る</a></p>
<% } else { %>
  <p>以下のサブフィールドを削除します。よろしいですか？</p>
  <table border="1" cellpadding="6">
    <tr><th>ID</th><td><%= s.getSubfieldId() %></td></tr>
    <tr><th>分野名</th>
      <td>
        <%= (s.getFieldName() != null && !s.getFieldName().isBlank())
              ? s.getFieldName()
              : ("ID: " + s.getFieldId()) %>
      </td>
    </tr>
    <tr><th>サブフィールド名</th><td><%= s.getSubfieldName() %></td></tr>
  </table>
  <form action="<%=ctx%>/subfielddata/SubfieldDeleteServlet" method="post" style="margin-top:10px;">
    <input type="hidden" name="id" value="<%= s.getSubfieldId() %>">
    <input type="submit" value="削除する" onclick="return confirm('本当に削除しますか？');">
    <a href="<%=ctx%>/subfields" style="margin-left:10px;">キャンセル</a>
  </form>
<% } %>
</body>
</html>
