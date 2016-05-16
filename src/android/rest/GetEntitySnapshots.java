package com.hpe.android.plugin.backgroundservice.rest;


import java.util.List;
import com.hpe.android.plugin.backgroundservice.utils.Util;
import com.hpe.android.plugin.backgroundservice.db.AccountInfoDbAdapterImpl;
import com.hpe.android.plugin.backgroundservice.data.Entity;
import com.hpe.android.plugin.backgroundservice.rest.RestCall;
import com.hpe.android.plugin.backgroundservice.data.Monitor;
import com.hpe.android.plugin.backgroundservice.db.AccountInfoDbAdapter;

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
            boolean statusChange = false;
            for(Entity entity : entities){
                //Util.appendLog("i = "+i+" paths.length: " + paths.length + " paths[i]: "+ paths[i]);
                JSONObject entityData  = (JSONObject) json.get(entity.getFullPath());
                //Util.appendLog("i = "+i+" " + entityData.toString());
                JSONObject runtime_snapshot  = (JSONObject) entityData.get("runtime_snapshot");
                String status = (String) runtime_snapshot.get("status");
                //Util.appendLog( entity.getParent_id() + " last status: " + entity.getStatus()+ "  new status: " + status);
                if(!status.equals(entity.getStatus())){
                    // status change
                    statusChange = true;
                    handleStatusChange(context,entity,entityData);
                }
            }
            if(!statusChange)
                Util.appendLog("Status not change , checked  " + entities.size());
        } catch (JSONException e) {
            Util.appendLog("Error in doInBackground : " + e.getMessage());
        }
        return null;
    }


    private void handleStatusChange(Context context, Entity entity, JSONObject entityData) throws JSONException{
        String oldStatus = entity.getStatus();
        updateEntity(entity, entityData);
        long res = mDbHelper.updateFavorite(entity);
        Util.appendLog("handleStatusChange  parentId: " + entity.getParent_id() + " new status: " + entity.getStatus() +" res: " + res);
        String title = " Sitescope Alert";
        String msg = "Status changes from " + oldStatus + " to: " + entity.getStatus() + " for entity   : " + entity.getFullPath().replace("_sis_path_delimiter_", "\\/") ;
        JSONObject values = new JSONObject();
        values.put(AccountInfoDbAdapter.KEY_ROWID,entity.getId());
        values.put(AccountInfoDbAdapter.PARENT_ID,entity.getParent_id());
        values.put(AccountInfoDbAdapter.FULL_PATH,entity.getFullPath());
        Util.addNotification(context, title,  msg ,values);
    }

    private void updateEntity(Entity entity, JSONObject entityData) throws JSONException {
        JSONObject runtime_snapshot  = (JSONObject) entityData.get("runtime_snapshot");
        JSONObject configuration_snapshot  = (JSONObject) entityData.get("configuration_snapshot");

        entity.setFullPath((String) configuration_snapshot.get("full_path"));
        entity.setStatus((String)runtime_snapshot.get("status"));
        entity.setName((String)configuration_snapshot.get("name"));
        entity.setUpdatedDate((String)configuration_snapshot.get("updated_date"));
        entity.setSummary((String)runtime_snapshot.get("summary"));
        entity.setDescription((String)configuration_snapshot.get("description"));
        entity.setRow_data(entityData.toString());
        if (!(entity.getEntityType()).equals("Group")) {
            ((Monitor) entity).setTargetName((String)configuration_snapshot.get("target_name"));
            ((Monitor) entity).setAvailabilityDescription((String)runtime_snapshot.get("availability_description"));
        }
    }

}
