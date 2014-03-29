package com.fmat.appchallenge_tutorvirtual;

import java.util.ArrayList;
import java.util.HashMap;

public class Materia {
	public String nombre;
	public String profesor;
	public HashMap<String, String> mapDias;
	
	public Materia(String nombre){
		mapDias = new HashMap<String, String>();
		this.nombre= nombre;		
	}
	public void setNombre(String nombre){
		this.nombre=nombre;
	}
	public String getNombre(){
		return nombre;
	}
	public void setProfesor(String profesor){
		this.profesor=profesor;
	}
	public String getProfesor(){
		return profesor;
	}
	
	public void agregarDia(String dia, String hora){
		mapDias.put(dia, hora);
	}
	public HashMap<String,String> getDias(){
		return mapDias;
	}
}
