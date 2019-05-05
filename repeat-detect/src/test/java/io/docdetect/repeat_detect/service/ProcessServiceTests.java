package io.docdetect.repeat_detect.service;

import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.docdetect.repeat_detect.domain.CompareModel;
import io.docdetect.repeat_detect.service.ProcessService;
import io.docdetect.repeat_detect.util.HDFSUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProcessServiceTests {
	
	private static final String[] FILES = new String[]{
			"/test1.txt",
			"/test2.txt"
	};
	private static final String[] CONTENTS = new String[]{
			"这 个 语句 是 测试 用例",  // Simulation of text segmentation.
			"那 个 语句 不是 测试 用例"
	};
	
	@Autowired
	private HDFSUtil hdfsUtil;
	@Autowired
	private ProcessService service;
	
	@Before
	public void createTestFile() {
		for (int i = 0; i < FILES.length; i++) {
			hdfsUtil.create(FILES[i], new ByteArrayInputStream(CONTENTS[i].getBytes()));
		}
	}
	
	@Test
	public void test() throws Exception {
		String uri = hdfsUtil.getURI();
		List<String> files = new ArrayList<>();
		for (int i = 0; i < FILES.length; i++) {
			files.add(uri + "/" + FILES[i]);
		}
		Vector<CompareModel> results = service.process(files);
		assertNotNull(results);
		// Print the results.
		for (CompareModel res : results) {
			System.out.print(res.getFiles()[0] + "|" + res.getFiles()[1] + "|");
			System.out.println(res.getResult());
		}
		// Remove the test file;
		for (String file : files) {
			hdfsUtil.remove(file);
		}
		hdfsUtil.close();
	}
}
