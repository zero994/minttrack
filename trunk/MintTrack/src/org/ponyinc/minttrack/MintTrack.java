package org.ponyinc.minttrack;

import android.app.TabActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TabHost;

public class MintTrack extends TabActivity {
    /** Called when the activity is first created. */
	TabHost mTabHost;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mTabHost = getTabHost();
        
        mTabHost.addTab(mTabHost.newTabSpec("Home").setIndicator("", getResources().getDrawable(R.drawable.homebtn)).setContent(R.id.tab1));
        mTabHost.addTab(mTabHost.newTabSpec("Transactions").setIndicator("", getResources().getDrawable(R.drawable.transactionbtn)).setContent(R.id.tab2));
        mTabHost.addTab(mTabHost.newTabSpec("Audit").setIndicator("", getResources().getDrawable(R.drawable.auditbtn)).setContent(R.id.tab3));
        mTabHost.addTab(mTabHost.newTabSpec("Tools").setIndicator("", getResources().getDrawable(R.drawable.toolsbtn)).setContent(R.id.tab4));
        mTabHost.setCurrentTab(0);
        
        //Button
        final ImageButton button = (ImageButton) findViewById(R.id.android_button);
        
        //Dropdown
        Spinner s = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(
                this, R.array.planets, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        
        
    }
}