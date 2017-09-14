package SNA;

import java.awt.*;
import javax.swing.*;
import java.sql.*;
import java.util.*;
import java.awt.event.*;
//功能一：显示人员信息
public class winPersonInfo extends JFrame{
	//rowData存放数据 
	//columnName存放列名
	private Vector rowData, columnName;
	private JTable jt = null;
	private JScrollPane jsp = null;
	database database = new database();
	//构造函数
	public winPersonInfo(){
		columnName = new Vector();
		//设置列名
		columnName.add("编号");
		columnName.add("姓名");
		columnName.add("性别");
		columnName.add("电话");
		columnName.add("邮箱");
		columnName.add("QQ");
		rowData = new Vector();
		try {
			ResultSet set = database.linkDatabase("select * from per;");	
			while(set.next()){
				Vector line1 = new Vector();
				line1.add(set.getString("id"));
				line1.add(set.getString("name"));
				line1.add(set.getString("sex"));
				line1.add(set.getString("phoneNum"));
				line1.add(set.getString("mail"));
				line1.add(set.getString("qq"));
				rowData.add(line1);
			}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		//rowData可存放多行
		jt = new JTable(rowData, columnName);	
		jsp = new JScrollPane(jt);	
		this.add(jsp);
		this.setTitle("人员信息表");
		this.setSize(560, 150);
		this.setLocation(320, 200);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);	
	}
	
}