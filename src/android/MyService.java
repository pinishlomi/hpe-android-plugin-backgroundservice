package com.hpe.android.plugin.backgroundservice;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.database.Cursor;
import android.util.Log;

import com.hpe.android.plugin.backgroundservice.db.AccountInfoDbAdapterImpl;
import com.hpe.android.plugin.backgroundservice.db.AccountInfoDbAdapter;
import com.red_folder.phonegap.plugin.backgroundservice.BackgroundService;

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
      msg += "  itemsNum : " + itemsNum;
      if (itemsNum > 0) {
        while (!itemInfoCursor.isAfterLast()) {
          String entityType = itemInfoCursor.getString(itemInfoCursor.getColumnIndex(AccountInfoDbAdapter.TYPE));
          String full_path = itemInfoCursor.getString(itemInfoCursor.getColumnIndex(AccountInfoDbAdapter.FULL_PATH));
          msg += " item    type: " + entityType + "  full_path: " + full_path;
        }
      }

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
