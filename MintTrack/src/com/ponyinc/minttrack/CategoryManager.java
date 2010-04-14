package com.ponyinc.minttrack;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class CategoryManager extends Activity {
	private Budget budget;
	private Spinner categorySpinner, categoryTypeSpinner;
	private EditText nameText;
	private Button saveCategory, newCategory, editCategory;
	private CheckBox activateCb;
	private TextView tvCategoryName, tvType, tvActive;
	private Categories categoryAccessor;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.catmgr);
		budget = new Budget(this);
		
		 findViewById(R.id.new_cat).setOnClickListener(newCategoryListener);
		 findViewById(R.id.edit_cat).setOnClickListener(editCategoryListener);
		 findViewById(R.id.save_cat).setOnClickListener(saveCategoryListener);
		
		setWidgets();
		
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
	            this, R.array.cattype_array, android.R.layout.simple_spinner_item);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    categoryTypeSpinner.setAdapter(adapter);
	}

	
	/**
	 * Initializes all widgets
	 */
	private void setWidgets(){
		newCategory = (Button)findViewById(R.id.new_cat);
		editCategory = (Button)findViewById(R.id.edit_cat);
		categorySpinner = (Spinner)findViewById(R.id.cat_spinner);
		categorySpinner.setVisibility(View.GONE);
		tvCategoryName = (TextView)findViewById(R.id.tv_catname);
		tvCategoryName.setVisibility(View.GONE);
		nameText = (EditText)findViewById(R.id.cat_name);
		nameText.setVisibility(View.GONE);
		tvType = (TextView)findViewById(R.id.tv_cattype);
		tvType.setVisibility(View.GONE);
		categoryTypeSpinner = (Spinner)findViewById(R.id.cat_type);
		categoryTypeSpinner.setVisibility(View.GONE);
		saveCategory = (Button)findViewById(R.id.save_cat);
		saveCategory.setVisibility(View.GONE);
		tvActive = (TextView)findViewById(R.id.tv_catactive);
		tvActive.setVisibility(View.GONE);
		activateCb = (CheckBox)findViewById(R.id.active_cat);
		activateCb.setVisibility(View.GONE);
	}
	
	/**OnClickListener for New Category button**/
	View.OnClickListener newCategoryListener = new View.OnClickListener()
	{	   
		@Override
		public void onClick(View v) 
		{		   
			newCategory.setEnabled(false);
			editCategory.setEnabled(true);
			categorySpinner.setVisibility(View.GONE);
			tvCategoryName.setVisibility(View.VISIBLE);
			nameText.setVisibility(View.VISIBLE);
			tvType.setVisibility(View.VISIBLE);
			categoryTypeSpinner.setVisibility(View.VISIBLE);
			saveCategory.setVisibility(View.VISIBLE);
			tvActive.setVisibility(View.VISIBLE);
			activateCb.setVisibility(View.VISIBLE);
		}
	};
	   
	/**OnClickListener for Edit Category button**/
	View.OnClickListener editCategoryListener = new View.OnClickListener()
	{	   
		@Override
		public void onClick(View v) 
		{		   
			newCategory.setEnabled(true);
			editCategory.setEnabled(false);
			categorySpinner.setVisibility(View.VISIBLE);
			tvCategoryName.setVisibility(View.VISIBLE);
			nameText.setVisibility(View.VISIBLE);
			tvType.setVisibility(View.VISIBLE);
			categoryTypeSpinner.setVisibility(View.VISIBLE);
			saveCategory.setVisibility(View.VISIBLE);
			tvActive.setVisibility(View.VISIBLE);
			activateCb.setVisibility(View.VISIBLE);
		}
	};
   
	/**OnClickListener for Save Category button**/
	View.OnClickListener saveCategoryListener = new View.OnClickListener()
	{	   
		@Override
		public void onClick(View v) 
		{
			//If a new category is being created
/*			if(!newCategory.isEnabled()){
				SimpleCursorAdapter catAdapter = (SimpleCursorAdapter) categoryTypeSpinner.getAdapter();
				Cursor catTypeCursor = catAdapter.getCursor();
				
				String name = nameText.getText().toString();
				int type = categoryTypeSpinner.getSelectedItemPosition();
				categoryAccessor.addCategory(name, 0.0, type);
			}
			//On update of pre-existing category
			else{
				SimpleCursorAdapter s1 = (SimpleCursorAdapter) categorySpinner.getAdapter();
				SimpleCursorAdapter s2 = (SimpleCursorAdapter) categoryTypeSpinner.getAdapter();
				
				Cursor catCursor = s1.getCursor();
				Cursor catTypeCursor = s2.getCursor();
			}
*/			//For all cases
			newCategory.setEnabled(true);
			editCategory.setEnabled(true);
			categorySpinner.setVisibility(View.GONE);
			tvCategoryName.setVisibility(View.GONE);
			nameText.setText("");
			nameText.setVisibility(View.GONE);
			tvType.setVisibility(View.GONE);
			categoryTypeSpinner.setVisibility(View.GONE);
			saveCategory.setVisibility(View.GONE);
			tvActive.setVisibility(View.GONE);
			activateCb.setChecked(true);
			activateCb.setVisibility(View.GONE);
		}
	};
}
