<%@ page contentType="text/html; charset=UTF-8" isErrorPage="true" %>
<%
  // 入力ミス扱いにしたい場合は 400 に変更（任意）
  response.setStatus(400);

  String msg = "入力内容に誤りがあります。存在しないIDを指定した可能性があります。";
  if (exception instanceof java.sql.SQLException) {
    var se = (java.sql.SQLException) exception;
    if ("23000".equals(se.getSQLState())) {
      if (se.getErrorCode()==1452) msg="外部キー制約エラー：指定した参照IDが存在しません。";
      else if (se.getErrorCode()==1451) msg="外部キー制約エラー：関連データがあるため削除できません。";
      else if (se.getErrorCode()==1062) msg="一意制約エラー：同じ値が既に登録されています。";
    }
  }
%>
<!DOCTYPE html><html><body>
<h1>処理できませんでした</h1>
<p><%= msg %></p>
<p><a href="javascript:history.back()">前の画面に戻る</a></p>
</body></html>
