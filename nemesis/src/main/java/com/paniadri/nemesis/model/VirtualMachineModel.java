package com.paniadri.nemesis.model;

public class VirtualMachineModel {
	
	String id;

	String port;
	
	String name;
	
	String status;
	
	String host;
	
	public VirtualMachineModel(String id, String port, String name, String status, String host) {
		super();
		this.id = id;
		this.port = port;
		this.name = name;
		this.status = status;
		this.host = host;
	}
	
	public VirtualMachineModel() {
	}

	
	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

}
