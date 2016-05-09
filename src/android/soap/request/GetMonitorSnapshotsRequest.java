package com.hpe.hybridsitescope.soap.request;


import com.hpe.hybridsitescope.data.Monitor;
import com.hpe.hybridsitescope.data.SiteScopeServer;
import com.hpe.hybridsitescope.soap.response.AbstractSoapResponse;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: mdv
 * Date: 19.04.11
 * Time: 18:52
 * To change this template use File | Settings | File Templates.
 */
public class GetMonitorSnapshotsRequest extends AbstractSoapRequest<List<Monitor>> {
    public GetMonitorSnapshotsRequest(long timeout, Class<? extends AbstractSoapResponse<List<Monitor>>> responseType, SiteScopeServer siteScopeServer, Object... requestParams) {
        super(timeout, responseType, siteScopeServer, "getMonitorSnapshots", requestParams);
    }
}
