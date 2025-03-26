package com.oneitthing.wda.view.information;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;

public class LogPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public LogPanel() {
		setPreferredSize(new Dimension(300, 120));
		setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		setLayout(new BorderLayout(0, 0));
		
		JLabel label = new JLabel("ログ");
		add(label, BorderLayout.NORTH);
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);

		JTextAreaStream jtas = new JTextAreaStream(textArea);
	}

}
