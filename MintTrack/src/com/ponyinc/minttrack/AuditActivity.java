package com.ponyinc.minttrack;

import android.app.ListActivity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;
import static com.ponyinc.minttrack.Constants.*;

public class AuditActivity extends ListActivity {

	Budget budget;

	public void onCreate(Bundle savedInstanceState) {
		Cursor test;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.audit);

		budget = new Budget(this);
		try {
			/*
			 * WARNING: (From Wilkins)
			 * This line will crash because no account exist by default.  To test go into the Transfer Method
			 * And comment up the section where it queries the account tables.  
			 * This line is however needed to enter sample transactions.
			 */
			budget.Transfer(1, 1, 2123.33, "Testing", "01022010");
			/*
			 * END OF WARNING
			 */
			test = budget.getTransactions();
			showEvents(test);
		} finally {

		}
	}

	private static int[] TO = { R.id.rowid, R.id.time, R.id.title, };
	private static String[] FROM = { _ID, TRANSACTION_AMOUNT, TRANSACTION_NOTE, };

	private void showEvents(Cursor cursor) {
		// Set up data binding
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
				R.layout.audititem, cursor, FROM, TO);
		setListAdapter(adapter);
	}
	/*
	 * private Cursor getTransactions() { final String[] FROM = { _ID,
	 * TRANSACTION_TOACCOUNT, TRANSACTION_FROMACCOUNT, TRANSACTION_AMOUNT,
	 * TRANSACTION_TYPE }; final String ORDER_BY = _ID + " DESC"; SQLiteDatabase
	 * db = MintLink.getReadableDatabase(); Cursor cursor =
	 * db.query(TRANSACTION_TBLNAM, FROM, null, null, null, null, ORDER_BY);
	 * startManagingCursor(cursor); return cursor; } private void
	 * AddTransfer(int ToAccount_ID, int FromAccount_ID, double Amount, String
	 * Note, String Date) //Date mmddyyyy - ex: 02052010 - no dashes or slashes-
	 * fill space with leading zeros { SQLiteDatabase db =
	 * MintLink.getWritableDatabase(); ContentValues values = new
	 * ContentValues(); values.put(TRANSACTION_TOACCOUNT, ToAccount_ID);
	 * values.put(TRANSACTION_FROMACCOUNT, FromAccount_ID);
	 * values.put(TRANSACTION_AMOUNT, Amount); values.put(TRANSACTION_NOTE,
	 * Note); values.put(TRANSACTION_DATE, Date); values.put(TRANSACTION_TYPE,
	 * "transfer");
	 * 
	 * db.insertOrThrow(TRANSACTION_TBLNAM, null, values);
	 * 
	 * db.close(); }
	 */
}