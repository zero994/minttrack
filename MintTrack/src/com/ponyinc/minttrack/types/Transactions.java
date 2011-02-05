package com.ponyinc.minttrack.types;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ponyinc.minttrack.Constants;
import com.ponyinc.minttrack.MintData;

/**Interface to transaction table */
public class Transactions implements Constants {
	/**
	 * hello
	 */
	private MintData MintLink;

	/** Create an object to talk to transaction table
	 *@param mintdata database to connect to
	 */
	public Transactions(MintData mintdata) {
		MintLink = mintdata;
	}
	/**Transfer founds from one account to another
	 * @param ToAccount_ID id of account which money will transfered to
	 * @param FromAccount_ID id of account which money will be transfered from
	 * @param Amount the amount to be transfered
	 * @param Note a note by the user
	 * @param Date date transfer took place
	 * @param Category reason for transfer*/
	public void createTransfer(String[] newInfo)
	// Date yyyymmdd - ex: 20110202 - no dashes or slashes- fill space with
	// leading zeros
	{
		SQLiteDatabase db = MintLink.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(TRANSACTION_TOACCOUNT, Long.parseLong(newInfo[TO]));
		values.put(TRANSACTION_FROMACCOUNT, Long.parseLong(newInfo[FROM]));
		values.put(TRANSACTION_AMOUNT, Double.parseDouble(newInfo[AMOUNT]));
		values.put(TRANSACTION_CATEGORY, Long.parseLong(newInfo[CATEGORY]));
		values.put(TRANSACTION_NOTE, newInfo[NOTE]);
		values.put(TRANSACTION_DATE, newInfo[DATE]);
		values.put(TRANSACTION_TYPE, TRANS_TYPE_TRANSFER);

		db.insertOrThrow(TRANSACTION_TBLNAM, null, values);
		db.close();
	}

