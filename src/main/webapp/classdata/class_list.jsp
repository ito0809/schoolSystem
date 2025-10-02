<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*, model.classdata.ClassData" %>
<!DOCTYPE html>
<html>
<head>
  <title>学科一覧</title>
  <style>
    table{border-collapse:collapse}
    th,td{border:1px solid #ccc; padding:6px;} /* cellpadding不要 */
  </style>
</head>
<body>
  <h1>学科一覧</h1>

<% String ctx = request.getContextPath(); %>
<form method="get">
  学科ID: <input type="number" name="id" required><br><br>

  <!-- 編集：/classdata/ClassUpdateServlet に GET -->
  <button type="submit"
          formaction="<%= ctx %>/classdata/ClassUpdateServlet"
          formmethod="get">編集</button>

  <!-- 削除はそのまま -->
  <button type="submit"
          formaction="<%= ctx %>/classdata/ClassDeleteServlet"
          formmethod="get">削除</button>
</form>


  <br>

<%! // ★ 警告なく安全に取り出すヘルパー
    @SuppressWarnings("unchecked")
    private java.util.List<model.classdata.ClassData> getClassList(javax.servlet.http.HttpServletRequest req){
        Object o = req.getAttribute("classList");
        if (o instanceof java.util.List<?>) {
            try { return (java.util.List<model.classdata.ClassData>) o; }
            catch (ClassCastException ignore) {}
        }
        return java.util.Collections.emptyList();
    }
%>

  <table>
    <tr>
      <th>学科ID</th><th>学科名</th>
    </tr>
    <%
      List<ClassData> list = getClassList(request); // ← ここで取得
      if (!list.isEmpty()) {
        for (ClassData cd : list) {
    %>
    <tr>
      <td><%= cd.getClassId() %></td>
      <td><%= cd.getClassName() %></td>
    </tr>
    <%
        }
      } else {
    %>
    <tr><td colspan="2">データが存在しません</td></tr>
    <% } %>
  </table>

  <form action="<%= request.getContextPath() %>/classdata/class_add.jsp" method="get" style="margin-top:10px;">
    <input type="submit" value="追加">
  </form>
</body>
</html>
