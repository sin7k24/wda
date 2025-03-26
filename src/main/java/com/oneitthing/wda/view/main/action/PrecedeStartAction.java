package com.oneitthing.wda.view.main.action;

import java.awt.CardLayout;
import java.io.File;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.oneitthing.swingcontrollerizer.action.BaseAction;
import com.oneitthing.swingcontrollerizer.controller.ParameterMapping;
import com.oneitthing.swingcontrollerizer.event.ModelProcessEvent;
import com.oneitthing.swingcontrollerizer.model.Model;
import com.oneitthing.wda.Const;
import com.oneitthing.wda.model.CyclicDecompileModel;
import com.oneitthing.wda.model.CyclicUnarchiveModel;
import com.oneitthing.wda.model.ListupModel;
import com.oneitthing.wda.model.PrepareWorkspaceModel;
import com.oneitthing.wda.view.main.MainPanel;
import com.oneitthing.wda.view.precede.PrecedePanel;

public class PrecedeStartAction extends BaseAction {

	private File original;
	
	private File revised;

	private PrecedePanel precedePanel;
	
	private Map<String, Object> workSpaceInfo;
	
	private long startTime;
	
	@Override
	protected boolean prepare(ParameterMapping parameterMapping) throws Exception {
		startTime = System.currentTimeMillis();
		
		String inputPath1 = ((JTextField)getComponent("fileChooserDialog.jtfInput1")).getText();
		String inputPath2 = ((JTextField)getComponent("fileChooserDialog.jtfInput2")).getText();

		this.original = new File(inputPath1);
		this.revised = new File(inputPath2);
		
		if(!this.original.exists()) {
			showMessageDialog("入力１は存在しません");
			return false;
		}
		if(!this.revised.exists()) {
			showMessageDialog("入力２は存在しません");
			return false;
		}

		this.getController().invoke(AnalyzeEndAction.class, parameterMapping);
		
		JPanel cardPanel = (JPanel)getComponent("mainFrame", "mainFrame.jpCard");
		CardLayout cardLayout = (CardLayout)cardPanel.getLayout();

		MainPanel mainPanel = new MainPanel();
		cardPanel.add(mainPanel, "mainPanel");
		cardLayout.show(cardPanel, "mainPanel");
		
		precedePanel = (PrecedePanel)getComponent("mainFrame", "precedePanel");
		precedePanel.setOriginalInputPath(original.getAbsolutePath());
		precedePanel.setRevisedInputPath(revised.getAbsolutePath());
		
		getController().invoke(PrecedeCancelAction.class, parameterMapping);
		
		return true;
	}
	
	@Override
	protected void reserveModels(List<Class<? extends Model>> models) {
		// 作業スペースへコピー、解凍
		models.add(PrepareWorkspaceModel.class);
		
		models.add(CyclicUnarchiveModel.class);
		models.add(CyclicUnarchiveModel.class);

		// 比較１を再帰的に逆コンパイル
		models.add(CyclicDecompileModel.class);
		// 比較２を再帰的に逆コンパイル
		models.add(CyclicDecompileModel.class);
		
		models.add(ListupModel.class);
		models.add(ListupModel.class);
	}
	
