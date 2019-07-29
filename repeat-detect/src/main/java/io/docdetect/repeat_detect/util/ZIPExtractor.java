package io.docdetect.repeat_detect.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
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
	 * @return all output files path from zip file
	 * @throws IOException 
	 * @throws URISyntaxException 
	 */
	static public List<String> unzip(String zipFilePath, String outputPath) throws IOException, URISyntaxException {
		ZipFile zipFile = new ZipFile(Paths.get(new URI(zipFilePath)).toFile(), "GBK");
		@SuppressWarnings("unchecked")
		Enumeration<ZipEntry> entries = zipFile.getEntries();
		List<String> files = new ArrayList<String>();
		
		while (entries.hasMoreElements()) {
			ZipEntry entry = entries.nextElement();
			String name = outputPath + "/" + entry.getName();
			File f = Paths.get(new URI(name)).toFile();
 			if (!entry.isDirectory()) {
 				files.add(name);
 				// Delete old file.
 				if (f.exists()) f.delete();
 				// Create parent path.
 				if (!f.getParentFile().exists())
					f.getParentFile().mkdirs();
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
 			} else {
 				// Create new directory.
 				if (!f.exists()) {
 					if (!f.mkdirs()) throw new IOException();
 				}
 			}
		}
		
		return files;
	}
	
}
