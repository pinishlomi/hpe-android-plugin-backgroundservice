package com.hpe.hybridsitescope.soap.response;


import android.util.Log;

import com.hpe.hybridsitescope.soap.request.SoapCallException;

/**
 * Created by IntelliJ IDEA.
 * User: mdv
 * Date: 11.04.11
 * Time: 10:54
 * To change this template use File | Settings | File Templates.
 */
public class GetSiteScopeMonitoringStatusWithIdentifierResponse extends AbstractSoapResponse<String> {
    public static final String TAG = GetSiteScopeMonitoringStatusWithIdentifierResponse.class.getSimpleName();

    @Override
    public String getResult() throws SoapCallException {
        super.getResult();

        //process SoapObject response from server and retrieve the result
        Log.d(TAG, "Got Soap Response from server: " + soapResponse.toString());
        return soapResponse.toString();
    }

}
