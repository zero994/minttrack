package com.ponyinc.minttrack.tabs;

import static com.ponyinc.minttrack.Constants.*;

import com.ponyinc.minttrack.AboutUs;
import com.ponyinc.minttrack.Budget;
import com.ponyinc.minttrack.R;
import com.ponyinc.minttrack.R.id;
import com.ponyinc.minttrack.R.layout;
import com.ponyinc.minttrack.R.menu;
import com.ponyinc.minttrack.help.HelpHome;

import java.text.NumberFormat;
import java.util.Locale;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
/** This class implements a summary screen to the MintTrack application.
 *  This summary screen displays an income, expense, and grand total of transactions as well as
 *  a short list of the last four transactions entered.
 * @author Jeff Titus 
 **/
public class HomeActivity extends Activity {

	private Budget budget;
	private TextView tv_date1, tv_type1, tv_amt1, tv_date2, tv_type2, tv_amt2,
			tv_date3, tv_type3, tv_amt3, tv_date4, tv_type4, tv_amt4;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		
		SetTextViews();
		
		budget = new Budget(this);
		
		Cursor AccountCursor =  budget.getActiveAccounts();
		Cursor CategoryCursor = budget.getActiveCategorys();
		
		if (AccountCursor.getCount() == 0){
			budget.addAccount("Savings", 0.00, true);
			budget.addAccount("Checking", 0.00, true);
		}
		if (CategoryCursor.getCount() == 0){
			budget.addCategory("Bills", 0.00, REASON_TYPE_INCOME, true);
			budget.addCategory("Food", 0.00, REASON_TYPE_EXPENSE, true);
		}
		
		CategoryCursor.close();
		AccountCursor.close();
		
