package com.oneitthing.wda.util;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ClassUtil {
	
	public static final Map<String, String> versionMap = new HashMap<String, String>();
	static {
		versionMap.put("45.3", "Java1.1");
		versionMap.put("46.0", "Java1.2");
		versionMap.put("47.0", "Java1.3");
		versionMap.put("48.0", "Java1.4");
		versionMap.put("49.0", "Java5");
		versionMap.put("50.0", "Java6");
		versionMap.put("51.0", "Java7");
	}
	
	public static String getVersion(File clazz) throws IOException {
		String ret = "";

		FileInputStream fis = new FileInputStream(clazz);
		DataInputStream dis = new DataInputStream(fis);
		if (0xCAFEBABE != dis.readInt()) {
			dis.close();
			fis.close();
			return "";
		}
		int minor = dis.readUnsignedShort();
		int major = dis.readUnsignedShort();
		String version = major + "." + minor;
		
		dis.close();
		fis.close();

		String jdk = versionMap.get(version);
		ret = jdk == null ? "unknnown" : jdk;
		
		return ret;
	}
}
