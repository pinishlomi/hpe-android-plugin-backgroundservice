package com.hpe.android.plugin.backgroundservice.rest.request;



import com.hpe.android.plugin.backgroundservice.data.Group;
import com.hpe.android.plugin.backgroundservice.data.SiteScopeServer;
import com.hpe.android.plugin.backgroundservice.rest.response.AbstractRestResponse;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: mdv
 * Date: 21.04.11
 * Time: 18:51
 * To change this template use File | Settings | File Templates.
 */
public class GetGroupSnapshotsRequest extends AbstractRestRequest<List<Group>> {
    public GetGroupSnapshotsRequest(long timeout, Class<? extends AbstractRestResponse<List<Group>>> responseType, SiteScopeServer siteScopeServer, Object... requestParams) {
        super(timeout, responseType, siteScopeServer, "getGroupSnapshots", requestParams);
    }
}
