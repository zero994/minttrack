/**This is the main class for the MintTrack application
 * @author Christopher C. Wilkins
 */
package com.ponyinc.minttrack;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class MintTrack extends TabActivity {

	TabHost tabHost;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Resources res = getResources(); // Resource object to get Drawables
		tabHost = getTabHost(); // The activity TabHost
		TabHost.TabSpec spec; // Resusable TabSpec for each tab
		Intent intent; // Reusable Intent for each tab

		// Create an Intent to launch an Activity for the tab (to be reused)
		intent = new Intent().setClass(this, HomeActivity.class);

		// Initialize a TabSpec for each tab and add it to the TabHost
		spec = tabHost.newTabSpec("Home").setIndicator("Home",
				res.getDrawable(R.drawable.ic_tab_home)).setContent(intent);
		tabHost.addTab(spec);

		// Do the same for the other tabs
		intent = new Intent().setClass(this, EntryActivity.class);
		spec = tabHost.newTabSpec("Entry").setIndicator("Entry",
				res.getDrawable(R.drawable.ic_tab_entry)).setContent(intent);
		tabHost.addTab(spec);

		intent = new Intent().setClass(this, AuditActivity.class);
		spec = tabHost.newTabSpec("Audit").setIndicator("Audit",
				res.getDrawable(R.drawable.ic_tab_audit)).setContent(intent);

		tabHost.addTab(spec);

		intent = new Intent().setClass(this, ToolActivity.class);
		spec = tabHost.newTabSpec("Tools").setIndicator("Tools",
				res.getDrawable(R.drawable.ic_tab_tools)).setContent(intent);

		tabHost.addTab(spec);

		tabHost.setCurrentTab(0);

	}
}