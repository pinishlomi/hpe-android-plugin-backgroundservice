package com.hpe.android.plugin.backgroundservice;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.database.Cursor;
import android.util.Log;
import android.content.Intent;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import com.hpe.android.plugin.backgroundservice.db.AccountInfoDbAdapterImpl;
import com.hpe.android.plugin.backgroundservice.db.AccountInfoDbAdapter;
import com.red_folder.phonegap.plugin.backgroundservice.BackgroundService;
import com.ionicframework.sismobile287465.MainActivity;
import android.support.v4.app.NotificationCompat;

public class MyService extends BackgroundService {

  private final static String TAG = MyService.class.getSimpleName();

  private String mHelloTo = "Sitescope";
  private AccountInfoDbAdapterImpl mDbHelper;
  private Cursor accountInfoCursor;
  private Cursor itemInfoCursor;
  @Override
  protected JSONObject doWork() {
    JSONObject result = new JSONObject();
    String msg = "";
    try {
      SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
      String now = df.format(new Date(System.currentTimeMillis()));

      msg = "Hello " + this.mHelloTo + " - its currently " + now;
      mDbHelper = new AccountInfoDbAdapterImpl(this.getApplicationContext());
      mDbHelper.open();
      accountInfoCursor = mDbHelper.fetchAccountsList();
       //If there is at least one account then whether retrieve monitor from alert or go to Home screen
      int accountsNum = accountInfoCursor.getCount();
      msg += " accountsNum : " + accountsNum;

      itemInfoCursor = mDbHelper.fetchFavoritesList();
      int itemsNum = itemInfoCursor.getCount();

      if(accountsNum > 0 && itemsNum > 0) {
        while (!accountInfoCursor.isAfterLast()) {
          int instanceId = accountInfoCursor.getInt(accountInfoCursor.getColumnIndex(AccountInfoDbAdapter.KEY_ROWID));
          msg += "  itemsNum : " + itemsNum;
          while (!itemInfoCursor.isAfterLast()) {
            String parentId = itemInfoCursor.getString(itemInfoCursor.getColumnIndex(AccountInfoDbAdapter.PARENT_ID));
            msg += "  parentId : " + parentId + "  instanceId : "+ instanceId;
            if (Integer.parseInt(parentId) == instanceId){
                String entityType = itemInfoCursor.getString(itemInfoCursor.getColumnIndex(AccountInfoDbAdapter.TYPE));
                String full_path = itemInfoCursor.getString(itemInfoCursor.getColumnIndex(AccountInfoDbAdapter.FULL_PATH));
                msg += " item    type: " + entityType + "  full_path: " + full_path ;
            }
            itemInfoCursor.moveToNext();
          }
          itemInfoCursor.moveToFirst();
        accountInfoCursor.moveToNext();
        }
      }
      msg+= addNotification("SiteScope Alert", msg);
      Log.d(TAG, msg );
    } catch (Exception e) {
      Log.e(TAG, "Error retrieving accounts - " + e.getMessage());
      msg += "  Error: " + e.getMessage();
    }

    try {
      result.put("Message",msg);
    } catch (Exception e) {
    }
    return result;
  }

  public String addNotification(String title, String msg)
  {
    Notification noti = new Notification.Builder(this)
            .setContentTitle(title)
            .setContentText(msg)
            //.setSmallIcon("resources/android/icon/drawable-hdpi-icon.png")
            .setAutoCancel(true).build();
    noti.flags |= Notification.FLAG_AUTO_CANCEL;
    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    notificationManager.notify(12345, noti);
    return " in addNotification .... ";
  }

  @Override
  protected JSONObject getConfig() {
    JSONObject result = new JSONObject();

    try {
      result.put("HelloTo", this.mHelloTo);
    } catch (JSONException e) {
    }

    return result;
  }

  @Override
  protected void setConfig(JSONObject config) {
    try {
      if (config.has("HelloTo"))
        this.mHelloTo = config.getString("HelloTo");
    } catch (JSONException e) {
    }

  }

  @Override
  protected JSONObject initialiseLatestResult() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected void onTimerEnabled() {
    // TODO Auto-generated method stub

  }

  @Override
  protected void onTimerDisabled() {
    // TODO Auto-generated method stub

  }


}
