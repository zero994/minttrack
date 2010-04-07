package com.ponyinc.minttrack;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;

public class HelpTools extends Activity
{
	 public void onCreate(Bundle savedInstanceState) 
	    {
	       super.onCreate(savedInstanceState);
	       setContentView(R.layout.helptools);
	       TextView text = (TextView)findViewById(R.id.help1);
	       TextView text2 = (TextView)findViewById(R.id.help2);
	       
	       text.setText("Help for Entry Tab");
	       text2.setText("The tools tab is a tab to select a tool with a double " +
	       				"click in the bottom of the tool.");
	       
	    }
	
}