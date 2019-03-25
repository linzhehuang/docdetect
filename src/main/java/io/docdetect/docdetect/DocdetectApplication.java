package io.docdetect.docdetect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import io.docdetect.docdetect.conf.ApplicationConf;

@SpringBootApplication
@Import(ApplicationConf.class)
public class DocdetectApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(DocdetectApplication.class, args);
	}
	
}
