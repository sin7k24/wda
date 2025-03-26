package com.oneitthing.wda;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.oneitthing.wda.controller.WdaController;
import com.oneitthing.wda.view.main.MainFrame;

public class Wda {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					new WdaController();
					
//					String lnf = System.getProperty("lnf");
					
//					UIManager.setLookAndFeel(lnf);
					UIManager.setLookAndFeel("org.jvnet.substance.skin.SubstanceRavenGraphiteGlassLookAndFeel");
//					UIManager.setLookAndFeel("org.jvnet.substance.skin.SubstanceAutumnLookAndFeel");
//					UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
					MainFrame mainFrame = new MainFrame();
					mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					mainFrame.setVisible(true);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (UnsupportedLookAndFeelException e) {
					e.printStackTrace();
				}
			}
		});
	}
}
