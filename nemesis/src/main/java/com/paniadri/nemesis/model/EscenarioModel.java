package com.paniadri.nemesis.model;

public class EscenarioModel {

	String id;
	int numeroEscenario;
	
	public EscenarioModel(){
		
	}
	
	public EscenarioModel(String id, int numeroEscenario) {
		super();
		this.id = id;
		this.numeroEscenario = numeroEscenario;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getNumeroEscenario() {
		return numeroEscenario;
	}
	public void setNumeroEscenario(int numeroEscenario) {
		this.numeroEscenario = numeroEscenario;
	}
	
	
}
