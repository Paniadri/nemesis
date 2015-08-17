package com.paniadri.nemesis.service;

import java.util.ArrayList;
import java.util.List;

import org.opennebula.client.Client;
import org.opennebula.client.OneResponse;
import org.opennebula.client.vm.VirtualMachine;
import org.opennebula.client.vm.VirtualMachinePool;
import org.springframework.stereotype.Service;

import com.paniadri.nemesis.model.VirtualMachineModel;

@Service
public class VirtualMachineServiceImpl implements VirtualMachineService{
	
	
	Client oneClient;
    String credenciales = "oneadmin:casa";
    
	@Override
	public void addVM() {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void deleteVM() {
		// TODO Auto-generated method stub
	}
	
	@Override
	public List<VirtualMachineModel> listVMs() throws Exception {
		
		oneClient = new Client(credenciales,null);
		
		ArrayList<VirtualMachineModel> lista = new ArrayList<VirtualMachineModel>();
		
		VirtualMachinePool vmPool = new VirtualMachinePool(oneClient);
        // Remember that we have to ask the pool to retrieve the information
        // from OpenNebula
		OneResponse rc = vmPool.info();

        if(rc.isError())
            throw new Exception( rc.getErrorMessage() );

        System.out.println(
                "\nThese are all the Virtual Machines in the pool:");
        
        for ( VirtualMachine vmachine : vmPool )
        {
        	lista.add(new VirtualMachineModel(
        			vmachine,
        			vmachine.getId(),
        			vmachine.xpath("TEMPLATE/GRAPHICS/PORT"),
        			vmachine.getName()));
        	
            System.out.println("\tID :" + vmachine.getId() +
                               ", Name :" + vmachine.getName() );
        }
        
		return lista;
	}

}
