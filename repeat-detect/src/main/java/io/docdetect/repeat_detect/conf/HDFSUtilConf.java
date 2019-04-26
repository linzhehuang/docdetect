package io.docdetect.repeat_detect.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.docdetect.repeat_detect.util.HDFSUtil;

@Configuration
public class HDFSUtilConf {
	@Value("${hdfs.host}")
	private String hdfsHost;
	@Value("${hdfs.port}")
	private String hdfsPort;
	@Value("${hdfs.user-name}")
	private String hdfsUserName;
	@Value("${hdfs.home}")
	private String hdfsHome;
	
	@Bean("hdfsUtil")
	public HDFSUtil hdfsUtil() {
		return new HDFSUtil(hdfsHost, hdfsPort, hdfsUserName, hdfsHome);
	}
}
