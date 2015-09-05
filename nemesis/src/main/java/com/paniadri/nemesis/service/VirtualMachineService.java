package com.paniadri.nemesis.service;

import java.util.List;

import com.paniadri.nemesis.model.VirtualMachineModel;

public interface VirtualMachineService {

	public void addVM(int numeroEscenario);
	
	public void deleteVM(int numeroVM);
	
	public List<VirtualMachineModel> listVMs() throws Exception;
	
}
