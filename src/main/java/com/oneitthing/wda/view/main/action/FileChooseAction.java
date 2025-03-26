package com.oneitthing.wda.view.main.action;

import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;

import com.oneitthing.swingcontrollerizer.action.BaseAction;
import com.oneitthing.swingcontrollerizer.controller.ParameterMapping;

public class FileChooseAction extends BaseAction {
	

	@Override
	protected boolean prepare(ParameterMapping parameterMapping) throws Exception {

		JButton jbEventSource = (JButton)parameterMapping.getEventSource();
//		String emsStatus = (String)eventSourceButton.getClientProperty("emsStatus");
//		String deviceStatus = (String)eventSourceButton.getClientProperty("deviceStatus");

		
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		File selectedFile = null;
		int ret = chooser.showOpenDialog(getWindow("fileChooserDialog"));
		if(ret == JFileChooser.APPROVE_OPTION) {
			selectedFile = chooser.getSelectedFile();
			String from = jbEventSource.getName();
			JTextField target = null;
			if("fileChooserDialog.jbRef1".equals(from)) {
				target = (JTextField)getComponent("fileChooserDialog.jtfInput1");
			}else if("fileChooserDialog.jbRef2".equals(from)) {
				target = (JTextField)getComponent("fileChooserDialog.jtfInput2");
			}
			target.setText(selectedFile.getAbsolutePath());
			
		}
		return true;
	}
}
