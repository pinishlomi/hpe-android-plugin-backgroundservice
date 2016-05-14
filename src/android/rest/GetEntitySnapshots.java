package com.hpe.android.plugin.backgroundservice.rest;


import java.util.List;
import com.hpe.android.plugin.backgroundservice.utils.Util;
import com.hpe.android.plugin.backgroundservice.db.AccountInfoDbAdapterImpl;
import com.hpe.android.plugin.backgroundservice.data.Entity;
import com.hpe.android.plugin.backgroundservice.rest.RestCall;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

/*
   * Async Task to make http call
   */
public class GetEntitySnapshots extends AsyncTask<Void, Void, Void> {
    private String url;
    private List<Entity> entities;
    private Context context;
    private AccountInfoDbAdapterImpl mDbHelper;

    public GetEntitySnapshots(String url,List<Entity> entities, Context context) {
        this.url = url;
        this.entities = entities;
        this.context =  context ;
        mDbHelper = new AccountInfoDbAdapterImpl(context);

    }

    @Override
    protected Void doInBackground(Void... args) {
        try {
            //Util.appendLog(url);
            JSONObject json = RestCall.getJSONFromUrl(url);
            //Util.appendLog(json.toString());
            for(Entity entity : entities){
                //Util.appendLog("i = "+i+" paths.length: " + paths.length + " paths[i]: "+ paths[i]);
                JSONObject entityData  = (JSONObject) json.get(entity.getFullPath());
                //Util.appendLog("i = "+i+" " + entityData.toString());
                JSONObject runtime_snapshot  = (JSONObject) entityData.get("runtime_snapshot");
                String status = (String) runtime_snapshot.get("status");
                //Util.appendLog( entity.getParent_id() + " last status: " + entity.getStatus()+ "  new status: " + status);
                if(!status.equals(entity.getStatus())){
                    // status change
                    handleStatusChange(context,entity,status);
                }
            }
        } catch (JSONException e) {
            Util.appendLog("Error in doInBackground : " + e.getMessage());
        }
        return null;
    }


    private void handleStatusChange(Context context, Entity entity, String status) {
        Util.appendLog("handleStatusChange  parentId: " + entity.getParent_id() + " new status: " + status);
        long res = mDbHelper.updateFavorite(entity, status);
        Util.appendLog("handleStatusChange  parentId: " + entity.getParent_id() + " new status: " + status +" res: " + res);
        String title = " Sitescope Alert";
        String msg = "Status changes from " + entity.getStatus() + " new status: " + status + " for entity in  : " + entity.getFullPath() ;
        Util.addNotification(context, title,  msg);
    }
}
