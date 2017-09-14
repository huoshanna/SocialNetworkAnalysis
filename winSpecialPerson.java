package SNA;

import java.sql.*;
import java.util.Vector;
import javax.swing.*;
import org.omg.CORBA.PUBLIC_MEMBER;

//特殊人员信息界面
public class winSpecialPerson extends JFrame {
	private Vector rowData, columnName;
	private JTable jt = null;
	private JScrollPane jsp = null;
	database database = new database();
	public winSpecialPerson(String str) {
		if(str=="main"||str=="margin"||str=="active"){
			createFrame(str);
		}
		else {
			isPerson(str);
		}
	}

	// 界面显示
	public void createFrame(String str) {
		columnName = new Vector();
		columnName.add("编号");
		columnName.add("姓名");
		columnName.add("性别");
		columnName.add("电话");
		columnName.add("邮箱");
		columnName.add("QQ");
		rowData = new Vector();
		rowData = isPerson(str);
		jt = new JTable(rowData, columnName);
		jsp = new JScrollPane(jt);
		this.add(jsp);
		this.setTitle("人员信息表");
		this.setSize(560, 100);
		this.setLocation(320, 200);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
	}

	// 判断要查找何种人
	public Vector isPerson(String str) {
		connect connect = new connect();
		MatrixGraph<String> graph = connect.create();
		try {
			if (str == "main") {
				// 查找核心人物
				String main = graph.mainperson();
				double id1 = Double.parseDouble(main);
				ResultSet set = database.linkDatabase("select * from per where id=" + (int) id1);
				while (set.next()) {
					Vector line1 = new Vector();
					line1.add(set.getString("id"));
					line1.add(set.getString("name"));
					line1.add(set.getString("sex"));
					line1.add(set.getString("phoneNum"));
					line1.add(set.getString("mail"));
					line1.add(set.getString("qq"));
					rowData.add(line1);
				}
			}
			if (str == "active") {
				// 查找活跃人物
				String active = graph.activeperson();
				double numa = Double.parseDouble(active);
				int id = (int) numa;
				ResultSet set = database.linkDatabase("select * from per where id=" + id);
				while (set.next()) {
					Vector line1 = new Vector();
					line1.add(set.getString("id"));
					line1.add(set.getString("name"));
					line1.add(set.getString("sex"));
					line1.add(set.getString("phoneNum"));
					line1.add(set.getString("mail"));
					line1.add(set.getString("qq"));
					rowData.add(line1);
				}
			}
			if (str == "margin") {
				// 查找边缘人物
				String margin = graph.marginperson();
				double id2 = Double.parseDouble(margin);
				ResultSet set = database.linkDatabase("select * from per where id=" + (int) id2);
				while (set.next()) {
					Vector line1 = new Vector();
					line1.add(set.getString("id"));
					line1.add(set.getString("name"));
					line1.add(set.getString("sex"));
					line1.add(set.getString("phoneNum"));
					line1.add(set.getString("mail"));
					line1.add(set.getString("qq"));
					rowData.add(line1);
				}
			}
			//小团体桥接人
			if (str == "link") {	
				JFrame frame = new JFrame("小团体桥接人");
				frame.setBounds(400, 300, 200, 200);
				frame.setVisible(true);
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				frame.getContentPane().setLayout(null);
				String name =link();
				JTextArea text1 = new JTextArea(350, 350);
				text1.setBounds(0, 0, 350, 350);
				text1.setText("小团体桥接人\n" + name);
				frame.add(text1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rowData;

	}

	public String link() throws SQLException {
		ResultSet set = null;
		connect connect = new connect();
		MatrixGraph<String> graph = connect.create();
		String name = "";
		int id[] = graph.Connecter();
		for (int i = 0; i < id.length; i++) {
			if (id[i] > 0) {
				set = database.linkDatabase("select name from per where id=" + id[i]);
				while (set.next()) {
					name = name + "  " + set.getString("name");
				}
			}
		}
		return name;
	}
}
