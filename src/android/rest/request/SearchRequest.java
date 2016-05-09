package android.rest.request;


import android.data.SiteScopeServer;

import android.rest.response.AbstractRestResponse;

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
