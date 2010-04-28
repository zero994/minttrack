package com.ponyinc.minttrack;
import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

/** This class is used to represent the listactivity for reviewing transactions
*	@author Christopher C. Wilkins
*/
public class AuditActivity extends ListActivity {

	public Budget budget;
	public Button btnToDate, btnFromDate, btnQuery, btnEdit, btnDelete;
	private int toYear;
	private int toMonth;
	private int toDay;
	private int fromYear;
	private int fromMonth;
	private int fromDay;
	private long clickItemID;
	static final int TODATE_DIALOG_ID = 0;
	static final int FROMDATE_DIALOG_ID = 1;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		final Calendar c = Calendar.getInstance();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.audit);
		initializeButtons();
		createOnClickListeners();
		budget = new Budget(this);
		// get the current date for both date buttons
		toYear = c.get(Calendar.YEAR);
		toMonth = c.get(Calendar.MONTH);
		toDay = c.get(Calendar.DAY_OF_MONTH);
		fromYear = c.get(Calendar.YEAR);
		fromMonth = c.get(Calendar.MONTH);
		fromDay = c.get(Calendar.DAY_OF_MONTH);
		getListView().setEmptyView(findViewById(R.id.empty));
		updateDisplay();
	}

	/** the callback received when the user "sets" the date in the dialog*/
	private DatePickerDialog.OnDateSetListener ToDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			toYear = year;
			toMonth = monthOfYear;
			toDay = dayOfMonth;
			updateDisplay();
		}
	};
	/** the callback received when the user "sets" the date in the dialog*/
	private DatePickerDialog.OnDateSetListener FromDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			fromYear = year;
			fromMonth = monthOfYear;
			fromDay = dayOfMonth;
			updateDisplay();
		}
	};
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case TODATE_DIALOG_ID:
			return new DatePickerDialog(this, ToDateSetListener, toYear, toMonth,
					toDay);
		case FROMDATE_DIALOG_ID:
			return new DatePickerDialog(this, FromDateSetListener, toYear, toMonth,
					toDay);
		}
		return null;
	}
	private void updateDisplay() {
		btnToDate.setText(new StringBuilder()
				// Month is 0 based so add 1
				.append(toMonth + 1).append("-").append(toDay).append("-")
				.append(toYear).append(" "));
		btnFromDate.setText(new StringBuilder()
		// Month is 0 based so add 1
		.append(fromMonth + 1).append("-").append(fromDay).append("-")
		.append(fromYear).append(" "));
	}
	/** Method used to inflate popup menu at bottom
	*	@return boolean Whether the menu is successfully populated
	* 	@param Menu The many object that you want to populate 
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
	protected void onListItemClick(ListView l, View v, int position, long id) {
		clickItemID = id;
		if(btnEdit == null || btnDelete == null){
		btnEdit = (Button) v.findViewById(R.id.editTransactionBtn);
		btnDelete = (Button) v.findViewById(R.id.deleteTransactionBtn);
		btnEdit.setVisibility(View.VISIBLE);
		btnDelete.setVisibility(View.VISIBLE);
			btnEdit.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					switchTabSpecial(clickItemID);
				}
			});
			btnDelete.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					budget.deleteTransaction(clickItemID);
					Query();
;				}
			});
		}
		else{
			btnEdit.setVisibility(View.GONE);
			btnDelete.setVisibility(View.GONE);
			btnEdit = (Button) v.findViewById(R.id.editTransactionBtn);
			btnDelete = (Button) v.findViewById(R.id.deleteTransactionBtn);
			btnEdit.setVisibility(View.VISIBLE);
			btnDelete.setVisibility(View.VISIBLE);
			btnEdit.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					switchTabSpecial(clickItemID);
				}
			});
			btnDelete.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					budget.deleteTransaction(clickItemID);
					Query();
				}
			});
		}
    }
	
	/* Handles item selections */
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId())
	    {
	    	case (R.id.help):
	    		executeHelpIntent();
	    		return true;
	    	case (R.id.info):
	    		executeInfoIntent();
	    		return true;
	    }
	    return false;
	}
	private void executeHelpIntent()
	{
		 Intent i = new Intent(this, AuditHelp.class);
	     startActivity(i);
	}
	private void executeInfoIntent()
	{
		 Intent i = new Intent(this, AboutUs.class);
	     startActivity(i);
	}
	/**
	 * Method is used to switch tabs nicely
	 */
	public void switchTabSpecial(long l){
		MintTrack ParentActivity;
		ParentActivity = (MintTrack) this.getParent();
		ParentActivity.setTransactionID(l);
		ParentActivity.switchTabSpecial(1);
	}
	/**
	 * Method is used to initialize button object
	 */
	private void initializeButtons(){
		btnToDate = (Button) findViewById(R.id.btnToDate);
		btnFromDate = (Button) findViewById(R.id.btnFromDate);
		btnQuery = (Button) findViewById(R.id.btnQuery);
	}
	
	/**
	 * Method is used to initialize all of the button onClickListeners
	 */
	private void createOnClickListeners(){
		btnToDate.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showDialog(TODATE_DIALOG_ID);
			}
		});
		btnFromDate.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showDialog(FROMDATE_DIALOG_ID);
			}
		});
		btnQuery.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Query();
			}
		});
	}
	void Query()
	{
		Cursor curser;
		String toDate = toYear + String.format("%02d", toMonth) + String.format("%02d", toDay);
		String fromDate = fromYear + String.format("%02d", fromMonth) + String.format("%02d", fromDay);
		long td = Integer.parseInt(toDate);
		long fd = Integer.parseInt(fromDate);
		
		//check to make sure from date is less than too date
		//make sure no date is greater than the current date
		if(td >= fd)
		{
			curser = budget.getTransactions(fromDate,toDate);
			showEvents(curser);
		}
	}
}