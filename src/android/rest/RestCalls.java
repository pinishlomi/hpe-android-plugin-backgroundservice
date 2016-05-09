package android.rest;

import java.util.Hashtable;
import java.util.Vector;

import android.data.SiteScopeServer;

import org.json.JSONObject;

public class RestCalls {
	public static final String ANDROID_IDENTIFIER = "android";
	public static final String START_TIME = "start_time";
	public static final String END_TIME = "end_time";
	public static final String MONITOR_ENITYTYPE = "Monitor";
	public static final String GROUP_ENITYTYPE = "Group";

	public static final String search = "search";
	public static final String getGroupSnapshots = "getGroupSnapshots";
	public static final String getMonitorSnapshots = "getMonitorSnapshots";
	public static final String getSiteScopeMonitoringStatusWithIdentifier = "getSiteScopeMonitoringStatusWithIdentifier";
	public static final String NAMESPACE = "http://configuration.api.sitescope.mercury.com";
	//public static final String APICONFIGURATIONIMPL_URL_SUFFIX = "/SiteScope/services/APIConfigurationImpl?wsdl";

	public static String getSiteScopeMonitoringStatusWithIdentifier(String protocol, String host, String port,
			String username, String password, boolean allowUntrustedCerts, String identifier)
			throws Exception {
		
		String url = protocol + "://" + host + ":" + port;

		return url;
	}

	public static JSONObject getMonitorSnapshots(SiteScopeServer ssServer, String identifier, Vector<String> paths)
			throws Exception {
		JSONObject request = new JSONObject(); // set

		return request;
	}
	
	public static JSONObject getGroupSnapshots(SiteScopeServer ssServer, String identifier, Vector<String> paths)
			throws Exception {
		JSONObject request = new JSONObject(); // set

		return request;
	}




	public static JSONObject search(SiteScopeServer ssServer, String identifier,
			int maxNumOfResults, Hashtable<String, String> map) {
		JSONObject request = new JSONObject(); // set

		return request;
	}
}
