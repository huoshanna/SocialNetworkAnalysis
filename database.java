package SNA;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class database {
	public ResultSet linkDatabase(String str) {
		ResultSet set = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://127.0.0.1:3306/huoshan";
			String ueser="huoshan";
			String pass="huoshan";
			Connection con = DriverManager.getConnection(url,ueser,pass);
			Statement sta = con.createStatement();
			set = sta.executeQuery(str);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return set;
	}
}
