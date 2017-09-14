package SNA;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

class MyWindow extends JFrame implements ActionListener {
	JButton b1, b2, b3, b4, b5, b6, b7, b8, b9;
	static JFrame frame;
	JPanel panel = new JPanel();
	JLabel label = new JLabel();
	public static void main(String args[]){
		new MyWindow();
	}
	MyWindow() {
		frame = new JFrame("SNA");
		frame.setBounds(300, 100, 600, 480);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		b1 = createButton(frame, b1, "1-团体信息", 200, 100, 200, 45);
		b2 = createButton(frame, b2, "2-人员信息", 200, 145, 200, 45);
		b3 = createButton(frame, b3, "3-社交关系力导向图", 200, 190, 200, 45);
		b4 = createButton(frame, b4, "4-团体核心人物", 200, 235, 200, 45);
		b5 = createButton(frame, b5, "5-团体活跃人物", 200, 280, 200, 45);
		b6 = createButton(frame, b6, "6-团体边缘人物", 200, 325, 200, 45);
		b7 = createButton(frame, b7, "7-小团体桥接人", 200, 370, 200, 45);
		b8 = createButton(frame, b8, "8-查询某人的圈子", 200, 415, 200, 45);
		createArea(frame, 200, 450, 'E', "/Users/huoshan/Workspaces/MyEclipse 2015/SocialNetwork/pic/a.jpg");
		createArea(frame, 200, 450, 'W', "/Users/huoshan/Workspaces/MyEclipse 2015/SocialNetwork/pic/b.jpg");
		createArea(frame, 600, 150, 'N', "/Users/huoshan/Workspaces/MyEclipse 2015/SocialNetwork/pic/c.jpg");
	}
	//创建按钮
	public JButton createButton(JFrame frame, JButton b, String str, int x, int y, int width, int height) {
		b = new JButton(str);
		b.setBounds(x, y, width, height);
		frame.add(b);
		b.addActionListener(this);
		frame.validate();
		return b;	
	}
	//创建背景图
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
			panelE.setBounds(0, 100, 200, 500);
		if (a == 'W')
			panelE.setBounds(400, 100, 200, 500);
		frame.add(panelE);
		frame.validate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//功能一：团体信息表
		if (e.getSource() == b1) {
			new winGroup();
		}
		//功能二：人员信息
		if (e.getSource() == b2) {
			new winPersonInfo();
			
		}
		// 功能三：社交关系人物
		if (e.getSource() == b3) {
			new winConInfo();
			winPic win=new winPic();
			win.callWeb();
			
		}
		// 功能四：核心人物
		if (e.getSource() == b4) {
			new winSpecialPerson("main");
			
		}
		// 功能五：活跃人物
		if (e.getSource() == b5) {
			new winSpecialPerson("active");
			
		}
		//功能六：边缘人物
		if(e.getSource()==b6){
			new winSpecialPerson("margin");
		}
		// 功能七：小团体桥接人
		if (e.getSource() == b7) {
			new winSpecialPerson("link");
		}
		//功能八：查询某人的圈子
		if(e.getSource()==b8){
			new winSearch();
		}

	}
}
