package io.docdetect.docdetect;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.docdetect.docdetect.util.FileBlockManager;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationConfTests {
	@Autowired
	private FileBlockManager tempFilePool;
	
	@Test
	public void test() {
		System.out.println(tempFilePool);
	}
}
