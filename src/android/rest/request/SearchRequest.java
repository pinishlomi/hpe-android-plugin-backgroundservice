package com.hpe.android.plugin.backgroundservice.rest.request;



import com.hpe.android.plugin.backgroundservice.data.SiteScopeServer;
import com.hpe.android.plugin.backgroundservice.rest.response.AbstractRestResponse;

import java.util.Hashtable;

/**
 * Created by IntelliJ IDEA.
 * User: mdv
 * To change this template use File | Settings | File Templates.
 */
public class SearchRequest extends AbstractRestRequest<Hashtable> {
    public SearchRequest(long timeout, Class<? extends AbstractRestResponse<Hashtable>> responseType, SiteScopeServer siteScopeServer, Object... requestParams) {
        super(timeout, responseType, siteScopeServer, "search", requestParams);
    }
}
