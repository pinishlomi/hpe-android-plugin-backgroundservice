package com.hpe.hybridsitescope.soap.response;


import android.util.Log;

import com.hpe.hybridsitescope.data.SiteScopeServer;
import com.hpe.hybridsitescope.soap.request.SoapCallException;
import com.hpe.hybridsitescope.utils.SiteScopeError;

/**
 * Created by IntelliJ IDEA.
 * User: mdv
 * Represents abstract soap response.
 * You should overcom.hp.sitescope.mobile.android_appe.mobile.android.soap.response.AbstractSoapResponse#getResult())
 * to parse SOAP response and extract return value
 */
public abstract class AbstractSoapResponse <T> {
    public static final String TAG = AbstractSoapResponse.class.getSimpleName();
    private static final String INSUFFICIENT_PERMISSIONS_ERROR = "User is not authorized to perform requested operation";

    protected Object soapResponse;
    private SoapCallException soapCallException;
    protected SiteScopeServer siteScopeServer;

    public final AbstractSoapResponse <T> setSiteScopeServer(SiteScopeServer siteScopeServer) {
        this.siteScopeServer = siteScopeServer;
        return this;
    }

    public final AbstractSoapResponse <T> setSoapResponse(Object soapResponse) {
        this.soapResponse = soapResponse;
        return this;
    }

    public final AbstractSoapResponse <T> setSoapCallException(SoapCallException soapCallException) {
        this.soapCallException = soapCallException;
        return this;
    }

    public T getResult() throws SoapCallException, SoapCallException {
        if (soapCallException != null) {
            checkForInsufficientUserPrivileges();
            throw soapCallException;
        }
        return null;
    }

    private void checkForInsufficientUserPrivileges() {
        if (soapCallException != null) {
            Log.d(TAG, "post processing SoapCallException. Verify if user is not allowed to perform specific action");
            String errorMessage = soapCallException.getMessage();
            if (errorMessage != null && errorMessage.contains(INSUFFICIENT_PERMISSIONS_ERROR)) {
                //substituting exception message with localized translation
                this.soapCallException = new SoapCallException(SiteScopeError.getErrorMsg(SiteScopeError.CODE_55524), soapCallException.getCause());
            }
        }
    }

}