		updateDisplay();
	}
	
	@Override
	public void onResume(){
		super.onResume();
		updateDisplay();
	}
	
	@Override
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
	
	/** Executes help functionality **/
	private void executeHelpIntent()
	{
		 Intent i = new Intent(this, HelpHome.class);
	     startActivity(i);
	}
	/** Executes Information screen **/
	private void executeInfoIntent()
	{
		 Intent i = new Intent(this, AboutUs.class);
	     startActivity(i);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu, menu);
	    return true;
	}
	
	/** Updates the display.<br>Called in <b>onCreate()</b> and <b>onResume()</b> **/
	private void updateDisplay(){
		Cursor TransactionsCursor = budget.getTransactions();
		TransactionsCursor.requery();
		double d_inTotal = 0;
		double d_exTotal = 0;
		TransactionsCursor.moveToFirst();
		//Search through transactions table and sum up all income and expenses
		while(!TransactionsCursor.isAfterLast())
		{
			if(TransactionsCursor.getInt(TransactionsCursor.getColumnIndex(TRANSACTION_TYPE)) == TRANS_TYPE_INCOME)
				d_inTotal+=TransactionsCursor.getDouble(TransactionsCursor.getColumnIndex(TRANSACTION_AMOUNT));
			else if(TransactionsCursor.getInt(TransactionsCursor.getColumnIndex(TRANSACTION_TYPE)) == TRANS_TYPE_EXPENSE)
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
	private NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);

	/**Displays the total value in the income text field
	 * @param iT Income total**/
	private void displayIncomeTotal(double iT){
		String s_incomeTotal=nf.format(iT);
		TextView tv_iTotal=(TextView)findViewById(R.id.in_total);
		tv_iTotal.setText(s_incomeTotal);
	}
	/**Displays the total value in the expense text field
	 * @param eT Expense total**/
	private void displayExpenseTotal(double eT){
		String s_expenseTotal=nf.format(eT);
		TextView tv_eTotal=(TextView)findViewById(R.id.ex_total);
		tv_eTotal.setText(s_expenseTotal);
	}
	/**Displays the total value in the grand total text field
	 * @param gT Grand total**/
	private void displayGrandTotal(double gT){
		String s_grandTotal = nf.format(gT);
		TextView tv_gTotal=(TextView)findViewById(R.id.gr_total);
		tv_gTotal.setText(s_grandTotal);
	}
	/**Returns the transaction type in string form
	 * @param transRefNum Transaction reference number
	 * @return The transaction type as a String
	 */
	private String getTransactionString(int transRefNum){
		switch(transRefNum)
		{
		case TRANS_TYPE_INCOME:
			return "Income";
		case TRANS_TYPE_EXPENSE:
			return "Expense";
		case TRANS_TYPE_TRANSFER:
			return "Transfer";
		}
		return "";
	}
	private void SetTextViews()
	{
		tv_date1 = (TextView)findViewById(R.id.date1);
		tv_type1 = (TextView)findViewById(R.id.type1);
		tv_amt1 = (TextView)findViewById(R.id.amount1);
		tv_date2 = (TextView)findViewById(R.id.date2);
		tv_type2 = (TextView)findViewById(R.id.type2);
		tv_amt2 = (TextView)findViewById(R.id.amount2);
		tv_date3 = (TextView)findViewById(R.id.date3);
		tv_type3 = (TextView)findViewById(R.id.type3);
		tv_amt3 = (TextView)findViewById(R.id.amount3);
		tv_date4 = (TextView)findViewById(R.id.date4);
		tv_type4 = (TextView)findViewById(R.id.type4);
		tv_amt4 = (TextView)findViewById(R.id.amount4);
	}
	
	
	/**Displays recent transactions at the bottom of the home tab
	 * 
	 * @param TransactionsCursor Iterator used to parse database entries
	 */
	private void displayRecentTransactions(Cursor TransactionsCursor){

		
		//Clean up display before generation
		tv_date1.setText("");
		tv_type1.setText("");
		tv_amt1.setText("");
		tv_date2.setText("");
		tv_type2.setText("");
		tv_amt2.setText("");
		tv_date3.setText("");
		tv_type3.setText("");
		tv_amt3.setText("");
		tv_date4.setText("");
		tv_type4.setText("");
		tv_amt4.setText("");
		
		//Cursor moved to first item in the table
		TransactionsCursor.moveToLast();
		//Set display information for first list item
		if(!TransactionsCursor.isBeforeFirst()){
			tv_date1.setText(getFormattedDate(TransactionsCursor.getString(TransactionsCursor.getColumnIndex(TRANSACTION_DATE))));
			tv_type1.setText(getTransactionString(TransactionsCursor.getInt(TransactionsCursor.getColumnIndex(TRANSACTION_TYPE))));
			tv_amt1.setText("$"+TransactionsCursor.getString(TransactionsCursor.getColumnIndex(TRANSACTION_AMOUNT)));
			TransactionsCursor.moveToPrevious();
		}
		//Set display information for second list item
		if(!TransactionsCursor.isBeforeFirst()){
			tv_date2.setText(getFormattedDate(TransactionsCursor.getString(TransactionsCursor.getColumnIndex(TRANSACTION_DATE))));
			tv_type2.setText(getTransactionString(TransactionsCursor.getInt(TransactionsCursor.getColumnIndex(TRANSACTION_TYPE))));
			tv_amt2.setText("$"+TransactionsCursor.getString(TransactionsCursor.getColumnIndex(TRANSACTION_AMOUNT)));
			TransactionsCursor.moveToPrevious();
		}
		//Set display information for third list item
		if(!TransactionsCursor.isBeforeFirst()){
			tv_date3.setText(getFormattedDate(TransactionsCursor.getString(TransactionsCursor.getColumnIndex(TRANSACTION_DATE))));
			tv_type3.setText(getTransactionString(TransactionsCursor.getInt(TransactionsCursor.getColumnIndex(TRANSACTION_TYPE))));
			tv_amt3.setText("$"+TransactionsCursor.getString(TransactionsCursor.getColumnIndex(TRANSACTION_AMOUNT)));
			TransactionsCursor.moveToPrevious();
		}
		//Set display information for fourth list item
		if(!TransactionsCursor.isBeforeFirst()){
			
			tv_date4.setText(getFormattedDate(TransactionsCursor.getString(TransactionsCursor.getColumnIndex(TRANSACTION_DATE))));
			
			tv_type4.setText(getTransactionString(TransactionsCursor.getInt(TransactionsCursor.getColumnIndex(TRANSACTION_TYPE))));
			
			tv_amt4.setText("$"+TransactionsCursor.getString(TransactionsCursor.getColumnIndex(TRANSACTION_AMOUNT)));
		}
	}
	/**Returns the date string in a date-like format (mm/dd/yyyy)
	 * @param str An unformatted date string
	 * @return String that shows the date of the transaction in mm/dd/yyyy format
	 */
	private String getFormattedDate(String str){
		String newMonth = "";
		int monthChange = Integer.parseInt(str.substring(4,6)) + 1;
		if(monthChange < 10)
			newMonth = "0" + String.valueOf(monthChange);
		else
			newMonth = String.valueOf(monthChange);
		
		return newMonth+"/"+str.substring(6)+"/"+str.substring(0,4);
	}
}
