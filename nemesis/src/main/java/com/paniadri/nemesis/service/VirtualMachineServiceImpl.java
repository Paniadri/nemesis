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
	//Las credenciales también pueden ser accedidas desde la variable $ONE_AUTH
	//si no se especifican, además de otros medios.
    String credenciales = "oneadmin:casa";
    
	@Override
	public void addVM(int numeroEscenario) {
		
		try{
			oneClient = new Client(credenciales, null);
			String vmTemplate;
			String nombreEscenario="";
			
			if(numeroEscenario == 1 || numeroEscenario == 2){
				
				if (numeroEscenario == 1) nombreEscenario="_simple_lxc";
				if (numeroEscenario == 2) nombreEscenario="_tutorial_lxc";
				vmTemplate =
		                  "NAME     = escenario"+nombreEscenario+"\n"
		                + "CPU = 1  MEMORY = 1532\n"
		                + "DISK     = [ IMAGE  = \"vnx image\" ]\n"
		                + "NIC    = [ NETWORK = \"privada\" ]\n"
		                + "GRAPHICS = [ TYPE = \"vnc\",  LISTEN = \"0.0.0.0\"]\n"
		                + "CONTEXT  = [\n"
		                + "\tFILES  = \"/var/lib/one/scripts/escenario"+numeroEscenario+"/init.sh\",\n"
		                + "\tNETWORK  = \"YES\",\n"
		                + "\tUSERNAME  = \"vnx\",\n"
						+ "\tSSH_PUBLIC_KEY  = \"$USER[SSH_PUBLIC_KEY]\" ]\n";
			}else{
				log.error("Numero de escenario no implementado: "+numeroEscenario);
				throw new Exception("Numero de escenario no implementado");
			}
			
			log.info("VM Template:\n" + vmTemplate+"\n");

            log.info("Trying to allocate the virtual machine... ");
            OneResponse rc = VirtualMachine.allocate(oneClient, vmTemplate);

            if( rc.isError()){
                log.error( "falló!");
                throw new Exception( rc.getErrorMessage() );
            }
            
            //Después de esto pasa a manos del scheduler de OpenNebula, que lo asignará
            // al nodo que satisfaga los requerimientos marcados en la template.
            
		}catch (Exception e){
			log.error("Excepción "+e.toString());
		}
	}
	
	@Override
	public void deleteVM(int numeroVM) {
		try{
			oneClient = new Client(credenciales, null);
			
			VirtualMachine vm = new VirtualMachine(numeroVM, oneClient);
			OneResponse rc = vm.delete();
			
			if( rc.isError()){
				log.error(rc.getErrorMessage());
            }
		}catch (Exception e){
			log.error("Excepción "+e.toString());
		}
	}
	
	@Override
	public List<VirtualMachineModel> listVMs() throws Exception {
		
		oneClient = new Client(credenciales, null);
		
		ArrayList<VirtualMachineModel> lista = new ArrayList<VirtualMachineModel>();
		
		VirtualMachinePool vmPool = new VirtualMachinePool(oneClient);

		// Le pedimos a la MachinePool que nos facilite la informacion desde OpenNebula
		OneResponse rc = vmPool.info();

        if(rc.isError())
            throw new Exception( rc.getErrorMessage() );

        log.info("VMs en la Pool:");
        
        for ( VirtualMachine vmachine : vmPool )
        {
        	lista.add(new VirtualMachineModel(
        			vmachine.getId(),
        			vmachine.xpath("TEMPLATE/GRAPHICS/PORT"),
        			vmachine.getName(),
        			vmachine.stateStr(),
        			vmachine.lcmStateStr(),
        			vmachine.xpath("HISTORY_RECORDS/HISTORY/HOSTNAME")));
        	
        	log.info("\tID :" + vmachine.getId() +
                   ", Nombre :" + vmachine.getName()+
                   ", Puerto :" + vmachine.xpath("TEMPLATE/GRAPHICS/PORT")+
                   ", Estado :" + vmachine.stateStr()+
                   ", Estado LCM :" + vmachine.lcmStateStr()+
                   ", Hostname :"+ vmachine.xpath("HISTORY_RECORDS/HISTORY/HOSTNAME"));
        }
        
		return lista;
	}

}
