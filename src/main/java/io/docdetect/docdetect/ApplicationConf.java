package io.docdetect.docdetect;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.docdetect.docdetect.util.FileBlockManager;

@Configuration
public class ApplicationConf {
	
	@Value("${spring.redis.host}")
	private String redisHost;
	@Value("${spring.redis.port}")
	private int redisPort;
	@Value("${hdfs.host}")
	private String hdfsHost;
	@Value("${hdsf.port}")
	private int hdfsPort;
	
	@Bean(name = "fileBLockManager")
	public FileBlockManager fileBLockManager() {
		StringBuilder hdfsLocation = new StringBuilder("hdfs://")
				.append(hdfsHost).append(String.valueOf(hdfsPort));
		return FileBlockManager.getInstance(redisHost, redisPort,
				hdfsLocation.toString(),
				"/temp", 2);
	}
}
