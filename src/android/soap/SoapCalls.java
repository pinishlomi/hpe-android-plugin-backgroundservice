package com.hpe.hybridsitescope.soap;

import com.hpe.hybridsitescope.data.SiteScopeServer;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalHashtable;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;

public class SoapCalls {
	public static final String ANDROID_IDENTIFIER = "android";
	public static final String IN = "in";
	public static final String IN0 = "in0";
	public static final String IN1 = "in1";
	public static final String IN2 = "in2";
	public static final String IN3 = "in3";
	public static final String IN4 = "in4";
	public static final String IN5 = "in5";
	public static final String IN6 = "in6";
	public static final String IN7 = "in7";
	public static final String START_TIME = "start_time";
	public static final String END_TIME = "end_time";
	public static final String MONITOR_ENITYTYPE = "Monitor";
	public static final String GROUP_ENITYTYPE = "Group";

	public static final String search = "search";
	public static final String getAlertReport = "getAlertReport";
	public static final String getQuickReport = "getQuickReport";
	public static final String disableAssociatedAlerts = "disableAssociatedAlerts";
	public static final String enableAssociatedAlerts = "enableAssociatedAlerts";
	public static final String addAcknowledgment = "addAcknowledgment";
	public static final String getAcknowledgments = "getAcknowledgments";
	public static final String disableMonitorWithDescription = "disableMonitorWithDescription";
	public static final String enableMonitorWithDescription = "enableMonitorWithDescription";
	public static final String runExistingMonitorExWithIdentifier = "runExistingMonitorExWithIdentifier";
	public static final String disableGroupWithDescription = "disableGroupWithDescription";
	public static final String enableGroupWithDescription = "enableGroupWithDescription";
	public static final String runExistingMonitorsInGroup = "runExistingMonitorsInGroup";
	public static final String getGroupSnapshots = "getGroupSnapshots";	
	public static final String getMonitorSnapshots = "getMonitorSnapshots";
	public static final String getSiteScopeMonitoringStatusWithIdentifier = "getSiteScopeMonitoringStatusWithIdentifier";
	public static final String NAMESPACE = "http://configuration.api.sitescope.mercury.com";
	public static final String APICONFIGURATIONIMPL_URL_SUFFIX = "/SiteScope/services/APIConfigurationImpl?wsdl";

	public static String getSiteScopeMonitoringStatusWithIdentifier(String protocol, String host, String port,
			String username, String password, boolean allowUntrustedCerts, String identifier)
			throws Exception {
		
		String url = protocol + "://" + host + ":" + port;
		
		SoapObject request = new SoapObject(NAMESPACE, getSiteScopeMonitoringStatusWithIdentifier); // set up request
		request.addProperty(IN0, username);
		request.addProperty(IN1, password);
		request.addProperty(IN2, identifier);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11); // put all required data into a soap
										// envelope
		envelope.setOutputSoapObject(request); // prepare request
       
		HttpTransportSE httpTransport = new HttpTransportSE(url + APICONFIGURATIONIMPL_URL_SUFFIX);
		httpTransport.call(NAMESPACE .concat(getSiteScopeMonitoringStatusWithIdentifier), envelope);

