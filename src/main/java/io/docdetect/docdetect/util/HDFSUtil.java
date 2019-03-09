package io.docdetect.docdetect.util;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;

public class HDFSUtil {
	private FileSystem fs = null;
	
	public HDFSUtil(String location) throws IllegalArgumentException, IOException, URISyntaxException {
		if ("".equals(location)) 
			throw new IllegalArgumentException("The location cannot be empty.");
		Configuration conf = new Configuration();
		fs = FileSystem.get(new URI(location), conf);
	}
	
	public FileSystem getFileSystem() {
		return fs;
	}
}
