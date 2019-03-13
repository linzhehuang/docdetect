package io.docdetect.docdetect.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.docdetect.docdetect.domain.ReturnValue;
import io.docdetect.docdetect.util.JobDispatcher;

@RestController
public class RootController {
	private final static String TEMP_PATH = "/tmp/docdetect";
	
	@Autowired
	JobDispatcher jobDispatcher;
	
	@RequestMapping(value = "/uploadzip", method = RequestMethod.POST)
	public ReturnValue uploadZip(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		// Return if file is empty.
		if (file.isEmpty()) return new ReturnValue(false);
		// Apply a job id.
		String id = null;
		if ((id = jobDispatcher.applyJobId()) == null) {
			return new ReturnValue(false);
		}
		// Save ZIP to the temporary folder.
		System.out.println(file.getOriginalFilename());
		return new ReturnValue(true);
	}
}
