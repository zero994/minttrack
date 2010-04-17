package com.ponyinc.minttrack;

import static android.provider.BaseColumns._ID;
import static com.ponyinc.minttrack.Constants.*;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
/**
*	Class represents the accounts object 
*   and contains methods for interacting with them 
*/
public class Accounts {
	private MintData MintLink;
	/** Secondary Constructor
	*	@param mintdata Takes a MintData object that represents the actual database object
	*/
	Accounts(MintData mintdata) {
		MintLink = mintdata;

	}
	/** Method is used to add an account to the acount table.
	*	@param strName The name of the account
	*	@param initalValue The initial account balance of the account
	*	@param isActive Is account active
	*/
	public void addAccount(String strName, double initalValue, boolean isActive) {
		// Insert a new record into the Events data source.
		// You would do something similar for delete and update
		SQLiteDatabase db = MintLink.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(ACCOUNT_NAME, strName);
		values.put(ACCOUNT_TOTAL, initalValue);
		if(isActive == true)
			values.put(ACCOUNT_ACTIVE, "active");
		else
			values.put(ACCOUNT_ACTIVE, "inactive");
		db.insertOrThrow(ACCOUNT_TBLNAM, null, values);
	}
	/** Method used to query the accounts table based on an accounts ID
	*	@para _id Account ID that you wish to query
	*/
	public Cursor getAccount(long _id) {
		final String[] FROM = { _ID, ACCOUNT_NAME, ACCOUNT_TOTAL,
				ACCOUNT_ACTIVE, };
		final String ORDER_BY = _ID + " DESC";

		SQLiteDatabase db = MintLink.getReadableDatabase();

		Cursor cursor = db.query(ACCOUNT_TBLNAM, FROM, "_ID=" + _id, null,
				null, null, ORDER_BY);

		return cursor;
	}
	/** Method is used to get a list of only accounts that have an active status
	*	@return Returns a Cursor contains accounts that are active in the accounts table
	*/
	public Cursor getActiveAccounts() {
		final String[] FROM = { _ID, ACCOUNT_NAME, ACCOUNT_TOTAL,
				ACCOUNT_ACTIVE, };
		final String ORDER_BY = _ID + " ASC";

		SQLiteDatabase db = MintLink.getReadableDatabase();

		Cursor cursor = db.query(ACCOUNT_TBLNAM, FROM, "ACCOUNT_ACTIVE ='active'", null,
				null, null, ORDER_BY);

		return cursor;
	}
	/** Method is used to get a Cursor of all the accounts in the account table
	*	@return A Cursor containing all the accounts on the account table
	*/
	public Cursor getAllAccounts() {
		final String[] FROM = { _ID, ACCOUNT_NAME, ACCOUNT_TOTAL,
				ACCOUNT_ACTIVE, };
		final String ORDER_BY = _ID + " ASC";
		SQLiteDatabase db = MintLink.getReadableDatabase();
		Cursor cursor = db.query(ACCOUNT_TBLNAM, FROM, null, null, null, null,
				ORDER_BY);
		return cursor;
	}
	/** Method is used to deactivate an account
	*	@param acc_id ID of the account which you wish to disable
	*/
	public void DeactivateAccount(long acc_id)
	// set account to inactive
	{
		SQLiteDatabase db = MintLink.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(ACCOUNT_ACTIVE, "inactive");
		db.update(ACCOUNT_TBLNAM, values, _ID + "=" + acc_id, null);
	}
	/** Method is used to edit the name of an existing account
	*	@param acc_id ID of the account you want to modify
	*	@param strName Name that will replace the account
	*/
	public void EditAccountName(long acc_id, String strName) {
		SQLiteDatabase db = MintLink.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(ACCOUNT_NAME, strName);
		db.update(ACCOUNT_TBLNAM, values, _ID + "=" + acc_id, null);
	}
	/** Method modifies the balance total of an account
	*	@param acc_id ID of the account you want to modify
	*	@param total New amount to be set as the balance of the account
	*/
	public void EditAccountTotal(long acc_id, double total) {
		SQLiteDatabase db = MintLink.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(ACCOUNT_TOTAL, total);
		db.update(ACCOUNT_TBLNAM, values, _ID + "=" + acc_id, null);
	}
	/** Method is used to reactive an inactive account that already exists
	*	@param acc_id ID of account which you want to activate
	*/
	public void ReactivateAccount(long acc_id)
	// set account to inactive
	{
		SQLiteDatabase db = MintLink.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(ACCOUNT_ACTIVE, "active");
		db.update(ACCOUNT_TBLNAM, values, _ID + "=" + acc_id, null);
	}
}
