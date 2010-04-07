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

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tool);
		budget = new Budget(this);
		
		// Bind the action for the save button.
       
        // Set up click listeners for all the buttons
        View tip_button = findViewById(R.id.tip_button);
        tip_button.setOnClickListener(this);
	}
	
	
	 public void onClick(View v) 
	 {
		 	//to put a switch if we have more options.
		 	//v.getId();
	        Intent i = new Intent(this, tipcal.class);
	        startActivity(i);
	 }
	 
	//Create menu
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu, menu);
	    return true;
	}
			 
	/* Handles item selections */
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId())
	    {
	    	case (R.id.help):
	    		executeIntent();
	    		return true;
	   
	    }
	    return false;
	}
	private void executeIntent()
	{
		 Intent i = new Intent(this, HelpTools.class);
	     startActivity(i);
	}
}
