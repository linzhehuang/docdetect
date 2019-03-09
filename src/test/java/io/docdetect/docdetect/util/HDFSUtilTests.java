package io.docdetect.docdetect.util;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class HDFSUtilTests {
	
	@Test
	public void mainTest() {
		@SuppressWarnings("unused")
		HDFSUtil hdfsUtil = null;
		try {
			hdfsUtil = new HDFSUtil("hdfs://localhost:9000");
		} catch (IllegalArgumentException | IOException | URISyntaxException e) {
			e.printStackTrace();
		}
		
	}
}
