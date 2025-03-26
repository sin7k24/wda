package com.oneitthing.wda.view.main.action;

import java.awt.Component;

import com.oneitthing.swingcontrollerizer.action.BaseAction;
import com.oneitthing.swingcontrollerizer.controller.ParameterMapping;
import com.oneitthing.wda.view.main.FileChooserDialog;

public class FileChooserOpenAction extends BaseAction {
	

	@Override
	protected boolean prepare(ParameterMapping parameterMapping) throws Exception {

		
		FileChooserDialog chooser = new FileChooserDialog();
		chooser.setLocationRelativeTo((Component)getComponent("mainFrame"));
		
		showWindow(chooser, false);
		
		return true;
	}
}
