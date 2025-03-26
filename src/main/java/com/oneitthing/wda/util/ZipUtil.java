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

		// ZipFileオープン
		// 文字コードはシステムデフォルトの文字コードにしておきます
		// Windows7だとMS932でした
		ZipFile zipFile = new ZipFile(archive,
				System.getProperty("file.encoding"));
		// エントリ取得
		Enumeration<ZipArchiveEntry> entries = zipFile.getEntries();
		byte[] b = new byte[500];

		String baseDir = toDir.getAbsolutePath() + File.separator + unzippedName;

		// エントリの数だけループ
		while (entries.hasMoreElements()) {
			// ZipArchiveEntryを取得
			ZipArchiveEntry ze = entries.nextElement();
			// ディレクトリは処理しません
			if (ze.isDirectory())
				continue;
			// ベースディレクトリ＋ZipArchiveEntry.getNameで出力パスを作ります
			File f = new File(baseDir + File.separator + ze.getName());
			// 親ディレクトリを取得
			File p = f.getParentFile();
			// ディレクトリが存在しない場合、ディレクトリを作成します
			if (!p.exists()) {
				p.mkdirs();
			}

			// エントリからInputStreamを取得
			InputStream is = zipFile.getInputStream(ze);
			BufferedInputStream bis = new BufferedInputStream(is);

			// 出力用のファイルストリーム作成
			FileOutputStream fo = new FileOutputStream(f);
			BufferedOutputStream bo = new BufferedOutputStream(fo);

			// InputStreamから読みだした分を順次ファイルに出力
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
