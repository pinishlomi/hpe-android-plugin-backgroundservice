package android.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import android.data.Entity;
import android.data.SiteScopeServer;
/**
 * Account info database access helper class. Defines the basic CRUD operations
 * and gives the ability to list all account info values as well as
 * retrieve or modify a specific account info item.
 * 
 * This also handles the Favorites table.
 */
public class AccountInfoDbAdapterImpl implements AccountInfoDbAdapter {

	private static final String TAG = AccountInfoDbAdapterImpl.class.getSimpleName();
    public static final String FAVES_DATABASE_TABLE = "favorites";

	private DatabaseHelperImpl mDbHelper;
	private SQLiteDatabase mDb;

	private  Context context;

    public DatabaseHelperImpl getmDbHelper() {
        return mDbHelper;
    }

    public AccountInfoDbAdapterImpl(Context context) {
		this.context = context;
		mDbHelper = new DatabaseHelperImpl(context);
	}

	/**
	 * Open the account_info database
	 */
	public AccountInfoDbAdapterImpl open() throws SQLException {
		//mDbHelper = new DatabaseHelperImpl(context);
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		mDbHelper.close();
	}

	/**
	 * Return a Cursor over the list of all account info values in the database
	 */
	public Cursor fetchAccountsList() {
		open();
		Cursor mCursor = mDb.query(DATABASE_TABLE, new String[] {KEY_ROWID,KEY_HOST, KEY_PORT, KEY_PROTOCOL, KEY_ALLOW_UNTRUSTED_CERTS, KEY_USERNAME, KEY_PASSWORD, KEY_NAME}, null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		close();
		return mCursor;
	}

	//get sitescope account based on ID
	public Cursor fetchAccountInfo(long rowId) throws SQLException {
		open();
		Cursor mCursor = mDb.query(true, DATABASE_TABLE, new String[] {KEY_HOST, KEY_PORT, KEY_PROTOCOL, KEY_ALLOW_UNTRUSTED_CERTS, KEY_USERNAME, KEY_PASSWORD, KEY_NAME}, KEY_ROWID + "=" + rowId, null,
				null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		close();
		return mCursor;
	}

	//get sitescope account based on display name
	public Cursor fetchAccountInfo(String displayName) throws SQLException {
		open();
		Cursor mCursor = mDb.query(true, DATABASE_TABLE, new String[]{KEY_HOST, KEY_PORT, KEY_PROTOCOL, KEY_USERNAME, KEY_ALLOW_UNTRUSTED_CERTS, KEY_PASSWORD, KEY_NAME}, KEY_NAME + "=\"" + displayName + "\"", null,
				null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		close();
		return mCursor;
	}

	public long updateAccount(long rowId, String host, String port, String protocol, String allowUntrustedCerts, String username, String password, String displayname) {
		open();
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_HOST, host);
		initialValues.put(KEY_PORT, port);
		initialValues.put(KEY_PROTOCOL, protocol);
		initialValues.put(KEY_ALLOW_UNTRUSTED_CERTS, allowUntrustedCerts);
		initialValues.put(KEY_USERNAME, username);
		initialValues.put(KEY_PASSWORD, password);
		initialValues.put(KEY_NAME, displayname);
		long result = mDb.update(DATABASE_TABLE, initialValues, KEY_ROWID + "=" + rowId, null);
		close();
		return  result;
	}    

	public long insertAccount(String host, String port, String protocol, String allowUntrustedCerts, String username, String password, String displayname) {
		open();
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_HOST, host);
		initialValues.put(KEY_PORT, port);
		initialValues.put(KEY_PROTOCOL, protocol);
		initialValues.put(KEY_ALLOW_UNTRUSTED_CERTS, allowUntrustedCerts);
		initialValues.put(KEY_USERNAME, username);
		initialValues.put(KEY_PASSWORD, password);
		initialValues.put(KEY_NAME, displayname);
		long result = mDb.insert(DATABASE_TABLE, null, initialValues);
		close();
		return result;
	}

	public boolean deleteAccount(long rowId)
	{
		open();
		boolean result = mDb.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
		close();
		return result;
	}

	/**
	 * Return a Cursor over the list of all account info values in the database
	 */
	public Cursor fetchFavoritesList() {
		open();
		Cursor mCursor = mDb.query(FAVES_DATABASE_TABLE,
				new String[] {
						PARENT_ID,
						NAME,
						UPDATE_DATE,
						TYPE,
						STATUS,
						SUMMARY,
						FULL_PATH,
						TARGET_DISPLAY_NAME,
						DESCREPTION,
						AVAILABILITY,
						ROW_DATA
				}, null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		close();
		return mCursor;
	}

	public Cursor fetchFavoriteInfo(long rowId) throws SQLException {
		open();
		Cursor mCursor = mDb.query(true, FAVES_DATABASE_TABLE,
				new String[] {
						PARENT_ID,
						NAME,
						UPDATE_DATE,
						TYPE,
						STATUS,
						SUMMARY,
						FULL_PATH,
						TARGET_DISPLAY_NAME,
						DESCREPTION,
						AVAILABILITY,
						ROW_DATA
				}, KEY_ROWID + "=" + rowId, null,
				null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		close();
		return mCursor;
	}    

	public long insertFavorite(
			String ssAcct,
			String name,
			String updated_date,
			String type,
			String status,
			String summary,
			String full_path,
			String target_display_name,
			String description,
			String availability,
			String row_data){
		open();
		ContentValues initialValues = new ContentValues();
		initialValues.put(PARENT_ID, ssAcct);
		initialValues.put(NAME, name);
		initialValues.put(UPDATE_DATE, updated_date);
		initialValues.put(TYPE, type);
		initialValues.put(STATUS, status);
		initialValues.put(SUMMARY, summary);
		initialValues.put(FULL_PATH, full_path);
		initialValues.put(TARGET_DISPLAY_NAME, target_display_name);
		initialValues.put(DESCREPTION, description);
		initialValues.put(AVAILABILITY, availability);
		initialValues.put(ROW_DATA, row_data);
		long result = mDb.insert(FAVES_DATABASE_TABLE, null, initialValues);
		close();
		return result;
	}

	public boolean deleteFavorite(long rowId)
	{
		open();
		boolean result = mDb.delete(FAVES_DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
		close();
		return result;
	}

	public boolean deleteFavorite(Entity entity)
	{
		open();
		String whereClause = NAME + "=\"" + entity.getName()
				+ "\" and " + TYPE + "=\"" + entity.getType()
				+ "\" and " + PARENT_ID + "=\"" + entity.getSiteScopeServer().getName()
				+ "\" and " + FULL_PATH + "=\"" + entity.getFullPath() + "\"";
		boolean result = mDb.delete(FAVES_DATABASE_TABLE, whereClause, null) >0;
		close();
		return result;
	}

	@Override
	public SiteScopeServer checkAccountExistsForHost(String siteScopeHost){
		final Cursor accountInfoCursor = fetchAccountsList();
		while(!accountInfoCursor.isAfterLast())
		{     // The Cursor is now set to the right position
			String ssAcctName = accountInfoCursor.getString(accountInfoCursor.getColumnIndex(android.db.AccountInfoDbAdapter.KEY_NAME));
			//String siteScopeHost = siteScopeHost;//getIntent().getStringExtra("siteScopeHost");
			String host = accountInfoCursor.getString(accountInfoCursor.getColumnIndex(android.db.AccountInfoDbAdapter.KEY_HOST));
			String port = accountInfoCursor.getString(accountInfoCursor.getColumnIndex(android.db.AccountInfoDbAdapter.KEY_PORT));
			String protocol = accountInfoCursor.getString(accountInfoCursor.getColumnIndex(android.db.AccountInfoDbAdapter.KEY_PROTOCOL));
			boolean allowUntrustedCerts = Boolean.valueOf(accountInfoCursor.getString(accountInfoCursor.getColumnIndex(android.db.AccountInfoDbAdapter.KEY_ALLOW_UNTRUSTED_CERTS)));
			String username = accountInfoCursor.getString(accountInfoCursor.getColumnIndex(android.db.AccountInfoDbAdapter.KEY_USERNAME));
			String password = accountInfoCursor.getString(accountInfoCursor.getColumnIndex(android.db.AccountInfoDbAdapter.KEY_PASSWORD));

			final SiteScopeServer ssServer = new SiteScopeServer(protocol, host, port, username, password, ssAcctName, allowUntrustedCerts);

			if(host.equalsIgnoreCase(siteScopeHost)) {
				Log.d(TAG, String.format("Account for host=%s exists", host));
				return ssServer;
			}
			accountInfoCursor.moveToNext();
		}
		return null;
	}

	@Override
	public int getAccountsCount() throws Exception {
		final Cursor accountsCursor = fetchAccountsList();
		if (accountsCursor != null) {
			return accountsCursor.getCount();
		} else throw new Exception("accountsCursor can't be null - status_error");
	}

}
