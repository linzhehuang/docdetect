package io.docdetect.docdetect.util;

import io.docdetect.docdetect.conf.RedisConf;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

public class JobDispatcher {
	private static final String JOBID_SET = "jobid_set";
	private static final String USING_SET = "using_set";
	
	private static JobDispatcher instance = null;
	private Jedis jedis = null;
	private int jobIdSize = 0;  
	private int maxJobIdSize = 1024; // The maximum number of job id. Default is 1024.
	
	private JobDispatcher(RedisConf conf, int maxJobIdSize) {
		this.maxJobIdSize = maxJobIdSize;
		
		// Create a jedis instance.
		if (jedis == null) jedis = new Jedis(conf.getHost(), conf.getPort());
		// Remove the old set if exist.
		if (jedis.exists(JOBID_SET)) jedis.del(JOBID_SET);
		// Initial the job id set.
		Pipeline pipeline = jedis.pipelined();
		for(int i = 0;i < this.maxJobIdSize; i++)
			pipeline.sadd(JOBID_SET, String.valueOf(i));
		pipeline.sync();
	}
	
	public static JobDispatcher getInstance(RedisConf conf, int maxJobIdSize) {
		// Create the instance if not exist.
		if (instance == null) instance = new JobDispatcher(conf, maxJobIdSize);
		
		return instance;
	}
	
	public static JobDispatcher getInstance(RedisConf conf) {
		return getInstance(conf, 1024);
	}
	
	public String applyJobId() {
		String id = null;
		synchronized (this) {
			if (jobIdSize < maxJobIdSize) return null;
			else {
				// Modify redis data.
				id = jedis.spop(JOBID_SET);
				if (id == null) return null;
				jedis.sadd(USING_SET, id);
				jobIdSize++;
			}
		}
		return id;
	}
	
	public void recycleJobId(String jobId) {
		synchronized (this) {
			if (jedis.srem(USING_SET, jobId).equals(1)) {
				jedis.sadd(JOBID_SET, jobId);
				jobIdSize--;
			}
		}
	}
}
