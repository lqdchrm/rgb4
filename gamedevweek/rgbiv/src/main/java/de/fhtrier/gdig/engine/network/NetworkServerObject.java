package de.fhtrier.gdig.engine.network;

import java.io.Serializable;
import java.net.InetAddress;

public class NetworkServerObject implements Serializable {
	private static final long serialVersionUID = 15986547546L;

	private InetAddress ip;
	private int port;
	private String name;
	private String map;
	private String version;
	private float latency;

	public InetAddress getIp() {
		return ip;
	}

	public void setIp(InetAddress ip) {
		this.ip = ip;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getPort() {
		return port;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMap() {
		return map;
	}

	public void setMap(String map) {
		this.map = map;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public float getLatency() {
		return latency;
	}

	public void setLatency(float latency) {
		this.latency = latency;
	}
}
