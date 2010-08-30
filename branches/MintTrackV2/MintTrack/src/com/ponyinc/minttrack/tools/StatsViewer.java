package com.ponyinc.minttrack.tools;



import com.ponyinc.minttrack.Budget;
import com.ponyinc.minttrack.R;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class StatsViewer extends ListActivity {
	private Budget budget;
	private int numberOfTransactions;
	private int numberOfCategories;
	private int numberOfAccounts;
	private TextView tvNOT, tvNOC, tvNOA;
//	private ArrayList<Accounts> accountArray = new ArrayList<Accounts>();
//	private ArrayList<Categories> categoryArray = new ArrayList<Categories>();
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.statsviewer);
		budget = new Budget(this);
		tvNOT = (TextView) findViewById(R.id.NUMTRANS);
		tvNOC = (TextView) findViewById(R.id.NUMCATS);
		tvNOA = (TextView) findViewById(R.id.NUMACCTS);
		getNumberOfTransactions();
		getStarRating();
		getNumberOfCategories();
		getCategoryList();
		getNumberOfAccounts();
		getAccountList();
		getListView().setEmptyView(findViewById(R.id.empty));
	}

	/**
	 * Retrieves number of transaction entries in database
	 */
	private void getNumberOfTransactions() {
		Cursor transactionCursor = budget.getTransactions();
		numberOfTransactions = transactionCursor.getCount();
		tvNOT.setText(String.valueOf(numberOfTransactions));
	}

	/**
	 * Get rating based on number of transactions in database
	 */
	private void getStarRating() {
		//Based off of number of transactions
		if(numberOfTransactions >= 100){
			findViewById(R.id.BRONZESTAR).setVisibility(View.VISIBLE);
			findViewById(R.id.SILVERSTAR).setVisibility(View.INVISIBLE);
			findViewById(R.id.GOLDSTAR).setVisibility(View.INVISIBLE);
		}
		else if(numberOfTransactions >= 250){
			findViewById(R.id.BRONZESTAR).setVisibility(View.INVISIBLE);
			findViewById(R.id.SILVERSTAR).setVisibility(View.VISIBLE);
			findViewById(R.id.GOLDSTAR).setVisibility(View.INVISIBLE);
		}
		else if(numberOfTransactions >= 500){
			findViewById(R.id.BRONZESTAR).setVisibility(View.INVISIBLE);
			findViewById(R.id.SILVERSTAR).setVisibility(View.INVISIBLE);
			findViewById(R.id.GOLDSTAR).setVisibility(View.VISIBLE);
		}
		else{
			findViewById(R.id.BRONZESTAR).setVisibility(View.INVISIBLE);
			findViewById(R.id.SILVERSTAR).setVisibility(View.INVISIBLE);
			findViewById(R.id.GOLDSTAR).setVisibility(View.INVISIBLE);
		}
	}

	/**
	 * Retrieves number of categories in the database
	 */
	private void getNumberOfCategories() {
		Cursor categoryCursor = budget.getAllCategorys();
		numberOfCategories = categoryCursor.getCount();
		tvNOC.setText(String.valueOf(numberOfCategories));
	}

	/**
	 * Retrieves a list of all categories in the database
	 */
	private void getCategoryList() {
		Cursor categoryCursor = budget.getAllCategorys();
		categoryCursor.moveToFirst();
		while(!categoryCursor.isAfterLast()){
			//TODO populate list
		}
	}

	/**
	 * Retrieves the number of accounts in the database
	 */
	private void getNumberOfAccounts() {
		Cursor accountCursor = budget.getAllAccounts();
		numberOfAccounts = accountCursor.getCount();
		tvNOA.setText(String.valueOf(numberOfAccounts));
	}

	/**
	 * Retrieves a list of all accounts in the database
	 */
	private void getAccountList() {
		Cursor accountCursor = budget.getAllAccounts();
		accountCursor.moveToFirst();
		while(!accountCursor.isAfterLast()){
			//TODO populate list
		}
		
	}
}
