package io.docdetect.repeat_detect.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.docdetect.computing_task.ComputingTask;
import io.docdetect.repeat_detect.domain.CompareModel;
import io.docdetect.repeat_detect.util.FileUtil;

@Service
public class ProcessService {
	
	private static final int THREAD_POOL_SIZE = 12;
	
	@Autowired
	private JavaSparkContext context;
	
	public Vector<CompareModel> process(List<String> fileList) {
		Vector<CompareModel> vector = new Vector<>();
		ExecutorService pool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
		List<FutureTask<Float>> tasks = new ArrayList<>();
		// Add jar file.
		String jarFile = ComputingTask.class.
				getProtectionDomain().getCodeSource().getLocation().getFile();
		if (!jarFile.contains(".jar")) return null;
		context.addJar(jarFile);
		// Submit computing task to thread pool.
		for (int i = 0, len = fileList.size(); i < len; i++) {
			for (int j = i+1; j < len; j++) {
				CompareModel cm = new CompareModel();
				cm.setFiles(new String[] {fileList.get(i), fileList.get(j)});
				vector.add(cm);
				// Generate and submit task.
				FutureTask<Float> task = new FutureTask<>(new ComputingTask(context, cm.getFiles()));
				tasks.add(task);
				pool.submit(task);
			}
		}
		// Wait all tasks done.
		try {
			pool.shutdown();
			pool.awaitTermination(1, TimeUnit.HOURS);
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		}
		// Get all results from tasks.
		int index = 0;
		for (FutureTask<Float> task : tasks) {
			try {
				vector.get(index).setResult(task.get());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
				return null;
			}
			String[] files = vector.get(index).getFiles();
			files[0] = FileUtil.getFileName(files[0]);
			files[1] = FileUtil.getFileName(files[1]);
			vector.get(index).setFiles(files);
			index++;
		}
		return vector;
	}
}
