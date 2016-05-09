package com.hpe.android.plugin.backgroundservice.rest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.hpe.android.plugin.backgroundservice.data.Entity;
import com.hpe.android.plugin.backgroundservice.data.Monitor;
import com.hpe.android.plugin.backgroundservice.data.SiteScopeServer;

import org.json.JSONObject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Example of how the soap calls should be constructed.
 * currently only be used in PasscodeActivity
 *
 */
public class GetMonitorSnapshots extends RestCall {
	

	public GetMonitorSnapshots(SiteScopeServer ssServer, Vector<String> paths, Handler handler) {
		super(ssServer, RestCalls.getMonitorSnapshots, handler);
		
		//set up properties
		ArrayList<Serializable> properties = new ArrayList<Serializable>();
		properties.add(paths);
		properties.add(null);
		properties.add(ssServer.getUsername());
		properties.add(ssServer.getPassword());
		properties.add(RestCalls.ANDROID_IDENTIFIER);
		
		setProperties(properties);
	}
	
	@Override
	protected void onPostExecute(JSONObject result) {
		super.onPostExecute(result);
		Entity monitor = new Monitor(ssServer);
		Message message = new Message();
		Bundle bundle = new Bundle();
		message.setData(bundle);
		this.handler.sendMessage(message);
	}
	
}
