package com.fmat.appchallenge_tutorvirtual;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 
 * Esta clase tiene como objetivo establecer la conexión correspondiente al web
 * service determinado mediante el uso del formato json.
 * 
 */
public class conexionWebService_thread extends Thread {
	public String url;
	public boolean isReady = false;
	private static final int TIMEOUT_MILLISEC = 1000;
	public boolean hasAnError = false;
	private static String errorMessage = null;
	public boolean isLogIn = true;
	public Activity activity;
	public static JSONObject jsonObjectAsignaturas;
	public conexionWebService_thread(String URL, boolean isLogIn) {
		this.url = URL;
		this.isLogIn = isLogIn;
		
	}
	public void setActivity(Activity a){
		this.activity=a;
	}
	/**
	 * Se toma el stream obtenido mediante el request, y se convierte a un
	 * String.
	 */
	private static String convertStreamToString(InputStream is) {

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append((line + "\n"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	/**
	 * Método principal del hilo, este se encarga de establecer la conexión, y el páso de parámetros, así
	 * como la obtención de datos.
	 */
	public void run() {
		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_MILLISEC);
		HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
		//Instanciamos un cliente
		HttpClient httpclient = new DefaultHttpClient();
		//Instanciamos un request POST
		HttpPost httppost = new HttpPost(url);

		try {
			//Verificamos como se ha accedidio, para determinar que tipo de json se enviará
			//if (isLogIn) {
				//Enviamos el email y el password correspondientes
				JSONObject obj = new JSONObject("{'student':{'email':'"
						+ LoginActivity.email + "','password':'"
						+ LoginActivity.password + "'}}");				
				StringEntity se = new StringEntity(obj.toString());
				httppost.setEntity(se);
				httppost.setHeader("Content-Type", "application/json");
				//Solicitamos
				HttpResponse response = httpclient.execute(httppost);
				//Obtenemos
				HttpEntity entity = response.getEntity();
				InputStream is = entity.getContent();
				//Convertimos
				String bodyJson = convertStreamToString(is);
				bodyJson = bodyJson.substring(1, bodyJson.length() - 1);
				//Parseamos
				JSONObject json = new JSONObject(bodyJson);
				//status puede tomar los siguientes valores: failure - succes
				String status = json.get("sign_in_status").toString();
				if (status.equals("failure")) {
					conexionWebService_thread
							.setErrorMessage("Usuario y/o contraseña inválidos");
					hasAnError = true;
					isReady = true;
				}
				//TextView I=(EditText)activity.findViewById(R.id.editInicio);
				//TextView F=(EditText)activity.findViewById(R.id.editFin);
				
				obj = new JSONObject("{'starting_date':'"+"11:00"+
						"','ending_date':'"+"20:00"+"'}");				
				se = new StringEntity(obj.toString());
				HttpPost httpPost = new HttpPost("http://192.168.228.101:3000/students/info.json");
				httpPost.setEntity(se);
				httpPost.setHeader("Content-Type", "application/json");
				//Solicitamos
				response = httpclient.execute(httpPost);
				//Obtenemos
				entity = response.getEntity();
				is = entity.getContent();
				//Convertimos
				bodyJson = convertStreamToString(is);
				this.jsonObjectAsignaturas= new JSONObject(bodyJson);
				Log.i("INFO", bodyJson);
			/* else {//Solicitaremos el JSON a otro web-service
				//Enviamos el email y el password correspondientes
				TextView I=(EditText)activity.findViewById(R.id.editInicio);
				TextView F=(EditText)activity.findViewById(R.id.editFin);
				
				JSONObject obj = new JSONObject("{'starting_date':'"+I.getText()+
						"','ending_date':'"+F.getText()+"'}");				
				StringEntity se = new StringEntity(obj.toString());
				httppost.setEntity(se);
				httppost.setHeader("Content-Type", "application/json");
				//Solicitamos
				HttpResponse response = httpclient.execute(httppost);
				//Obtenemos
				HttpEntity entity = response.getEntity();
				InputStream is = entity.getContent();
				//Convertimos
				String bodyJson = convertStreamToString(is);
				bodyJson = bodyJson.substring(1, bodyJson.length() - 1);
				Log.i("BABABA", "BODY: "+bodyJson);
				//Parseamos
				JSONObject json = new JSONObject(bodyJson);
				//status puede tomar los siguientes valores: failure - succes
				String status = json.get("sign_in_status").toString();
				if (status.equals("failure")) {
					conexionWebService_thread
							.setErrorMessage("Usuario y/o contraseña inválidos");
					hasAnError = true;
					isReady = true;
				}
			}*/
			// Parse
			// JSONObject json = new JSONObject(responseBody);

			// JSONArray jArray = json.getJSONArray("posts");
			/*
			 * ArrayList<HashMap<String, String>> mylist = new
			 * ArrayList<HashMap<String, String>>();
			 * 
			 * for (int i = 0; i < jArray.length(); i++) { HashMap<String,
			 * String> map = new HashMap<String, String>(); JSONObject e =
			 * jArray.getJSONObject(i); String s = e.getString("post");
			 * JSONObject jObject = new JSONObject(s);
			 * 
			 * map.put("user", jObject.getString("user")); map.put("password",
			 * jObject.getString("password"));
			 * 
			 * //map.put("UserName", jObject.getString("UserName")); //
			 * map.put("FullName", jObject.getString("FullName"));
			 * 
			 * mylist.add(map); }
			 */
			// conexionWebService_thread.setErrorMessage("Error al parsear Json");
			// hasAnError=true;
			// Toast.makeText(this, mylist.get(0).get("name"),
			// Toast.LENGTH_LONG).show();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			conexionWebService_thread
					.setErrorMessage("Error al conectar con el servicio web");
			hasAnError = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			conexionWebService_thread
					.setErrorMessage("Error al conectar con el servicio web");
			hasAnError = true;
		} catch (Throwable t) {// Error al parsear el Json
			// Toast.makeText(this, "Request failed: " + t.toString(),
			// Toast.LENGTH_LONG).show();
			Log.i("Error", t.toString());
			conexionWebService_thread.setErrorMessage("Error al parsear Json");
			hasAnError = true;
		} finally {
			isReady = true;
		}
		// Log.i(getClass().getSimpleName(), "send  task - end");

	}

	public JSONObject asignaturasJSON(){
		return jsonObjectAsignaturas;
	}
	public boolean hasAnError() {
		return hasAnError;
	}

	public boolean isReady() {
		return isReady;
	}

	public static String getErrorMessage() {
		return errorMessage;
	}

	public static void setErrorMessage(String errorMessage) {
		conexionWebService_thread.errorMessage = errorMessage;
	}
}
