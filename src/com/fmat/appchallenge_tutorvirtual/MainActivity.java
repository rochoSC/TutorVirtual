package com.fmat.appchallenge_tutorvirtual;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.opengl.Visibility;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	String dias[]={"Lunes","Martes","Miércoles","Jueves","Viernes"};
	List <String> asignaturasGrid = new ArrayList<String>(); 
	ArrayList <Materia> asignaturas = new ArrayList<Materia>();
	GridView gridView;
	
	JSONObject json;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		gridView = (GridView) findViewById(R.id.gridView1);
		json =conexionWebService_thread.jsonObjectAsignaturas;
		desplegar();
	}
	public void desplegar(){
		JSONArray a = json.names();
		
			for(int c=0;c<a.length();c++){
				try {
					Materia m = new Materia(a.getString(c));
						if(json.getJSONObject(m.getNombre()).names().get(0).equals("notice"))
							m.setNotice(json.getJSONObject(m.getNombre()).get("notice").toString());
						else{
							if(json.getJSONObject(m.getNombre()).names().get(0).equals("monday")){
								if(!json.getJSONObject(m.getNombre()).get("monday").toString().equals("24"))
									m.agregarDia("Lunes", json.getJSONObject(m.getNombre()).get("monday").toString()
											+" - "+json.getJSONObject(m.getNombre()).get("monday_end").toString() 
											);								
							}	
							if(json.getJSONObject(m.getNombre()).names().get(0).equals("tuesday")){
								if(!json.getJSONObject(m.getNombre()).get("tuesday").toString().equals("24"))
									m.agregarDia("Martes", json.getJSONObject(m.getNombre()).get("tuesday").toString()
										+" - "+json.getJSONObject(m.getNombre()).get("tuesday_end").toString() 
										);
							}
							if(json.getJSONObject(m.getNombre()).names().get(0).equals("wednesday")){
								if(!json.getJSONObject(m.getNombre()).get("wednesday").toString().equals("24"))
									m.agregarDia("Miercoles", json.getJSONObject(m.getNombre()).get("wednesday").toString()
										+" - "+json.getJSONObject(m.getNombre()).get("wednesday_end").toString() 
										);
							}
							if(json.getJSONObject(m.getNombre()).names().get(0).equals("thursday")){
								if(!json.getJSONObject(m.getNombre()).get("thursday").toString().equals("24"))
									m.agregarDia("Jueves", json.getJSONObject(m.getNombre()).get("thursday").toString()
										+" - "+json.getJSONObject(m.getNombre()).get("thursday_end").toString() 
										);
							}
							if(json.getJSONObject(m.getNombre()).names().get(0).equals("friday")){
								if(!json.getJSONObject(m.getNombre()).get("friday").toString().equals("24"))
									m.agregarDia("Viernes", json.getJSONObject(m.getNombre()).get("friday").toString()
										+" - "+json.getJSONObject(m.getNombre()).get("friday_end").toString() 
										);
							}
						}
						asignaturas.add(m);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//Materia m = new Materia("Algebra Superior I");
			//m.setProfesor("A");
			//m.agregarDia("Miércoles", "12:30 - 15:00");
			////m.agregarDia("Viernes", "12:30 - 15:00");
		//asignaturas.add(m);
		/*Materia m1 = new Materia("Algebra Superior II");
		m1.setProfesor("B");
		m1.agregarDia("Lunes", "10:30 - 12:00");
		m1.agregarDia("Martes", "12:30 - 15:00");
		m1.agregarDia("Viernes", "10:30 - 12:00");
		asignaturas.add(m1);	*/	
		asignaturasGrid.add("Asignatura");
		asignaturasGrid.add("Lunes");
		asignaturasGrid.add("Martes");
		asignaturasGrid.add("Miércoles");
		asignaturasGrid.add("Jueves");
		asignaturasGrid.add("Viernes");
		
		for(int c = 0; c<asignaturas.size();c++){
			Materia mat = asignaturas.get(c);
			asignaturasGrid.add(mat.getNombre());
			HashMap<String, String> map= mat.getDias();
			for(int i=0;i<5;i++){
				//String hora = map.get(dias[i]);				
				if(!map.containsKey(dias[i])){//Este día no tiene hora
					asignaturasGrid.add("");
				}else{//Este día si tiene hora
					asignaturasGrid.add("Profersor: "+mat.getProfesor()+"\n"+map.get(dias[i]));
				}
			}
			
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, asignaturasGrid);
		
		gridView.setAdapter(adapter);
		}
		
		
		
		
		
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
