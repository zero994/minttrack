package com.ponyinc.minttrack;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class HomeActivity extends Activity {

	Budget budget;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);

		budget = new Budget(this);
	}
}
