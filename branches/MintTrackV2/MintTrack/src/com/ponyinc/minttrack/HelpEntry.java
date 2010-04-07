package com.ponyinc.minttrack;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;

public class HelpEntry extends Activity
{
	 public void onCreate(Bundle savedInstanceState) 
	    {
	       super.onCreate(savedInstanceState);
	       setContentView(R.layout.helpentry);
	       TextView text = (TextView)findViewById(R.id.help1);
	       TextView text2 = (TextView)findViewById(R.id.help2);
	       
	       text.setText("Help for Entry Tab");
	       text2.setText("In the Entry Tab you can introduce the amount in the data base. " +
			       		"The first step is select: Income, Expense or Transfer " +
			       		"then you have to select the Payment Type to. " +
			       		"After that you have to select the reason " +
			       		"and finally add a note and click the bottom Save");
	       
	    }
	
}