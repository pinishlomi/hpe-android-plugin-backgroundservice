package android.rest.response;


import android.util.Log;
import org.json.JSONObject;

public class GetMonitorSnapshotsResponse extends AbstractRestResponse<JSONObject> {
    public static final String TAG = GetMonitorSnapshotsResponse.class.getSimpleName();

    @Override
    public JSONObject getResult() throws Exception {
        super.getResult();

        JSONObject groups = (JSONObject) restResponse;
        Log.d(TAG, "Got a response: " + groups.toString());
        return groups;
    }
}
