package io.docdetect.docdetect.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class FileUtil {
	public static boolean create(String path, byte[] data) {
		try {
			File file = new File(path);
			if (!file.exists()) {
				if (!file.getParentFile().exists())
					file.getParentFile().mkdirs();
				file.createNewFile();
			}
			OutputStream out = new FileOutputStream(path);
			out.write(data);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static boolean remove(String path) {
		File file = new File(path);
		if (file.exists()) {
			if (file.delete()) return true;
			else return false;
		}
		// Return true if file do not exist.
		return true;
	}
	
	public static String getFileName(String file) {
		return (file.substring(file.lastIndexOf("/") + 1));
	}
	
	public static String getStringFromStream(InputStream in) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		StringBuffer buffer = new StringBuffer();
		String line = null;
		try {
			while ((line = reader.readLine()) != null)
				buffer.append(line);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}
	
	public static byte[] toByteArray(InputStream input) throws IOException {
	    ByteArrayOutputStream output = new ByteArrayOutputStream();
	    byte[] buffer = new byte[4096];
	    int n = 0;
	    while (-1 != (n = input.read(buffer))) {
	        output.write(buffer, 0, n);
	    }
	    return output.toByteArray();
	}
}
