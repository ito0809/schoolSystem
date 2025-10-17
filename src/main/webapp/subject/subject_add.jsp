<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.subject.SubjectData" %>
<% String ctx = request.getContextPath();
   String errorMessage = (String)request.getAttribute("errorMessage");
   SubjectData sd = (SubjectData)request.getAttribute("subjectData");
%>
<!DOCTYPE html>
<html><head><title>科目追加</title>
<style>label{display:inline-block;width:140px;margin-top:6px}</style>
</head><body>
<h1>科目追加</h1>
<% if (errorMessage!=null){ %><p style="color:red;"><%= errorMessage %></p><% } %>
<form action="<%=ctx%>/subjectdata/SubjectAddServlet" method="post">
  <div><label>サブフィールドID</label>
    <input type="number" name="subfield_id" value="<%= sd!=null&&sd.getSubfieldId()!=null?sd.getSubfieldId():"" %>">
  </div>
  <div><label>科目名</label>
    <input type="text" name="subject_name" value="<%= sd!=null&&sd.getSubjectName()!=null?sd.getSubjectName():"" %>" required>
  </div>
  <div><label>単位（例: 2 または 2.5）</label>
    <input type="number" step="0.1" name="credits" value="<%= sd!=null&&sd.getCredits()!=null?sd.getCredits():"" %>">
  </div>
  <div style="margin-top:10px;">
    <input type="submit" value="登録">
    <a href="<%=ctx%>/subjects" style="margin-left:10px;">一覧へ戻る</a>
  </div>
</form>
</body></html>
