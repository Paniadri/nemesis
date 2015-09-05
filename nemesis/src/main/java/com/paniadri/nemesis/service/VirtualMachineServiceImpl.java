package com.paniadri.nemesis.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opennebula.client.Client;
import org.opennebula.client.OneResponse;
import org.opennebula.client.vm.VirtualMachine;
import org.opennebula.client.vm.VirtualMachinePool;
import org.springframework.stereotype.Service;

import com.paniadri.nemesis.model.VirtualMachineModel;

@Service
public class VirtualMachineServiceImpl implements VirtualMachineService{
	
	private static Log log = LogFactory.getLog(VirtualMachineServiceImpl.class);
	
	Client oneClient;
    String credenciales = "oneadmin:casa";
    
	@Override
	public void addVM(int numeroEscenario) {
		
		try{
			oneClient = new Client(credenciales,null);
			String vmTemplate;
			
			if(numeroEscenario == 1 || numeroEscenario == 2){
				vmTemplate =
		                  "NAME     = escenario"+numeroEscenario+"\n"
		                + "CPU = 1  VCPU = 1  MEMORY = 1532\n"
		                + "DISK     = [ IMAGE  = \"VNX image\" ]\n"
		                + "OS       = [ ARCH = x86_64 ]\n"
		                + "# NIC    = [ NETWORK = \"Non existing network\" ]\n"
		                + "GRAPHICS = [ TYPE = \"vnc\",  LISTEN = \"0.0.0.0\"]\n"
		                + "CONTEXT  = [\n"
		                + "\tFILES  = \"/var/lib/one/scripts/escenario"+numeroEscenario+"/init.sh\",\n"
		                + "\tNETWORK  = \"YES\",\n"
		                + "\tUSERNAME  = \"vnx\",\n"
						+ "\tSSH_PUBLIC_KEY  = \"VNX image\" ]\n";
			}else{
				log.error("Numero de escenario no implementado: "+numeroEscenario);
				throw new Exception("Numero de escenario no implementado");
			}
			
			System.out.println("Virtual Machine Template:\n" + vmTemplate);
            System.out.println();

            System.out.print("Trying to allocate the virtual machine... ");
            OneResponse rc = VirtualMachine.allocate(oneClient, vmTemplate);

            if( rc.isError() )
            {
                System.out.println( "failed!");
                throw new Exception( rc.getErrorMessage() );
            }

		}catch (Exception e){
			log.error("Excepción "+e.toString());
		}
	}
	
	@Override
	public void deleteVM(int numeroVM) {
		try{
			oneClient = new Client(credenciales,null);
			
			VirtualMachine vm = new VirtualMachine(numeroVM, oneClient);
			OneResponse rc = vm.delete();
			
			if( rc.isError() )
            {
				log.error(rc.getErrorMessage());
            }
		}catch (Exception e){
			log.error("Excepción "+e.toString());
		}
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
        			vmachine.getName(),
        			vmachine.stateStr(),
        			vmachine.xpath("HISTORY_RECORDS/HISTORY/HOSTNAME")));
        	
            System.out.println("\tID :" + vmachine.getId() +
                               ", Nombre :" + vmachine.getName()+
                               ", Puerto :" + vmachine.xpath("TEMPLATE/GRAPHICS/PORT")+
                               ", Estado :" + vmachine.stateStr()+
                               ", Hostname :"+ vmachine.xpath("HISTORY_RECORDS/HISTORY/HOSTNAME"));
        }
        
		return lista;
	}

}
