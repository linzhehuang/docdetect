package io.docdetect.docdetect.conf;

import static org.junit.Assert.assertNotNull;

import org.apache.spark.api.java.JavaSparkContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.docdetect.repeat_detect.util.HDFSUtil;
import io.docdetect.repeat_detect.util.JobDispatcher;
import io.docdetect.repeat_detect.util.Tokenizer;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationConfTest {
	@Autowired
	JobDispatcher jobDispatcher;
	@Autowired
	HDFSUtil hdfsUtil;
	@Autowired
	JavaSparkContext JavaSparkContext;
	@Autowired
	Tokenizer tokenizer;
	
	@Test
	public void notNullTest() {
		assertNotNull(jobDispatcher);
		assertNotNull(hdfsUtil);
		assertNotNull(JavaSparkContext);
		assertNotNull(tokenizer);
	}
}
