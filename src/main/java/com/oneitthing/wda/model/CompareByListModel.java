package com.oneitthing.wda.model;

import java.io.File;
import java.util.List;

import com.oneitthing.swingcontrollerizer.event.ModelProcessEvent;
import com.oneitthing.swingcontrollerizer.model.BaseModel;
import com.oneitthing.wda.util.ClassUtil;
import com.oneitthing.wda.util.FileUtil;
import com.oneitthing.wda.view.compare.ComparePanel;

public class CompareByListModel extends BaseModel {

	private List<String> originalFiles;

	private List<String> revisedFiles;
	
	private String originalWorkspacePath;
	
	private String revisedWorkspacePath;
	
	private ComparePanel comparePanel;
	
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
	
	public String getOriginalWorkspacePath() {
		return originalWorkspacePath;
	}

	public void setOriginalWorkspacePath(String originalWorkspacePath) {
		this.originalWorkspacePath = originalWorkspacePath;
	}

	public String getRevisedWorkspacePath() {
		return revisedWorkspacePath;
	}

	public void setRevisedWorkspacePath(String revisedWorkspacePath) {
		this.revisedWorkspacePath = revisedWorkspacePath;
	}

	
	
	public ComparePanel getComparePanel() {
		return comparePanel;
	}

	public void setComparePanel(ComparePanel comparePanel) {
		this.comparePanel = comparePanel;
	}

	public void mainproc() throws Exception {
		
		int rowNum = 0;
		for(String original : originalFiles) {
			rowNum++;
			
			String[] compareRow = new String[7];
			File originalFile = new File(originalWorkspacePath + File.separator + original);
			String originalHash = FileUtil.getHash(originalFile);
			compareRow[0] = String.valueOf(rowNum);
			compareRow[1] = original;
			compareRow[2] = originalHash;
			if("class".equals(FileUtil.getSuffix(originalFile))) {
				compareRow[3] = ClassUtil.getVersion(originalFile);
			}else{
				compareRow[3] = "";
			}
			
			if(revisedFiles.contains(original)) {
				File revisedFile = new File(revisedWorkspacePath + File.separator + original);
				
				String revisedHash = FileUtil.getHash(revisedFile);
			
				
				compareRow[4] = original;
				compareRow[5] = revisedHash;
				if("class".equals(FileUtil.getSuffix(revisedFile))) {
					compareRow[6] = ClassUtil.getVersion(revisedFile);
				}else{
					compareRow[6] = "";
				}
				
				revisedFiles.remove(original);
			}else{
				compareRow[4] = "";
				compareRow[5] = "";
				compareRow[6] = "";
			}
			comparePanel.addCompareRow(compareRow);
		}

		System.out.println("revised 残り = " + revisedFiles);
		for(String revised : revisedFiles) {
			rowNum++;
			String[] compareRow = new String[7];
			File revisedFile = new File(revisedWorkspacePath + File.separator + revised);
			String revisedHash = FileUtil.getHash(revisedFile);
			compareRow[0] = String.valueOf(rowNum);
			compareRow[1] = "";
			compareRow[2] = "";
			compareRow[3] = "";
			compareRow[4] = revised;
			compareRow[5] = revisedHash;
			if("class".equals(FileUtil.getSuffix(revisedFile))) {
				compareRow[6] = ClassUtil.getVersion(revisedFile);
			}else{
				compareRow[6] = "";
			}

			comparePanel.addCompareRow(compareRow);
			
		}
	}

	public void postproc() {
		ModelProcessEvent successEvent = new ModelProcessEvent(this);
		successEvent.setResult(getResult());
		fireModelSuccess(successEvent);

		fireModelFinished(new ModelProcessEvent(this));
	}
}
