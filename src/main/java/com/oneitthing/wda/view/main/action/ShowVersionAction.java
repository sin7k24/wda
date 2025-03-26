package com.oneitthing.wda.view.main.action;

import com.oneitthing.swingcontrollerizer.action.BaseAction;
import com.oneitthing.swingcontrollerizer.controller.ParameterMapping;

public class ShowVersionAction extends BaseAction {
	

	@Override
	protected boolean prepare(ParameterMapping parameterMapping) throws Exception {
		showMessageDialog("0.1");
		return true;
	}
}
