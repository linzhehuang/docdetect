package io.docdetect.docdetect.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.docdetect.docdetect.domain.ReturnValue;

@RestController
public class UploadZipController {
	@RequestMapping(value = "/uploadzip", method = RequestMethod.POST)
	public ReturnValue uploadZip(@RequestParam("file") MultipartFile file) {
		// Return if file is empty.
		if (file.isEmpty()) return new ReturnValue(false);
		System.out.println(file.getOriginalFilename());
		return new ReturnValue(true);
	}
}
