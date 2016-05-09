package com.hpe.android.plugin.backgroundservice.rest.request;


import com.hpe.android.plugin.backgroundservice.data.Monitor;
import com.hpe.android.plugin.backgroundservice.data.SiteScopeServer;
import com.hpe.android.plugin.backgroundservice.rest.response.AbstractRestResponse;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: mdv
 * Date: 19.04.11
 * Time: 18:52
 * To change this template use File | Settings | File Templates.
 */
public class GetMonitorSnapshotsRequest extends AbstractRestRequest<List<Monitor>> {
    public GetMonitorSnapshotsRequest(long timeout, Class<? extends AbstractRestResponse<List<Monitor>>> responseType, SiteScopeServer siteScopeServer, Object... requestParams) {
        super(timeout, responseType, siteScopeServer, "getMonitorSnapshots", requestParams);
    }
}
