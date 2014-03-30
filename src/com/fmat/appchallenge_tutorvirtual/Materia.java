package com.fmat.appchallenge_tutorvirtual;


import java.util.HashMap;
/**
 * 
 * Esta clase, servira para un uso cómodo del json recibido, y contendrá los horarios correspondientes
 * así como el nombre del profesor.
 *
 */
public class Materia {
	public String nombre;
	public String profesor;
	public HashMap<String, String> mapDias;
	public String notice;
	
	public Materia(String nombre){
		mapDias = new HashMap<String, String>();
		this.nombre= nombre;		
	}
	
	public void setNotice(String notice){
		this.notice=notice;
	}
	public String getNotice(){
	return notice;
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
