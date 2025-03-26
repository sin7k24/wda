package com.oneitthing.wda.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import com.oneitthing.swingcontrollerizer.event.ModelProcessEvent;
import com.oneitthing.swingcontrollerizer.model.BaseModel;
import com.oneitthing.wda.util.FileUtil;
import com.oneitthing.wda.util.ZipUtil;

public class CyclicUnarchiveModel extends BaseModel {

	private File root;

	
	public File getRoot() {
		return root;
	}

	public void setRoot(File root) {
		this.root = root;
	}

	
	public boolean preproc() throws Exception {
		
		return true;
	}
	
	public void mainproc() throws Exception {
		System.out.println(getRoot().toPath() + "内のjarファイルを解凍中");
		Files.walkFileTree(getRoot().toPath(), new SimpleFileVisitor<Path>() {

			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes atts) {
				
				if (file != null) {
					String suffix = FileUtil.getSuffix(file.toFile());
					if("jar".equals(suffix)) {
						try {
							System.out.println(file.toString() + "を解凍中");
							ZipUtil.unzip(file.toFile(), file.toFile().getParentFile());
						} catch (IOException e) {
							e.printStackTrace();
						}
//						decompileJar(file.toFile());
					}
				}
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFileFailed(Path file, IOException exc) {
				System.err.println(exc);
				return FileVisitResult.CONTINUE;
			}
		});
	}


	public void postproc() {
		ModelProcessEvent successEvent = new ModelProcessEvent(this);
		successEvent.setResult(getResult());
		fireModelSuccess(successEvent);

		fireModelFinished(new ModelProcessEvent(this));
	}
}
