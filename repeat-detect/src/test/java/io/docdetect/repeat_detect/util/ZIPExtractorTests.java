package io.docdetect.repeat_detect.util;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;

import io.docdetect.repeat_detect.util.ZIPExtractor;

@RunWith(SpringRunner.class)
public class ZIPExtractorTests {
	
	private static final String OUTPUT_PATH = "/tmp/zip-util";
	private static final String ZIP_FILE = "test/test.zip";
	
	@Test
	public void unzipTest() {
		try {
			ClassPathResource resource = new ClassPathResource(ZIP_FILE);
			String zipFilePath = resource.getFile().getAbsolutePath();
			List<String> files = ZIPExtractor.unzip(zipFilePath, OUTPUT_PATH);
			for (String file : files) {
				System.out.println(file);
			}
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@After
	public void cleanUp() {
		//FileUtil.remove(OUTPUT_PATH);
	}
}
