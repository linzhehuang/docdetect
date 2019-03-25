package io.docdetect.docdetect.util;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class WordExtractorTests {
	private static final String WORD_FILE = "test/test.doc";
	
	@Test
	public void extractTest() {
		try {
			ClassPathResource resource = new ClassPathResource(WORD_FILE);
			String wordFilePath = resource.getFile().getAbsolutePath();
			assertNotNull(WordExtractor.extract(wordFilePath));
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
}
