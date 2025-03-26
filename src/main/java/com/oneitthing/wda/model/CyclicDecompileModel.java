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
import com.oneitthing.wda.Const;
import com.oneitthing.wda.util.FileUtil;
import com.strobel.decompiler.DecompilerDriver;

public class CyclicDecompileModel extends BaseModel {

	private File root;

	private File decompiledDir;
	
	
	public File getRoot() {
		return root;
	}

	public void setRoot(File root) {
		this.root = root;
	}

	public File getDecompiledDir() {
		return decompiledDir;
	}

	
	public boolean preproc() throws Exception {
		decompiledDir = new File(getRoot().getAbsolutePath() + File.separator + Const.EXTRACT + File.separator + Const.DECOMPILED);
		decompiledDir.mkdirs();
		
		return true;
	}
	
	public void mainproc() throws Exception {
		Files.walkFileTree(getRoot().toPath(), new SimpleFileVisitor<Path>() {

			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes atts) {
				
				if (file != null) {
					String suffix = FileUtil.getSuffix(file.toFile());
					if("jar".equals(suffix)) {
//						decompileJar(file.toFile());
					}else if("class".equals(suffix)){
						decompileClass(file.toFile());
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

	private void decompileJar(File jar) {
    	String[] decompileArgs = new String[]{ "-jar", jar.getAbsolutePath(), "-o", getDecompiledDir().getAbsolutePath()};
    	DecompilerDriver.main(decompileArgs);
	}
	
	private void decompileClass(File clazz) {
    	String[] decompileArgs = new String[]{clazz.getAbsolutePath(), "-o", getDecompiledDir().getAbsolutePath()};
    	DecompilerDriver.main(decompileArgs);
	}

	public void postproc() {
		ModelProcessEvent successEvent = new ModelProcessEvent(this);
		successEvent.setResult(getResult());
		fireModelSuccess(successEvent);

		fireModelFinished(new ModelProcessEvent(this));
	}
}
