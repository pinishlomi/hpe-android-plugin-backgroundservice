package android.rest.response;


import android.util.Log;

/**
 * Created by IntelliJ IDEA.
 * User: mdv
 * Date: 11.04.11
 * Time: 10:54
 * To change this template use File | Settings | File Templates.
 */
public class GetSiteScopeMonitoringStatusWithIdentifierResponse extends AbstractRestResponse<String> {
    public static final String TAG = GetSiteScopeMonitoringStatusWithIdentifierResponse.class.getSimpleName();

    @Override
    public String getResult() throws Exception {
        super.getResult();

        //process SoapObject response from server and retrieve the result
        Log.d(TAG, "Got Soap Response from server: " + restResponse.toString());
        return restResponse.toString();
    }

}
