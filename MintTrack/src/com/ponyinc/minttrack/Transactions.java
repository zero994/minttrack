package com.ponyinc.minttrack;

import static android.provider.BaseColumns._ID;
import static com.ponyinc.minttrack.Constants.*;

import java.util.Date;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

/**Interface to transaction table */
public class Transactions {
	/**
	 * hello
	 */
	private MintData MintLink;

	/** Create an object to talk to transaction table
	 *@param mintdata database to connect to
	 */
	Transactions(MintData mintdata) {
		MintLink = mintdata;
	}
	/**Transfer founds from one account to another
	 * @param ToAccount_ID id of account which money will transfered to
	 * @param FromAccount_ID id of account which money will be transfered from
	 * @param Amount the amount to be transfered
	 * @param Note a note by the user
	 * @param Date date transfer took place
	 * @param Category reason for transfer*/
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

	/**Take founds from one account for a given reason
	 * @param FromAccount_ID id of account which money will be taken from
	 * @param Amount the amount to be taken
	 * @param Note a note by the user
	 * @param Date date expense took place
	 * @param Category reason for expense*/
	public void createExpense(int FromAccount_ID, double Amount, String Note,
			String Date, int Category) {

		SQLiteDatabase db = MintLink.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(TRANSACTION_FROMACCOUNT, FromAccount_ID);
		values.put(TRANSACTION_AMOUNT, Amount);
		values.put(TRANSACTION_NOTE, Note);
		values.put(TRANSACTION_CATEGORY, Category);
		values.put(TRANSACTION_DATE, Date);
		values.put(TRANSACTION_TYPE, 1);

		db.insertOrThrow(TRANSACTION_TBLNAM, null, values);
	}
	/**Add founds to one account because of income
	 * @param ToAccount_ID id of account which money will put into
	 * @param Amount the amount to be add
	 * @param Note a note by the user
	 * @param Date date income too place
	 * @param Category reason for income*/
	public void createIncome(int ToAccount_ID, double Amount, String Note,
			String Date, int Category) {

		SQLiteDatabase db = MintLink.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(TRANSACTION_TOACCOUNT, ToAccount_ID);
		values.put(TRANSACTION_AMOUNT, Amount);
		values.put(TRANSACTION_NOTE, Note);
		values.put(TRANSACTION_DATE, Date);
		values.put(TRANSACTION_TYPE, 0);
		values.put(TRANSACTION_CATEGORY, Category);
		
		db.insertOrThrow(TRANSACTION_TBLNAM, null, values);
	}
	/** get all transactions
	 * @return Cursor of transactions
	 */
	public Cursor getTransactions(){
        SQLiteDatabase db = MintLink.getWritableDatabase();
        return db.rawQuery("SELECT " + TRANSACTION_TBLNAM + "._ID, " + TRANSACTION_AMOUNT + ", " + TRANSACTION_NOTE + ", " + TRANSACTION_TYPE + ", " 
        					+ TRANSACTION_DATE + ", " + TRANSACTION_CATEGORY + ", " + TRANSACTION_TOACCOUNT + ", " + TRANSACTION_FROMACCOUNT 
        					+ ", C1." + CATEGORY_NAME + " AS CATNAME, A1." + ACCOUNT_NAME + " AS ACT1NAME, A2." + ACCOUNT_NAME + " AS ACT2NAME FROM " 
        					+ TRANSACTION_TBLNAM + " LEFT JOIN " + ACCOUNT_TBLNAM + " A1 ON " + TRANSACTION_TOACCOUNT + " = A1." + _ID + " LEFT JOIN " + ACCOUNT_TBLNAM 
        					+ " A2 ON " + TRANSACTION_FROMACCOUNT + " = A2." + _ID + " LEFT JOIN " + CATEGORY_TBLNAM + " C1 ON " 
        					+ TRANSACTION_CATEGORY + " = C1." + _ID, null);
	}
	public Cursor getTransactions(String FromDate, String ToDate){
        SQLiteDatabase db = MintLink.getWritableDatabase();
        return db.rawQuery("SELECT " + TRANSACTION_TBLNAM + "._ID, " + TRANSACTION_AMOUNT + ", " + TRANSACTION_NOTE + ", " + TRANSACTION_TYPE + ", " 
        					+ TRANSACTION_DATE + ", " + TRANSACTION_CATEGORY + ", " + TRANSACTION_TOACCOUNT + ", " + TRANSACTION_FROMACCOUNT 
        					+ ", C1." + CATEGORY_NAME + " AS CATNAME, A1." + ACCOUNT_NAME + " AS ACT1NAME, A2." + ACCOUNT_NAME + " AS ACT2NAME FROM " 
        					+ TRANSACTION_TBLNAM + " LEFT JOIN " + ACCOUNT_TBLNAM + " A1 ON " + TRANSACTION_TOACCOUNT + " = A1." + _ID + " LEFT JOIN " + ACCOUNT_TBLNAM 
        					+ " A2 ON " + TRANSACTION_FROMACCOUNT + " = A2." + _ID + " LEFT JOIN " + CATEGORY_TBLNAM + " C1 ON " 
        					+ TRANSACTION_CATEGORY + " = C1." + _ID + " WHERE " + TRANSACTION_DATE + " BETWEEN '" + FromDate + "' AND '" + ToDate + "'" , null);
	}
	/**
	 * 
	 * @param transID
	 * @return cursor of 
	 */
	public Cursor getTransaction(double transID) {
		final String[] FROM = { _ID, TRANSACTION_TOACCOUNT,
				TRANSACTION_FROMACCOUNT, TRANSACTION_AMOUNT, TRANSACTION_TYPE,
				TRANSACTION_DATE, TRANSACTION_CATEGORY, TRANSACTION_NOTE, };
		final String ORDER_BY = _ID + " DESC";

		SQLiteDatabase db = MintLink.getReadableDatabase();

		Cursor cursor = db.query(TRANSACTION_TBLNAM, FROM, "_ID=" + transID, null,
				null, null, ORDER_BY);

		return cursor;
	}
	public void deleteTransaction(double transID){
		double transactionAmount;
		long toAccountID, fromAccountID, categoryID;
		int transactionType;
		final String[] FROMTRANSACTION = { _ID, TRANSACTION_AMOUNT, TRANSACTION_TOACCOUNT,TRANSACTION_FROMACCOUNT, TRANSACTION_TYPE, TRANSACTION_CATEGORY };
		final String[] FROMACCOUNT = { _ID, ACCOUNT_TOTAL };
		final String[] FROMCATEGORY = { _ID, CATEGORY_TOTAL };
		final String ORDER_BY = _ID + " ASC";
		
		SQLiteDatabase db = MintLink.getWritableDatabase();
		Cursor cursor = db.query(TRANSACTION_TBLNAM, FROMTRANSACTION, "_ID =" + transID, null,
				null, null, ORDER_BY);
		cursor.moveToNext();
		
		transactionType = cursor.getInt(cursor.getColumnIndex(TRANSACTION_TYPE));
		toAccountID = cursor.getLong(cursor.getColumnIndex(TRANSACTION_TOACCOUNT));
		fromAccountID = cursor.getLong(cursor.getColumnIndex(TRANSACTION_FROMACCOUNT));
		categoryID = cursor.getLong(cursor.getColumnIndex(TRANSACTION_CATEGORY));
		transactionAmount = cursor.getDouble(cursor.getColumnIndex(TRANSACTION_AMOUNT));
		
		if(transactionType == 0){
			double categoryTotal,accountTotal;
			
			cursor = db.query(ACCOUNT_TBLNAM, FROMACCOUNT, "_ID =" + toAccountID, null,
					null, null, ORDER_BY);
			cursor.moveToNext();
			
			accountTotal = cursor.getLong(cursor.getColumnIndex(ACCOUNT_TOTAL));
			//update account table total
			db.rawQuery("UPDATE " + ACCOUNT_TBLNAM + " SET " + ACCOUNT_TOTAL + " = " + (accountTotal - transactionAmount) + " WHERE _ID=" + toAccountID , null);
			
			cursor = db.query(CATEGORY_TBLNAM, FROMCATEGORY, "_ID =" + categoryID, null,
					null, null, ORDER_BY);
			cursor.moveToNext();
			
			categoryTotal = cursor.getLong(cursor.getColumnIndex(CATEGORY_TOTAL));
			
			//update category table total
			db.rawQuery("UPDATE " + CATEGORY_TBLNAM + " SET " + CATEGORY_TOTAL + " = " + (categoryTotal - transactionAmount) + " WHERE _ID=" + categoryID , null);
			
		}
		else if (transactionType == 1){
			
		}
		else if (transactionType == 2){
			
		}
		else{
			//throw error
		}
		
		//do delete
		db.rawQuery("DELETE FROM " + TRANSACTION_TBLNAM + " WHERE _ID=" + transID, null);
	}
	/**
	 * Clear Transaction table
	 */
	public void ClearTable()
	{
		SQLiteDatabase db = MintLink.getWritableDatabase();
		db.delete(TRANSACTION_TBLNAM, null, null);
	}
}
