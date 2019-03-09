package io.docdetect.docdetect.util;

import java.io.IOException;
import java.net.URISyntaxException;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

public class FileBlockManager {
	private static FileBlockManager instance = null;
	private final String IFBI_SET = "idlefileblockid_set";
	private final String FBI_SET = "fileblockid_set";
	private final int STATE_RUNNING = 1, STATE_CLEANED = 0;
	
	private Jedis jedis = null;
	private String basePath = "/";
	private String hdfsLocation = null;
	private int maximumFileBlockSize = 512;
	private int state = STATE_CLEANED;
	private static int fileBlockSize = 0;
	
	private FileBlockManager(String redisHost, int redisPort,
			String hdfsLocation,
			String basePath, int maximumFileBlockSize) {
		
		// TODO: Check the arguments.
		this.basePath = basePath;
		this.maximumFileBlockSize = maximumFileBlockSize;
		this.hdfsLocation = hdfsLocation;
		
		// Create a jedis instance.
		if (jedis == null) jedis = new Jedis(redisHost, redisPort);
		// Remove the old set from redis if exist.
		if (jedis.exists(FBI_SET)) {
			jedis.del(FBI_SET);
		}
		if (jedis.exists(IFBI_SET)) {
			jedis.del(IFBI_SET);
		}
		// Initial the idle file block id set.
		Pipeline pipeline = jedis.pipelined();
		for(int i = 0;i < maximumFileBlockSize; i++)
			pipeline.sadd(IFBI_SET, String.valueOf(i));
		pipeline.sync();
		
		state = STATE_RUNNING;
	}
	
	/**
	 * Get the single instance of FileBlockManager.
	 * @param redisHost
	 * @param redisPort
	 * @param basePath The temporary path.
	 * @param maximumFileBlockSize
	 * @return
	 */
	static public FileBlockManager getInstance(String redisHost, int redisPort,
			String hdfsLocation,
			String basePath, int maximumFileBlockSize) {
		if (instance == null) instance = new FileBlockManager(redisHost, redisPort,
				hdfsLocation,
				basePath, maximumFileBlockSize);
		return instance;
	}
	
	/**
	 * Clean the all sets from redis.
	 */
	public void clean() {
		if (state != STATE_RUNNING) return;
		
		// Remove the set from redis if exist.
		if (jedis.exists(FBI_SET)) {
			jedis.del(FBI_SET);
		}
		if (jedis.exists(IFBI_SET)) {
			jedis.del(IFBI_SET);
		}
		state = STATE_CLEANED;
	}
	
	/**
	 * Get a new file block instance.
	 * @return The FileBlock instance if success. Return null if failed.
	 * @throws IOException 
	 * @throws NullPointerException 
	 * @throws URISyntaxException 
	 * @throws IllegalArgumentException 
	 */
	public FileBlock newFileBlock() 
			throws NullPointerException, IOException, IllegalArgumentException, URISyntaxException {
		if ((state == STATE_CLEANED) || 
				(fileBlockSize >= maximumFileBlockSize))
			return null;
		
		String id = null;
		// Get and delete a random id from IFBI_SET.
		id = jedis.spop(IFBI_SET);
		if (id == null) return null;
		// Add the id to FBI_SET.
		jedis.sadd(FBI_SET, id);
		
		synchronized (instance) {			
			fileBlockSize++;
		}
		return new FileBlock(instance, basePath, hdfsLocation, id);
	}
	
	public void destoryFileBlock(String fileBlockId) {
		if (jedis.srem(FBI_SET, fileBlockId) == 1) {
			jedis.sadd(IFBI_SET, fileBlockId);
		}
		synchronized (instance) {			
			fileBlockSize--;
		}
	}
}
