package com.hpe.android.plugin.backgroundservice;

import com.red_folder.phonegap.plugin.backgroundservice.BackgroundService;
import hpe.com.sitescope.MainActivity;
import hpe.com.sitescope.R;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import android.os.Environment;
import android.database.Cursor;
import android.app.Notification;
import android.app.NotificationManager;
import com.hpe.android.plugin.backgroundservice.db.AccountInfoDbAdapterImpl;
import com.hpe.android.plugin.backgroundservice.db.AccountInfoDbAdapter;
import com.hpe.android.plugin.backgroundservice.data.Entity;
import com.hpe.android.plugin.backgroundservice.data.Group;
import com.hpe.android.plugin.backgroundservice.data.Monitor;
import android.os.AsyncTask;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import com.hpe.android.plugin.backgroundservice.utils.Util;
import com.hpe.android.plugin.backgroundservice.rest.GetEntitySnapshots;

public class MyService extends BackgroundService {

  private final static String TAG = MyService.class.getSimpleName();

  private AccountInfoDbAdapterImpl mDbHelper;
  private Cursor accountInfoCursor;
  private Cursor itemInfoCursor;
  private String msg = "";
  @Override
  protected JSONObject doWork() {
    JSONObject result = new JSONObject();
    try {
      mDbHelper = new AccountInfoDbAdapterImpl(this.getApplicationContext());
      mDbHelper.open();
      accountInfoCursor = mDbHelper.fetchAccountsList();
      int accountsNum = accountInfoCursor.getCount();
      itemInfoCursor = mDbHelper.fetchFavoritesList();
      int itemsNum = itemInfoCursor.getCount();
      Util.createLogFile();
      if(accountsNum > 0 && itemsNum > 0) {
        List<Entity> groups = null;
        String groupPaths=null;
        List<Entity> monitors = null;
        String monitorPaths=null;
        while (!accountInfoCursor.isAfterLast()) {
          // start instance
          groups = new ArrayList<Entity>();
          groupPaths="";
          monitors = new ArrayList<Entity>();
          monitorPaths="";
          int instanceId = accountInfoCursor.getInt(accountInfoCursor.getColumnIndex(AccountInfoDbAdapter.KEY_ROWID));
          while (!itemInfoCursor.isAfterLast()) {
            String parentId = itemInfoCursor.getString(itemInfoCursor.getColumnIndex(AccountInfoDbAdapter.PARENT_ID));
            if (Integer.parseInt(parentId) == instanceId){
              int itemId = itemInfoCursor.getInt(itemInfoCursor.getColumnIndex(AccountInfoDbAdapter.KEY_ROWID));
              String entityType = itemInfoCursor.getString(itemInfoCursor.getColumnIndex(AccountInfoDbAdapter.TYPE));
              String name = itemInfoCursor.getString(itemInfoCursor.getColumnIndex(AccountInfoDbAdapter.TYPE));
              String full_path  = itemInfoCursor.getString(itemInfoCursor.getColumnIndex(AccountInfoDbAdapter.FULL_PATH));
              full_path = full_path.replace(" ", "%20");
              msg = "itemId: " + itemId +" type: " + entityType + "  full_path: " + full_path + "\n" ;
              //Util.appendLog("add \n" + msg);
              if(entityType.equals("Group")){
                groups.add(createEntity(itemInfoCursor));
                groupPaths+=full_path+";";
              }else{// monitor
                monitors.add(createEntity(itemInfoCursor));
                monitorPaths+=full_path+";";
              }
            }
            itemInfoCursor.moveToNext();
          }
          itemInfoCursor.moveToFirst();
          if((groups.size()>0)|(monitors.size()>0)){
            // get instace data
            String protocol =  accountInfoCursor.getString(accountInfoCursor.getColumnIndex(AccountInfoDbAdapter.KEY_PROTOCOL));
            String host =  accountInfoCursor.getString(accountInfoCursor.getColumnIndex(AccountInfoDbAdapter.KEY_HOST));
            String port =  accountInfoCursor.getString(accountInfoCursor.getColumnIndex(AccountInfoDbAdapter.KEY_PORT));
            String prefixUrl = protocol + "://" + host + ":" + port ;
            if(groups.size()>0)
              getEntitiesStatuses(prefixUrl + "/SiteScope/api/monitors/groups/snapshots?fullPathsToGroups="+groupPaths, groups) ;
            if(monitors.size()>0)
              getEntitiesStatuses(prefixUrl + "/SiteScope/api/monitors/snapshots?fullPathsToMonitors="+monitorPaths, monitors) ;
          }
          accountInfoCursor.moveToNext();
        }

      }
    } catch (Exception e) {
      Util.appendLog("Error in doWork: " + e.getMessage());
    }

    try {
      result.put("Message",msg);
    } catch (Exception e) {
      Util.appendLog("Error in doWork in result: " + e.getMessage());
    }
    return result;
  }

  private Entity createEntity(Cursor itemInfoCursor) {
    String entityType = itemInfoCursor.getString(itemInfoCursor.getColumnIndex(AccountInfoDbAdapter.TYPE));
    Entity entity =  null;
    if(entityType.equals("Group"))
      entity = new Group(entityType);
    else
      entity = new Monitor(entityType);

    entity.setId(itemInfoCursor.getString(itemInfoCursor.getColumnIndex(AccountInfoDbAdapter.KEY_ROWID))+"");
    entity.setFullPath(itemInfoCursor.getString(itemInfoCursor.getColumnIndex(AccountInfoDbAdapter.FULL_PATH)));
    entity.setParent_id(itemInfoCursor.getString(itemInfoCursor.getColumnIndex(AccountInfoDbAdapter.PARENT_ID)));
    entity.setStatus(itemInfoCursor.getString(itemInfoCursor.getColumnIndex(AccountInfoDbAdapter.STATUS)));
    entity.setName(itemInfoCursor.getString(itemInfoCursor.getColumnIndex(AccountInfoDbAdapter.NAME)));
    entity.setUpdatedDate(itemInfoCursor.getString(itemInfoCursor.getColumnIndex(AccountInfoDbAdapter.UPDATE_DATE)));
    entity.setSummary(itemInfoCursor.getString(itemInfoCursor.getColumnIndex(AccountInfoDbAdapter.SUMMARY)));
    entity.setDescription(itemInfoCursor.getString(itemInfoCursor.getColumnIndex(AccountInfoDbAdapter.DESCREPTION)));
    entity.setRow_data(itemInfoCursor.getString(itemInfoCursor.getColumnIndex(AccountInfoDbAdapter.ROW_DATA)));
    if (!entityType.equals("Group")) {
      ((Monitor) entity).setTargetName(itemInfoCursor.getString(itemInfoCursor.getColumnIndex(AccountInfoDbAdapter.TARGET_DISPLAY_NAME)));
      ((Monitor) entity).setAvailabilityDescription(itemInfoCursor.getString(itemInfoCursor.getColumnIndex(AccountInfoDbAdapter.AVAILABILITY)));
    }

    return entity;
  }
  //================================================================================================================

  public void getEntitiesStatuses(String url, List<Entity> entities ){

    GetEntitySnapshots getEntitySnapshots = new GetEntitySnapshots(url,entities,this);
    getEntitySnapshots.execute();
  }


//=====================================================================================================================

  @Override
  protected JSONObject getConfig() {
    JSONObject result = new JSONObject();
    return result;
  }

  @Override
  protected void setConfig(JSONObject config) {
  }

  @Override
  protected JSONObject initialiseLatestResult() {
    return null;
  }
}
