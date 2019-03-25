package io.docdetect.docdetect.util;

import java.io.ByteArrayInputStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HDFSUtilTests {
	
	private static final String FILE = "/tmp/test.txt";
	private static final byte[] FILE_DATA = new String("TEST\n").getBytes();
	
	@Autowired
	HDFSUtil hdfsUtil;
	
	@Test
	public void test() {
		hdfsUtil.create(FILE, new ByteArrayInputStream(FILE_DATA));
	}
}
