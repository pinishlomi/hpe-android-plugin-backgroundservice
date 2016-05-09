package android.rest.request;


import android.data.SiteScopeServer;

import android.rest.response.AbstractRestResponse;


/**
 * Created by IntelliJ IDEA.
 * User: mdv
 *
 *
 */

public class GetSiteScopeMonitoringStatusWithIdentifierRequest extends AbstractRestRequest<String> {
    public GetSiteScopeMonitoringStatusWithIdentifierRequest(
            long timeout,
            Class<? extends AbstractRestResponse<String>> responseType,
            SiteScopeServer siteScopeServer,
            Object... requestParams) {
        super(timeout, responseType, siteScopeServer, "getSiteScopeMonitoringStatusWithIdentifier", requestParams);
    }
}
