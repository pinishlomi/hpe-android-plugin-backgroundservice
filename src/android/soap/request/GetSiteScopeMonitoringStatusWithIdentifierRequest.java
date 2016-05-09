package com.hpe.hybridsitescope.soap.request;


import com.hpe.hybridsitescope.data.SiteScopeServer;
import com.hpe.hybridsitescope.soap.response.AbstractSoapResponse;


/**
 * Created by IntelliJ IDEA.
 * User: mdv
 *
 *
 */

public class GetSiteScopeMonitoringStatusWithIdentifierRequest extends AbstractSoapRequest<String> {
    public GetSiteScopeMonitoringStatusWithIdentifierRequest(
            long timeout,
            Class<? extends AbstractSoapResponse<String>> responseType,
            SiteScopeServer siteScopeServer,
            Object... requestParams) {
        super(timeout, responseType, siteScopeServer, "getSiteScopeMonitoringStatusWithIdentifier", requestParams);
    }
}
