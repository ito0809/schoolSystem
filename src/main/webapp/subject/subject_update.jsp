<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.subject.SubjectData" %>
<% String ctx = request.getContextPath();
   String errorMessage = (String)request.getAttribute("errorMessage");
   SubjectData s = (SubjectData)request.getAttribute("subjectData");
%>
<!DOCTYPE html>
<html><head><title>科目更新</title>
<style>label{display:inline-block;width:140px;margin-top:6px}</style>
</head><body>
<h1>科目更新</h1>
<% if (errorMessage!=null){ %><p style="color:red;"><%= errorMessage %></p><% } %>
<% if (s==null){ %>
  <p style="color:red;">科目が見つかりませんでした。</p>
  <p><a href="<%=ctx%>/subjects">一覧へ戻る</a></p>
<% } else { %>
<form action="<%=ctx%>/subjectdata/SubjectUpdateServlet" method="post">
  <input type="hidden" name="subject_id" value="<%= s.getSubjectId() %>">
  <div><label>サブフィールドID</label>
    <input type="number" name="subfield_id" value="<%= s.getSubfieldId()==null?"":s.getSubfieldId() %>">
  </div>
  <div><label>科目名</label>
    <input type="text" name="subject_name" value="<%= s.getSubjectName()==null?"":s.getSubjectName() %>" required>
  </div>
  <div><label>単位</label>
    <input type="number" step="0.1" name="credits" value="<%= s.getCredits()==null?"":s.getCredits() %>">
  </div>
  <div style="margin-top:10px;">
    <input type="submit" value="更新">
    <a href="<%=ctx%>/subjects" style="margin-left:10px;">一覧へ戻る</a>
  </div>
</form>
<% } %>
</body></html>
