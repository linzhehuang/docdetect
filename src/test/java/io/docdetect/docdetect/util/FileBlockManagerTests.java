package io.docdetect.docdetect.util;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileBlockManagerTests {
	@Autowired
	private FileBlockManager fileBlockManager;
	
	@Test
	public void test() throws NullPointerException, IOException, IllegalArgumentException, URISyntaxException {
		FileBlock fileBlock = fileBlockManager.newFileBlock();
		if (fileBlock == null) 
			System.out.println("");
		System.out.println(fileBlock.getFileBlockId());
		fileBlock.destory();
		fileBlockManager.clean();
	}
}
