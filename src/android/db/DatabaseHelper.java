package com.hpe.android.plugin.backgroundservice.db;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by IntelliJ IDEA.
 * User: mdv
 * Date: 05.04.11
 * Time: 15:58
 * To change this template use File | Settings | File Templates.
 */
public interface DatabaseHelper {
    public SQLiteDatabase getWritableDatabase();
    public void close();
}
