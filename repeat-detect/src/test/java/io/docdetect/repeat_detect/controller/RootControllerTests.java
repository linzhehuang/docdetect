package io.docdetect.repeat_detect.controller;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import java.io.FileInputStream;

import javax.servlet.http.Cookie;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RootControllerTests {
	
	@Autowired
	private MockMvc mvc;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Test
	public void mainTest() throws Exception {
		// Get test.zip file.
		ClassPathResource resouce = new ClassPathResource("test/test.zip");
		MockMultipartFile file = new MockMultipartFile("file", new FileInputStream(resouce.getFile()));
		// Upload the zip file.
		MvcResult result = mvc.perform(multipart("/uploadzip").file(file))
		   .andExpect(status().isOk())
		   .andDo(print())
		   .andReturn();
		// Save the cookies.
		Cookie[] cookies = result.getResponse().getCookies();
		// Post process with cookies.
		log.info("------------- Request process -----------");
		mvc.perform(post("/process").cookie(cookies))
		   .andExpect(status().isOk())
		   .andDo(print());
		log.info("All tests done in RootController.");
	}
}
