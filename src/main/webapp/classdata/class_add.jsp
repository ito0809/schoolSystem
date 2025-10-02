<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head><title>学科追加</title></head>
<body>
  <h1>学科追加</h1>

  <%
    String errorMessage = (String) request.getAttribute("errorMessage");
    if (errorMessage != null) {
  %>
    <p style="color:red;"><%= errorMessage %></p>
  <%
    }
  %>

  <!-- ★ action のパスをサーブレットの @WebServlet と一致させる -->
  <form action="<%= request.getContextPath() %>/classdata/ClassAddServlet" method="post" accept-charset="UTF-8">
    学科名: <input type="text" name="class_name" required><br><br>
    コースID: <input type="number" name="course_id" required><br><br>
    学校ID: <input type="number" name="school_id" required><br><br>

    <input type="submit" value="登録">
    <!-- ★ 一覧のURLに合わせる（@WebServlet("/classes")） -->
    <a href="<%= request.getContextPath() %>/classes" style="margin-left:10px;">一覧へ戻る</a>
  </form>
</body>
</html>
