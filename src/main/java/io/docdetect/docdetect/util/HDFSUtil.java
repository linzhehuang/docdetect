package io.docdetect.docdetect.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;


public class HDFSUtil {
	private static final String HADOOP_USER_NAME = "HADOOP_USER_NAME";
	private static final String HADOOP_HOME = "hadoop.home.dir";

	private String hadoopUserName = null;
	private String hadoopHome = null;
	private FileSystem fs = null;
	
	
	public HDFSUtil(String location, String userName, String home) throws IllegalArgumentException, IOException, URISyntaxException {
		if ("".equals(location)) 
			throw new IllegalArgumentException("The location cannot be empty.");
		
		hadoopUserName = userName;
		hadoopHome = home;
		// Set the remote user name and local hadoop home.
		System.setProperty(HADOOP_USER_NAME, hadoopUserName);
		System.setProperty(HADOOP_HOME, hadoopHome);
		
		Configuration conf = new Configuration();
		fs = FileSystem.get(new URI(location), conf);
	}
	
	public FileSystem getFileSystem() {
		return fs;
	}
	
	public void createFile(String filePath, InputStream dataStream) throws IOException {
		Path path = new Path(filePath);
		FSDataOutputStream output = fs.create(path);
		// Write the data.
		byte[] buffer = new byte[1024];
		int count = -1;
		while ((count = dataStream.read(buffer)) != -1)
			output.write(buffer, 0, count);
		output.close();
	}
}
