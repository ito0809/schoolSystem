<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*, model.subject.SubjectData, model.subfield.SubfieldData" %>
<%
  String ctx = request.getContextPath();
  String msg = (String)request.getAttribute("errorMessage");
  SubjectData sd = (SubjectData)request.getAttribute("subjectData");
  List<SubfieldData> subfieldList = (List<SubfieldData>)request.getAttribute("subfieldList");
  Integer selSubfieldId = (sd!=null)? sd.getSubfieldId() : null;
%>
<!DOCTYPE html>
<html>
<head><title>科目更新</title>
<style>label{display:inline-block;width:140px;margin-top:6px}</style>
</head>
<body>
<h1>科目更新</h1>

<% if (msg != null) { %><p style="color:red;"><%= msg %></p><% } %>

<form action="<%=ctx%>/subjectdata/SubjectUpdateServlet" method="post">
  <input type="hidden" name="subject_id" value="<%= sd.getSubjectId() %>">

  <div><label>サブフィールド</label>
    <select name="subfield_id" required>
      <option value="">-- 選択 --</option>
      <% if (subfieldList != null) {
           for (SubfieldData sf : subfieldList) {
             boolean sel = (selSubfieldId != null && selSubfieldId.equals(sf.getSubfieldId())); %>
        <option value="<%= sf.getSubfieldId() %>" <%= sel ? "selected" : "" %>>
          <%= sf.getSubfieldName() %>
        </option>
      <% } } %>
    </select>
  </div>

  <div><label>科目名</label>
    <input type="text" name="subject_name"
           value="<%= sd.getSubjectName()==null? "" : sd.getSubjectName() %>" required>
  </div>

  <div><label>単位</label>
    <input type="text" name="credits"
           value="<%= sd.getCredits()==null? "" : sd.getCredits() %>" required>
  </div>

  <div style="margin-top:10px;">
    <input type="submit" value="更新">
    <a href="<%=ctx%>/subjects" style="margin-left:10px;">一覧へ戻る</a>
  </div>
</form>
</body>
</html>
