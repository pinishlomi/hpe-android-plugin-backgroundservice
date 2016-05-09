package com.hpe.hybridsitescope.soap.request;

import android.util.Log;

import com.hpe.hybridsitescope.data.SiteScopeServer;
import com.hpe.hybridsitescope.soap.SoapCalls;
import com.hpe.hybridsitescope.soap.response.AbstractSoapResponse;
import com.hpe.hybridsitescope.tasks.AbstractTimedAsyncTask;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalHashtable;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.ksoap2.transport.HttpsTransportSE;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: mdv
 * Represents abstract soap request.
 * Subclass it and provide corresponding Response type implementation.
 * @param <T> - soap action return type
 */
public abstract class AbstractSoapRequest<T>  extends AbstractTimedAsyncTask<Void, Void, AbstractSoapResponse<T>> {
    public static final String TAG = AbstractSoapRequest.class.getSimpleName();

    protected final SiteScopeServer siteScopeServer;
    protected final String methodName;
    protected final Object[] requestParams;
    protected HttpTransportSE httpTransport;
    private final Class<? extends AbstractSoapResponse<T>> responseType;

    /**
     *
     * @param timeout - call will be force terminated after timeout expires. In milliseconds
     * @param responseType - corresponding response type implementation class
     * @param siteScopeServer -
     * @param methodName - soap action to invoke
     * @param requestParams - list of parameters to be passed to soap action
     */
    public AbstractSoapRequest(long timeout,
                               Class<? extends AbstractSoapResponse<T>> responseType,
                               SiteScopeServer siteScopeServer, String methodName, Object... requestParams) {
        super(timeout);
        this.siteScopeServer = siteScopeServer;
        this.methodName = methodName;
        this.requestParams = requestParams;
        this.responseType = responseType;

    }

    @Override
    protected AbstractSoapResponse<T> doInBackground(Void... voids) {

        //create soap object
        SoapObject request = new SoapObject(SoapCalls.NAMESPACE, methodName);

        boolean marshalHashtable = false;

        //populate it's request parameters
        for (int i = 0; i < requestParams.length; i++) {
            if(requestParams[i] instanceof Map) {
                marshalHashtable = true;
            }
            request.addProperty(SoapCalls.IN.concat(String.valueOf(i)), requestParams[i].equals("null")?null:requestParams[i]);
        }

        // put all required data into a soap envelope
        SoapSerializationEnvelope envelope  = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        if(marshalHashtable) {
            new MarshalHashtable().register(envelope);
            Log.d(TAG, "marshalHashtable = true. Registering in envelope");
        }
        //put request in envelope
        envelope.setOutputSoapObject(request);

        //enable

        //detect whether we want to establish secure connection

        if (siteScopeServer.getProtocol().toLowerCase().equals("http")) {
            Log.d(TAG, "Creating ksoap transport over HTTP");
            httpTransport = new HttpTransportSE(siteScopeServer.getUrl() + SoapCalls.APICONFIGURATIONIMPL_URL_SUFFIX);
        }
        else {
            Log.d(TAG, "Creating ksoap transport over HTTPS!");
            final int port = Integer.valueOf(siteScopeServer.getPort()).intValue();
            httpTransport = new HttpsTransportSE(siteScopeServer.getHost(), port, SoapCalls.APICONFIGURATIONIMPL_URL_SUFFIX, 60*1000);
        }


        Throwable throwable;
        String message;

        try {
            ArrayList<HeaderProperty> headerPropertyArrayList = new ArrayList<HeaderProperty>();
            headerPropertyArrayList.add(new HeaderProperty("Connection", "close"));
            httpTransport.call(SoapCalls.NAMESPACE.concat(methodName), envelope,headerPropertyArrayList);
            AbstractSoapResponse response = ((AbstractSoapResponse)responseType.newInstance()).setSoapResponse(envelope.getResponse()).setSiteScopeServer(siteScopeServer);
            return response;
        } catch (Exception e) { //we need to catch all Runtime exceptions!
            e.printStackTrace();
            throwable = e;
            message = e.getMessage();
            Log.e(TAG, String.format("Exception %s occurred in %s. Msg: %s", e.getCause(), responseType.getSimpleName(), message));
        }
        try {
            return ((AbstractSoapResponse)responseType.newInstance()).setSoapCallException(new SoapCallException(message, throwable));
        } catch (Exception e) { //we need to catch all Runtime exceptions!
            throwable = e;
            message = e.getMessage();
            Log.e(TAG, String.format("Exception %s occurred in %s. Msg: %s", e.getCause(), responseType.getSimpleName(), message));
            return null;
        }
    }

    protected RequestTaskEventAdapter<T> eventAdapter;

    public AbstractSoapRequest<T> setEventAdapter(RequestTaskEventAdapter<T> eventAdapter) {
        this.eventAdapter = eventAdapter;
        return this;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (eventAdapter != null) eventAdapter.onBeforeExecute();
    }

    @Override
    protected void onPostExecute(AbstractSoapResponse<T> tAbstractSoapResponse) {
        super.onPostExecute(tAbstractSoapResponse);
        try {
            if (eventAdapter != null)
                eventAdapter.onSuccess(tAbstractSoapResponse.getResult());

        } catch (SoapCallException e) {
            if (eventAdapter != null)
                eventAdapter.onError(e.getMessage(), e);
        } catch (Exception e2){
            if (eventAdapter != null)
                eventAdapter.onError(e2.getMessage(), e2);

        }
    }

    @Override
    protected void onTimeout() {

        if (eventAdapter != null) eventAdapter.onTimeout();
    }

    /**
     * Catches all standard AsyncTask callbacks and pushes corresponding events to caller activity.
     * To receive events in your activity just override desired callbacks
     * @param <T> - pass through soap action return type
     */
    public abstract static class RequestTaskEventAdapter<T> {

        /**
         * AsyncTask->onPostExecute if no exception occurred during execution
         * @param result
         */
        public void onSuccess(T result) {
            Log.d(TAG, "RequestTaskEventAdapter.onSuccess fired!");
        }

        /**
         * Added timeout behavior
         */
        public void onTimeout() {
            Log.d(TAG, "RequestTaskEventAdapter.onTimeout fired!");
        }

        /**
         * AsyncTask->onPreExecute
         */
        public void onBeforeExecute() {
            Log.d(TAG, "RequestTaskEventAdapter.onBeforeExecute fired!");
        }

        /**
         * If exception occurs during doInBackground execution we catch one and send onError event instead of
         * onPostExecution. Be aware that body of this callback is not invoked in UI thread! That's why we should wrap it
         * into runOnUiThread call for example
         * @param errorMessage
         * @param throwable
         */
        public void onError(String errorMessage, Throwable throwable) {
            Log.d(TAG, "RequestTaskEventAdapter.onError fired!");
        }

    }

}
