/**This class implements the Tip Calculator when called from the Tip Calculator button in the Tools Tab
 * @author Pablo BajoLaso and Jeff Titus
 */
package com.ponyinc.minttrack;

import java.text.NumberFormat;
import java.util.Locale;

import android.app.Activity;

import android.view.View;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.ponyinc.minttrack.R;

public class TipCalculator extends Activity
{
	EditText bill;
    EditText tip;
    EditText split;
    TextView totalCheck;
    TextView checkPerPerson;
    TextView errorString;
    
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.tipcal);
        
       // Find the EditTexts and TextViews
       bill = (EditText)findViewById(R.id.bill_text);
       tip = (EditText)findViewById(R.id.tip_text);
       split = (EditText)findViewById(R.id.split_text);
       totalCheck = (TextView)findViewById(R.id.total_check);
       checkPerPerson = (TextView)findViewById(R.id.total_per_person);
       errorString = (TextView)findViewById(R.id.error_message);
       
       // Bind the action for the save button.
       findViewById(R.id.execute_button).setOnClickListener(executeListener);
       findViewById(R.id.clear_button).setOnClickListener(clearListener);
    }
    
    /**
     * Checks to see if str is valid input
     * @param str String entered by the user
     * @return true if string is OK, or false if not
     */
    public boolean isValid(String str){
    	for(int c = 0; c < str.length(); c++){
    		if((str.charAt(c) <= 46 || str.charAt(c) >= 58) && (str.charAt(c) != '.'))
    			return false;
    	}
    	return true;
    }
	
   /**OnClickListener for Execute button**/
   View.OnClickListener executeListener = new View.OnClickListener()
   {	   
	   @Override
       public void onClick(View v) 
       {		   
    	   NumberFormat n = NumberFormat.getCurrencyInstance(Locale.US);
           
           String s_bill= bill.getText().toString();
           String s_tip= tip.getText().toString();
           String s_split= split.getText().toString();
           
           if(s_bill.equalsIgnoreCase("")){
        	   errorString.setText("X Please enter a value for the bill.");
		   }
           else if (!isValid(s_bill)){
        	   errorString.setText("X Bill input is not valid.");
           }
		   
		   else if(s_tip.equalsIgnoreCase("")){
			  errorString.setText("X Please enter a value for the tip.");
		   }
		   else if (!isValid(s_tip)){
        	   errorString.setText("X Tip input is not valid.");
           }
		   
		   else if(s_split.equalsIgnoreCase("")){
			  errorString.setText("X Please enter a value for split.");
		   }
		   else if (!isValid(s_split)){
        	   errorString.setText("X Split input is not valid.");
           }
           
		   else{
			   errorString.setText("");
	           float f_bill= new Float(s_bill).floatValue(); 
	           float f_tip= new Float(s_tip).floatValue();
	           float f_split= new Float(s_split).floatValue();
	           double d_total=(f_bill+(f_bill*f_tip/100));
	           double d_perPerson = d_total/f_split;
	           // show the string in total editText	        
	           String s_totalBill = n.format(d_total);
	           String s_totalPerPerson = n.format(d_perPerson);
	           totalCheck.setText(s_totalBill);
	           checkPerPerson.setText(s_totalPerPerson);
		   }
       }
   };
   
   /**OnClickListener for Clear button**/
   View.OnClickListener clearListener = new View.OnClickListener()
   {	   
	   @Override
       public void onClick(View v) 
       {		   
		    errorString.setText("");
		    bill.setText("");
	 	  	tip.setText("");
	 	  	split.setText("");
	 	  	totalCheck.setText("");
	 	  	checkPerPerson.setText("");
       }
   };
}