package com.ponyinc.minttrack;

import static com.ponyinc.minttrack.Constants.*;
import java.util.Calendar;
import android.app.*;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
/**
 * This class represents the entry tab and all of it's pieces and interactions
 * @author Stephan Krach & Christopher Wilkins
 */
public class EntryActivity extends Activity {
//	private TextView mDateDisplay;
	private Button mPickDate, mSave, mIncomeButton, mExpenseButton, mTransButton, mCancelButton;
	private TextView mAmount, mNotes, mtxtPay_To, mtxtPay_From, mtxt_Reason, mWarning;
	private Spinner mReason, mPaymentType_To, mPaymentType_From;
	private int mYear, mMonth, mDay;
	 
	private boolean isUpdate = false;
	private long trans_ID;
	
	Budget budget;

	static final int DATE_DIALOG_ID = 0;
	
	static final int INCOME_MODE = 0;
	static final int EXPENSE_MODE = 1;
	static final int TRANSFER_MODE = 2;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.entry);
		budget = new Budget(this);

		SetWidgets();
		
		setListeners();
		
		fillCatDropDown(mReason, REASON_TYPE_INCOME);
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
	public void onResume()
	{
		super.onResume();
	
		ResetTab();
		
		fillCatDropDown(mReason, REASON_TYPE_INCOME);
		fillAccountDropDown(mPaymentType_To);
		fillAccountDropDown(mPaymentType_From);
		
		setWidgetMode(INCOME_MODE);
		
		MintTrack mt = (MintTrack) this.getParent();
		if(mt.getTransactionID() >= 0){
			trans_ID = mt.getTransactionID();
			setEntryTab(trans_ID);
			mt.setTransactionID(-1);
			mCancelButton.setVisibility(View.VISIBLE);
			mWarning.setText("You are editing a transaction");
			mWarning.setVisibility(View.VISIBLE);
			isUpdate = true; //set to update new
		}
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
		mCancelButton = (Button) findViewById(R.id.cancelButton);
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
		mWarning = (TextView) findViewById(R.id.Warning);
		mWarning.setVisibility(View.VISIBLE);
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
	    		executeHelpIntent();
	    		return true;
	    	case (R.id.info):
	    		executeInfoIntent();
	    		return true;
	    }
	    return false;
	}
	/** Starts Help*/
	private void executeHelpIntent()
	{
		 Intent i = new Intent(this, HelpEntry.class);
	     startActivity(i);
	}
	
	/** Starts Help*/
	private void executeInfoIntent()
	{
		 Intent i = new Intent(this, AboutUs.class);
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
		mIncomeButton.setEnabled(false);
		mExpenseButton.setEnabled(true);
		mTransButton.setEnabled(true);
		mCancelButton.setVisibility(View.GONE);
		isUpdate = false;
		trans_ID = -1;
		updateDisplay();
	}
	
	/**
	 * Get transaction and fill entry tab
	 * @param trans_ID id to be used to fill tab
	 */
	private void setEntryTab(long trans_ID)
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
		int trans_type = cursor_trans.getInt(cursor_trans.getColumnIndex(TRANSACTION_TYPE));
		
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
			if(To_acc_cursor.getInt(To_acc_cursor.getColumnIndex(_ID)) == to_id)
			{
				to_pos = To_acc_cursor.getPosition();
				break;
			}	
		}
		for(int count = 0; From_acc_cursor.getPosition() < From_acc_cursor.getCount(); From_acc_cursor.moveToNext(), count++  )
		{
			if(From_acc_cursor.getInt(From_acc_cursor.getColumnIndex(_ID)) == from_id)
			{
				from_pos = From_acc_cursor.getPosition();
				break;
			}
		}
		for(int count = 0;cat_cursor.getPosition() < cat_cursor.getCount(); cat_cursor.moveToNext(), count++  )
		{
			if(cat_cursor.getInt(cat_cursor.getColumnIndex(_ID)) == cat_id)
			{
				cat_pos = cat_cursor.getPosition();
				break;
			}
		}
		
		mAmount.setText(amount);
		mNotes.setText(note);
		mMonth = Integer.parseInt(date.substring(4, 6));
		mDay = Integer.parseInt(date.substring(6));
		mYear = Integer.parseInt(date.substring(0, 4));
		mReason.setSelection(cat_pos);
		mPaymentType_From.setSelection(from_pos);
		mPaymentType_To.setSelection(to_pos);
		if(trans_type == TRANS_TYPE_INCOME)
			setWidgetMode(INCOME_MODE);
		else if(trans_type == TRANS_TYPE_EXPENSE)
			setWidgetMode(EXPENSE_MODE);
		else if(trans_type == TRANS_TYPE_TRANSFER)
			setWidgetMode(TRANSFER_MODE);
		
		updateDisplay();
	}
	
	void setWidgetMode(int mode)
	{
		switch(mode)
		{
			case(INCOME_MODE):
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
				fillCatDropDown(mReason, REASON_TYPE_INCOME);
				if(!isUpdate)
					mWarning.setVisibility(View.GONE);
				break;
			}
			
			case(EXPENSE_MODE):
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
				fillCatDropDown(mReason, REASON_TYPE_EXPENSE);
				if(!isUpdate)
					mWarning.setVisibility(View.GONE);
				break;
			}
			
			case(TRANSFER_MODE):
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
				if(!isUpdate)
					mWarning.setVisibility(View.GONE);
				break;
			}
			default:
		}
	}
	void setListeners()
	{
		mCancelButton.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v)
			{
				ResetTab();
				//Get rid of message when done editting
				mWarning.setVisibility(View.GONE);
			}
		});
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
					
					int cat_ID = cat_cursor.getInt(cat_cursor.getColumnIndex(_ID));
					int To_ID = To_acc_cursor.getInt(To_acc_cursor.getColumnIndex(_ID));
					int From_ID = From_acc_cursor.getInt(From_acc_cursor.getColumnIndex(_ID));
					String S_amount = String.valueOf(mAmount.getText());
					String Date = mYear + String.format("%02d", mMonth) + String.format("%02d", mDay);
					
					
					String notes = String.valueOf(mNotes.getText());
					
					
					//Formatting
					//No value entered - invalid
					if(S_amount.equals("")){
						mWarning.setText("You have not entered an amount.");
						mWarning.setVisibility(View.VISIBLE);
					}
					//Amount string does not contain a decimal point
					else if(!S_amount.contains(".")){
						S_amount = S_amount + ".00";
					}
					//Amount string only contains a decimal point
					else if(S_amount.endsWith(".") && S_amount.length()==1){
						S_amount = "0.00";
					}
					//Amount string ends with a decimal point
					else if(S_amount.endsWith(".")){
						S_amount = S_amount + ".00";
					}
					
					
					double amount = 0.00;
					
					//Entered 0
					if(S_amount.equals("0.00")){
						mWarning.setText("Please enter a non-zero amount.");
						mWarning.setVisibility(View.VISIBLE);
					}
					//Valid value entered - process
					else if (isValid(S_amount)){
						amount = Math.abs(Double.parseDouble(S_amount));

						if(isUpdate) //update old
						{
							if(!mIncomeButton.isEnabled())
								budget.updateIncome(trans_ID, To_ID, amount, notes, Date, cat_ID);
							else if(!mExpenseButton.isEnabled())
								budget.updateExpense(trans_ID, From_ID, amount, notes, Date, cat_ID);
							else if(!mTransButton.isEnabled())
								budget.updateTransfer(trans_ID, To_ID, From_ID, amount, notes, Date, cat_ID);
							else;
							ResetTab();
							isUpdate = false;//set back to create new
						}
						else//create new
						{
							if(!mIncomeButton.isEnabled())
								budget.Income(To_ID, amount, notes, Date, cat_ID);
							else if(!mExpenseButton.isEnabled())
								budget.Expense(From_ID, amount, notes, Date, cat_ID);
							else if(!mTransButton.isEnabled())
								budget.Transfer(To_ID, From_ID, amount, notes, Date, cat_ID);
							else;
							ResetTab();
						}
						//Get rid of message when done editting
						mWarning.setVisibility(View.GONE);
					}
					//Bad value entered - invalid
					else{
						mWarning.setText("Please enter a valid amount");
						mWarning.setVisibility(View.VISIBLE);
					}
					
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
		});
		mIncomeButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) 
			{
				setWidgetMode(INCOME_MODE);
			}
		});
		mExpenseButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) 
			{
				setWidgetMode(EXPENSE_MODE);
			}
		});
		mTransButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) 
			{
				setWidgetMode(TRANSFER_MODE);
			}
		});
		mPickDate.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID);
			}
		});
	}
	
	/**
     * Checks to see if str is valid input
     * @param str String entered by the user
     * @return true if string is OK, or false if not
     */
    public boolean isValid(String str){
    	for(int c = 0; c < str.length(); c++){
    		if((str.charAt(c) <= 46 || str.charAt(c) >= 58) && (str.charAt(c) != '.'))
    			return false;
    	}
    	return true;
    }
}
