<!-- src/main/webapp/subfield/subfield_update.jsp -->
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*, model.subfield.SubfieldData, model.field.FieldData" %>
<%
  String ctx  = request.getContextPath();
  String err  = (String)request.getAttribute("errorMessage");
  SubfieldData s = (SubfieldData)request.getAttribute("subfieldData");
  List<FieldData> fields = (List<FieldData>)request.getAttribute("fieldList");
  Integer selField = (s!=null)? s.getFieldId() : null;
%>
<!DOCTYPE html>
<html>
<head>
  <title>サブフィールド更新</title>
  <style>label{display:inline-block;width:140px;margin-top:6px}</style>
</head>
<body>
<h1>サブフィールド更新</h1>
<% if (err!=null){ %><p style="color:red;"><%= err %></p><% } %>

<% if (s==null) { %>
  <p style="color:red;">サブフィールドが見つかりませんでした。</p>
  <p><a href="<%=ctx%>/subfields">一覧へ戻る</a></p>
<% } else { %>
<form action="<%=ctx%>/subfielddata/SubfieldUpdateServlet" method="post">
  <input type="hidden" name="subfield_id" value="<%= s.getSubfieldId() %>">

  <div><label>分野名</label>
    <select name="field_id" required>
      <option value="">--選択--</option>
      <% if (fields != null) for (FieldData f : fields) { %>
        <option value="<%=f.getFieldId()%>"
          <%= (selField!=null && selField.equals(f.getFieldId())) ? "selected" : "" %>>
          <%= f.getFieldName() %>
        </option>
      <% } %>
    </select>
  </div>

  <div><label>サブフィールド名</label>
    <input type="text" name="subfield_name"
           value="<%= s.getSubfieldName()==null? "" : s.getSubfieldName() %>" required>
  </div>

  <div style="margin-top:10px;">
    <input type="submit" value="更新">
    <a href="<%=ctx%>/subfields" style="margin-left:10px;">一覧へ戻る</a>
  </div>
</form>
<% } %>
</body>
</html>
