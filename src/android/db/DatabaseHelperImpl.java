package com.hpe.hybridsitescope.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by IntelliJ IDEA.
 * User: mdv
 * Date: 05.04.11
 * Time: 15:58
 * To change this template use File | Settings | File Templates.
 */
public class DatabaseHelperImpl extends SQLiteOpenHelper implements DatabaseHelper {
    public static final String TAG = DatabaseHelperImpl.class.getSimpleName();

    public DatabaseHelperImpl(Context context) {
        super(context, AccountInfoDbAdapter.DATABASE_NAME, null, AccountInfoDbAdapter.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(AccountInfoDbAdapter.DATABASE_CREATE);
        db.execSQL(AccountInfoDbAdapter.FAVES_DATABASE_CREATE);
        db.execSQL(AccountInfoDbAdapter.SETTING_DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + AccountInfoDbAdapter.DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + AccountInfoDbAdapter.FAVES_DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + AccountInfoDbAdapter.SETTING_DATABASE_CREATE);
        onCreate(db);
    }
}
