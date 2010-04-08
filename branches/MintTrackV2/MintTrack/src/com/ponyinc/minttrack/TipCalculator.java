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
    TextView total;
    TextView errorString;
    
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.tipcal);
        
       // Find the EditTexts
       bill = (EditText)findViewById(R.id.bill_text);
       tip = (EditText)findViewById(R.id.tip_text);
       split = (EditText)findViewById(R.id.split_text);
       
       // Bind the action for the save button.
       findViewById(R.id.execute_button).setOnClickListener(executeListener);
       findViewById(R.id.clear_button).setOnClickListener(clearListener);
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
           
           errorString = (TextView)findViewById(R.id.error_message);
           
           if(s_bill.equalsIgnoreCase("")){
        	   errorString.setText("X Please enter a value for the bill.");
		   }
		   
		   else if(s_tip.equalsIgnoreCase("")){
			  errorString.setText("X Please enter a value for the tip.");
		   }
		   
		   else if(s_split.equalsIgnoreCase("")){
			  errorString.setText("X Please enter a value for split.");
		   }
           
		   else{
			   errorString.setText("");
	           float f_bill= new Float(s_bill).floatValue(); 
	           float f_tip= new Float(s_tip).floatValue();
	           float f_split= new Float(s_split).floatValue();
	           double f_total=(f_bill+(f_bill*f_tip/100))/f_split;
	
	           // show the string in total editText	        
	           String str = n.format(f_total);
	           total = (TextView)findViewById(R.id.total_text);
	           total.setText(str);
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
    	  total.setText("");
       }
   };
}