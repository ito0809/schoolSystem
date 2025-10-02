<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.student.StudentData" %>
<% String ctx = request.getContextPath(); %>
<!DOCTYPE html>
<html>
<head><title>学生削除</title></head>
<body>
<h1>学生削除</h1>
<%
  StudentData s = (StudentData)request.getAttribute("studentData");
  if (s == null) {
%>
  <p style="color:red;">学生が見つかりませんでした。</p>
  <p><a href="<%=ctx%>/students">一覧へ戻る</a></p>
<% } else { %>
  <p>以下の学生を削除します。よろしいですか？</p>
  <table border="1" cellpadding="6" cellspacing="0">
    <tr><th>ID</th><td><%=s.getStudentId()%></td></tr>
    <tr><th>学籍番号</th><td><%=s.getStudentNumber()==null?"":s.getStudentNumber()%></td></tr>
    <tr><th>氏名</th><td><%=s.getFullName()%></td></tr>
    <tr><th>カナ</th><td><%=s.getFullNameKana()%></td></tr>
    <tr><th>生年月日</th><td><%=s.getBirthDate()==null?"":s.getBirthDate()%></td></tr>
    <tr><th>性別ID</th><td><%=s.getGenderId()==null?"":s.getGenderId()%></td></tr>
    <tr><th>住所</th><td>
      〒<%=s.getPostalCode()==null?"":s.getPostalCode()%><br>
      <%=s.getPrefecture()==null?"":s.getPrefecture()%>
      <%=s.getCity()==null?"":s.getCity()%>
      <%=s.getAddressLine()==null?"":s.getAddressLine()%>
    </td></tr>
    <tr><th>TEL</th><td><%=s.getTel()==null?"":s.getTel()%></td></tr>
    <tr><th>学校ID</th><td><%=s.getSchoolId()%></td></tr>
    <tr><th>入学日</th><td><%=s.getEnrollmentDate()==null?"":s.getEnrollmentDate()%></td></tr>
    <tr><th>卒業日</th><td><%=s.getGraduationDate()==null?"":s.getGraduationDate()%></td></tr>
    <tr><th>作成日時</th><td><%=s.getCreatedAt()==null?"":s.getCreatedAt()%></td></tr>
    <tr><th>更新日時</th><td><%=s.getUpdatedAt()==null?"":s.getUpdatedAt()%></td></tr>
  </table>

  <form action="<%=ctx%>/studentdata/StudentDeleteServlet" method="post" style="margin-top:10px;">
    <input type="hidden" name="id" value="<%=s.getStudentId()%>">
    <input type="submit" value="削除する" onclick="return confirm('本当に削除しますか？');">
    <a href="<%=ctx%>/students" style="margin-left:10px;">キャンセル</a>
  </form>
<% } %>
</body>
</html>
