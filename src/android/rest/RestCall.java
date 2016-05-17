package com.hpe.android.plugin.backgroundservice.rest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 *
 */
public class RestCall  {

	public  static JSONObject getJSONFromUrl(String targetUrl) throws JSONException {
		JSONObject jObj = new JSONObject();
		try {
			URL url = new URL(targetUrl);
			URLConnection urlConnection = url.openConnection();
			urlConnection.setRequestProperty("Authorization", "Basic YWRtaW46YWRtaW4=");
			InputStream is = urlConnection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			int numCharsRead;
			char[] charArray = new char[1024];
			StringBuffer sb = new StringBuffer();
			while ((numCharsRead = isr.read(charArray)) > 0) {
				sb.append(charArray, 0, numCharsRead);
			}
			String result = sb.toString();
			jObj = new JSONObject(result);
		} catch (MalformedURLException e) {
			jObj.put("ErrorMsg from MalformedURLException : ", e.getMessage());
		} catch (IOException e) {
			jObj.put("ErrorMsg from IOException : ",e.getMessage());
		}
		return jObj;
	}
}
