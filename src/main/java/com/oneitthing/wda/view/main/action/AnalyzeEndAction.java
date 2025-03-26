package com.oneitthing.wda.view.main.action;

import java.awt.CardLayout;
import java.io.File;

import javax.swing.JPanel;

import com.oneitthing.swingcontrollerizer.action.BaseAction;
import com.oneitthing.swingcontrollerizer.controller.ParameterMapping;
import com.oneitthing.wda.Const;
import com.oneitthing.wda.util.FileUtil;
import com.oneitthing.wda.view.main.MainPanel;

public class AnalyzeEndAction extends BaseAction {


	@Override
	protected boolean prepare(ParameterMapping parameterMapping) throws Exception {

		JPanel cardPanel = (JPanel)getComponent("mainFrame", "mainFrame.jpCard");

		CardLayout cardLayout = (CardLayout)cardPanel.getLayout();
		
		MainPanel mainPanel = (MainPanel)getComponent("mainFrame", "mainPanel");
		if(mainPanel != null) {
			System.out.println("解析終了");
			cardLayout.removeLayoutComponent(mainPanel);
			mainPanel.removeAll();
			File workDir = (File)getController().getPermanent().get(Const.WORKSPACE);
	
			FileUtil.delete(workDir);
			cardLayout.show(cardPanel, "startPanel");
		}
		
		return true;
	}
}
