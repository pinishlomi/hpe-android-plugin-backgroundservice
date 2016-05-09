package com.hpe.hybridsitescope.soap;

import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import com.hpe.hybridsitescope.data.SiteScopeServer;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.Vector;


/**
 * Example of how the soap calls should be constructed.
 * currently only be used in PasscodeActivity
 *
 */
public class SoapCall extends AsyncTask<Void, Integer, SoapObject> {
	
	SiteScopeServer ssServer;
	ArrayList<?> properties;
	String method;
	protected Handler handler;
	
	public SoapCall(SiteScopeServer ssServer, String method, Handler handler) {
		super();
		this.ssServer = ssServer;
		this.method = method;
		this.handler = handler;
	}
	
	public void setProperties(ArrayList<?> properties) {
		this.properties = properties;
	}

	@Override
	protected SoapObject doInBackground(Void... params) {
		SoapObject response = null;
		try 
		{
			SoapObject request = new SoapObject(SoapCalls.NAMESPACE, method ); // set up request
			
			
			for(int i=0; i<properties.size(); i++)
			{
				Object property = properties.get(i);
				if(property instanceof Vector<?>)
					property = (Vector<?>)property;
				request.addProperty(SoapCalls.IN.concat(String.valueOf(i)), property);
			}
			
			// put all required data into a soap envelope
			SoapSerializationEnvelope envelope  = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			
			// prepare request
			envelope.setOutputSoapObject(request); 


			HttpTransportSE httpTransport = new HttpTransportSE(ssServer.getUrl() + SoapCalls.APICONFIGURATIONIMPL_URL_SUFFIX);
			httpTransport.call(SoapCalls.NAMESPACE.concat(method), envelope);
			response = (SoapObject)envelope.getResponse();
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
