package com.ponyinc.minttrack;

import static android.provider.BaseColumns._ID;
import static com.ponyinc.minttrack.Constants.*;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
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
//		budget.addCategory("Bills", 0.00, 1);
//		budget.DeactivateAccount(3);
//
		SetWidgets();
		
		mSave.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v)
			{
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
					amount = Double.parseDouble(S_amount);
			
				
				if(!mIncomeButton.isEnabled())
					budget.Income(To_ID, amount, notes, Date, cat_ID);
				else if(!mExpenseButton.isEnabled())
					budget.Expense(From_ID, amount, notes, Date, cat_ID);
				else if(!mTransButton.isEnabled())
					budget.Transfer(To_ID, From_ID, amount, notes, Date, cat_ID);
				else;
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
		fillCatDropDown(mReason);
		fillAccountDropDown(mPaymentType_To);
		fillAccountDropDown(mPaymentType_From);
		// get the current date
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		
		// display the current date (this method is below)
		updateDisplay();
	}
	
	//Create menu
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu, menu);
	    return true;
	}

	// updates the date in the TextView
	private void updateDisplay() {
		mPickDate.setText(new StringBuilder()
				// Month is 0 based so add 1
				.append(mMonth + 1).append("-").append(mDay).append("-")
				.append(mYear).append(" "));
	}

	// the callback received when the user "sets" the date in the dialog
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

	public void fillCatDropDown(Spinner s) {
		Cursor cursor = budget.getCategorys();
		SimpleCursorAdapter s1 = new SimpleCursorAdapter(this,
				android.R.layout.simple_spinner_item, cursor, new String[] {
						CATEGORY_NAME, _ID }, new int[] { android.R.id.text1,
						android.R.id.text2 });
		s1
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		s.setAdapter(s1);
	}
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
}
