package com.hpe.hybridsitescope.soap.request;


import com.hpe.hybridsitescope.data.Group;
import com.hpe.hybridsitescope.data.SiteScopeServer;
import com.hpe.hybridsitescope.soap.response.AbstractSoapResponse;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: mdv
 * Date: 21.04.11
 * Time: 18:51
 * To change this template use File | Settings | File Templates.
 */
public class GetGroupSnapshotsRequest extends AbstractSoapRequest<List<Group>>{
    public GetGroupSnapshotsRequest(long timeout, Class<? extends AbstractSoapResponse<List<Group>>> responseType, SiteScopeServer siteScopeServer, Object... requestParams) {
        super(timeout, responseType, siteScopeServer, "getGroupSnapshots", requestParams);
    }
}
