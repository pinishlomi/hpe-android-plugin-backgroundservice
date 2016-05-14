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
			//tring webPage = "http://52.201.214.26:8080/SiteScope/api/monitors/snapshots?fullPathsToMonitors=memMonitors_sis_path_delimiter_mem";
			//String webPage = "http://52.201.214.26:8080/SiteScope/api/monitors/snapshots?fullPathsToMonitors=CpuMonitors_sis_path_delimiter_gr1_sis_path_delimiter_Memory for same on SiteScope Server";
			URL url = new URL(targetUrl);
			//Util.appendLog("webPage:\n" + targetUrl);
			URLConnection urlConnection = url.openConnection();
			urlConnection.setRequestProperty("Authorization", "Basic YWRtaW46YWRtaW4=");
			//Util.appendLog("Befoe urlConnection.getInputStream():\n");
			InputStream is = urlConnection.getInputStream();
			//Util.appendLog("Befoe InputStreamReader(is):\n");
			InputStreamReader isr = new InputStreamReader(is);
			//Util.appendLog("After InputStreamReader(is):\n");
			int numCharsRead;
			char[] charArray = new char[1024];
			StringBuffer sb = new StringBuffer();
			while ((numCharsRead = isr.read(charArray)) > 0) {
				sb.append(charArray, 0, numCharsRead);
			}
			String result = sb.toString();
			//Util.appendLog("result:\n" + result);
			jObj = new JSONObject(result);
		} catch (MalformedURLException e) {
			jObj.put("ErrorMsg from MalformedURLException : ", e.getMessage());
		} catch (IOException e) {
			jObj.put("ErrorMsg from IOException : ",e.getMessage());
		}
		return jObj;
	}
}
