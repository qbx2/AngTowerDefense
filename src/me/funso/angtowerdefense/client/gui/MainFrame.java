package me.funso.angtowerdefense.client.gui;

import java.awt.Container;

import javax.swing.JFrame;

public class MainFrame extends JFrame {
	
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		new MainMenu(this);
	}
}
