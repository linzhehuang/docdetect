package io.docdetect.docdetect.util;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.tools.ant.filters.StringInputStream;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class HDFSUtilTests {
	
	@Test
	public void mainTest() {
		HDFSUtil hdfsUtil = null;
		try {
			hdfsUtil = new HDFSUtil("hdfs://192.168.1.3:9000", "wzserver3", "/opt/hadoop-2.9.2");
			hdfsUtil.createFile("/test.txt", new StringInputStream("12346878978464"));
		} catch (IllegalArgumentException | IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}
}
