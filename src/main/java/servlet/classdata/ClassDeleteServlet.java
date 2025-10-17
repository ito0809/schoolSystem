// src/main/java/servlet/classdata/ClassDeleteServlet.java
package servlet.classdata;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.classdata.ClassDeleteDao;
import dao.classdata.ClassListDao;
import model.classdata.ClassData;

@WebServlet("/classdata/ClassDeleteServlet")
public class ClassDeleteServlet extends HttpServlet {

  // 確認画面表示
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	    throws ServletException, IOException {

	  String idStr = req.getParameter("id");
	  if (idStr == null || idStr.isBlank()) {
	    resp.sendRedirect(req.getContextPath()+"/classes?badid=1");
	    return;
	  }

	  try {
	    int id = Integer.parseInt(idStr);
	    var c = new dao.classdata.ClassListDao().findById(id);
	    if (c == null) {
	      resp.sendRedirect(req.getContextPath()+"/classes?notfound=1");
	      return;
	    }
	    req.setAttribute("classData", c);
	    req.getRequestDispatcher("/classdata/class_delete.jsp").forward(req, resp);

	  } catch (NumberFormatException e) {
	    resp.sendRedirect(req.getContextPath()+"/classes?badid=1");
	  } catch (java.sql.SQLException e) {
	    throw new ServletException(e);
	  }
	}

  // 削除実行
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    req.setCharacterEncoding("UTF-8");
    String idStr = req.getParameter("id");
    if (idStr == null || idStr.isBlank()) {
      resp.sendRedirect(req.getContextPath()+"/classes");
      return;
    }

    try {
      int id = Integer.parseInt(idStr);
      int deleted = new ClassDeleteDao().deleteById(id);

      if (deleted == 1) {
        // 成功 → 一覧
        resp.sendRedirect(req.getContextPath()+"/classes?deleted="+id);
      } else {
        // 対象なし → そのまま戻す
        req.setAttribute("errorMessage", "削除対象が見つかりません。");
        // 確認画面を再表示するためにデータ再取得
        ClassData c = new ClassListDao().findById(id);
        req.setAttribute("classData", c);
        req.getRequestDispatcher("/classdata/class_delete.jsp").forward(req, resp);
      }

    } catch (SQLIntegrityConstraintViolationException e) {
      // 参照整合性違反（他テーブルに紐づきあり）など
      req.setAttribute("errorMessage",
          "この学科は他のデータに紐づいているため削除できません。");
      try {
        ClassData c = new ClassListDao().findById(Integer.parseInt(idStr));
        req.setAttribute("classData", c);
      } catch (SQLException ignore) {}
      req.getRequestDispatcher("/classdata/class_delete.jsp").forward(req, resp);

    } catch (NumberFormatException e) {
      resp.sendRedirect(req.getContextPath()+"/classes?badid=1");

    } catch (SQLException e) {
      throw new ServletException(e);
    }
  }
}
