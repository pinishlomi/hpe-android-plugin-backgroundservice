package com.hpe.android.plugin.backgroundservice.rest.request;


import com.hpe.android.plugin.backgroundservice.data.SiteScopeServer;
import com.hpe.android.plugin.backgroundservice.rest.response.AbstractRestResponse;

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
