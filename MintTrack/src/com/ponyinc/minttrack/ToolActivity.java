/**This class implements a set of tools for the user including Account manager, Category manager, 
 * 		and Tip calculator
 * @author Pablo BajoLaso and Jeff Titus
 */
package com.ponyinc.minttrack;

import android.app.Activity;

import android.os.Bundle;

import android.content.Intent;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;


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
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent acctIntent = new Intent();
			acctIntent.setClassName("com.ponyinc.minttrack", "com.ponyinc.minttrack.AccountManager");
	        startActivity(acctIntent);
		}
	};
	
	/**Manage Categories Button Listener**/
	View.OnClickListener manCatListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent catIntent = new Intent();
			catIntent.setClassName("com.ponyinc.minttrack", "com.ponyinc.minttrack.CategoryManager");
	        startActivity(catIntent);
		}
	};
	
	/**Tip Calculator Button Listener**/
	View.OnClickListener tipCalcListener = new View.OnClickListener() {
		
		@Override
		 public void onClick(View v) 
		 {
			Intent calcIntent = new Intent();
			calcIntent.setClassName("com.ponyinc.minttrack", "com.ponyinc.minttrack.TipCalculator");
	        startActivity(calcIntent);
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
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
			 
	
}
