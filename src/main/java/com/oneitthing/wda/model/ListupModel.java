package com.oneitthing.wda.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.oneitthing.swingcontrollerizer.event.ModelProcessEvent;
import com.oneitthing.swingcontrollerizer.model.BaseModel;
import com.oneitthing.wda.util.FileUtil;

public class ListupModel extends BaseModel {

	private File root;

	private int directoryNum;
	
	private Map<String, Integer> fileRetio = new HashMap<String, Integer>();
	
	private List<String> files = new ArrayList<String>();

	public File getRoot() {
		return root;
	}

	public void setRoot(File root) {
		this.root = root;
	}
	
	public int getDirectoryNum() {
		return directoryNum;
	}

	public Map<String, Integer> getFileRetio() {
		return fileRetio;
	}

	public List<String> getFiles() {
		return files;
	}

	public void mainproc() throws Exception {
		
		System.out.println(getRoot().toPath() + "内のファイルをリストアップ中");
		Files.walkFileTree(getRoot().toPath(), new SimpleFileVisitor<Path>() {

			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes atts) {
				if (file != null) {
					String suffix = FileUtil.getSuffix(file.toFile()) ;
					if(suffix.length() == 0) {
						suffix = "拡張子なし";
					}
					
					if(!fileRetio.containsKey(suffix)) {
						fileRetio.put(suffix, 0);
					}
					int suffixSum = fileRetio.get(suffix);
					fileRetio.put(suffix, ++suffixSum);
//					System.out.println("そのまま = " + file);
//					System.out.println("相対     = " + getRoot().toPath().relativize(file));
					
					files.add(getRoot().toPath().relativize(file).toString());
				}
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
				directoryNum++;
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFileFailed(Path file, IOException exc) {
				System.err.println(exc);
				return FileVisitResult.CONTINUE;
			}
		});
		
		System.out.println("fileRetio = " + fileRetio);
		System.out.println("files size = " + files.size());
		System.out.println("directoryNum = " + directoryNum);
	}

	public void postproc() {
		ModelProcessEvent successEvent = new ModelProcessEvent(this);
		successEvent.setResult(getResult());
		fireModelSuccess(successEvent);

		fireModelFinished(new ModelProcessEvent(this));
	}
}
