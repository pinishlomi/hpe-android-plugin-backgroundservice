package com.hpe.android.plugin.backgroundservice.rest.request;

import android.util.Log;

import com.hpe.android.plugin.backgroundservice.data.SiteScopeServer;
import com.hpe.android.plugin.backgroundservice.rest.response.AbstractRestResponse;
import com.hpe.android.plugin.backgroundservice.tasks.AbstractTimedAsyncTask;

/**
 * Created by IntelliJ IDEA.
 * User: mdv
 * Represents abstract soap request.
 * Subclass it and provide corresponding Response type implementation.
 * @param <T> - soap action return type
 */
public abstract class AbstractRestRequest<T>  extends AbstractTimedAsyncTask<Void, Void, AbstractRestResponse<T>> {
    public static final String TAG = AbstractRestRequest.class.getSimpleName();

    protected final SiteScopeServer siteScopeServer;
    protected final String methodName;
    protected final Object[] requestParams;
    //protected HttpTransportSE httpTransport;
    private final Class<? extends AbstractRestResponse<T>> responseType;

    /**
     *
     * @param timeout - call will be force terminated after timeout expires. In milliseconds
     * @param responseType - corresponding response type implementation class
     * @param siteScopeServer -
     * @param methodName - soap action to invoke
     * @param requestParams - list of parameters to be passed to soap action
     */
    public AbstractRestRequest(long timeout,
                               Class<? extends AbstractRestResponse<T>> responseType,
                               SiteScopeServer siteScopeServer, String methodName, Object... requestParams) {
        super(timeout);
        this.siteScopeServer = siteScopeServer;
        this.methodName = methodName;
        this.requestParams = requestParams;
        this.responseType = responseType;

    }

    @Override
    protected AbstractRestResponse<T> doInBackground(Void... voids) {
        return null;
    }

    protected RequestTaskEventAdapter<T> eventAdapter;

    public AbstractRestRequest<T> setEventAdapter(RequestTaskEventAdapter<T> eventAdapter) {
        this.eventAdapter = eventAdapter;
        return this;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (eventAdapter != null) eventAdapter.onBeforeExecute();
    }

    @Override
    protected void onPostExecute(AbstractRestResponse<T> tAbstractSoapResponse) {
        super.onPostExecute(tAbstractSoapResponse);
        try {
            if (eventAdapter != null)
                eventAdapter.onSuccess(tAbstractSoapResponse.getResult());

        } catch (Exception e){
            if (eventAdapter != null)
                eventAdapter.onError(e.getMessage(), e);

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
