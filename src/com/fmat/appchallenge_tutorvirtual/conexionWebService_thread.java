package com.fmat.appchallenge_tutorvirtual;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import android.util.Log;

public class conexionWebService_thread extends Thread {
	public String url;
	public boolean isReady = false;
	private static final int TIMEOUT_MILLISEC = 1000;
	public boolean hasAnError = false;
	private static String errorMessage = null;
	public List<NameValuePair> nameValuePairs;
	public boolean isLogIn = true;

	public conexionWebService_thread(String URL,
			List<NameValuePair> nameValuePairs, boolean isLogIn) {
		this.url = URL;
		this.nameValuePairs = nameValuePairs;
		this.isLogIn = isLogIn;
	}

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

	public void run() {
		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_MILLISEC);
		HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);

		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);

		try {

			if (isLogIn) {
				JSONObject obj = new JSONObject("{'student':{'email':'"
						+ LoginActivity.email + "','password':'"
						+ LoginActivity.password + "'}}");
				// JSONObject holder = new JSONObject();
				StringEntity se = new StringEntity(obj.toString());
				httppost.setEntity(se);
				httppost.setHeader("Content-Type", "application/json");
				HttpResponse response = httpclient.execute(httppost);
				HttpEntity entity = response.getEntity();
				InputStream is = entity.getContent();
				String bodyJson = convertStreamToString(is);
				bodyJson = bodyJson.substring(1, bodyJson.length() - 1);
				Log.i("Probando bodyJson", "BODY JSON: " + bodyJson);
				JSONObject json = new JSONObject(bodyJson);
				String status = json.get("sign_in_status").toString();// Valores:
																		// succes
																		// ,
																		// failure

				if (status.equals("failure")) {
					conexionWebService_thread
							.setErrorMessage("Usuario y/o contraseña inválidos");
					hasAnError = true;
					isReady = true;
				}
				Log.i("ResponseBody", "BODY: " + status);
			} else {

			}
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
