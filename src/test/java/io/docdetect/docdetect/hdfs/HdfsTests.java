package io.docdetect.docdetect.hdfs;

import java.io.IOException;
import java.net.URISyntaxException;

//import org.apache.hadoop.conf.Configuration;
//import org.apache.hadoop.fs.FileSystem;
//import org.apache.hadoop.fs.Path;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class HdfsTests {
	@Test
	public void hdfsIO() throws IOException, URISyntaxException {
		System.setProperty("HADOOP_USER_NAME", "wzserver3");
		System.setProperty("hadoop.home.dir", "/opt/hadoop-2.9.2");
		
		// Configure hdfs.
		//Configuration conf = new Configuration();
		// Create the base path.
		//FileSystem hdfs = FileSystem.get(new URI("hdfs://192.168.1.3:9000"), conf);
		
	}
}
