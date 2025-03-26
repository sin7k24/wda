package com.oneitthing.wda.view.main.action;

import com.oneitthing.swingcontrollerizer.action.BaseAction;
import com.oneitthing.swingcontrollerizer.controller.ParameterMapping;
import com.oneitthing.wda.view.main.FileChooserDialog;

public class PrecedeCancelAction extends BaseAction {
	

	@Override
	protected boolean prepare(ParameterMapping parameterMapping) throws Exception {
		FileChooserDialog chooser = (FileChooserDialog)getController().getWindowManager().getWindowByName("fileChooserDialog");
		chooser.setVisible(false);
		chooser.dispose();
		
		return true;
	}
}
