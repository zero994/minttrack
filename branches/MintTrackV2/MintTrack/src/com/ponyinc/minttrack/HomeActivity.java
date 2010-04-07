package com.ponyinc.minttrack;

import static com.ponyinc.minttrack.Constants.TRANSACTION_AMOUNT;
import static com.ponyinc.minttrack.Constants.TRANSACTION_DATE;
import static com.ponyinc.minttrack.Constants.TRANSACTION_TYPE;
import com.ponyinc.minttrack.HelpHome;

import java.text.NumberFormat;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class HomeActivity extends Activity {

	Budget budget;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		
		budget = new Budget(this);
		
		Cursor AccountCursor =  budget.getAllAccounts();
		Cursor CategoryCursor = budget.getCategorys();
		
		if (AccountCursor.getCount() == 0){
			budget.addAccount("Savings", 0.00);
			budget.addAccount("Checking", 0.00);
		}
		if (CategoryCursor.getCount() == 0){
			budget.addCategory("Bills", 0.00, 0);
			budget.addCategory("Food", 0.00, 1);
		}
		
		CategoryCursor.close();
		AccountCursor.close();
		
		updateDisplay();
		
		findViewById(R.id.refresh).setOnClickListener(homeOCL);
	}
	
	View.OnClickListener homeOCL = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			updateDisplay();
		}
	};
	/* Handles item selections */
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId())
	    {
	    	case (R.id.help):
	    		executeIntent();
	    		return true;
	    	case (R.id.info):
	    		executeIntent2();
	    		return true;
	   
	   
	    }
	    return false;
	}
	private void executeIntent()
	{
		 Intent i = new Intent(this, HelpHome.class);
	     startActivity(i);
	}
	private void executeIntent2()
	{
		 Intent i = new Intent(this, AboutUs.class);
	     startActivity(i);
	}
	
	 

	
	//Create menu
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu, menu);
	    return true;
	}
	
	//Updates the home screen display
	private void updateDisplay(){
		Cursor TransactionsCursor = budget.getTransactions();
		double d_inTotal = 0;
		double d_exTotal = 0;
		TransactionsCursor.moveToFirst();
		//Search through transactions table and sum up all income and expenses
		while(!TransactionsCursor.isAfterLast())
		{
			if(TransactionsCursor.getInt(TransactionsCursor.getColumnIndex(TRANSACTION_TYPE)) == 0)
				d_inTotal+=TransactionsCursor.getDouble(TransactionsCursor.getColumnIndex(TRANSACTION_AMOUNT));
			else if(TransactionsCursor.getInt(TransactionsCursor.getColumnIndex(TRANSACTION_TYPE)) == 1)
				d_exTotal+=TransactionsCursor.getDouble(TransactionsCursor.getColumnIndex(TRANSACTION_AMOUNT));
			TransactionsCursor.moveToNext();
		}
		//Updates individual data fields
		displayIncomeTotal(d_inTotal);
		displayExpenseTotal(d_exTotal);	
		displayGrandTotal(d_inTotal-d_exTotal);
		displayRecentTransactions(TransactionsCursor);
		
		TransactionsCursor.close();
	}
	
	//Number format (US dollars)
	NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
	//Text field variables
	TextView tv_iTotal;
	TextView tv_eTotal;
	TextView tv_gTotal;
	//Text field string variables holding number formats
	String s_incomeTotal;
	String s_expenseTotal;
	String s_grandTotal;
	//Displays the total value in the income text field
	private void displayIncomeTotal(double iT){
		s_incomeTotal=nf.format(iT);
		tv_iTotal=(TextView)findViewById(R.id.in_total);
		tv_iTotal.setText(s_incomeTotal);
	}
	//Displays the total value in the expense text field
	private void displayExpenseTotal(double eT){
		s_expenseTotal=nf.format(eT);
		tv_eTotal=(TextView)findViewById(R.id.ex_total);
		tv_eTotal.setText(s_expenseTotal);
	}
	//Displays the total value in the grand total text field
	private void displayGrandTotal(double gT){
		s_grandTotal = nf.format(gT);
		tv_gTotal=(TextView)findViewById(R.id.gr_total);
		tv_gTotal.setText(s_grandTotal);
	}
	//Returns transaction type in string form
	private String getTransactionString(int x){
		switch(x)
		{
		case 0:
			return "Income";
		case 1:
			return "Expense";
		case 2:
			return "Transfer";
		}
		return "";
	}
	//Displays recent transactions at bottom of home screen
	private void displayRecentTransactions(Cursor TransactionsCursor){
		//Cursor moved to first item in the table
		TransactionsCursor.moveToLast();
		//Set display information for first list item
		if(!TransactionsCursor.isBeforeFirst()){
			TextView tv_date1 = (TextView)findViewById(R.id.date1);
			tv_date1.setText(getFormattedDate(TransactionsCursor.getString(TransactionsCursor.getColumnIndex(TRANSACTION_DATE))));
			TextView tv_type1 = (TextView)findViewById(R.id.type1);
			tv_type1.setText(getTransactionString(TransactionsCursor.getInt(TransactionsCursor.getColumnIndex(TRANSACTION_TYPE))));
			TextView tv_amt1 = (TextView)findViewById(R.id.amount1);
			tv_amt1.setText("$"+TransactionsCursor.getString(TransactionsCursor.getColumnIndex(TRANSACTION_AMOUNT)));
			TransactionsCursor.moveToPrevious();
		}
		//Set display information for second list item
		if(!TransactionsCursor.isBeforeFirst()){
			TextView tv_date2 = (TextView)findViewById(R.id.date2);
			tv_date2.setText(getFormattedDate(TransactionsCursor.getString(TransactionsCursor.getColumnIndex(TRANSACTION_DATE))));
			TextView tv_type2 = (TextView)findViewById(R.id.type2);
			tv_type2.setText(getTransactionString(TransactionsCursor.getInt(TransactionsCursor.getColumnIndex(TRANSACTION_TYPE))));
			TextView tv_amt2 = (TextView)findViewById(R.id.amount2);
			tv_amt2.setText("$"+TransactionsCursor.getString(TransactionsCursor.getColumnIndex(TRANSACTION_AMOUNT)));
			TransactionsCursor.moveToPrevious();
		}
		//Set display information for third list item
		if(!TransactionsCursor.isBeforeFirst()){
			TextView tv_date3 = (TextView)findViewById(R.id.date3);
			tv_date3.setText(getFormattedDate(TransactionsCursor.getString(TransactionsCursor.getColumnIndex(TRANSACTION_DATE))));
			TextView tv_type3 = (TextView)findViewById(R.id.type3);
			tv_type3.setText(getTransactionString(TransactionsCursor.getInt(TransactionsCursor.getColumnIndex(TRANSACTION_TYPE))));
			TextView tv_amt3 = (TextView)findViewById(R.id.amount3);
			tv_amt3.setText("$"+TransactionsCursor.getString(TransactionsCursor.getColumnIndex(TRANSACTION_AMOUNT)));
			TransactionsCursor.moveToPrevious();
		}
	}
	//Returns the date string in a date-like format (mm/dd/yyyy)
	private String getFormattedDate(String str){
		return str.substring(0,2)+"/"+str.substring(2,4)+"/"+str.substring(4,8);
	}
}
