package io.docdetect.repeat_detect.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtil {
	public static boolean create(String uri, byte[] data) {
		
		try {
			Path path = Paths.get(new URI(uri));
			if (!Files.exists(path)) {
				Files.createDirectories(path.getParent());
				Files.createFile(path);
			}
			OutputStream out = new FileOutputStream(path.toFile());
			out.write(data);
			out.close();
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static boolean create(String uri, InputStream in) {
		byte[] buf = new byte[1024];
		try {
			Path path = Paths.get(new URI(uri));
			if (!Files.exists(path)) {
				Files.createDirectories(path.getParent());
				Files.createFile(path);
			}
			OutputStream out = new FileOutputStream(path.toFile());
			int count = -1;
			while ((count = in.read(buf)) > 0) {
				out.write(buf, 0, count);
			}
			out.close();
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static boolean remove(String uri) {
		try {
			File file = Paths.get(new URI(uri)).toFile();
			if (file.exists()) {
				if (file.delete()) return true;
				else return false;
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return false;
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
