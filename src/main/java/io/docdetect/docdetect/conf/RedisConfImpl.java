package io.docdetect.docdetect.conf;

public class RedisConfImpl implements RedisConf {
	private String host;
	private int port;
	
	public RedisConfImpl(String host, int port) {
		this.host = host;
		this.port = port;
	}
	
	@Override
	public String getHost() {
		return this.host;
	}

	@Override
	public void setHost(String host) {
		this.host = host;
	}

	@Override
	public int getPort() {
		return port;
	}

	@Override
	public void setPort(int port) {
		this.port = port;
	}

}
