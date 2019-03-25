package io.docdetect.docdetect.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;


public class HDFSUtil {
	private static final String HADOOP_USER_NAME = "HADOOP_USER_NAME";
	private static final String HADOOP_HOME = "hadoop.home.dir";

	private String uri;
	private FileSystem fs = null;
	
	private boolean connect() {
		// Already connected.
		if (fs != null) return true;
		Configuration conf = new Configuration();
		try {
			fs = FileSystem.get(new URI(uri), conf);
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public HDFSUtil(String host, String port, String userName, String home) {
		uri = new StringBuilder("hdfs://").
				append(host).append(":").append(port).toString();
		// Configuration HADOOP environment.
		System.setProperty(HADOOP_USER_NAME, userName);
		System.setProperty(HADOOP_HOME, home);
	}
	
	public void close() {
		if (fs != null) {
			try {
				fs.close();
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
			fs = null;
		}
	}
	
	public boolean create(String file, InputStream data) {
		if (!connect()) return false;
		try {
			FSDataOutputStream out = fs.create(new Path(file));
			byte[] buf = new byte[1024];
			int c = -1;
			while ((c = data.read(buf)) != -1)
				out.write(buf, 0, c);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean remove(String file) {
		if (!connect()) return false;
		try {
			Path path = new Path(file);
			if (fs.isDirectory(path)) {
				fs.delete(path, true);
			} else {
				fs.delete(path, false);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public String getURI() {
		return uri;
	}
}
