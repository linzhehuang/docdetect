package io.docdetect.docdetect.hdfs;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class HdfsTests {
	@Test
	public void hdfsIO() throws IOException, URISyntaxException {
		// Configure hdfs.
		Configuration conf = new Configuration();
		
		// Create the base path.
		FileSystem hdfs = FileSystem.get(new URI("hdfs://localhost:9000"), conf);
		Path newPath = new Path("/temp");
		if (hdfs.mkdirs(newPath)) 
			System.out.println("success");
		System.out.println("Done");
	}
}
