package io.docdetect.repeat_detect.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.docdetect.repeat_detect.domain.CompareModel;
import io.docdetect.repeat_detect.domain.ProcessResultModel;
import io.docdetect.repeat_detect.domain.SuccessModel;
import io.docdetect.repeat_detect.service.ProcessService;
import io.docdetect.repeat_detect.util.FileUtil;
import io.docdetect.repeat_detect.util.HDFSUtil;
import io.docdetect.repeat_detect.util.JobDispatcher;
import io.docdetect.repeat_detect.util.Tokenizer;
import io.docdetect.repeat_detect.util.WordExtractor;
import io.docdetect.repeat_detect.util.ZIPExtractor;

@RestController
public class RootController {
	
	private static final  String TEMP_PATH = "/tmp/docdetect";
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private JobDispatcher jobDispatcher;
	@Autowired
	private HDFSUtil hdfsUtil;
	@Autowired
	private ProcessService service;
	@Autowired
	private Tokenizer tokenizer;
	
	@RequestMapping(value = "/uploadzip", method = RequestMethod.POST)
	public SuccessModel uploadZip(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		// Return if file is empty.
		if ((file == null) || file.isEmpty()) return new SuccessModel(false);
		// Apply a job id.
		String id = null;
		if ((id = jobDispatcher.applyJobId()) == null) {
			log.info("Failed to apply a job id.");
			return new SuccessModel(false);
		}
		// Save zip file to temporary path.
		String zipFile = new StringBuffer(TEMP_PATH)
				.append("/").append(id)
				.append("/").append(id).append(".zip").toString();
		
		try {
			FileUtil.create(zipFile, file.getBytes());
		} catch (IOException e) {
			log.info("Failed to save zip file.");
			FileUtil.remove(zipFile);
			return new SuccessModel(false);
		}
		// Save job id to the session.
		request.getSession().setAttribute("jobId", id);
		return new SuccessModel(true);
	}
	
	@RequestMapping(value = "/process", method = RequestMethod.POST)
	public ProcessResultModel process(HttpServletRequest request) {
		ProcessResultModel ret = new ProcessResultModel(false);		
		// Get id from session.
		String id = (String)request.getSession().getAttribute("jobId");
		if (id == null) return ret;
		// Extract the zip file.
		String basePath = new StringBuffer(TEMP_PATH)
				.append("/").append(id).toString();
		List<String> localFiles;
		try {
			localFiles = ZIPExtractor.unzip(basePath + "/" + id + ".zip", basePath);
		} catch (IOException e) {
			log.info("Extract zip file failed.");
			return ret;
		}
		if (localFiles == null) {
			log.info("Extract zip file failed.");
			return ret;
		}
		// Process the data.
		List<String> remoteFiles = new ArrayList<String>();
		String uri = hdfsUtil.getURI();
		for (String file : localFiles) {
			String prefix = file.substring(file.lastIndexOf("/") + 1, file.lastIndexOf("."));
			String remoteFile = new StringBuffer(basePath)
					.append("/").append(prefix).append(".txt").toString();
			remoteFiles.add(uri + remoteFile);
			// Extractor text from word file.
			String text = WordExtractor.extract(file);
			if (text == null) return ret;
			// Upload to HDFS.
			if (!hdfsUtil.create(remoteFile, tokenizer.seg(text))) {
				log.info("Upload to HDFS failed.");
				return ret;
			}
		}
		// Computing
		Vector<CompareModel> result = service.process(remoteFiles);
		if (result == null) {
			log.info("Computing failed.");
			return ret;
		}
		// Return the result.
		ret.setResult(result);
		ret.setSuccess(true);
		return ret;
	}

}
