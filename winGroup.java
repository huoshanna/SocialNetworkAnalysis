package SNA;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

class winGroup extends JFrame implements ActionListener {
	connect connect = new connect();
	MatrixGraph<String> graph = connect.create();
	JButton b1, b2;
	JFrame frame;
	JPanel panel = new JPanel();
	JLabel label = new JLabel();
	JTextArea text1, text2;
	ResultSet set = null;
	database database = new database();

	winGroup() {
		JFrame frame = new JFrame("团体信息");
		frame.setBounds(450, 200, 300, 280);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		JPanel p = new JPanel();
		JLabel l = new JLabel();
		b1 = createButton(frame, b1, "1-团体邻接矩阵", 80, 80, 150, 45);
		b2 = createButton(frame, b2, "2-小团体信息", 80, 140, 150, 45);
		createArea(frame, 300, 280, 'E', "/Users/huoshan/Workspaces/MyEclipse 2015/SocialNetwork/pic/a.jpg");
	}

	public JButton createButton(JFrame frame, JButton b, String str, int x, int y, int width, int height) {
		b = new JButton(str);
		b.setBounds(x, y, width, height);
		frame.add(b);
		b.addActionListener(this);
		return b;
	}

	public static void createArea(JFrame frame, int weight, int height, char a, String str) {
		JPanel panelE = new JPanel();
		JLabel labelE = new JLabel();
		ImageIcon icon2 = new ImageIcon(str);
		icon2.setImage(icon2.getImage().getScaledInstance(weight, height, Image.SCALE_DEFAULT));
		labelE.setIcon(icon2);
		panelE.add(labelE);
		if (a == 'N')
			panelE.setBounds(0, 0, 600, 100);
		if (a == 'E')
			panelE.setBounds(0, 0, 300, 500);
		if (a == 'W')
			panelE.setBounds(400, 100, 200, 500);
		frame.add(panelE);
		frame.validate();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// 文本显示团体矩阵
		if (e.getSource() == b1) {
			JFrame frame = new JFrame("团体矩阵");
			frame.setBounds(500, 300, 200, 200);
			frame.setVisible(true);
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.getContentPane().setLayout(null);
			text1 = new JTextArea(350, 350);
			text1.setBounds(0, 0, 350, 350);
			text1.setText("团体矩阵\n" + graph.toString());
			frame.add(text1);
		}
		// 文本显示小团体矩阵
		if (e.getSource() == b2) {
			try {
				JFrame frame = new JFrame("小团体信息");
				frame.setBounds(500, 300, 200, 200);
				frame.setVisible(true);
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				frame.getContentPane().setLayout(null);
				String name = smallGroup();
				text1 = new JTextArea(350, 350);
				text1.setBounds(0, 0, 350, 350);
				text1.setText("每个人所在的小团体信息\n(第一列为其所在的小团体)\n" + name);
				frame.add(text1);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	public String smallGroup() throws SQLException {
		int id[] = graph.group();
		String name = "";
		for (int i = 0; i < id.length; i++) {
			if (id[i] != -1 && id[i] != 0) {
				set = database.linkDatabase("select name from per where id=" + id[i]);
				while (set.next()) {
					name = name + "  " + set.getString("name");
				}
			} else if (id[i] == -1) {
				name += "\n";
			}
		}
		return name;
	}
}