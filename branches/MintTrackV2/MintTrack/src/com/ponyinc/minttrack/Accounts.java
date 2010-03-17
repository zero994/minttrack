package com.ponyinc.minttrack;

import static android.provider.BaseColumns._ID;
import static com.ponyinc.minttrack.Constants.*;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Accounts {
	private MintData MintLink;

	Accounts(MintData mintdata) {
		MintLink = mintdata;

	}

	public void addAccount(String strName, double initalValue) {
		// Insert a new record into the Events data source.
		// You would do something similar for delete and update
		SQLiteDatabase db = MintLink.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(ACCOUNT_NAME, strName);
		values.put(ACCOUNT_TOTAL, initalValue);
		values.put(ACCOUNT_ACTIVE, "active");
		db.insertOrThrow(ACCOUNT_TBLNAM, null, values);
	}

	public Cursor getAccount(int _id) {
		final String[] FROM = { _ID, ACCOUNT_NAME, ACCOUNT_TOTAL,
				ACCOUNT_ACTIVE, };
		final String ORDER_BY = _ID + " DESC";

		SQLiteDatabase db = MintLink.getReadableDatabase();

		Cursor cursor = db.query(ACCOUNT_TBLNAM, FROM, "_ID=" + _id, null,
				null, null, ORDER_BY);

		return cursor;
	}
	
	public Cursor getActiveAccounts() {
		final String[] FROM = { _ID, ACCOUNT_NAME, ACCOUNT_TOTAL,
				ACCOUNT_ACTIVE, };
		final String ORDER_BY = _ID + " ASC";

		SQLiteDatabase db = MintLink.getReadableDatabase();

		Cursor cursor = db.query(ACCOUNT_TBLNAM, FROM, "ACCOUNT_ACTIVE ='active'", null,
				null, null, ORDER_BY);

		return cursor;
	}

	public Cursor getAllAccounts() {
		final String[] FROM = { _ID, ACCOUNT_NAME, ACCOUNT_TOTAL,
				ACCOUNT_ACTIVE, };
		final String ORDER_BY = _ID + " ASC";
		SQLiteDatabase db = MintLink.getReadableDatabase();
		Cursor cursor = db.query(ACCOUNT_TBLNAM, FROM, null, null, null, null,
				ORDER_BY);
		return cursor;
	}

	public void DeactivateAccount(int acc_id)
	// set account to inactive
	{
		SQLiteDatabase db = MintLink.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(ACCOUNT_ACTIVE, "inactive");
		db.update(ACCOUNT_TBLNAM, values, _ID + "=" + acc_id, null);
	}

	public void EditAccountName(int acc_id, String strName) {
		SQLiteDatabase db = MintLink.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(ACCOUNT_NAME, strName);
		db.update(ACCOUNT_TBLNAM, values, _ID + "=" + acc_id, null);
	}

	public void EditAccountTotal(int acc_id, double total) {
		SQLiteDatabase db = MintLink.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(ACCOUNT_TOTAL, total);
		db.update(ACCOUNT_TBLNAM, values, _ID + "=" + acc_id, null);
	}

	public void ReactivateAccount(int acc_id)
	// set account to inactive
	{
		SQLiteDatabase db = MintLink.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(ACCOUNT_ACTIVE, "active");
		db.update(ACCOUNT_TBLNAM, values, _ID + "=" + acc_id, null);
	}
}
