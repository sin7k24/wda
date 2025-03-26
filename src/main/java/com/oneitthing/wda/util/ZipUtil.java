package com.oneitthing.wda.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;

public class ZipUtil {

	private ZipUtil() {
	}

	public static void unzip(File archive, File toDir) throws IOException {
		String unzippedName = archive.getName().substring(0, archive.getName().length() - 4);
		unzip(archive, toDir, unzippedName);
	}
	
	public static void unzip(File archive, File toDir, String unzippedName) throws IOException {

		ZipFile zipFile = new ZipFile(archive,
				System.getProperty("file.encoding"));
		// エントリ取得
		Enumeration<ZipArchiveEntry> entries = zipFile.getEntries();
		byte[] b = new byte[500];

		String baseDir = toDir.getAbsolutePath() + File.separator + unzippedName;

		while (entries.hasMoreElements()) {
			ZipArchiveEntry ze = entries.nextElement();
			if (ze.isDirectory())
				continue;
			File f = new File(baseDir + File.separator + ze.getName());
			File p = f.getParentFile();
			if (!p.exists()) {
				p.mkdirs();
			}

			InputStream is = zipFile.getInputStream(ze);
			BufferedInputStream bis = new BufferedInputStream(is);

			FileOutputStream fo = new FileOutputStream(f);
			BufferedOutputStream bo = new BufferedOutputStream(fo);

			int size = 0;
			while ((size = bis.read(b)) > 0) {
				bo.write(b, 0, size);
			}
			bo.close();
			bis.close();
			is.close();
//			// 処理したファイルを、ファイル名＋圧縮ファイルサイズ＋解凍ファイルサイズの形式で標準出力
//			System.out.printf("%s %,3d => %,3d\n", ze.getName(),
//					ze.getCompressedSize(), ze.getSize());
		}
	}
}
