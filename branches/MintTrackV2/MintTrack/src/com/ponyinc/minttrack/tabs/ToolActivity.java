package com.ponyinc.minttrack.tabs;

import com.ponyinc.minttrack.AboutUs;
import com.ponyinc.minttrack.Budget;
import com.ponyinc.minttrack.R;
import com.ponyinc.minttrack.R.id;
import com.ponyinc.minttrack.R.layout;
import com.ponyinc.minttrack.R.menu;
import com.ponyinc.minttrack.help.HelpTools;
import com.ponyinc.minttrack.tools.AccountManager;
import com.ponyinc.minttrack.tools.CategoryManager;
import com.ponyinc.minttrack.tools.TipCalculator;

import android.app.Activity;

import android.os.Bundle;

import android.content.Intent;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

/**This class implements a set of tools for the user including Account manager, Category manager, 
 * 		and Tip calculator
 * @author Pablo BajoLaso and Jeff Titus
 */
public class ToolActivity extends Activity implements OnClickListener
{

	Budget budget;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tool);
		budget = new Budget(this);
       
        // Set up click listeners for all the buttons
        View tip_button = findViewById(R.id.tip_button);
        tip_button.setOnClickListener(tipCalcListener);
        
        View account_button = findViewById(R.id.manage_accounts);
        account_button.setOnClickListener(manAcctListener);
        
        View category_button = findViewById(R.id.manage_cats);
        category_button.setOnClickListener(manCatListener);
	}
	 
	/**Manage Accounts Button Listener**/
	View.OnClickListener manAcctListener = new View.OnClickListener() {
			
		@Override
		public void onClick(View v) 
		{
			executeAccountManager();
		}
	};
	
	/**Manage Categories Button Listener**/
	View.OnClickListener manCatListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) 
		{
			executeCategoryManager();
		}
	};
	
	/**Tip Calculator Button Listener**/
	View.OnClickListener tipCalcListener = new View.OnClickListener() {
		
		@Override
		 public void onClick(View v) 
		 {
			executeTipCalculator();
		 }
	};
	 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu, menu);
	    return true;
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
		 Intent i = new Intent(this, HelpTools.class);
	     startActivity(i);
	}
	/** Executes Information screen **/
	private void executeInfoIntent()
	{
		 Intent i = new Intent(this, AboutUs.class);
	     startActivity(i);
	}
	/** Executes Tip Calculator Functionality **/
	private void executeTipCalculator()
	{
		Intent calcIntent = new Intent(this, TipCalculator.class);
		startActivity(calcIntent);
	}
	/** Executes Account Manager Functionality **/
	private void executeAccountManager()
	{
		Intent accountIntent = new Intent(this, AccountManager.class);
		startActivity(accountIntent);
	}
	/** Executes Category manager Functionality **/
	private void executeCategoryManager()
	{
		Intent categoryIntent = new Intent(this, CategoryManager.class);
		startActivity(categoryIntent);
	}

	@Override
	public void onClick(View arg0) {
		
	}
			 
	
}
