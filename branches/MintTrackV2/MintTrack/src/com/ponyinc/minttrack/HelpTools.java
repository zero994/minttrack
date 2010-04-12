package com.ponyinc.minttrack;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
/** Help screen for the Tools activity
 */
public class HelpTools extends Activity
{
	 public void onCreate(Bundle savedInstanceState) 
	    {
	       super.onCreate(savedInstanceState);
	       setContentView(R.layout.helptools);
	       TextView text = (TextView)findViewById(R.id.help1);
	       TextView text2 = (TextView)findViewById(R.id.help2);
	       TextView text3 = (TextView)findViewById(R.id.help3);
	       
	       text.setText("Help for Entry Tab");
	       text2.setText("Components");
	       text3.setText("Manage Accounts buttom - to open the tab manage accounts.\n" +
	       				"Manage Accounts buttom - to open the tab manage categories.\n" +
	       				"Tip Calculator buttom - to open the application Tip Calculator.\n");
	       
	    }
	
}