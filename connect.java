package SNA;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import extra.Matrix;
import extra.Triple;

import java.awt.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class connect {
	public MatrixGraph<String> create() {
		int m = 0;
		ArrayList<String> list1 = new ArrayList<String>();
		ArrayList<String> list2 = new ArrayList<String>();
		MatrixGraph<String> graph = new MatrixGraph<String>();
		String name = "";
		String friends = "";
		String num = "";
		database database=new database();
		ResultSet set=null;
		try {
			set = database.linkDatabase("select * from per");
			while (set.next()) {
				String per1 = set.getString("id");
				String per2 = set.getString("name");
				String per3 = set.getString("sex");
				String per4 = set.getString("phoneNum");
				String per5 = set.getString("mail");
				String per6 = set.getString("qq");
				// 插入顶点集
				graph.insertVertex(per1);
			}
			set = database.linkDatabase("select * from  con");
			while (set.next()) {
				int con1 = set.getInt("proactive");
				int con2 = set.getInt("beactive");
				int con3 = set.getInt("num");
				// 插入边集
				graph.insertEdges(con1 - 1, con2 - 1, con3);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return graph;
	}
}
