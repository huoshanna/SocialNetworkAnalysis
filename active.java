package SNA;

import java.awt.font.GraphicAttribute;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import extra.SeqList;

public class active {
	public static void main(String args[]) throws SQLException {
		ResultSet set = null;
		connect connect = new connect();
		database database = new database();
		MatrixGraph<String> graph = connect.create();
		int n = graph.getVertex().size();
		String a[] = new String[n];
		String names="";
		for (int i = 0; i < n; i++) {
			a[i] = graph.getVertex().get(i);
		}
		String name[] = graph.circle(a, "3");
//		int id[] = new int[n];
		for (int i = 0; i < name.length; i++) {
			if (name[i] != null){
				double id =Double.parseDouble(name[i]);
				set = database.linkDatabase("select name from per where id=" +(int)id);
				while (set.next()) {
					names+="  "+set.getString("name");
				}
			}
		}
		System.out.println(names);
		// double id = Double.parseDouble(a);
		// String name = "";
		// set = database.linkDatabase("select * from per where id=" + id);
		// while (set.next()) {
		// System.out.println(set.getString("name"));
		// }
	}
}
