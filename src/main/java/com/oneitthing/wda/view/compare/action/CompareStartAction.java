package com.oneitthing.wda.view.compare.action;

import java.util.List;

import com.oneitthing.swingcontrollerizer.action.BaseAction;
import com.oneitthing.swingcontrollerizer.controller.ParameterMapping;
import com.oneitthing.swingcontrollerizer.event.ModelProcessEvent;
import com.oneitthing.swingcontrollerizer.model.Model;
import com.oneitthing.wda.model.CompareByListModel;
import com.oneitthing.wda.view.compare.ComparePanel;
import com.oneitthing.wda.view.precede.PrecedePanel;

public class CompareStartAction extends BaseAction {

	
	private long startTime;
	
	@Override
	protected boolean prepare(ParameterMapping parameterMapping) throws Exception {
		startTime = System.currentTimeMillis();
		
		System.out.println("CompareStart----------------------");
		return true;
	}
	
	@Override
	protected void reserveModels(List<Class<? extends Model>> models) {
		models.add(CompareByListModel.class);
	}
	
	@Override
	public boolean nextModel(int index, ModelProcessEvent prev, Model next) throws Exception {
		System.out.println("nextModel index = " + index);
		if(index == 0) {
			PrecedePanel precedePanel = (PrecedePanel)getComponent("precedePanel");
			List<String> originalFiles = precedePanel.getOriginalFiles();
			List<String> revisedFiles = precedePanel.getRevisedFiles();
			String originalExtractPath = precedePanel.getOriginalExtractPath();
			String revisedExtractPath = precedePanel.getRevisedExtractPath();
			
			ComparePanel comparePanel = (ComparePanel)getComponent("comparePanel");
			
			((CompareByListModel)next).setOriginalFiles(originalFiles);
			((CompareByListModel)next).setRevisedFiles(revisedFiles);
			((CompareByListModel)next).setOriginalWorkspacePath(originalExtractPath);
			((CompareByListModel)next).setRevisedWorkspacePath(revisedExtractPath);
			((CompareByListModel)next).setComparePanel(comparePanel);
			
			((CompareByListModel)next).setAsync(true);
		}
		
		return true;
	}
	
	@Override
	public void successForward(int index, Model model, Object result) throws Exception {
		System.out.println(index + "success");
		
		if(index == 0) { 
		}
	}
	
	@Override
	public void complete(ParameterMapping parameterMapping) throws Exception {
		System.out.println("complete");

		ComparePanel comparePanel = (ComparePanel)getComponent("comparePanel");
		comparePanel.setErrorNum(comparePanel.errorNum);
		
	}
}
