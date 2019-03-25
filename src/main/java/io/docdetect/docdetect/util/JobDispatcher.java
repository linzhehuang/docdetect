package io.docdetect.docdetect.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

public class JobDispatcher {
	private static final String JOBID_SET = "jobid_set";
	private static final String USING_SET = "using_set";
	
	private static JobDispatcher instance = null;
	private Jedis jedis = null;
	private int jobIdSize = 0;  
	private int maxJobIdSize; // The maximum number of job id.
	
	private JobDispatcher(String host, int port, int maxJobIdSize) {
		this.maxJobIdSize = maxJobIdSize;
		
		// Create a jedis instance.
		if (jedis == null) jedis = new Jedis(host, port);
		// Remove the old set if exist.
		if (jedis.exists(JOBID_SET)) jedis.del(JOBID_SET);
		// Initial the job id set.
		Pipeline pipeline = jedis.pipelined();
		for(int i = 0;i < this.maxJobIdSize; i++)
			pipeline.sadd(JOBID_SET, String.valueOf(i));
		pipeline.sync();
	}
	
	public static JobDispatcher getInstance(String host, int port, int maxJobIdSize) {
		// Create the instance if not exist.
		if (instance == null) instance = new JobDispatcher(host, port, maxJobIdSize);
		
		return instance;
	}
	
	public boolean isConnect() {
		if (jedis == null) return false;
		if ("PONG".equalsIgnoreCase(jedis.ping())) return true;
		return false;
	}
	
	public String applyJobId() {
		String id = null;
		synchronized (this) {
			if (jobIdSize > maxJobIdSize) return null;
			else {
				// Modify data on redis.
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
			if (jedis.srem(USING_SET, jobId).equals(1L)) {
				jedis.sadd(JOBID_SET, jobId);
				jobIdSize--;
			}
		}
	}
}
