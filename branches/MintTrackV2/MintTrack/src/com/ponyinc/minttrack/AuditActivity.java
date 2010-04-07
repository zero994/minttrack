/** This class is used to represent the listactivity for reviewing transactions
*	@author Christopher C. Wilkins
*/
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
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Cursor test;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.audit);

		budget = new Budget(this);
		try {
			test = budget.getTransactions();
			showEvents(test);
		} finally {

		}
	}
	/** Method used to inflate popup menu at bottom
	*	@return boolean Whether the menu is successfully populated
	* 	@param Menu The meny object that you want to populate 
	*/
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu, menu);
	    return true;
	}
	/** Method used to populate the ListActivity from a database Cursor
	*	@param Cursor A cursor containing the rows of the transaction database that you want to display
	*/
	private void showEvents(Cursor cursor) {
		setListAdapter(new AuditCursorAdapter(this, cursor));
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
        //need to interface to stephan's code
    }
}