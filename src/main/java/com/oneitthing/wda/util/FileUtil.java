package com.oneitthing.wda.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.bouncycastle.util.encoders.Base64;

public class FileUtil {

	private FileUtil() {
	}

	public static void delete(File f) {
		if (f.exists() == false) {
			return;
		}

		if (f.isFile()) {
			f.delete();
		}

		if (f.isDirectory()) {
			File[] files = f.listFiles();
			for (int i = 0; i < files.length; i++) {
				delete(files[i]);
			}
			f.delete();
		}
	}

	public static String getSuffix(File path) {
		String suffix = "";
		if (path.isDirectory()) {
			return suffix;
		}

		String fileName = path.getName();

		int lastDotPosition = fileName.lastIndexOf(".");
		if (lastDotPosition != -1) {
			suffix = fileName.substring(lastDotPosition + 1);
		}
		return suffix;
	}

	public static String getFilenameWithoutSuffix(File path) {
		String fileNameBody = "";

		String fileName = path.getName();

		int lastDotPosition = fileName.lastIndexOf(".");
		if (lastDotPosition != -1) {
			fileNameBody = fileName.substring(0, lastDotPosition);
		} else {
			fileNameBody = fileName;
		}
		return fileNameBody;

	}

	public static String getHash(File file) throws IOException, NoSuchAlgorithmException {
		String filename = file.getAbsolutePath();
		MessageDigest md = MessageDigest.getInstance("MD5");
		DigestInputStream inStream = new DigestInputStream(
				new BufferedInputStream(new FileInputStream(filename)), md);

		while (inStream.read() != -1) {
		}
		byte[] digest = md.digest();
		inStream.close();

		Base64 b64 = new Base64();
		return new String(b64.encode(digest));
	}
	
	public static void copyDir(File srcDir, File targetDir) throws IOException {
		FileVisitor<Path> visitor = new CopyVisitor(srcDir.toPath(), targetDir.toPath());
		Files.walkFileTree(srcDir.toPath(), visitor);
	}

	public static List<String> listUp(File f) throws IOException {
		final List<String> ret = new ArrayList<String>();

		Files.walkFileTree(f.toPath(), new SimpleFileVisitor<Path>() {

			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes atts) {
				String path = file.toString();
				if (path != null) {
					ret.add(path);
				}
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFileFailed(Path file, IOException exc) {
				System.err.println(exc);
				return FileVisitResult.CONTINUE;
			}
		});

		return ret;
	}
	
	static class CopyVisitor extends SimpleFileVisitor<Path> {

		private final Path source;
		private final Path target;

		CopyVisitor(Path source, Path target) {
			this.source = source;
			this.target = target;
		}

		@Override
		public FileVisitResult preVisitDirectory(Path dir,
				BasicFileAttributes atts) throws IOException {
			Path targetDir = this.target.resolve(this.source.relativize(dir));
			try {
				Files.copy(dir, targetDir);
				System.out.println("[COPY DIR] " + targetDir);

			} catch (FileAlreadyExistsException ex) {
				if (!Files.isDirectory(targetDir))
					throw ex;
			}

			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes atts)
				throws IOException {
			Path targetFile = this.target.resolve(this.source.relativize(file));
			Files.copy(file, targetFile);
			System.out.println("[COPY FILE] " + targetFile);
			return FileVisitResult.CONTINUE;
		}
	}

}
