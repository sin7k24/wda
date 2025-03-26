package com.oneitthing.wda.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.oneitthing.swingcontrollerizer.event.ModelProcessEvent;
import com.oneitthing.swingcontrollerizer.model.BaseModel;
import com.oneitthing.wda.Const;
import com.oneitthing.wda.util.FileUtil;
import com.oneitthing.wda.util.ZipUtil;

public class PrepareWorkspaceModel extends BaseModel {

	private File original;
	
	private File revised;
	
	public File getOriginal() {
		return original;
	}

	public void setOriginal(File original) {
		this.original = original;
	}

	public File getRevised() {
		return revised;
	}

	public void setRevised(File revised) {
		this.revised = revised;
	}


	public void mainproc() throws Exception {

		File original = getOriginal();
		File revised = getRevised();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date now = new Date();
		String workSpaceName = sdf.format(now);
		File workSpace = new File(System.getProperty("java.io.tmpdir") + File.separator + workSpaceName);
		File originalWorkSpace = new File(workSpace.getAbsolutePath() + File.separator + Const.ORIGINAL);
		File revisedWorkSpace = new File(workSpace.getAbsolutePath() + File.separator + Const.REVISED);
		File originalExtract = new File(workSpace.getAbsolutePath() + File.separator + Const.ORIGINAL + File.separator + Const.EXTRACT);
		File revisedExtract = new File(workSpace.getAbsolutePath() + File.separator + Const.REVISED + File.separator + Const.EXTRACT);

		originalWorkSpace.mkdirs();
		revisedWorkSpace.mkdirs();

		
		if(original.isFile()) {
			copyAsFile(original, originalWorkSpace);
		}else if(original.isDirectory()) {
			copyAsDirectory(original, originalWorkSpace);
		}
		
		if(revised.isFile()) {
			copyAsFile(revised, revisedWorkSpace);
		}else if(revised.isDirectory()) {
			copyAsDirectory(revised, revisedWorkSpace);
		}
		
		Map<String, Object> workSpaceInfo = new HashMap<String, Object>();
		workSpaceInfo.put(Const.WORKSPACE, workSpace);
		workSpaceInfo.put(Const.ORIGINAL_WORKSPACE, originalWorkSpace);
		workSpaceInfo.put(Const.REVISED_WORKSPACE, revisedWorkSpace);
		workSpaceInfo.put(Const.ORIGINAL_EXTRACT, originalExtract);
		workSpaceInfo.put(Const.REVISED_EXTRACT, revisedExtract);
		
		setResult(workSpaceInfo);

	}
	
	private void copyAsFile(File zip, File workspace) throws IOException {

		File to = new File(workspace.getAbsolutePath() + File.separator + zip.getName());
//		File revisedTo = new File(revisedWorkSpace.getAbsolutePath() + File.separator + revised.getName());
		
		System.out.println(zip.getAbsolutePath() + "を" + to.getAbsolutePath() + "にコピー中");
		Files.copy(zip.toPath(), to.toPath());
//		System.out.println(revised.getAbsolutePath() + "を" + revisedTo.getAbsolutePath() + "にコピー中");
//		Files.copy(revised.toPath(), revisedTo.toPath());

		System.out.println(to.getAbsolutePath() + "を" + "解凍中");
		ZipUtil.unzip(to, workspace, Const.EXTRACT);
//		System.out.println(revisedTo.getAbsolutePath() + "を" + "解凍中");
//		ZipUtil.unzip(revisedTo, revisedWorkSpace, Const.EXTRACT);

		
	}
	
	private void copyAsDirectory(File dir, File workspace) throws IOException {
		File extractDir = new File(workspace.getAbsolutePath() + File.separator + Const.EXTRACT);
		FileUtil.copyDir(dir, extractDir);
	}
	
	public void postproc() {
		ModelProcessEvent successEvent = new ModelProcessEvent(this);
		successEvent.setResult(getResult());
		fireModelSuccess(successEvent);

		fireModelFinished(new ModelProcessEvent(this));
	}
}
