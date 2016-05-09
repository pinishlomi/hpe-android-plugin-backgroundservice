package com.hpe.hybridsitescope.soap;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.hpe.hybridsitescope.data.Entity;
import com.hpe.hybridsitescope.data.EntityHandler;
import com.hpe.hybridsitescope.data.Monitor;
import com.hpe.hybridsitescope.data.MonitorHelper;
import com.hpe.hybridsitescope.data.SiteScopeServer;

import org.ksoap2.serialization.SoapObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Example of how the soap calls should be constructed.
 * currently only be used in PasscodeActivity
 *
 */
public class GetMonitorSnapshots extends SoapCall {
	
	private Activity activity;

	public GetMonitorSnapshots(SiteScopeServer ssServer, Vector<String> paths, Handler handler, Activity activity) {
		super(ssServer, SoapCalls.getMonitorSnapshots, handler);
		
		//set up properties
		ArrayList<Serializable> properties = new ArrayList<Serializable>();
		properties.add(paths);
		properties.add(null);
		properties.add(ssServer.getUsername());
		properties.add(ssServer.getPassword());
		properties.add(SoapCalls.ANDROID_IDENTIFIER);
		
		setProperties(properties);
		
		this.activity = activity;
	}
	
	@Override
	protected void onPostExecute(SoapObject result) {
		super.onPostExecute(result);
		Entity monitor = new Monitor(ssServer);
		EntityHandler entityHandler = new EntityHandler(monitor, this.activity);
		Message message = new Message();
		Bundle bundle = new Bundle();
		if(result!=null)
    	{			        		
    		for(int p=0; p<result.getPropertyCount(); p++)
    		{
    			monitor = entityHandler.getMonitor(result, p);				        			
    		}	
    		bundle.putSerializable(MonitorHelper.ENTITY_OBJECT, monitor);
    	}	
		else
		{
			bundle.putString("result", "FAIL");
		}
		message.setData(bundle);
		this.handler.sendMessage(message);
	}
	
}
