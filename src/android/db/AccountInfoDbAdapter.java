package android.db;

import android.database.Cursor;
import android.database.SQLException;

import android.data.Entity;
import android.data.SiteScopeServer;

/**
 * Created by IntelliJ IDEA.
 * User: mdv
 * Date: 05.04.11
 * Time: 13:18
 * To change this template use File | Settings | File Templates.
 */
public interface AccountInfoDbAdapter {

    public static final String KEY_ROWID = "_id";

    //column names of SiteScope account info
    public static final String KEY_NAME = "name";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_PROTOCOL = "protocol";
    public static final String KEY_HOST = "host";
    public static final String KEY_PORT = "port";
    public static final String KEY_ALLOW_UNTRUSTED_CERTS = "allowUntrustedCertificate";
    public static final String KEY_LAST_CONNECTION_TIME = "lastConnectionTime";

    public static final int DISPLAYNAME_COLUMNINDEX = 7;

    public static final String DATABASE_NAME = "sitescope_db";
    public static final String DATABASE_TABLE = "account_info";
    public static final String SETTING_TABLE = "setting";
    public static final int DATABASE_VERSION = 2;

    /**
     * Account Info Database creation sql statement
     */
    public static final String DATABASE_CREATE =
        "create table "+DATABASE_TABLE+" ("
                + KEY_ROWID + " INTEGER, "
                + KEY_NAME + " VARCHAR,"
                + KEY_USERNAME + " VARCHAR, "
                + KEY_PASSWORD + " VARCHAR, "
                + KEY_PROTOCOL + " VARCHAR, "
                + KEY_HOST + " VARCHAR, "
                + KEY_PORT + " INTEGER, "
                + KEY_ALLOW_UNTRUSTED_CERTS + " VARCHAR, "
                + KEY_LAST_CONNECTION_TIME  + " VARCHAR, " +
        		" PRIMARY KEY ("+KEY_ROWID+"));";

    public static final String FAVES_DATABASE_TABLE = "favorites";
    //column names related to favorites table
    public static final String PARENT_ID = "parent_id";
    public static final String NAME = "name";
    public static final String UPDATE_DATE = "updated_date";
    public static final String TYPE = "type";
    public static final String STATUS = "status";
    public static final String SUMMARY = "summary";
    public static final String FULL_PATH = "full_path";
    public static final String TARGET_DISPLAY_NAME = "target_display_name";
    public static final String DESCREPTION = "description";
    public static final String AVAILABILITY = "availability";
    public static final String ROW_DATA = "row_data";


    // favorites database table creation sql statement
    public static final String FAVES_DATABASE_CREATE =
        "create table "+FAVES_DATABASE_TABLE+" ("
                + KEY_ROWID + " INTEGER, "
                + PARENT_ID + " VARCHAR, "
                + NAME + " VARCHAR, "
                + UPDATE_DATE+ " VARCHAR, "
                + TYPE+ " VARCHAR, "
                + STATUS+ " VARCHAR, "
                + SUMMARY+ " VARCHAR, "
                + FULL_PATH + " VARCHAR, "
                + TARGET_DISPLAY_NAME+ " VARCHAR, "
                + DESCREPTION+ " VARCHAR, "
                + AVAILABILITY+ " VARCHAR, "
                + ROW_DATA+ " VARCHAR, "
                + " PRIMARY KEY ("+KEY_ROWID+"));";

    //column names of SiteScope setting
    public static final String KEY = "key";
    public static final String KEY_VALUE = "value";

    /**
     * Account Info Database creation sql statement
     */
    public static final String SETTING_DATABASE_CREATE =
            "create table "+SETTING_TABLE+" ("
                    + KEY + " VARCHAR,"
                    + KEY_VALUE + " VARCHAR );";

    public AccountInfoDbAdapterImpl open() throws SQLException;

    public void close();

    public Cursor fetchAccountsList();

    public Cursor fetchAccountInfo(long rowId) throws SQLException;

    public Cursor fetchAccountInfo(String displayName) throws SQLException;

    public long updateAccount(long rowId, String host, String port, String protocol,
                              String allowUntrustedCerts, String username, String password, String displayname);

    public long insertAccount(String host, String port, String protocol, String allowUntrustedCerts, String username, String password, String displayname);

    public boolean deleteAccount(long rowId);

    public Cursor fetchFavoritesList();

    public Cursor fetchFavoriteInfo(long rowId) throws SQLException;

    public long insertFavorite(
            String parent_id,
            String name,
            String updated_date,
            String type,
            String status,
            String summary,
            String full_path,
            String target_display_name,
            String description,
            String availability,
            String row_data );

    public boolean deleteFavorite(long rowId);

    public boolean deleteFavorite(Entity entity);

    SiteScopeServer checkAccountExistsForHost(String siteScopeHost);

    int getAccountsCount() throws Exception;

}
