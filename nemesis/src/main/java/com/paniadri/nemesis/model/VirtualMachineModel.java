package com.paniadri.nemesis.model;

import org.opennebula.client.vm.VirtualMachine;

public class VirtualMachineModel {
	
	VirtualMachine vm;
	
	String id;

	String port;
	
	String name;
	
	

	public VirtualMachineModel(VirtualMachine vm, String id, String port, String name) {
		super();
		this.vm = vm;
		this.id = id;
		this.port = port;
		this.name = name;
	}
	

	public VirtualMachineModel() {
	}


	public VirtualMachine getVm() {
		return vm;
	}

	public void setVm(VirtualMachine vm) {
		this.vm = vm;
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

}
