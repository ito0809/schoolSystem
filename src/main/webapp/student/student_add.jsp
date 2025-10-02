<%@ page contentType="text/html; charset=UTF-8" %>
<% String ctx = request.getContextPath(); %>
<!DOCTYPE html>
<html>
<head><title>学生追加</title>
<style>label{display:inline-block;width:140px;margin-top:6px}</style>
</head>
<body>
<h1>学生追加</h1>
<%
  String msg = (String)request.getAttribute("errorMessage");
  if (msg != null) { out.print("<p style='color:red;'>"+msg+"</p>"); }
%>
<form action="<%=ctx%>/studentdata/StudentAddServlet" method="post">
  <div><label>学籍番号</label><input type="text"  name="student_number"></div>
  <div><label>姓</label><input type="text" name="last_name"></div>
  <div><label>名</label><input type="text" name="first_name"></div>
  <div><label>セイ</label><input type="text" name="last_name_kana"></div>
  <div><label>メイ</label><input type="text" name="first_name_kana"></div>
  <div><label>生年月日</label><input type="date" name="birth_date"></div>
  <div><label>性別ID</label><input type="number" name="gender_id"></div>
  <div><label>郵便番号</label><input type="text" name="postal_code"></div>
  <div><label>都道府県</label><input type="text" name="prefecture"></div>
  <div><label>市区町村</label><input type="text" name="city"></div>
  <div><label>番地等</label><input type="text" name="address_line"></div>
  <div><label>TEL</label><input type="text" name="tel"></div>
  <div><label>学校ID</label><input type="number" name="school_id" required></div>
  <div><label>入学日</label><input type="date" name="enrollment_date"></div>
  <div><label>卒業日</label><input type="date" name="graduation_date"></div>

  <div style="margin-top:10px;">
    <input type="submit" value="登録">
    <a href="<%=ctx%>/students" style="margin-left:10px;">一覧へ戻る</a>
  </div>
</form>
</body>
</html>
