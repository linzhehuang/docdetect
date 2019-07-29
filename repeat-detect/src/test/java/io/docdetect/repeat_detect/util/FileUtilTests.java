package io.docdetect.repeat_detect.util;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import io.docdetect.repeat_detect.util.FileUtil;

@RunWith(SpringRunner.class)
public class FileUtilTests {
	private static final String FILE_URI = "file:///c/Users/zehong.lin/tmp/test.txt";
	private static final byte[] FILE_DATA = new String("TEST\n").getBytes();
	
	@Test
	public void createTest() {
		assertTrue(FileUtil.create(FILE_URI, FILE_DATA));
	}
	
	@Test
	public void removeTest() {
		assertTrue(FileUtil.create(FILE_URI, FILE_DATA));
		assertTrue(FileUtil.remove(FILE_URI));
	}
}
