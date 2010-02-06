package org.ponyinc.minttrack;

import android.database.Cursor;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class CatOnItemSelectedListener implements OnItemSelectedListener {

    public void onItemSelected(AdapterView<?> parent,
        View view, int pos, long id) {
    	if (pos >= 0) {
            Cursor c = (Cursor) parent.getItemAtPosition(pos);
            //mPhone.setText(c.getString(mPhoneColumnIndex));
            Toast.makeText(parent.getContext(), "The planet is " +
        			c.getString(0)
        		  //parent.getItemAtPosition(1).toString()
          , Toast.LENGTH_LONG).show();
        }

    	
    	
    	
    }

    public void onNothingSelected(AdapterView parent) {
      // Do nothing.
    }
}