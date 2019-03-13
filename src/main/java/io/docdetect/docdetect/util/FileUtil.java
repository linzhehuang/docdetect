package io.docdetect.docdetect.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class FileUtil {
	public static void createFile(String filePath, byte[] data) throws IOException {
		File file = new File(filePath);
		if (!file.exists()) {
			if (!file.getParentFile().exists())
				file.getParentFile().mkdirs();
			file.createNewFile();
		}
		OutputStream out = new FileOutputStream(filePath);
		out.write(data);
		out.close();
	}
}
