
package com.ponyinc.minttrack;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;

public class AboutUs extends Activity
{
	 public void onCreate(Bundle savedInstanceState) 
	    {
	       super.onCreate(savedInstanceState);
	       setContentView(R.layout.aboutus);
	       TextView text = (TextView)findViewById(R.id.title);
	       TextView text2 = (TextView)findViewById(R.id.version);
	       TextView text3 = (TextView)findViewById(R.id.names);
	       
	       text.setText("MintTrack");
	       text2.setText("version 1.1");
	       text3.setText("names");
	       
	       
	    }
	
}