	@Override
	public boolean nextModel(int index, ModelProcessEvent prev, Model next) throws Exception {
		System.out.println("nextModel index = " + index);
		if(index == 0) {
			((PrepareWorkspaceModel)next).setOriginal(this.original);
			((PrepareWorkspaceModel)next).setRevised(this.revised);
			((PrepareWorkspaceModel)next).setAsync(true);
		}else if(index == 1) {
			if(next!= null) {
				JCheckBox jckCyclicDecompile = (JCheckBox)getComponent("fileChooserDialog.jckCyclicDecompile");
				if(jckCyclicDecompile.isSelected()) {
					File originExtract = (File)workSpaceInfo.get(Const.ORIGINAL_EXTRACT);
					((CyclicUnarchiveModel)next).setRoot(originExtract);
					((CyclicUnarchiveModel)next).setAsync(true);
				}else{
					((CyclicUnarchiveModel)next).setSkip(true);
				}
			}
		}else if(index == 2) {
			if(next!= null) {
				JCheckBox jckCyclicDecompile = (JCheckBox)getComponent("fileChooserDialog.jckCyclicDecompile");
				if(jckCyclicDecompile.isSelected()) {
					File revisedExtract = (File)workSpaceInfo.get(Const.REVISED_EXTRACT);
					((CyclicUnarchiveModel)next).setRoot(revisedExtract);
					((CyclicUnarchiveModel)next).setAsync(true);
				}else{
					((CyclicUnarchiveModel)next).setSkip(true);
				}
			}			
		}else if(index == 3) {
			if(next!= null) {
				File workspace = (File)workSpaceInfo.get(Const.ORIGINAL_WORKSPACE);
				System.out.println("next = " + next);
				System.out.println("workspace = " + workspace);
				((CyclicDecompileModel)next).setRoot(workspace);
				((CyclicDecompileModel)next).setAsync(true);
			}
		}else if(index == 4) {
			if(next!= null) {
				File workspace = (File)workSpaceInfo.get(Const.REVISED_WORKSPACE);
				System.out.println("next = " + next);
				System.out.println("workspace = " + workspace);
				((CyclicDecompileModel)next).setRoot(workspace);
				((CyclicDecompileModel)next).setAsync(true);
			}
		}else if(index == 5) {
			File originExtract = (File)workSpaceInfo.get(Const.ORIGINAL_EXTRACT);
			((ListupModel)next).setRoot(originExtract);
			((ListupModel)next).setAsync(true);
		}else if(index == 6) {
			File revisedExtract = (File)workSpaceInfo.get(Const.REVISED_EXTRACT);
			((ListupModel)next).setRoot(revisedExtract);
			((ListupModel)next).setAsync(true);
		}
		
		return true;
	}
	
	@Override
	public void successForward(int index, Model model, Object result) throws Exception {
		System.out.println(index + "success");
		
		if(index == 0) { 
			if(model.getResult() != null) {
				System.out.println("model = " + model);
				System.out.println("workspaceinfo = " + this.workSpaceInfo);
				this.workSpaceInfo = (Map<String, Object>)model.getResult();
				File originalWorkspace = (File)this.workSpaceInfo.get(Const.ORIGINAL_WORKSPACE);
				File revisedWorkspace = (File)this.workSpaceInfo.get(Const.REVISED_WORKSPACE);
				File originalExtract = (File)this.workSpaceInfo.get(Const.ORIGINAL_EXTRACT);
				File revisedExtract = (File)this.workSpaceInfo.get(Const.REVISED_EXTRACT);

				precedePanel.setOriginalWorkSpacePath(originalWorkspace.getAbsolutePath());
				precedePanel.setRevisedWorkSpacePath(revisedWorkspace.getAbsolutePath());
				
				precedePanel.setOriginalExtractPath(originalExtract.getAbsolutePath());
				precedePanel.setRevisedExtractPath(revisedExtract.getAbsolutePath());
				
				File workspace = (File)this.workSpaceInfo.get(Const.WORKSPACE);
				this.getController().getPermanent().put(Const.WORKSPACE, workspace);
			}
		}else if(index == 5) {
			List<String> files = ((ListupModel)model).getFiles();
			int directoryNum = ((ListupModel)model).getDirectoryNum();
			Map<String, Integer> fileRetio = ((ListupModel)model).getFileRetio();

			precedePanel.setOriginalDetectFileNum(NumberFormat.getInstance().format(files.size()));
			precedePanel.setOriginalDetectDirNum(NumberFormat.getInstance().format(directoryNum));
			precedePanel.setOriginalChartData(fileRetio);
			precedePanel.setOriginalFiles(files);
		}else if(index == 6) {
			List<String> files = ((ListupModel)model).getFiles();
			int directoryNum = ((ListupModel)model).getDirectoryNum();
			Map<String, Integer> fileRetio = ((ListupModel)model).getFileRetio();
			
			precedePanel.setRevisedDetectFileNum(NumberFormat.getInstance().format(files.size()));
			precedePanel.setRevisedDetectDirNum(NumberFormat.getInstance().format(directoryNum));
			precedePanel.setRevisedChartData(fileRetio);
			precedePanel.setRevisedFiles(files);

			precedePanel.enableJbCompareStart();
}
	}
	
	@Override
	public void complete(ParameterMapping parameterMapping) throws Exception {
		System.out.println("complete");
//		precedePanel.enableJbCompareStart();
		
		long endTime = System.currentTimeMillis();
		System.out.println("解析準備完了：所要時間 " + (NumberFormat.getInstance().format(endTime - startTime)) + "ミリ秒");
				
		
	}
}
