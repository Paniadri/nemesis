package com.paniadri.nemesis.controller;


import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.paniadri.nemesis.model.EscenarioModel;
import com.paniadri.nemesis.model.VirtualMachineModel;
import com.paniadri.nemesis.service.VirtualMachineService;

@Controller
public class VirtualMachineController{
	
	private static Log log = LogFactory.getLog(VirtualMachineController.class);
	
	@Autowired
	private VirtualMachineService virtualMachineService;
	
	@RequestMapping(value="/")
	public String list(Model model) {
	
	  //la función del método es rellenar las variables vms, vm y escenario, una con la lista de vms 
	  //sacados de OpenNebula, y vm y escenario para que puedan ser rellenadas con los datos del formulario.
		
		try {
			log.info("Leyendo lista de VMs");
			
			List<VirtualMachineModel> listaVms = virtualMachineService.listVMs();
			model.addAttribute("vms", listaVms);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		model.addAttribute("escenario", new EscenarioModel());
		model.addAttribute("vm", new VirtualMachineModel());
		return "index";
	  }
	
	
	@RequestMapping(value="/add")
	public String addEscenario(@ModelAttribute("escenario") EscenarioModel escenario) {
		
		log.info("Procediendo a añadir escenario"+escenario.getNumeroEscenario());
		
		virtualMachineService.addVM(escenario.getNumeroEscenario());
		
		return "redirect:/";
  }
	
	
	@RequestMapping(value="/delete")
	public String deleteEscenario(@ModelAttribute("vm") VirtualMachineModel vm) {
		
		log.info("Procediendo a eliminar vm"+vm.getId());
		
		virtualMachineService.deleteVM(Integer.parseInt(vm.getId()));
		
		return "redirect:/";
  }
	
	 
	/**
	 * Redirige al cliente VNC dados los parámetros del formulario de entrada
	 * 
	 * @param direccion
	 * @param puerto
	 * @param model
	 * @return jsp correspondiente al cliente VNC con los datos de la conexión.
	 */
	@RequestMapping(value="/mostrar")
	public String showClient(@RequestParam("direccion") String direccion,
						@RequestParam("puerto") String puerto, Model model) {
		log.info("Redireccionando a cliente guacamole");
		
		model.addAttribute("direccion", direccion);
	    model.addAttribute("puerto", puerto);
	    
		return "client";
  }
	
}