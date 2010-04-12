
package com.ponyinc.minttrack;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;
/**This class implements the about us screen displayed after clicking the Information button in the options menu
 * @author Pablo BajoLaso
 */
public class AboutUs extends Activity
{
	 @Override
	 public void onCreate(Bundle savedInstanceState) 
	    {
	       super.onCreate(savedInstanceState);
	       setContentView(R.layout.aboutus);
	       
	      
	       TextView txttittle = (TextView)findViewById(R.id.title);
	       TextView txtversion = (TextView)findViewById(R.id.version);
	       TextView txtnames = (TextView)findViewById(R.id.names);
	       TextView txtwebsite = (TextView)findViewById(R.id.website);
	     
	       
	       txttittle.setText("MintTrack");
	       txtversion.setText("version 1.1");
	       txtnames.setText("Stephan Krach\n" +
	       					"Christopher Wilkins\n" +
	       					"Jeff Titus\n" +
	       					"Pablo Bajo Laso");
	       txtwebsite.setText("www.minttrack.com");
	       
	    }
	
}