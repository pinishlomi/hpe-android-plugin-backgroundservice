package com.hpe.hybridsitescope.soap.request;


import com.hpe.hybridsitescope.data.SiteScopeServer;
import com.hpe.hybridsitescope.soap.response.AbstractSoapResponse;

import java.util.Hashtable;

/**
 * Created by IntelliJ IDEA.
 * User: mdv
 * To change this template use File | Settings | File Templates.
 */
public class SearchRequest extends AbstractSoapRequest<Hashtable> {
    public SearchRequest(long timeout, Class<? extends AbstractSoapResponse<Hashtable>> responseType, SiteScopeServer siteScopeServer, Object... requestParams) {
        super(timeout, responseType, siteScopeServer, "search", requestParams);
    }
}
