package com.oneitthing.wda.view.main;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class MainFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setName("mainFrame");
		setTitle("Archive Difference Analyzer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1024, 768);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem menuItem = new JMenuItem("開く");
		menuItem.setName("jmiFileOpen");
		mnFile.add(menuItem);
		
		JMenuItem menuItem_1 = new JMenuItem("解析終了");
		menuItem_1.setName("mainFrame.jmiAnalyzeEnd");
		mnFile.add(menuItem_1);
		
		JMenu mnHelp = new JMenu("help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmVersion = new JMenuItem("version");
		mntmVersion.setName("mainFrame.jmiVersion");
		mnHelp.add(mntmVersion);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setName("mainFrame.jpCard");
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new CardLayout(0, 0));
		
		StartPanel startPanel = new StartPanel();
		panel.add(startPanel, "startPanel");
	}

}
