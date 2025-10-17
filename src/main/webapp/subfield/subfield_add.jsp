<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*, model.field.FieldData, model.subfield.SubfieldData" %>
<%
  String ctx = request.getContextPath();
  SubfieldData sd = (SubfieldData)request.getAttribute("subfieldData");
  List<FieldData> fields = (List<FieldData>)request.getAttribute("fieldList");
  Integer selField = (sd!=null) ? sd.getFieldId() : null;
%>
<!DOCTYPE html>
<html>
<head><title>サブフィールド追加</title></head>
<body>
<h1>サブフィールド追加</h1>

<form action="<%=ctx%>/subfielddata/SubfieldAddServlet" method="post">
  <div>
    分野名:
    <select name="field_id" required>
      <option value="">--選択--</option>
      <% if (fields != null) {
           for (FieldData f : fields) {
             boolean selected = (selField != null && selField.equals(f.getFieldId()));
      %>
        <option value="<%= f.getFieldId() %>" <%= selected ? "selected" : "" %>>
          <%= f.getFieldName() %>
        </option>
      <%   }
         } %>
    </select>
  </div>

  <div>サブフィールド名:
    <input type="text" name="subfield_name"
           value="<%= sd!=null && sd.getSubfieldName()!=null ? sd.getSubfieldName() : "" %>" required>
  </div>

  <div style="margin-top:8px;">
    <button type="submit">登録</button>
    <a href="<%=ctx%>/subfields" style="margin-left:10px;">一覧へ戻る</a>
  </div>
</form>
</body>
</html>
