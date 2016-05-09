package com.hpe.hybridsitescope.soap.response;


import android.util.Log;

import com.hpe.hybridsitescope.soap.request.SoapCallException;

import java.util.Hashtable;

/**
 * Created by IntelliJ IDEA.
 * User: mdv
 * To change this template use File | Settings | File Templates.
 */
public class SearchResponse extends AbstractSoapResponse<Hashtable> {
    public static final String TAG = SearchResponse.class.getSimpleName();

    @Override
    public Hashtable getResult() throws SoapCallException {
        super.getResult();
        Hashtable searchResults = (Hashtable)soapResponse;
        Log.d(TAG, "Got a response: " + searchResults);

        return searchResults;
    }
}
