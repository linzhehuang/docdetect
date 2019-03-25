package io.docdetect.computing_task;

import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.regex.Pattern;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

public class ComputingTask implements Callable<Float> {

	private JavaSparkContext context;
	private String[] files;
	
	public ComputingTask(JavaSparkContext context, String[] files) {
		this.context = context;
		this.files = files;
	}
	
	@Override
	public Float call() throws Exception {
		float iCount = 0f, uCount = 0f;
		JavaRDD<String> fSet = context.textFile(files[0])
				.flatMap(str -> Arrays.asList(Pattern.compile(" ").split(str)).iterator());
		JavaRDD<String> sSet = context.textFile(files[1])
				.flatMap(str -> Arrays.asList(Pattern.compile(" ").split(str)).iterator());
		iCount = fSet.intersection(sSet).count();
		uCount = fSet.union(sSet).distinct().count();
		return (iCount/uCount);
	}

}
