package android.rest.response;


import android.util.Log;

import org.json.JSONObject;
public class SearchResponse extends AbstractRestResponse<JSONObject> {
    public static final String TAG = SearchResponse.class.getSimpleName();

    @Override
    public JSONObject getResult() throws Exception {
        super.getResult();
        JSONObject searchResults = (JSONObject) restResponse;
        Log.d(TAG, "Got a response: " + searchResults);

        return searchResults;
    }
}
