package me.funso.angtowerdefense.client.gui;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import me.funso.angtowerdefense.client.Device;
import me.funso.angtowerdefense.op.OpResJoin;

public class JoinResult implements ActionListener {
	Join prev;
	JFrame frame;
	private JLabel label;
	private JButton button;
	private Container container;
	private OpResJoin opResJoin;
	
	public JoinResult(OpResJoin opResJoin, Join prev) {
		this.prev = prev;
		frame = prev.frame;
		container = new Container();
		this.opResJoin = opResJoin;
		init();
	}
	
	public void init() {
		frame.setContentPane(container);
		
		if(opResJoin.errorCode == 0) {
			label = new JLabel("JOIN SUCCESS");
			label.setFont(new Font("궁서",Font.BOLD,Device.dim.height/10));
			button = new JButton("CONFIRM");
		}
		else {
			label = new JLabel(opResJoin.message);
			label.setFont(new Font("궁서",Font.BOLD,Device.dim.height/30));
			button = new JButton("BACK");
		}
		label.setSize(Device.dim.width/2, Device.dim.height/4);
		label.setLocation(Device.dim.width/4, Device.dim.height/25*4);
		label.setHorizontalAlignment(JLabel.CENTER);
		frame.add(label);
		
		button.setSize(Device.dim.width/4, Device.dim.height/10);
		button.setLocation(Device.dim.width/8*3, Device.dim.height/25*13);
		button.setFont(new Font("궁서",Font.BOLD,Device.dim.height/15));
		button.setHorizontalAlignment(JLabel.CENTER);
		frame.add(button);
		button.addActionListener(this);
		
		frame.setVisible(true);
		container.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		container.setVisible(false);
		if(opResJoin.errorCode == 0)
			prev.toMain();
		else
			prev.resume(true);
	}
}
