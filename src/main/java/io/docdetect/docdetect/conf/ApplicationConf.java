package io.docdetect.docdetect.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import io.docdetect.docdetect.util.Tokenizer;


@Configuration
@Import({
	JobDispatcherConf.class,
	HDFSUtilConf.class,
	JavaSparkContextConf.class
})
public class ApplicationConf {
	@Bean(value = "tokenizer", initMethod = "load") // Uncomment in production environment.
	public Tokenizer tokenizer() {
		return new Tokenizer();
	}
}
