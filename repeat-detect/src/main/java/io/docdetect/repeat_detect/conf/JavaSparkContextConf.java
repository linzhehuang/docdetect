package io.docdetect.repeat_detect.conf;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JavaSparkContextConf {
	private static final String APP_NAME = "docdetect";
	
	@Value("${spark.host}")
	private String sparkHost;
	@Value("${spark.port}")
	private String sparkPort;
	
	@Bean("javaSparkContext")
	public JavaSparkContext javaSparkContext() {
		String master;
		if (sparkHost.contains("local")) {
			master = sparkHost;
		} else {
			master = new StringBuilder("spark://")
					.append(sparkHost).append(":").append(sparkPort).toString();
		}
		SparkConf conf = new SparkConf();
		conf.setMaster(master);
		conf.setAppName(APP_NAME);
		return new JavaSparkContext(conf);
	}
	
}