	/**Take founds from one account for a given reason
	 * @param FromAccount_ID id of account which money will be taken from
	 * @param Amount the amount to be taken
	 * @param Note a note by the user
	 * @param Date date expense took place
	 * @param Category reason for expense*/
	public void createExpense(String[] newInfo) {

		SQLiteDatabase db = MintLink.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(TRANSACTION_FROMACCOUNT, Long.parseLong(newInfo[FROM]));
		values.put(TRANSACTION_AMOUNT, Double.parseDouble(newInfo[AMOUNT]));
		values.put(TRANSACTION_NOTE, newInfo[NOTE]);
		values.put(TRANSACTION_CATEGORY, Long.parseLong(newInfo[CATEGORY]));
		values.put(TRANSACTION_DATE, newInfo[DATE]);
		values.put(TRANSACTION_TYPE, TRANS_TYPE_EXPENSE);

		db.insertOrThrow(TRANSACTION_TBLNAM, null, values);
		db.close();
	}
	/**Add founds to one account because of income
	 * @param ToAccount_ID id of account which money will put into
	 * @param Amount the amount to be add
	 * @param Note a note by the user
	 * @param Date date income too place
	 * @param Category reason for income*/
	public void createIncome(String[] newInfo) {

		SQLiteDatabase db = MintLink.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(TRANSACTION_TOACCOUNT, Long.parseLong(newInfo[TO]));
		values.put(TRANSACTION_AMOUNT, Double.parseDouble(newInfo[AMOUNT]));
		values.put(TRANSACTION_NOTE, newInfo[NOTE]);
		values.put(TRANSACTION_DATE, newInfo[DATE]);
		values.put(TRANSACTION_TYPE, TRANS_TYPE_INCOME);
		values.put(TRANSACTION_CATEGORY, Long.parseLong(newInfo[CATEGORY]));
		
		db.insertOrThrow(TRANSACTION_TBLNAM, null, values);
		db.close();
	}
	/** get all transactions
	 * @return Cursor of transactions
	 */
	public Cursor getTransactions(){
        SQLiteDatabase db = MintLink.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT " + TRANSACTION_TBLNAM + "._ID, " + TRANSACTION_AMOUNT + ", " + TRANSACTION_NOTE + ", " + TRANSACTION_TYPE + ", " 
        					+ TRANSACTION_DATE + ", " + TRANSACTION_CATEGORY + ", " + TRANSACTION_TOACCOUNT + ", " + TRANSACTION_FROMACCOUNT 
        					+ ", C1." + CATEGORY_NAME + " AS CATNAME, A1." + ACCOUNT_NAME + " AS ACT1NAME, A2." + ACCOUNT_NAME + " AS ACT2NAME FROM " 
        					+ TRANSACTION_TBLNAM + " LEFT JOIN " + ACCOUNT_TBLNAM + " A1 ON " + TRANSACTION_TOACCOUNT + " = A1." + _ID + " LEFT JOIN " + ACCOUNT_TBLNAM 
        					+ " A2 ON " + TRANSACTION_FROMACCOUNT + " = A2." + _ID + " LEFT JOIN " + CATEGORY_TBLNAM + " C1 ON " 
        					+ TRANSACTION_CATEGORY + " = C1." + _ID, null);
        return c;
	}
	public Cursor getTransactions(String FromDate, String ToDate){
        SQLiteDatabase db = MintLink.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT " + TRANSACTION_TBLNAM + "._ID, " + TRANSACTION_AMOUNT + ", " + TRANSACTION_NOTE + ", " + TRANSACTION_TYPE + ", " 
        					+ TRANSACTION_DATE + ", " + TRANSACTION_CATEGORY + ", " + TRANSACTION_TOACCOUNT + ", " + TRANSACTION_FROMACCOUNT 
        					+ ", C1." + CATEGORY_NAME + " AS CATNAME, A1." + ACCOUNT_NAME + " AS ACT1NAME, A2." + ACCOUNT_NAME + " AS ACT2NAME FROM " 
        					+ TRANSACTION_TBLNAM + " LEFT JOIN " + ACCOUNT_TBLNAM + " A1 ON " + TRANSACTION_TOACCOUNT + " = A1." + _ID + " LEFT JOIN " + ACCOUNT_TBLNAM 
        					+ " A2 ON " + TRANSACTION_FROMACCOUNT + " = A2." + _ID + " LEFT JOIN " + CATEGORY_TBLNAM + " C1 ON " 
        					+ TRANSACTION_CATEGORY + " = C1." + _ID + " WHERE " + TRANSACTION_DATE + " BETWEEN '" + FromDate + "' AND '" + ToDate + "'" , null);
        return c;
	}
	/**
	 * 
	 * @param transID
	 * @return cursor of 
	 */
	public Cursor getTransaction(long transID) {
		final String[] FROM = { _ID, TRANSACTION_TOACCOUNT,
				TRANSACTION_FROMACCOUNT, TRANSACTION_AMOUNT, TRANSACTION_TYPE,
				TRANSACTION_DATE, TRANSACTION_CATEGORY, TRANSACTION_NOTE, };
		final String ORDER_BY = _ID + " DESC";

		SQLiteDatabase db = MintLink.getReadableDatabase();

		Cursor cursor = db.query(TRANSACTION_TBLNAM, FROM, "_ID=" + transID, null,
				null, null, ORDER_BY);

		return cursor;
	}
	
	public void updateTransfer(long trans_ID, String[] newInfo)
	// Date mmddyyyy - ex: 02052010 - no dashes or slashes- fill space with
	// leading zeros
	{
		SQLiteDatabase db = MintLink.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(TRANSACTION_TOACCOUNT, Long.parseLong(newInfo[TO]));
		values.put(TRANSACTION_FROMACCOUNT, Long.parseLong(newInfo[FROM]));
		values.put(TRANSACTION_AMOUNT, Double.parseDouble(newInfo[AMOUNT]));
		values.put(TRANSACTION_CATEGORY, Long.parseLong(newInfo[CATEGORY]));
		values.put(TRANSACTION_NOTE, newInfo[NOTE]);
		values.put(TRANSACTION_DATE, newInfo[DATE]);
		values.put(TRANSACTION_TYPE, TRANS_TYPE_TRANSFER);

		db.update(TRANSACTION_TBLNAM, values,  _ID + "=" + trans_ID, null);
		db.close();
	}
	
	public void updateExpense(long trans_ID, String[] newInfo) {

		SQLiteDatabase db = MintLink.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(TRANSACTION_FROMACCOUNT, Long.parseLong(newInfo[FROM]));
		values.put(TRANSACTION_AMOUNT, Double.parseDouble(newInfo[AMOUNT]));
		values.put(TRANSACTION_NOTE, newInfo[NOTE]);
		values.put(TRANSACTION_CATEGORY, Long.parseLong(newInfo[CATEGORY]));
		values.put(TRANSACTION_DATE, newInfo[DATE]);
		values.put(TRANSACTION_TYPE, TRANS_TYPE_EXPENSE);

		db.update(TRANSACTION_TBLNAM, values,  _ID + "=" + trans_ID, null);
		db.close();
	}

	public void updateIncome(long trans_ID, String[] newInfo) {

		SQLiteDatabase db = MintLink.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(TRANSACTION_TOACCOUNT, Long.parseLong(newInfo[TO]));
		values.put(TRANSACTION_AMOUNT, Double.parseDouble(newInfo[AMOUNT]));
		values.put(TRANSACTION_NOTE, newInfo[NOTE]);
		values.put(TRANSACTION_DATE, newInfo[DATE]);
		values.put(TRANSACTION_TYPE, TRANS_TYPE_INCOME);
		values.put(TRANSACTION_CATEGORY, Long.parseLong(newInfo[CATEGORY]));
		
		db.update(TRANSACTION_TBLNAM, values,  _ID + "=" + trans_ID, null);
		db.close();
	}
	
	public void removeTransaction(long trans_ID)
	{
		SQLiteDatabase db = MintLink.getWritableDatabase();
		
		db.delete(TRANSACTION_TBLNAM, "_ID =" + trans_ID , null);
		db.close();
	}
	/**
	 * Clear Transaction table
	 */
	public void ClearTable()
	{
		SQLiteDatabase db = MintLink.getWritableDatabase();
		db.delete(TRANSACTION_TBLNAM, null, null);
		db.close();
	}
}
