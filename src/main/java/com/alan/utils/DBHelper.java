package com.alan.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBHelper {
	public static final String name = "com.mysql.jdbc.Driver";

	public Connection conn = null;
	public PreparedStatement pst = null;

	public DBHelper(String db, String sql, String URL, String USER, String PASSWORD) {
		try {
			Class.forName(name);// 指定连接类型
			conn = DriverManager.getConnection(URL + db, USER, PASSWORD);// 获取连接
			pst = conn.prepareStatement(sql);// 准备执行语句
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			this.conn.close();
			this.pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

	public static Connection getConnectionSqlServer(String dbURL, String userName, String userPwd) {

        String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

        Connection dbConn = null;
        try {
            Class.forName(driverName).newInstance();
        } catch (Exception ex) {
            System.err.println("驱动加载失败");
            ex.printStackTrace();
        }
        try {
            dbConn = DriverManager.getConnection(dbURL, userName, userPwd);
          System.out.println("成功连接数据库！");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dbConn;
    }
}