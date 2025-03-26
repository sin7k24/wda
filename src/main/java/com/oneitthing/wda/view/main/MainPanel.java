package com.oneitthing.wda.view.main;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import com.oneitthing.wda.view.compare.ComparePanel;
import com.oneitthing.wda.view.information.LogPanel;
import com.oneitthing.wda.view.precede.PrecedePanel;

public class MainPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public MainPanel() {
		setName("mainPanel");
		setLayout(new BorderLayout(0, 0));
		
		PrecedePanel precedePanel = new PrecedePanel();
		precedePanel.setPreferredSize(new Dimension(300, 280));
		add(precedePanel, BorderLayout.NORTH);
		
		LogPanel logPanel = new LogPanel();
		add(logPanel, BorderLayout.SOUTH);
		
		ComparePanel comparePanel = new ComparePanel();
		add(comparePanel, BorderLayout.CENTER);

	}

}
