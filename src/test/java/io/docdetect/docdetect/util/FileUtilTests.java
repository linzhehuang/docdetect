package io.docdetect.docdetect.util;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class FileUtilTests {
	@Test
	public void createFileTest() throws IOException {
		FileUtil.createFile("/tmp/demo/test.txt", new String("some thing here\n").getBytes());
	}
}
