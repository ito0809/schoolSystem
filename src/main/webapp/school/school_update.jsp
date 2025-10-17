<!-- src/main/webapp/school/school_update.jsp -->
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.school.SchoolData" %>
<%
  String ctx = request.getContextPath();
  String err = (String)request.getAttribute("errorMessage");
  SchoolData s = (SchoolData)request.getAttribute("school");
%>
<!DOCTYPE html><html><head><title>学校更新</title>
<style>label{display:inline-block;width:120px;margin-top:6px}</style>
</head><body>
<h1>学校更新</h1>
<% if (err!=null){ %><p style="color:red;"><%=err%></p><% } %>
<% if (s==null){ %>
  <p style="color:red;">データが見つかりません。</p>
  <p><a href="<%=ctx%>/schools">一覧へ戻る</a></p>
<% } else { %>
<form action="<%=ctx%>/schooldata/SchoolUpdateServlet" method="post">
  <input type="hidden" name="school_id" value="<%= s.getSchoolId() %>">
  <div><label>学校コード</label>
    <input type="text" name="school_code" value="<%= s.getSchoolCode()==null?"":s.getSchoolCode() %>"></div>
  <div><label>学校名</label>
    <input type="text" name="school_name" value="<%= s.getSchoolName()==null?"":s.getSchoolName() %>" required></div>
  <div style="margin-top:10px;">
    <input type="submit" value="更新">
    <a href="<%=ctx%>/schools" style="margin-left:10px;">一覧へ戻る</a>
  </div>
</form>
<% } %>
</body></html>
