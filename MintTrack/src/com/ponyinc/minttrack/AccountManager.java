package com.ponyinc.minttrack;

import static com.ponyinc.minttrack.Constants.*;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.widget.AdapterView.OnItemSelectedListener;

public class AccountManager extends Activity {
	private Budget budget;
	private Spinner accountSpinner;
	private TextView nameText, balText;
	private Button saveButton, newAccount, editAccount;
	private CheckBox activateCb;
	private TextView tvAccountName, tvBalance, tvActive;
	private EntryActivity eA;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.acctmgr);
		budget = new Budget(this);
		
		 findViewById(R.id.new_acct).setOnClickListener(newAccountListener);
		 findViewById(R.id.edit_acct).setOnClickListener(editAccountListener);
		 findViewById(R.id.save_acct).setOnClickListener(saveAccountListener);
		
		setWidgets();
	
		fillAccountDropDown(accountSpinner);
		accountSpinner.setOnItemSelectedListener(spinnerListener);
	}
	
	private void setWidgets(){
		newAccount = (Button)findViewById(R.id.new_acct);
		editAccount = (Button)findViewById(R.id.edit_acct);
		accountSpinner = (Spinner)findViewById(R.id.acct_spinner);
		accountSpinner.setVisibility(View.GONE);
		tvAccountName = (TextView)findViewById(R.id.tv_acctname);
		tvAccountName.setVisibility(View.GONE);
		nameText = (EditText)findViewById(R.id.acct_name);
		nameText.setVisibility(View.GONE);
		tvBalance = (TextView)findViewById(R.id.tv_balance);
		tvBalance.setVisibility(View.GONE);
		balText = (EditText)findViewById(R.id.acct_bal);
		balText.setVisibility(View.GONE);
		tvActive = (TextView)findViewById(R.id.tv_active);
		tvActive.setVisibility(View.GONE);
		activateCb = (CheckBox)findViewById(R.id.active_acct);
		activateCb.setVisibility(View.GONE);
		saveButton = (Button)findViewById(R.id.save_acct);
		saveButton.setVisibility(View.GONE);
	}
	
	/**OnClickListener for New Account button**/
	View.OnClickListener newAccountListener = new View.OnClickListener()
	{	   
		@Override
		public void onClick(View v) 
		{
			newAccount.setEnabled(false);
			editAccount.setEnabled(true);
			accountSpinner.setVisibility(View.GONE);
			tvAccountName.setVisibility(View.VISIBLE);
			nameText.setVisibility(View.VISIBLE);
			tvBalance.setVisibility(View.VISIBLE);
			balText.setVisibility(View.VISIBLE);
			saveButton.setVisibility(View.VISIBLE);
			tvActive.setVisibility(View.VISIBLE);
			activateCb.setVisibility(View.VISIBLE);
		}
	};
	   
	/**OnClickListener for Edit Account button**/
	View.OnClickListener editAccountListener = new View.OnClickListener()
	{	   
		@Override
		public void onClick(View v) 
		{
			newAccount.setEnabled(true);
			editAccount.setEnabled(false);
			accountSpinner.setVisibility(View.VISIBLE);
			tvAccountName.setVisibility(View.VISIBLE);
			nameText.setVisibility(View.VISIBLE);
			tvBalance.setVisibility(View.VISIBLE);
			balText.setVisibility(View.VISIBLE);
			saveButton.setVisibility(View.VISIBLE);
			tvActive.setVisibility(View.VISIBLE);
			activateCb.setVisibility(View.VISIBLE);
		}
	};
   
	/**OnClickListener for Save Account button**/
	View.OnClickListener saveAccountListener = new View.OnClickListener()
	{	   
		@Override
		public void onClick(View v) 
		{		   
			newAccount.setEnabled(true);
			editAccount.setEnabled(true);
			accountSpinner.setVisibility(View.GONE);
			tvAccountName.setVisibility(View.GONE);
			nameText.setVisibility(View.GONE);
			tvBalance.setVisibility(View.GONE);
			balText.setVisibility(View.GONE);
			saveButton.setVisibility(View.GONE);
			tvActive.setVisibility(View.GONE);
			activateCb.setVisibility(View.GONE);
			
			if(!newAccount.isEnabled())
				budget.addAccount(String.valueOf(nameText.getText()), Double.parseDouble(String.valueOf(balText.getText())));
			else if(!editAccount.isEnabled())
				//TODO
				;
			fillAccountDropDown(accountSpinner);
		}
	};

	AdapterView.OnItemSelectedListener spinnerListener = new OnItemSelectedListener(){

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1,
				int arg2, long arg3) {
			
			SimpleCursorAdapter s = (SimpleCursorAdapter) accountSpinner.getAdapter();
			Cursor spinCoursor = s.getCursor();
			
	//		spinCoursor.moveToPosition(accountSpinner.getSelectedItemPosition());
			spinCoursor.moveToPosition(arg2);
			Cursor cursor = budget.getAccount(spinCoursor.getInt(0));
			cursor.moveToFirst();
			
			String name = cursor.getString(cursor.getColumnIndex(ACCOUNT_NAME));
			String amount = cursor.getString(cursor.getColumnIndex(ACCOUNT_TOTAL));
			
			nameText.setText(name);
			balText.setText(amount);
			
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	};
	
	/** Fill in Account drop down
	 * @param s Spinner to be used to fill drop down*/
	public void fillAccountDropDown(Spinner s) {
		Cursor cursor = budget.getAllAccounts();
		SimpleCursorAdapter s1 = new SimpleCursorAdapter(this,
				android.R.layout.simple_spinner_item, cursor, new String[] {
				ACCOUNT_NAME, _ID }, new int[] { android.R.id.text1,
						android.R.id.text2 });
		s1
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		s.setAdapter(s1);
	}
}
