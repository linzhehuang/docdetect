package io.docdetect.docdetect.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import io.docdetect.docdetect.util.JobDispatcher;


@Configuration
@Import(RedisConf.class)
public class ApplicationConf {
	@Autowired
	RedisConf redisConf;
	@Bean("jobDispatcher")
	public JobDispatcher jobDispatcher() {
		return JobDispatcher.getInstance(redisConf, 1024);
	}
}
