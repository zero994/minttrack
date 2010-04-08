package com.ponyinc.minttrack;

import static android.provider.BaseColumns._ID;
import static com.ponyinc.minttrack.Constants.*;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Button;

public class EntryActivity extends Activity {
//	private TextView mDateDisplay;
	private Button mPickDate, mSave, mIncomeButton, mExpenseButton, mTransButton;
	private TextView mAmount, mNotes, mtxtPay_To, mtxtPay_From, mtxt_Reason;
	private Spinner mReason, mPaymentType_To, mPaymentType_From;
	private int mYear;
	private int mMonth;
	private int mDay;
	Budget budget;

	static final int DATE_DIALOG_ID = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.entry);
		budget = new Budget(this);
//		budget.addAccount("TD Bank", 3500.65);
//		budget.addCategory("tax return", 0.00, 0);
//		budget.DeactivateAccount(3);
//
		SetWidgets();
		
		mSave.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v)
			{
				try{
				SimpleCursorAdapter s1 = (SimpleCursorAdapter) mPaymentType_To.getAdapter();
				SimpleCursorAdapter s2 = (SimpleCursorAdapter) mReason.getAdapter();
				SimpleCursorAdapter s3 = (SimpleCursorAdapter) mPaymentType_From.getAdapter();
				
				Cursor To_acc_cursor = s1.getCursor();
				Cursor cat_cursor = s2.getCursor();
				Cursor From_acc_cursor = s3.getCursor();
				
				
				To_acc_cursor.moveToPosition(mPaymentType_To.getSelectedItemPosition());
				cat_cursor.moveToPosition(mReason.getSelectedItemPosition());
				From_acc_cursor.moveToPosition(mPaymentType_From.getSelectedItemPosition());
				
				int cat_ID = cat_cursor.getInt(0);
				int To_ID = To_acc_cursor.getInt(0);
				int From_ID = From_acc_cursor.getInt(0);
				String S_amount = String.valueOf(mAmount.getText());
				String Date = String.format("%02d", mMonth+1) + String.format("%02d", mDay) + mYear;
				String notes = String.valueOf(mNotes.getText());
				
				double amount = 0;					
				if(S_amount.equals(""))
					amount = 0;
				else
					amount = Math.abs(Double.parseDouble(S_amount));
			
				
				if(!mIncomeButton.isEnabled())
					budget.Income(To_ID, amount, notes, Date, cat_ID);
				else if(!mExpenseButton.isEnabled())
					budget.Expense(From_ID, amount, notes, Date, cat_ID);
				else if(!mTransButton.isEnabled())
					budget.Transfer(To_ID, From_ID, amount, notes, Date, cat_ID);
				else;
				ResetTab();
				}catch(Exception e)
				{
					
				}
			}
		});
		mIncomeButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) 
			{
				mIncomeButton.setEnabled(false);
				mExpenseButton.setEnabled(true);
				mTransButton.setEnabled(true);
				mPaymentType_From.setVisibility(View.GONE);
				mtxtPay_From.setVisibility(View.GONE);
				mPaymentType_To.setVisibility(View.VISIBLE);
				mtxtPay_To.setVisibility(View.VISIBLE);
				mReason.setVisibility(View.VISIBLE);
				mtxt_Reason.setVisibility(View.VISIBLE);
				fillCatDropDown(mReason, 0);
			}
		});
		mExpenseButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) 
			{
				mIncomeButton.setEnabled(true);
				mExpenseButton.setEnabled(false);
				mTransButton.setEnabled(true);
				mPaymentType_From.setVisibility(View.VISIBLE);
				mtxtPay_From.setVisibility(View.VISIBLE);
				mPaymentType_To.setVisibility(View.GONE);
				mtxtPay_To.setVisibility(View.GONE);
				mReason.setVisibility(View.VISIBLE);
				mtxt_Reason.setVisibility(View.VISIBLE);
				fillCatDropDown(mReason, 1);
			}
		});
		mTransButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) 
			{
				mIncomeButton.setEnabled(true);
				mExpenseButton.setEnabled(true);
				mTransButton.setEnabled(false);
				mPaymentType_From.setVisibility(View.VISIBLE);
				mtxtPay_From.setVisibility(View.VISIBLE);
				mPaymentType_To.setVisibility(View.VISIBLE);
				mtxtPay_To.setVisibility(View.VISIBLE);
				mReason.setVisibility(View.GONE);
				mtxt_Reason.setVisibility(View.GONE);
			}
		});
		mPickDate.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID);
			}
		});
		
		// Dropdown Pay Type
		fillCatDropDown(mReason, 0);
		fillAccountDropDown(mPaymentType_To);
		fillAccountDropDown(mPaymentType_From);
		// get the current date
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		
		// display the current date (this method is below)
		updateDisplay();
