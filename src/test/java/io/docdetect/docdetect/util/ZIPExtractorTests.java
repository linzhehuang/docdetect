package io.docdetect.docdetect.util;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class ZIPExtractorTests {
	
	private final String zipFile = "/tmp/zipextractor/test.zip";
	private final String output = "/tmp";
	
	@Test
	public void unzipTest() {
		try {
			ZIPExtractor.unzip(zipFile, output);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getFilesTest() {
		try {
			List<String> fileList = ZIPExtractor.getFiles(zipFile, output);
			for (String file : fileList) {
				System.out.println(file);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
