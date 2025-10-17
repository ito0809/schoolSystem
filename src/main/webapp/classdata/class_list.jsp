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

<!-- 画面に見える入力欄は１つだけ -->
学科ID:
<input type="number" id="classIdInput" required style="width:120px">
<br><br>

<!-- 編集用：独立したGETフォーム（hiddenにidを入れて送る） -->
<form id="editForm" action="<%=ctx%>/classdata/ClassUpdateServlet" method="get" style="display:inline;">
  <input type="hidden" name="id" id="editId">
  <button type="submit">編集</button>
</form>

<!-- 削除用：独立したGETフォーム（hiddenにidを入れて送る） -->
<form id="deleteForm" action="<%=ctx%>/classdata/ClassDeleteServlet" method="get" style="display:inline;margin-left:8px;">
  <input type="hidden" name="id" id="deleteId">
  <button type="submit">削除</button>
</form>

<script>
  // 入力値 → hidden へ常に同期
  const box = document.getElementById('classIdInput');
  const editId = document.getElementById('editId');
  const delId  = document.getElementById('deleteId');

  function sync() {
    editId.value = box.value.trim();
    delId.value  = box.value.trim();
  }
  box.addEventListener('input', sync);
  sync();

  // 入力未指定で送らせない（見栄えはそのまま）
  document.getElementById('editForm').addEventListener('submit', (e)=>{
    if (!box.value.trim()) { box.reportValidity(); e.preventDefault(); }
  });
  document.getElementById('deleteForm').addEventListener('submit', (e)=>{
    if (!box.value.trim()) { box.reportValidity(); e.preventDefault(); }
  });
</script>





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

  <form action="<%= request.getContextPath() %>/classdata/ClassAddServlet" method="get" style="margin-top:10px;">
    <input type="submit" value="追加">
  </form>
</body>
</html>