		return envelope.getResponse().toString();
	}

	public static SoapObject getMonitorSnapshots(SiteScopeServer ssServer, String identifier, Vector<String> paths)
			throws Exception {
		SoapObject request = new SoapObject(NAMESPACE, getMonitorSnapshots); // set
		// up
		// request
		request.addProperty(IN0, paths);
		request.addProperty(IN1, null);
		request.addProperty(IN2, ssServer.getUsername());
		request.addProperty(IN3, ssServer.getPassword());
		request.addProperty(IN4, identifier);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11); // put all required data into a soap
		// envelope
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(ssServer.getUrl()+ APICONFIGURATIONIMPL_URL_SUFFIX);
		httpTransport.call(NAMESPACE.concat(getMonitorSnapshots), envelope);
		// TODO: how to handle no response from server, connection timeout
		// httpTransport.wait(10000);

		return (SoapObject) envelope.getResponse();
	}
	
	public static SoapObject getGroupSnapshots(SiteScopeServer ssServer, String identifier, Vector<String> paths)
			throws Exception {
		SoapObject response = null;
		SoapObject request = new SoapObject(NAMESPACE, getGroupSnapshots); // set
		// up
		// request
		request.addProperty(IN0, paths);
		request.addProperty(IN1, null);
		request.addProperty(IN2, ssServer.getUsername());
		request.addProperty(IN3, ssServer.getPassword());
		request.addProperty(IN4, identifier);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11); // put all required data into a soap
		// envelope
		envelope.setOutputSoapObject(request); // prepare request


		HttpTransportSE httpTransport = new HttpTransportSE(ssServer.getUrl() + APICONFIGURATIONIMPL_URL_SUFFIX);

		httpTransport.call(NAMESPACE.concat(getGroupSnapshots), envelope);
		// TODO: how to handle no response from server, connection timeout
		// httpTransport.wait(10000);

		response = (SoapObject) envelope.getResponse();

		return response;
	}

	public static SoapObject runExistingMonitorExWithIdentifier(SiteScopeServer ssServer, String identifier,
			Vector<String> paths) throws IOException, XmlPullParserException {
		SoapObject response = null;
		SoapObject request = new SoapObject(NAMESPACE,
				runExistingMonitorExWithIdentifier); // set up request
		request.addProperty(IN0, paths);
		request.addProperty(IN1, Long.valueOf(5000));
		request.addProperty(IN2, ssServer.getUsername());
		request.addProperty(IN3, ssServer.getPassword());
		request.addProperty(IN4, identifier);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11); // put all required data into a soap
										// envelope
		envelope.setOutputSoapObject(request); // prepare request

		HttpTransportSE httpTransport = new HttpTransportSE(ssServer.getUrl() + APICONFIGURATIONIMPL_URL_SUFFIX);
		httpTransport.call(NAMESPACE
					.concat(runExistingMonitorExWithIdentifier), envelope);
			// TODO: how to handle no response from server, connection timeout
			// httpTransport.wait(10000);
			response = (SoapObject) envelope.getResponse();		

		return response;
	}
	
	public static SoapObject runExistingMonitorsInGroup(SiteScopeServer ssServer, String identifier,
			Vector<String> paths) {
		SoapObject response = null;
		SoapObject request = new SoapObject(NAMESPACE,
				runExistingMonitorsInGroup); // set up request
		request.addProperty(IN0, paths);
		request.addProperty(IN1, true);
		request.addProperty(IN2, ssServer.getUsername());
		request.addProperty(IN3, ssServer.getPassword());
		request.addProperty(IN4, identifier);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11); // put all required data into a soap
										// envelope
		envelope.setOutputSoapObject(request); // prepare request
		

		HttpTransportSE httpTransport = new HttpTransportSE(ssServer.getUrl() + APICONFIGURATIONIMPL_URL_SUFFIX);
		try {
			httpTransport.call(NAMESPACE
					.concat(runExistingMonitorsInGroup), envelope);
			// TODO: how to handle no response from server, connection timeout
			// httpTransport.wait(10000);
			response = (SoapObject) envelope.getResponse();
		} catch (Exception e) {
			response = null;
		}

		return response;
	}
	
	public static SoapObject enableMonitorWithDescription(SiteScopeServer ssServer, String identifier,
			Vector<String> paths, String disableDescription) {
		SoapObject response = null;
		SoapObject request = new SoapObject(NAMESPACE,
				enableMonitorWithDescription); // set up request
		request.addProperty(IN0, paths);
		request.addProperty(IN1, disableDescription);
		request.addProperty(IN2, ssServer.getUsername());
		request.addProperty(IN3, ssServer.getPassword());
		request.addProperty(IN4, identifier);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11); // put all required data into a soap
										// envelope
		envelope.setOutputSoapObject(request); // prepare request


		HttpTransportSE httpTransport = new HttpTransportSE(ssServer.getUrl() + APICONFIGURATIONIMPL_URL_SUFFIX);
		try {
			httpTransport.call(NAMESPACE
					.concat(enableMonitorWithDescription), envelope);
			// TODO: how to handle no response from server, connection timeout
			// httpTransport.wait(10000);
			response = (SoapObject) envelope.getResponse();
		} catch (Exception e) {
			response = null;
		}

		return response;
	}
	
	public static SoapObject enableGroupWithDescription(SiteScopeServer ssServer, String identifier,
			Vector<String> paths, String disableDescription) {
		SoapObject response = null;
		SoapObject request = new SoapObject(NAMESPACE,
				enableGroupWithDescription); // set up request
		request.addProperty(IN0, paths);
		request.addProperty(IN1, disableDescription);
		request.addProperty(IN2, ssServer.getUsername());
		request.addProperty(IN3, ssServer.getPassword());
		request.addProperty(IN4, identifier);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11); // put all required data into a soap
										// envelope
		envelope.setOutputSoapObject(request); // prepare request


		HttpTransportSE httpTransport = new HttpTransportSE(ssServer.getUrl() + APICONFIGURATIONIMPL_URL_SUFFIX);
		try {
			httpTransport.call(NAMESPACE
					.concat(enableGroupWithDescription), envelope);
			// TODO: how to handle no response from server, connection timeout
			// httpTransport.wait(10000);
			response = (SoapObject) envelope.getResponse();
		} catch (Exception e) {
			response = null;
		}

		return response;
	}
	
	public static SoapObject disableMonitorWithDescription(SiteScopeServer ssServer, String identifier,
			Vector<String> paths, String startTime, String endTime, String disableDescription) {
		SoapObject response = null;
		SoapObject request = new SoapObject(NAMESPACE,
				disableMonitorWithDescription); // set up request
		request.addProperty(IN0, paths);
		request.addProperty(IN1, startTime);
		request.addProperty(IN2, endTime);
		request.addProperty(IN3, disableDescription);
		request.addProperty(IN4, ssServer.getUsername());
		request.addProperty(IN5, ssServer.getPassword());
		request.addProperty(IN6, identifier);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11); // put all required data into a soap
										// envelope
		envelope.setOutputSoapObject(request); // prepare request


		HttpTransportSE httpTransport = new HttpTransportSE(ssServer.getUrl() + APICONFIGURATIONIMPL_URL_SUFFIX);
		try {
			httpTransport.call(NAMESPACE
					.concat(disableMonitorWithDescription), envelope);
			// TODO: how to handle no response from server, connection timeout
			// httpTransport.wait(10000);
			response = (SoapObject) envelope.getResponse();
		} catch (Exception e) {
			response = null;
		}

		return response;
	}
	
	public static SoapObject disableGroupWithDescription(SiteScopeServer ssServer, String identifier,
			Vector<String> paths, String startTime, String endTime, String disableDescription) {
		SoapObject response = null;
		SoapObject request = new SoapObject(NAMESPACE,
				disableGroupWithDescription); // set up request
		request.addProperty(IN0, paths);
		request.addProperty(IN1, startTime);
		request.addProperty(IN2, endTime);
		request.addProperty(IN3, disableDescription);
		request.addProperty(IN4, ssServer.getUsername());
		request.addProperty(IN5, ssServer.getPassword());
		request.addProperty(IN6, identifier);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11); // put all required data into a soap
										// envelope
		envelope.setOutputSoapObject(request); // prepare request


		HttpTransportSE httpTransport = new HttpTransportSE(ssServer.getUrl() + APICONFIGURATIONIMPL_URL_SUFFIX);
		try {
			httpTransport.call(NAMESPACE
					.concat(disableGroupWithDescription), envelope);
			// TODO: how to handle no response from server, connection timeout
			// httpTransport.wait(10000);
			response = (SoapObject) envelope.getResponse();
		} catch (Exception e) {
			response = null;
		}

		return response;
	}
	
	public static SoapObject enableAssociatedAlerts(SiteScopeServer ssServer, String identifier,
			Vector<String> paths, String disableDescription) {
		SoapObject response = null;
		SoapObject request = new SoapObject(NAMESPACE, enableAssociatedAlerts); // set up request
		request.addProperty(IN0, paths);
		request.addProperty(IN1, disableDescription);
		request.addProperty(IN2, ssServer.getUsername());
		request.addProperty(IN3, ssServer.getPassword());
		request.addProperty(IN4, identifier);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); // put all required data into a soap
										// envelope
		envelope.setOutputSoapObject(request); // prepare request


		HttpTransportSE httpTransport = new HttpTransportSE(ssServer.getUrl() + APICONFIGURATIONIMPL_URL_SUFFIX);
		try {
			httpTransport.call(NAMESPACE
					.concat(enableAssociatedAlerts), envelope);
			// TODO: how to handle no response from server, connection timeout
			// httpTransport.wait(10000);
			response = (SoapObject) envelope.getResponse();
		} catch (Exception e) {
			response = null;
		}

		return response;
	}	
	
	
	public static SoapObject disableAssociatedAlerts(SiteScopeServer ssServer, String identifier,
			Vector<String> paths, String startTime, String endTime, String disableDescription) {
		SoapObject response = null;
		SoapObject request = new SoapObject(NAMESPACE,
				disableAssociatedAlerts); // set up request
		request.addProperty(IN0, paths);
		request.addProperty(IN1, startTime);
		request.addProperty(IN2, endTime);
		request.addProperty(IN3, disableDescription);
		request.addProperty(IN4, ssServer.getUsername());
		request.addProperty(IN5, ssServer.getPassword());
		request.addProperty(IN6, identifier);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11); // put all required data into a soap
										// envelope
		envelope.setOutputSoapObject(request); // prepare request


		HttpTransportSE httpTransport = new HttpTransportSE(ssServer.getUrl() + APICONFIGURATIONIMPL_URL_SUFFIX);
		try {
			httpTransport.call(NAMESPACE
					.concat(disableAssociatedAlerts), envelope);
			// TODO: how to handle no response from server, connection timeout
			// httpTransport.wait(10000);
			response = (SoapObject) envelope.getResponse();
		} catch (Exception e) {
			response = null;
		}

		return response;
	}
	
	public static SoapObject addAcknowledgment(SiteScopeServer ssServer, String identifier,
			Vector<String> paths, String startTime, String endTime, String disableDescription, String acknowledgmentComment) {
		SoapObject response = null;
		SoapObject request = new SoapObject(NAMESPACE,
				addAcknowledgment); // set up request
		request.addProperty(IN0, paths);
		request.addProperty(IN1, acknowledgmentComment);
		request.addProperty(IN2, startTime);
		request.addProperty(IN3, endTime);
		request.addProperty(IN4, disableDescription);
		request.addProperty(IN5, ssServer.getUsername());
		request.addProperty(IN6, ssServer.getPassword());
		request.addProperty(IN7, identifier);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11); // put all required data into a soap
										// envelope
		envelope.setOutputSoapObject(request); // prepare request


		HttpTransportSE httpTransport = new HttpTransportSE(ssServer.getUrl() + APICONFIGURATIONIMPL_URL_SUFFIX);
		try {
			httpTransport.call(NAMESPACE
					.concat(addAcknowledgment), envelope);
			// TODO: how to handle no response from server, connection timeout
			// httpTransport.wait(10000);
			response = (SoapObject) envelope.getResponse();
		} catch (Exception e) {
			response = null;
		}

		return response;
	}
	
	public static Vector<SoapObject> getAcknowledgments(SiteScopeServer ssServer, String identifier,
			Vector<String> paths) {
		Vector<SoapObject> response = null;
		SoapObject request = new SoapObject(NAMESPACE, getAcknowledgments); // set up request
		request.addProperty(IN0, paths);
		request.addProperty(IN1, ssServer.getUsername());
		request.addProperty(IN2, ssServer.getPassword());
		request.addProperty(IN3, identifier);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11); // put all required data into a soap
										// envelope
		envelope.setOutputSoapObject(request); // prepare request


		HttpTransportSE httpTransport = new HttpTransportSE(ssServer.getUrl() + APICONFIGURATIONIMPL_URL_SUFFIX);
		try {
			httpTransport.call(NAMESPACE
					.concat(getAcknowledgments), envelope);
			// TODO: how to handle no response from server, connection timeout
			// httpTransport.wait(10000);
			response = (Vector<SoapObject>) envelope.getResponse();
		} catch (Exception e) {
			response = null;
		}

		return response;
	}
	
	public static String getQuickReport(SiteScopeServer ssServer, String identifier,
			Vector<String> paths, Hashtable<String, String> map) {
		String response = null;
		SoapObject request = new SoapObject(NAMESPACE, getQuickReport); // set up request	

		request.addProperty(IN0, paths);
		request.addProperty(IN1, map);
		request.addProperty(IN2, ssServer.getUsername());
		request.addProperty(IN3, ssServer.getPassword());
		request.addProperty(IN4, identifier);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11); // put all required data into a soap
										// envelope
		MarshalHashtable mh = new MarshalHashtable();
		mh.register(envelope);
		envelope.setOutputSoapObject(request); // prepare request


		HttpTransportSE httpTransport = new HttpTransportSE(ssServer.getUrl()+ APICONFIGURATIONIMPL_URL_SUFFIX);
		try {
			httpTransport.call(NAMESPACE
					.concat(getQuickReport), envelope);
			// TODO: how to handle no response from server, connection timeout
			// httpTransport.wait(10000);
			response = (String) envelope.getResponse();
		} catch (Exception e) {
			response = null;
		}

		return response;
	}
	
	public static String getAlertReport(SiteScopeServer ssServer, String identifier,
			Vector<String> paths, Hashtable<String, String> map) {
		String response = null;
		SoapObject request = new SoapObject(NAMESPACE, getAlertReport); // set up request	

		request.addProperty(IN0, paths);
		request.addProperty(IN1, map);
		request.addProperty(IN2, ssServer.getUsername());
		request.addProperty(IN3, ssServer.getPassword());
		request.addProperty(IN4, identifier);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11); // put all required data into a soap
										// envelope
		MarshalHashtable mh = new MarshalHashtable();
		mh.register(envelope);
		envelope.setOutputSoapObject(request); // prepare request


		HttpTransportSE httpTransport = new HttpTransportSE(ssServer.getUrl() + APICONFIGURATIONIMPL_URL_SUFFIX);
		try {
			httpTransport.call(NAMESPACE
					.concat(getAlertReport), envelope);
			// TODO: how to handle no response from server, connection timeout
			// httpTransport.wait(10000);
			response = (String) envelope.getResponse();
		} catch (Exception e) {
			response = null;
		}

		return response;
	}
	
	public static Hashtable search(SiteScopeServer ssServer, String identifier,
			int maxNumOfResults, Hashtable<String, String> map) {
		Hashtable response = null;
		SoapObject request = new SoapObject(NAMESPACE, search); // set up request	

		request.addProperty(IN0, map);
		request.addProperty(IN1, maxNumOfResults);
		request.addProperty(IN2, ssServer.getUsername());
		request.addProperty(IN3, ssServer.getPassword());
		request.addProperty(IN4, identifier);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11); // put all required data into a soap
										// envelope
		MarshalHashtable mh = new MarshalHashtable();
		mh.register(envelope);
		envelope.setOutputSoapObject(request); // prepare request
		

		HttpTransportSE httpTransport = new HttpTransportSE(ssServer.getUrl() + APICONFIGURATIONIMPL_URL_SUFFIX);
		try {
			httpTransport.call(NAMESPACE
					.concat(search), envelope);
			// TODO: how to handle no response from server, connection timeout
			// httpTransport.wait(10000);
			response = (Hashtable) envelope.getResponse();
		} catch (Exception e) {
			response = null;
		}

		return response;
	}
}
