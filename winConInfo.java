package SNA;

import java.awt.*;
import javax.swing.*;
import java.sql.*;
import java.util.*;
import java.awt.event.*;

//功能二：联系表
public class winConInfo extends JFrame {
	// rowData存放数据
	// columnName存放列名
	private Vector rowData, columnName;
	private JTable jt = null;
	private JScrollPane jsp = null;
	database database = new database();
	// 构造函数
	public winConInfo() {
		columnName = new Vector();
		rowData = new Vector();
		columnName.add("联系人编号");
		columnName.add("被联系人编号");
		columnName.add("联系次数");
		try {
			ResultSet set = database.linkDatabase("select * from con;");
			while (set.next()) {
				Vector line1 = new Vector();
				line1.add(set.getString("proactive"));
				line1.add(set.getString("beactive"));
				line1.add(set.getString("num"));
				rowData.add(line1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		// rowData可存放多行
		// 初始化JTable
		jt = new JTable(rowData, columnName);
		jsp = new JScrollPane(jt);
		this.add(jsp);
		this.setTitle("联系信息表");
		this.setSize(300, 300);
		this.setLocation(460, 200);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
	}

}
