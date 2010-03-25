package com.ponyinc.minttrack;

import android.app.ListActivity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ListView;
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
			//budget.Transfer(1, 2, 2123.33, "Testing", "01022010", 1);
			//budget.Transfer(1, 2, 2123.33, "Testing", "01022010", 2);
			test = budget.getTransactions();
	//		test = budget.getActiveAccounts();
			showEvents(test);
		} finally {

		}
	}
	//Create menu
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu, menu);
	    return true;
	}

	private void showEvents(Cursor cursor) {
		// Set up data binding
		//SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
		//		R.layout.audititem, cursor, FROM, TO);
		setListAdapter(new AuditCursorAdapter(this, cursor));
	}
	public void onListItemClick(ListView l, View v, int position, long id) {
        //need to interface to stephan's code
    }
	//private static int[] TO = { R.id.transactionDate, R.id.transactionType, R.id.transactionAmount,R.id.transactionCategory,R.id.transactionNote, };
	//private static String[] FROM = { TRANSACTION_DATE, TRANSACTION_TYPE, TRANSACTION_AMOUNT, "CATNAME", TRANSACTION_NOTE,};
}