//	setEntryTab(1);
	}
	
	//Create menu
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu, menu);
	    return true;
	}
	public void onResume()
	{
		super.onResume();
	
		ResetTab();
		MintTrack mt = (MintTrack) this.getParent();
		if(mt.getTransactionID() >= 0){
			setEntryTab(mt.getTransactionID());
			mt.setTransactionID(-1);
		}

//		setEntryTab(1);
	}

	/** updates the date in the TextView*/
	private void updateDisplay() {
		mPickDate.setText(new StringBuilder()
				// Month is 0 based so add 1
				.append(mMonth + 1).append("-").append(mDay).append("-")
				.append(mYear).append(" "));
	}

	/** the callback received when the user "sets" the date in the dialog*/
	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			updateDisplay();
		}
	};

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
					mDay);
		}
		return null;
	}
	
	/** Fill in category drop down
	 *  @param s Spinner to be used to fill drop down
	 *  @param type fill for income(0) or expense(1)*/
	public void fillCatDropDown(Spinner s, int type) {
		Cursor cursor = budget.getCategorys(type);
		SimpleCursorAdapter s1 = new SimpleCursorAdapter(this,
				android.R.layout.simple_spinner_item, cursor, new String[] {
						CATEGORY_NAME, _ID }, new int[] { android.R.id.text1,
						android.R.id.text2 });
		s1
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		s.setAdapter(s1);
	}
	
	/** Fill in Account drop down
	 * @param s Spinner to be used to fill drop down*/
	public void fillAccountDropDown(Spinner s) {
		Cursor cursor = budget.getActiveAccounts();
		SimpleCursorAdapter s1 = new SimpleCursorAdapter(this,
				android.R.layout.simple_spinner_item, cursor, new String[] {
				ACCOUNT_NAME, _ID }, new int[] { android.R.id.text1,
						android.R.id.text2 });
		s1
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		s.setAdapter(s1);
	}
	/**Set all widgets for this view */
	private void SetWidgets()
	{
		mSave = (Button) findViewById(R.id.saveButton);
		mPickDate = (Button) findViewById(R.id.pickDate);
		mReason = (Spinner) findViewById(R.id.reason1);
		mPaymentType_To = (Spinner) findViewById(R.id.paytype_To);
		mPaymentType_From = (Spinner) findViewById(R.id.paytype_From);
		mAmount = (TextView) findViewById(R.id.entry1);
		mNotes = (TextView) findViewById(R.id.notes);
		mIncomeButton = (Button) findViewById(R.id.incomeButton);
		mExpenseButton = (Button) findViewById(R.id.expenseButton);
		mTransButton = (Button) findViewById(R.id.transferButton);
		mtxtPay_To = (TextView) findViewById(R.id.txt_type_to);
		mtxtPay_From = (TextView) findViewById(R.id.txt_type_from);
		mtxt_Reason = (TextView) findViewById(R.id.txt_reason);
		mIncomeButton.setEnabled(false);
		mPaymentType_From.setVisibility(View.GONE);
		mtxtPay_From.setVisibility(View.GONE);
	}
	/** Handles item selections
	 * @param item 
	 * @return True if help selected False if not*/
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId())
	    {
	    	case (R.id.help):
	    		executeIntent();
	    		return true;
	   
	    }
	    return false;
	}
	/** Starts Help*/
	private void executeIntent()
	{
		 Intent i = new Intent(this, HelpEntry.class);
	     startActivity(i);
	}
	
	/**
	 * Reset all widgets to default
	 */
	void ResetTab()
	{
		mNotes.setText("");
		mAmount.setText("");
		mReason.setSelection(0);
		mPaymentType_From.setSelection(0);
		mPaymentType_To.setSelection(0);
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		updateDisplay();
	}
	
	/**
	 * Get transaction and fill entry tab
	 * @param trans_ID id to be used to fill tab
	 */
	private void setEntryTab(int trans_ID)
	{
			
		
	//	SetWidgets();
		Cursor cursor_trans = budget.getTransaction(trans_ID);
		cursor_trans.moveToFirst();
		
		String amount = cursor_trans.getString(cursor_trans.getColumnIndexOrThrow(TRANSACTION_AMOUNT));
		String note = cursor_trans.getString(cursor_trans.getColumnIndex(TRANSACTION_NOTE));
		int from_id = cursor_trans.getInt(cursor_trans.getColumnIndex(TRANSACTION_FROMACCOUNT));
		int to_id = cursor_trans.getInt(cursor_trans.getColumnIndex(TRANSACTION_TOACCOUNT));
		int cat_id = cursor_trans.getInt(cursor_trans.getColumnIndex(TRANSACTION_CATEGORY));
		String date = cursor_trans.getString(cursor_trans.getColumnIndex(TRANSACTION_DATE));
		 
		SimpleCursorAdapter from = (SimpleCursorAdapter) mPaymentType_From.getAdapter();
		SimpleCursorAdapter to = (SimpleCursorAdapter) mPaymentType_To.getAdapter();
		SimpleCursorAdapter reason = (SimpleCursorAdapter) mReason.getAdapter();
		
		Cursor To_acc_cursor = to.getCursor();
		To_acc_cursor.moveToFirst();
		Cursor cat_cursor = reason.getCursor();
		cat_cursor.moveToFirst();
		Cursor From_acc_cursor = from.getCursor();
		From_acc_cursor.moveToFirst();
		
		int to_pos = 0;
		int from_pos = 0;
		int cat_pos = 0;
		
		
		for(int count = 0; To_acc_cursor.getPosition() < To_acc_cursor.getCount(); To_acc_cursor.moveToNext(), count++ )
		{
			if(To_acc_cursor.getInt(0) == to_id)
			{
				to_pos = To_acc_cursor.getPosition();
				break;
			}	
		}
		for(int count = 0; From_acc_cursor.getPosition() < From_acc_cursor.getCount(); From_acc_cursor.moveToNext(), count++  )
		{
			if(From_acc_cursor.getInt(0) == from_id)
			{
				from_pos = From_acc_cursor.getPosition();
				break;
			}
		}
		for(int count = 0;cat_cursor.getPosition() < cat_cursor.getCount(); cat_cursor.moveToNext(), count++  )
		{
			if(cat_cursor.getInt(0) == cat_id)
			{
				cat_pos = cat_cursor.getPosition();
				break;
			}
		}
		
		/* fix string date from doping lead zero*/
		
		mAmount.setText(amount);
		mNotes.setText(note);
		mMonth = Integer.parseInt(date.substring(0, 2));
		mDay = Integer.parseInt(date.substring(2, 4));
		mYear = Integer.parseInt(date.substring(4));
		mReason.setSelection(cat_pos);
		mPaymentType_From.setSelection(from_pos);
		mPaymentType_To.setSelection(to_pos);
		
		updateDisplay();
		
		
	}
	
}
