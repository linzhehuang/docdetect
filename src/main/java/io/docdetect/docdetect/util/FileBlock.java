package io.docdetect.docdetect.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class FileBlock {
	private FileBlockManager fileBlockManager = null;
	private String fileBlockId = null;
	private String basePath = null;
	private FileSystem fs = null;
	
	public FileBlock(FileBlockManager fileBlockManager,
			String hdfsLocation, String parentPath, 
			String fileBlockId) throws NullPointerException, IOException, IllegalArgumentException, URISyntaxException {
		
		if (fileBlockManager == null)
			throw new NullPointerException("The arguement fileBlockManager is null.");
		
		this.fileBlockManager = fileBlockManager;
		this.fileBlockId = fileBlockId;
		this.basePath = new StringBuilder(parentPath)
				.append("/").append(String.valueOf(fileBlockId)).toString();
		
		fs = new HDFSUtil(hdfsLocation).getFileSystem();
		
		// Create the temporary directory.
		fs.mkdirs(new Path(basePath));
	}
	
	public void destory() throws IOException {
		// Remove all files in temporary directory.
		fs.delete(new Path(basePath), true);
		// Remove file block id.
		fileBlockManager.destoryFileBlock(fileBlockId);
	}
	
	public void addFile(String fileName, byte[] fileData) {
		
	}
	
	// Getters and setters.
	public String getFileBlockId() {
		return fileBlockId;
	}
}
