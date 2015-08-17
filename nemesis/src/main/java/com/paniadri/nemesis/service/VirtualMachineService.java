package com.paniadri.nemesis.service;

import java.util.List;

import com.paniadri.nemesis.model.VirtualMachineModel;

public interface VirtualMachineService {

	public void addVM();
	
	public void deleteVM();
	
	public List<VirtualMachineModel>  listVMs() throws Exception;
	
}
