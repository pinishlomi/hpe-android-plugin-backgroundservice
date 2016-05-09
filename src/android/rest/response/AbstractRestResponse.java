package android.rest.response;


import android.util.Log;

import android.data.SiteScopeServer;
import android.utils.SiteScopeError;

/**
 * Created by IntelliJ IDEA.
 * User: mdv
 * Represents abstract soap response.
 * You should overcom.hp.sitescope.mobile.android_appe.mobile.android.soap.response.AbstractSoapResponse#getResult())
 * to parse SOAP response and extract return value
 */
public abstract class AbstractRestResponse<T> {
    public static final String TAG = AbstractRestResponse.class.getSimpleName();
    private static final String INSUFFICIENT_PERMISSIONS_ERROR = "User is not authorized to perform requested operation";
    private Exception restCallException;
    protected Object restResponse;
     protected SiteScopeServer siteScopeServer;

    public final AbstractRestResponse<T> setSiteScopeServer(SiteScopeServer siteScopeServer) {
        this.siteScopeServer = siteScopeServer;
        return this;
    }

    public final AbstractRestResponse<T> setRestResponse(Object restResponse) {
        this.restResponse = restResponse;
        return this;
    }

    public final AbstractRestResponse<T> setRestCallException(Exception restCallException) {
        this.restCallException = restCallException;
        return this;
    }

    public T getResult() throws Exception {
        if (restCallException != null) {
            checkForInsufficientUserPrivileges();
            throw restCallException;
        }
        return null;
    }

    private void checkForInsufficientUserPrivileges() {
        if (restCallException != null) {
            Log.d(TAG, "post processing SoapCallException. Verify if user is not allowed to perform specific action");
            String errorMessage = restCallException.getMessage();
            if (errorMessage != null && errorMessage.contains(INSUFFICIENT_PERMISSIONS_ERROR)) {
                //substituting exception message with localized translation
                this.restCallException = new Exception(SiteScopeError.getErrorMsg(SiteScopeError.CODE_55524), restCallException.getCause());
            }
        }
    }

}
