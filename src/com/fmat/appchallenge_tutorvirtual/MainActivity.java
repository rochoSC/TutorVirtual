package com.fmat.appchallenge_tutorvirtual;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	String dias[]={"Lunes","Martes","Miércoles","Jueves","Viernes"};
	List <String> asignaturasGrid = new ArrayList<String>(); 
	ArrayList <Materia> asignaturas = new ArrayList<Materia>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Materia m = new Materia("Algebra Superior I");
			m.setProfesor("A");
			m.agregarDia("Lunes", "12:30 - 15:00");
			m.agregarDia("Miércoles", "12:30 - 15:00");
			m.agregarDia("Viernes", "12:30 - 15:00");
		asignaturas.add(m);
		Materia m1 = new Materia("Algebra Superior II");
		m1.setProfesor("B");
		m1.agregarDia("Lunes", "10:30 - 12:00");
		m1.agregarDia("Martes", "12:30 - 15:00");
		m1.agregarDia("Viernes", "10:30 - 12:00");
		asignaturas.add(m1);		
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
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		GridView gridView = (GridView) findViewById(R.id.gridView1);
		//gridview.setHorizontalScrollBarEnabled(true);
		//gridview.setVerticalScrollBarEnabled(true);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, asignaturasGrid);
 
		gridView.setAdapter(adapter);
		gridView.setColumnWidth(121);
		/*gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
				int position, long id) {
			   Toast.makeText(getApplicationContext(),((TextView) v).getText(), Toast.LENGTH_SHORT).show();
			}
		});*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
