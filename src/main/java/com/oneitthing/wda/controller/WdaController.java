package com.oneitthing.wda.controller;

import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.File;

import com.oneitthing.swingcontrollerizer.controller.BaseController;
import com.oneitthing.swingcontrollerizer.controller.ClientConfig;
import com.oneitthing.swingcontrollerizer.controller.EventBinder;
import com.oneitthing.wda.Const;
import com.oneitthing.wda.util.FileUtil;
import com.oneitthing.wda.view.compare.action.CompareStartAction;
import com.oneitthing.wda.view.compare.action.DiffDialogOpenAction;
import com.oneitthing.wda.view.compare.action.JumpToNextErrorAction;
import com.oneitthing.wda.view.compare.action.JumpToPrevErrorAction;
import com.oneitthing.wda.view.main.action.AnalyzeEndAction;
import com.oneitthing.wda.view.main.action.FileChooseAction;
import com.oneitthing.wda.view.main.action.FileChooserOpenAction;
import com.oneitthing.wda.view.main.action.PrecedeCancelAction;
import com.oneitthing.wda.view.main.action.PrecedeStartAction;
import com.oneitthing.wda.view.main.action.ShowVersionAction;

public class WdaController extends BaseController {

	@Override
	protected void initialize(ClientConfig config) {
//		Properties prop = ResourceUtil.instance.asProperties("conf/config.properties");
//		Map<Object, Object> permanent = getPermanent();
//		permanent.put("config.properties", prop);
	}

	@Override
	protected void bind(EventBinder eventBinder) {

		eventBinder.addEventBinding("jmiFileOpen", ActionListener.class, "actionPerformed", FileChooserOpenAction.class);
		eventBinder.addEventBinding("mainFrame.jmiAnalyzeEnd", ActionListener.class, "actionPerformed", AnalyzeEndAction.class);
		eventBinder.addEventBinding("mainFrame.jmiVersion", ActionListener.class, "actionPerformed", ShowVersionAction.class);
		
		eventBinder.addEventBinding("jbPrecedeStart", ActionListener.class, "actionPerformed", PrecedeStartAction.class);

		eventBinder.addEventBinding("fileChooserDialog.jbPrecedeCancel", ActionListener.class, "actionPerformed", PrecedeCancelAction.class);
		eventBinder.addEventBinding("fileChooserDialog.jbRef1", ActionListener.class, "actionPerformed", FileChooseAction.class);
		eventBinder.addEventBinding("fileChooserDialog.jbRef2", ActionListener.class, "actionPerformed", FileChooseAction.class);
		
		eventBinder.addEventBinding("precedePanel.jbCompareStart", ActionListener.class, "actionPerformed", CompareStartAction.class);
		
		eventBinder.addEventBinding("comparePanel.jtLeft", MouseListener.class, "mouseClicked", DiffDialogOpenAction.class);
		eventBinder.addEventBinding("comparePanel.jtRight", MouseListener.class, "mouseClicked", DiffDialogOpenAction.class);
		
		eventBinder.addEventBinding("comparePanel.jbPrevError", MouseListener.class, "mouseClicked", JumpToPrevErrorAction.class);
		eventBinder.addEventBinding("comparePanel.jbNextError", MouseListener.class, "mouseClicked", JumpToNextErrorAction.class);
	}
	
	@Override
	protected void shutdown() {
		System.out.println("jvm shutdown");
		File workspace = (File)getPermanent().get(Const.WORKSPACE);
		if(workspace != null && workspace.exists()) {
			System.out.println("kesu");
			FileUtil.delete(workspace);
		}
	}
}
