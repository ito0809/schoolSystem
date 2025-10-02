package dao.base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 全DAO共通のベースクラス
 * DB接続を提供する
 */
public abstract class BaseDao {

    // 環境に合わせて書き換えてください
    private static final String URL  = "jdbc:mysql://localhost:3306/school_system?serverTimezone=Asia/Tokyo";
    private static final String USER = "root";       // MySQLのユーザー名
    private static final String PASS = "kamimura";   // MySQLのパスワード

    static {
        try {
            // JDBCドライバをロード（MySQL 8.x）
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQLドライバのロードに失敗しました", e);
        }
    }

    /** コネクションを取得 */
    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
