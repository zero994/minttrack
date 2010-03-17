package com.ponyinc.minttrack;

import static android.provider.BaseColumns._ID;
import static com.ponyinc.minttrack.Constants.*;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

public class Transactions {
	private MintData MintLink;

	Transactions(MintData mintdata) {
		MintLink = mintdata;
	}

	public void createTransfer(int ToAccount_ID, int FromAccount_ID,
			double Amount, String Note, String Date, int Category)
	// Date mmddyyyy - ex: 02052010 - no dashes or slashes- fill space with
	// leading zeros
	{
		SQLiteDatabase db = MintLink.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(TRANSACTION_TOACCOUNT, ToAccount_ID);
		values.put(TRANSACTION_FROMACCOUNT, FromAccount_ID);
		values.put(TRANSACTION_AMOUNT, Amount);
		values.put(TRANSACTION_CATEGORY, Category);
		values.put(TRANSACTION_NOTE, Note);
		values.put(TRANSACTION_DATE, Date);
		values.put(TRANSACTION_TYPE, 2);

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
		values.put(TRANSACTION_TYPE, 1);

		db.insertOrThrow(TRANSACTION_TBLNAM, null, values);
	}
	public void createIncome(int ToAccount_ID, double Amount, String Note,
			String Date) {

		SQLiteDatabase db = MintLink.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(TRANSACTION_TOACCOUNT, ToAccount_ID);
		values.put(TRANSACTION_AMOUNT, Amount);
		values.put(TRANSACTION_NOTE, Note);
		values.put(TRANSACTION_DATE, Date);
		values.put(TRANSACTION_TYPE, 0);

		db.insertOrThrow(TRANSACTION_TBLNAM, null, values);
	}

	public Cursor getTransactions(){
        SQLiteDatabase db = MintLink.getWritableDatabase();
        return db.rawQuery("SELECT " + TRANSACTION_TBLNAM + "._ID, " + TRANSACTION_AMOUNT + ", " + TRANSACTION_NOTE + ", " + TRANSACTION_TYPE + ", " 
        					+ TRANSACTION_DATE + ", " + TRANSACTION_CATEGORY + ", " + TRANSACTION_TOACCOUNT + ", " + TRANSACTION_FROMACCOUNT 
        					+ ", C1." + CATEGORY_NAME + " AS CATNAME, A1." + ACCOUNT_NAME + " AS ACT1NAME, A2." + ACCOUNT_NAME + " AS ACT2NAME FROM " 
        					+ TRANSACTION_TBLNAM + " LEFT JOIN " + ACCOUNT_TBLNAM + " A1 ON " + TRANSACTION_TOACCOUNT + " = A1." + _ID + " LEFT JOIN " + ACCOUNT_TBLNAM 
        					+ " A2 ON " + TRANSACTION_FROMACCOUNT + " = A2." + _ID + " LEFT JOIN " + CATEGORY_TBLNAM + " C1 ON " 
        					+ TRANSACTION_CATEGORY + " = C1." + _ID, null);
	}
	public void ClearTable()
	{
		SQLiteDatabase db = MintLink.getWritableDatabase();
		db.delete(TRANSACTION_TBLNAM, null, null);
	}
}
