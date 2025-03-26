package com.oneitthing.wda.view.precede;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class PrecedePanel extends JPanel {

	PieChartPanel pieChartPanel;
	
	PieChartPanel pieChartPanel_1;

	JButton jbCompareStart;
	
	List<String> originalFiles;
	
	List<String> revisedFiles;
	
	String originalExtractPath;
	
	String revisedExtractPath;
	
	/**
	 * Create the panel.
	 */
	public PrecedePanel() {
		setName("precedePanel");
		setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		setPreferredSize(new Dimension(1000, 300));
		setLayout(new BorderLayout(0, 0));
		
		jbCompareStart = new JButton("比較開始");
		jbCompareStart.setEnabled(false);
		jbCompareStart.setName("precedePanel.jbCompareStart");
		add(jbCompareStart, BorderLayout.SOUTH);
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		
		pieChartPanel = new PieChartPanel();
		pieChartPanel.setName("precedePanel.jpOrigin");
		panel.add(pieChartPanel);

		pieChartPanel_1 = new PieChartPanel();
		pieChartPanel_1.setName("precedePanel.jpRevised");
		panel.add(pieChartPanel_1);
	}
	
	public void setOriginalInputPath(String inputPath) {
		pieChartPanel.setInputPath(inputPath);
	}

	public void setRevisedInputPath(String inputPath) {
		pieChartPanel_1.setInputPath(inputPath);
	}

	public void setOriginalWorkSpacePath(String workSpacePath) {
		pieChartPanel.setWorkSpacePath(workSpacePath);
	}

	public String getOriginalWorkSpacePath() {
		return pieChartPanel.getWorkSpacePath();
	}

	public void setRevisedWorkSpacePath(String workSpacePath) {
		pieChartPanel_1.setWorkSpacePath(workSpacePath);
	}
	
	public String getRevisedWorkSpacePath() {
		return pieChartPanel_1.getWorkSpacePath();
	}
	
	public void setOriginalDetectFileNum(String detectFileNum) {
		pieChartPanel.setDetectFileNum(detectFileNum);
	}
	
	public void setRevisedDetectFileNum(String detectFileNum) {
		pieChartPanel_1.setDetectFileNum(detectFileNum);
	}

	public void setOriginalDetectDirNum(String detectDirNum) {
		pieChartPanel.setDetectDirNum(detectDirNum);
	}
	
	public void setRevisedDetectDirNum(String detectDirNum) {
		pieChartPanel_1.setDetectDirNum(detectDirNum);
	}

	public void setOriginalChartData(Map<String, Integer> fileRetio) {
		pieChartPanel.setChartData(fileRetio);
	}

	public void setRevisedChartData(Map<String, Integer> fileRetio) {
		pieChartPanel_1.setChartData(fileRetio);
	}

	public List<String> getOriginalFiles() {
		return originalFiles;
	}

	public void setOriginalFiles(List<String> originalFiles) {
		this.originalFiles = originalFiles;
	}

	public List<String> getRevisedFiles() {
		return revisedFiles;
	}

	public void setRevisedFiles(List<String> revisedFiles) {
		this.revisedFiles = revisedFiles;
	}

	public String getOriginalExtractPath() {
		return originalExtractPath;
	}

	public void setOriginalExtractPath(String originalExtractPath) {
		this.originalExtractPath = originalExtractPath;
	}

	public String getRevisedExtractPath() {
		return revisedExtractPath;
	}

	public void setRevisedExtractPath(String revisedExtractPath) {
		this.revisedExtractPath = revisedExtractPath;
	}
	
	
	public void enableJbCompareStart() {
		jbCompareStart.setEnabled(true);
	}
	
}
