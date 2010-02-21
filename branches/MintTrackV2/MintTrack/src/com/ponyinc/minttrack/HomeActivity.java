package com.ponyinc.minttrack;

import static android.provider.BaseColumns._ID;
import static com.ponyinc.minttrack.Constants.TRANSACTION_AMOUNT;
import static com.ponyinc.minttrack.Constants.TRANSACTION_NOTE;
import android.app.Activity;
import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class HomeActivity extends ListActivity {

	Budget budget;

	public void onCreate(Bundle savedInstanceState) {
		Cursor translist;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);

		budget = new Budget(this);
		try{
			budget.Transfer(1,1,2123.33, "Testing", "01022010");
			translist = budget.getTransactions();
			showEvents(translist);
		}
		finally{}
		
		Cursor AccountCursor =  budget.getAccounts();
		Cursor CategoryCursor = budget.getCategorys();
		
		if (AccountCursor.getCount() == 0)
			budget.addAccount("Miscellaneous", 0.00);
		
		if (CategoryCursor.getCount() == 0){
			budget.addCategory("Miscellaneous", 0.00, 0);
			budget.addCategory("Miscellaneous", 0.00, 1);
		}
		
		CategoryCursor.close();
		AccountCursor.close();
	}
	
	private static int[] TO = { R.id.rowid, R.id.time, R.id.title, };
	private static String[] FROM = { _ID, TRANSACTION_AMOUNT, TRANSACTION_NOTE, };
	
	private void showEvents(Cursor cursor) {
		// Set up data binding
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
				R.layout.audititem, cursor, FROM, TO);
		setListAdapter(adapter);
	}
}
