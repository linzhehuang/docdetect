package io.docdetect.docdetect.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

public class ZIPExtractor {
	/**
	 * Unpack the zip file to specified output directory.
	 * @param zipFilePath
	 * @param outputPath
	 * @throws IOException 
	 */
	static public void unzip(String zipFilePath, String outputPath) throws IOException {
		File file = new File(zipFilePath);
		ZipFile zipFile = new ZipFile(file, "GBK");
		@SuppressWarnings("unchecked")
		Enumeration<ZipEntry> entries = zipFile.getEntries();
		
		ZipEntry entry = null;
		while (entries.hasMoreElements()) {
			entry = entries.nextElement();
			String name = entry.getName();
			File f = new File(outputPath + File.separator + name);
 			if (entry.isDirectory()) {
 				// Create new directory.
 				if (!f.exists()) {
 					if (!f.mkdirs()) throw new IOException();
 				}
 			} else {
 				// Delete the old file.
 				if (f.exists()) f.delete();
 				// Create new file.
 				if (!f.createNewFile()) throw new IOException();
 				InputStream input = zipFile.getInputStream(entry);
 				FileOutputStream output = new FileOutputStream(f);
 				// Write the data to file.
 				byte[] buf = new byte[1024];
 				int count = -1;
 				while ((count = input.read(buf)) != -1) 
 					output.write(buf, 0, count);
 				output.close();
 				input.close();
 			}
		}
	}
	/**
	 * Get the file list in zip file.
	 * @param zipFilePath
	 * @param basePath
	 * @return A file list.
	 * @throws IOException
	 */
	static public List<String> getFiles(String zipFilePath, String basePath) throws IOException {
		File file = new File(zipFilePath);
		ZipFile zipFile = new ZipFile(file, "GBK");
		@SuppressWarnings("unchecked")
		Enumeration<ZipEntry> entries = zipFile.getEntries();
		
		List<String> list = new ArrayList<String>();
		ZipEntry entry = null;
		while (entries.hasMoreElements()) {
			entry = entries.nextElement();
			String name = entry.getName();
			if (!entry.isDirectory()) {
				// Add to the file list.
				list.add(basePath + File.separator + name);
			}
		}
		
		return list;
	}
}