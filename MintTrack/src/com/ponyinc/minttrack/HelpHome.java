package com.ponyinc.minttrack;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;

public class HelpHome extends Activity
{
	 public void onCreate(Bundle savedInstanceState) 
	    {
	       super.onCreate(savedInstanceState);
	       setContentView(R.layout.helph);
	       TextView text = (TextView)findViewById(R.id.help1);
	       TextView text2 = (TextView)findViewById(R.id.help2);
	       
	       text.setText("Help for Home Tab");
	       text2.setText("In the Home Tab you can check your Income Total, Expense Total, the Grant total and the Recent Transactions ");
	       
	    }
	
}
