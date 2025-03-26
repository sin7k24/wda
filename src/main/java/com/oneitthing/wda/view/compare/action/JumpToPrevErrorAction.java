package com.oneitthing.wda.view.compare.action;

import com.oneitthing.swingcontrollerizer.action.BaseAction;
import com.oneitthing.swingcontrollerizer.controller.ParameterMapping;
import com.oneitthing.wda.view.compare.ComparePanel;

public class JumpToPrevErrorAction extends BaseAction {

	@Override
	protected boolean prepare(ParameterMapping parameterMapping)
			throws Exception {
		ComparePanel comparePanel = (ComparePanel)getComponent("comparePanel");
		comparePanel.jumpToPrevError();


		return false;
	}
}
