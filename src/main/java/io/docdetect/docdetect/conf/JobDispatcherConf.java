package io.docdetect.docdetect.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.docdetect.docdetect.util.JobDispatcher;

@Configuration
public class JobDispatcherConf {
	
	private static final int MAX_JOB_SIZE = 256; // maximum number of jobs
	
	@Value("${redis.host}")
	private String redisHost;
	@Value("${redis.port}")
	private int redisPort;
	
	@Bean("jobDispatcher")
	public JobDispatcher jobDispatcher() {
		JobDispatcher jobDispatcher = JobDispatcher
				.getInstance(redisHost, redisPort, MAX_JOB_SIZE);
		return jobDispatcher;
	}
}
