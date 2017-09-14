package SNA;

import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

//查找某人的圈子
public class winSearch extends JFrame implements ActionListener {
	JButton b1 = null, b2 = null;
	JTextField input;
	JTextArea text1;
	connect connect = new connect();
	MatrixGraph<String> graph = connect.create();
	database database = new database();
	ResultSet set = null;

	winSearch() {
		JTextArea text1 = new JTextArea(350, 350);
		text1.setBounds(0, 0, 50, 50);
		text1.setText("团体矩阵\n");
		Box baseBox, box1, box2;
		input = new JTextField(10);
		input.addActionListener(this);
		box1 = Box.createVerticalBox();
		box1.add(new JLabel(""));
		box1.add(Box.createVerticalStrut(4));
		box1.add(new JLabel("输入要查询的人姓名"));
		box1.add(Box.createVerticalStrut(16));
		box2 = Box.createVerticalBox();
		box2.add(input);
		box2.add(Box.createVerticalStrut(16));
		baseBox = Box.createHorizontalBox();
		baseBox.add(box1);
		baseBox.add(box1.createHorizontalStrut(16));
		baseBox.add(box2);
		baseBox.setBounds(50, 50, 300, 50);
		setLayout(null);
		add(baseBox);
		validate();
		setBounds(380, 300, 400, 200);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		b1 = createButton(b1, "查询", 60, 120, 100, 35);
		b2 = createButton(b2, "返回", 200, 120, 100, 35);
		createArea(400, 200, "/Users/huoshan/Workspaces/MyEclipse 2015/SocialNetwork/pic/a.jpg");
	}

	public JButton createButton(JButton b, String str, int x, int y, int width, int height) {
		b = new JButton(str);
		b.setBounds(x, y, width, height);
		add(b);
		b.addActionListener(this);
		validate();
		return b;
	}

	public void createArea(int weight, int height, String str) {
		JPanel panelE = new JPanel();
		JLabel labelE = new JLabel();
		ImageIcon icon2 = new ImageIcon(str);
		icon2.setImage(icon2.getImage().getScaledInstance(weight, height, Image.SCALE_DEFAULT));
		labelE.setIcon(icon2);
		panelE.add(labelE);
		panelE.setBounds(0, 0, 400, 200);
		add(panelE);
		validate();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	public String searchCirle(String str) throws SQLException {
		ResultSet set = null;
		connect connect = new connect();
		database database = new database();
		MatrixGraph<String> graph = connect.create();
		int n = graph.getVertex().size();
		String a[] = new String[n];
		String names = "";
		for (int i = 0; i < n; i++) {
			a[i] = graph.getVertex().get(i);
		}
		String name[] = graph.circle(a, str);
		for (int i = 0; i < name.length; i++) {
			if (name[i] != null) {
				double id = Double.parseDouble(name[i]);
				set = database.linkDatabase("select name from per where id=" + (int) id);
				while (set.next()) {
					names += "  " + set.getString("name");
				}
			}
		}
		return names;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == b1) {
			String name = input.getText();
			JFrame frame = new JFrame("查询结果");
			frame.setBounds(450, 300, 200, 200);
			frame.setVisible(true);
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.getContentPane().setLayout(null);
			text1 = new JTextArea(350, 350);
			text1.setBounds(0, 0, 350, 350);
			text1.setText("输入不正确或查无此人\n请重新查询！");
			frame.add(text1);
			frame.validate();
			set = database.linkDatabase("select id from per where name='" + name + "'");
			try {
				while (set.next()) {
					String a = set.getString("id");
					double b = Double.parseDouble(a);
					boolean flag = false;
					String text = searchCirle(a);
					if (b > 0)
						flag = true;
					if (flag) {
						text1.setText(text);
						frame.add(text1);
						frame.validate();
					}
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			frame.validate();
		}
		if (e.getSource() == b2) {
			dispose();
		}
	}
}
