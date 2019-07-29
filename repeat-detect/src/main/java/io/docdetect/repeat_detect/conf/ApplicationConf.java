package io.docdetect.repeat_detect.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import io.docdetect.repeat_detect.util.Tokenizer;


@Configuration
@Import({
	JobDispatcherConf.class,
	HDFSUtilConf.class,
	JavaSparkContextConf.class
})
public class ApplicationConf {
	@Value("${temp.uri}")
	private String tempURI;
	
	@Bean(value = "tokenizer", initMethod = "load") // Uncomment in production environment.
	public Tokenizer tokenizer() {
		return new Tokenizer();
	}
	
	public String getTempURI() {
		return tempURI;
	}
}
