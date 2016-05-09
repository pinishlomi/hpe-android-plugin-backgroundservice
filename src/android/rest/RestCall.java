package com.hpe.android.plugin.backgroundservice.rest;

import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import com.hpe.android.plugin.backgroundservice.data.SiteScopeServer;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Vector;


/**
 * Example of how the soap calls should be constructed.
 * currently only be used in PasscodeActivity
 *
 */
public class RestCall extends AsyncTask<Void, Integer, JSONObject> {
	
	SiteScopeServer ssServer;
	ArrayList<?> properties;
	String method;
	protected Handler handler;
	
	public RestCall(SiteScopeServer ssServer, String method, Handler handler) {
		super();
		this.ssServer = ssServer;
		this.method = method;
		this.handler = handler;
	}
	
	public void setProperties(ArrayList<?> properties) {
		this.properties = properties;
	}

	@Override
	protected JSONObject doInBackground(Void... params) {
		JSONObject response = null;
		try 
		{
			JSONObject request = new JSONObject();//(RestCalls.NAMESPACE, method ); // set up request
			
			
			for(int i=0; i<properties.size(); i++)
			{
				Object property = properties.get(i);
				if(property instanceof Vector<?>)
					property = (Vector<?>)property;
				//request.addProperty(RestCalls.IN.concat(String.valueOf(i)), property);
			}
			
			// put all required data into a soap envelope



			response = request;
		} 
		catch (Exception e) 
		{
			//TODO: when SocketException is thrown, it is thrown about 5 minutes AFTER this thread has already been canceled
			//but for some reason, it is not canceled yet
			
			Log.e("SITESCOPE", "problem excecuting soap method: " + method + "; " + e.getMessage());
			response = null;
		}
		
		return response;
	}
}
