<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.subject.SubjectData" %>
<% String ctx = request.getContextPath();
   SubjectData s = (SubjectData)request.getAttribute("subjectData"); %>
<!DOCTYPE html>
<html><head><title>科目削除</title>
<style>table{border-collapse:collapse}th,td{border:1px solid #ccc;padding:6px}</style>
</head><body>
<h1>科目削除</h1>
<% if (s==null){ %>
  <p style="color:red;">科目が見つかりませんでした。</p>
  <p><a href="<%=ctx%>/subjects">一覧へ戻る</a></p>
<% } else { %>
<p>以下の科目を削除します。よろしいですか？</p>
<table>
  <tr><th>ID</th><td><%= s.getSubjectId() %></td></tr>
  <tr><th>サブフィールドID</th><td><%= s.getSubfieldId()==null?"":s.getSubfieldId() %></td></tr>
  <tr><th>科目名</th><td><%= s.getSubjectName()==null?"":s.getSubjectName() %></td></tr>
  <tr><th>単位</th><td><%= s.getCredits()==null?"":s.getCredits() %></td></tr>
</table>
<form action="<%=ctx%>/subjectdata/SubjectDeleteServlet" method="post" style="margin-top:10px;">
  <input type="hidden" name="id" value="<%= s.getSubjectId() %>">
  <input type="submit" value="削除する" onclick="return confirm('本当に削除しますか？');">
  <a href="<%=ctx%>/subjects" style="margin-left:10px;">キャンセル</a>
</form>
<% } %>
</body></html>
