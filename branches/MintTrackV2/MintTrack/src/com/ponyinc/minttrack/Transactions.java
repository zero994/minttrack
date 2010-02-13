package com.ponyinc.minttrack;

import static android.provider.BaseColumns._ID;
import static com.ponyinc.minttrack.Constants.*;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Transactions {
	private MintData MintLink;

	Transactions(MintData mintdata) {
		MintLink = mintdata;
	}

	public void createTransfer(int ToAccount_ID, int FromAccount_ID,
			double Amount, String Note, String Date)
	// Date mmddyyyy - ex: 02052010 - no dashes or slashes- fill space with
	// leading zeros
	{
		SQLiteDatabase db = MintLink.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(TRANSACTION_TOACCOUNT, ToAccount_ID);
		values.put(TRANSACTION_FROMACCOUNT, FromAccount_ID);
		values.put(TRANSACTION_AMOUNT, Amount);
		values.put(TRANSACTION_NOTE, Note);
		values.put(TRANSACTION_DATE, Date);
		values.put(TRANSACTION_TYPE, "transfer");

		db.insertOrThrow(TRANSACTION_TBLNAM, null, values);
	}

	public void createExpense(int FromAccount_ID, double Amount, String Note,
			String Date) {

		SQLiteDatabase db = MintLink.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(TRANSACTION_FROMACCOUNT, FromAccount_ID);
		values.put(TRANSACTION_AMOUNT, Amount);
		values.put(TRANSACTION_NOTE, Note);
		values.put(TRANSACTION_DATE, Date);
		values.put(TRANSACTION_TYPE, 0);

		db.insertOrThrow(TRANSACTION_TBLNAM, null, values);
	}

	public Cursor getTransactions() {
		final String[] FROM = { _ID, TRANSACTION_TOACCOUNT,
				TRANSACTION_FROMACCOUNT, TRANSACTION_AMOUNT, TRANSACTION_TYPE,
				TRANSACTION_DATE, TRANSACTION_CATEGORY, TRANSACTION_NOTE, };
		final String ORDER_BY = _ID + " DESC";
		SQLiteDatabase db = MintLink.getReadableDatabase();
		Cursor cursor = db.query(TRANSACTION_TBLNAM, FROM, null, null, null,
				null, ORDER_BY);
		return cursor;
	}
}
