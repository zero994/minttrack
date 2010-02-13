package com.ponyinc.minttrack;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

public class HomeActivity extends Activity {

	Budget budget;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);

		budget = new Budget(this);
		
		Cursor AccountCursor =  budget.getAccounts();
		Cursor CategoryCursor = budget.getCategorys();
		
		if (AccountCursor.getCount() == 0)
			budget.addAccount("Miscellaneous", 0.00);
		
		if (CategoryCursor.getCount() == 0){
			budget.addCategory("Miscellaneous", 0.00, 0);
			budget.addCategory("Miscellaneous", 0.00, 1);
		}
		
		CategoryCursor.close();
		AccountCursor.close();
	}
}
