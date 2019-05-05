package io.docdetect.repeat_detect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import io.docdetect.repeat_detect.conf.ApplicationConf;

@SpringBootApplication
@Import(ApplicationConf.class)
public class RepeatDetectApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(RepeatDetectApplication.class, args);
	}
	
}
