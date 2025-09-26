<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List, model.classdata.ClassData" %>
<html>
<head>
  <title>クラス一覧</title>
  <style>
    table{border-collapse:collapse} th,td{border:1px solid #ccc;padding:6px}
    label{display:inline-block;width:80px}
  </style>
</head>
<body>
<h2>クラス一覧</h2>

<form method="get" action="<c:url value='/classes'/>">
  <label>学校ID</label><input type="text" name="school_id" value="${school_id}"/>
  <label>コースID</label><input type="text" name="course_id" value="${course_id}"/>
  <label>名称</label><input type="text" name="q" value="${q}"/>
  <button type="submit">検索</button>
  <a href="<c:url value='/classes/add'/>">新規追加</a>
</form>

<c:if test="${not empty param.created}">
  <p style="color:green">作成しました（ID: ${param.created}）。</p>
</c:if>
<c:if test="${not empty param.updated}">
  <p style="color:green">更新しました（ID: ${param.updated}）。</p>
</c:if>
<c:if test="${param.deleted == '1'}">
  <p style="color:green">削除しました。</p>
</c:if>
<c:if test="${param.error == 'constraint'}">
  <p style="color:red">削除できません（在籍や開講などから参照されています）。</p>
</c:if>

<table>
  <tr><th>ID</th><th>クラス名</th><th>コースID</th><th>学校ID</th><th>操作</th></tr>
  <c:forEach var="cdata" items="${list}">
    <tr>
      <td>${cdata.classId}</td>
      <td>${cdata.className}</td>
      <td>${cdata.courseId}</td>
      <td>${cdata.schoolId}</td>
      <td>
        <a href="<c:url value='/classes/edit?id=${cdata.classId}'/>">編集</a>
        <a href="<c:url value='/classes/delete?id=${cdata.classId}'/>">削除</a>
      </td>
    </tr>
  </c:forEach>
</table>

<div style="margin-top:8px">
  <c:forEach var="p" begin="1" end="${pages}">
    <c:choose>
      <c:when test="${p == page}"><b>[${p}]</b></c:when>
      <c:otherwise>
        <a href="?page=${p}&q=${q}&school_id=${school_id}&course_id=${course_id}">[${p}]</a>
      </c:otherwise>
    </c:choose>
  </c:forEach>
</div>

<p><a href="<c:url value='/'/>">メニューへ</a></p>
</body>
</html>
