package com.ponyinc.minttrack;


import static com.ponyinc.minttrack.Constants.*;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.widget.AdapterView.OnItemSelectedListener;

public class CategoryManager extends Activity {
	private Budget budget;
	private Spinner categorySpinner, categoryTypeSpinner;
	private TextView nameText;
	private Button saveCategory, newCategory, editCategory;
	private CheckBox activateCb;
	private TextView tvCategoryName, tvType, tvActive;
	
	/**mode for manage account*/
	private static final int Default = 1;
	/**mode for editing account	*/
	private static final int Update = 2;
	/**mode for creating new account*/
	private static final int New = 3;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.catmgr);
		budget = new Budget(this);
		
		 findViewById(R.id.new_cat).setOnClickListener(newCategoryListener);
		 findViewById(R.id.edit_cat).setOnClickListener(editCategoryListener);
		 findViewById(R.id.save_cat).setOnClickListener(saveCategoryListener);
		
		setWidgets();
		
		//Fill the spinner for category types
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
	            this, R.array.cattype_array, android.R.layout.simple_spinner_item);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    categoryTypeSpinner.setAdapter(adapter);
	    
	    fillCatDropDown(categorySpinner,REASON_TYPE_INCOME);
	    categorySpinner.setOnItemSelectedListener(spinnerListener);

	    
	}

	
	/**
	 * Initializes all widgets
	 */
	private void setWidgets(){
		newCategory = (Button)findViewById(R.id.new_cat);
		editCategory = (Button)findViewById(R.id.edit_cat);
		categorySpinner = (Spinner)findViewById(R.id.cat_spinner);
		tvCategoryName = (TextView)findViewById(R.id.tv_catname);
		nameText = (EditText)findViewById(R.id.cat_name);
		tvType = (TextView)findViewById(R.id.tv_cattype);
		categoryTypeSpinner = (Spinner)findViewById(R.id.cat_type);
		saveCategory = (Button)findViewById(R.id.save_cat);
		tvActive = (TextView)findViewById(R.id.tv_catactive);
		activateCb = (CheckBox) findViewById(R.id.active_cat);
		setWidgetVisiblity(Default);
	}
	
	/**OnClickListener for New Category button**/
	View.OnClickListener newCategoryListener = new View.OnClickListener()
	{	   
		@Override
		public void onClick(View v) 
		{
			setWidgetVisiblity(New);
		}
	};
	   
	/**OnClickListener for Edit Category button**/
	View.OnClickListener editCategoryListener = new View.OnClickListener()
	{	   
		@Override
		public void onClick(View v) 
		{	
			setWidgetVisiblity(Update);
		}
	};
   
	/**OnClickListener for Save Category button**/
	View.OnClickListener saveCategoryListener = new View.OnClickListener()
	{	   
		@Override
		public void onClick(View v) 
		{
			String name = String.valueOf(nameText.getText());
			if(name.equals("") == false)
			{
				//If a new category is being created
				if(!newCategory.isEnabled()){
					int type = categoryTypeSpinner.getSelectedItemPosition();
					if(activateCb.isChecked())
					{
						budget.addCategory(String.valueOf(nameText.getText()), 0.0, type, true);
					}
					else
					{
						budget.addCategory(String.valueOf(nameText.getText()), 0.0, type, false);
					}
				}
				//On update of pre-existing category
				else
				{
					SimpleCursorAdapter s1 = (SimpleCursorAdapter) categorySpinner.getAdapter();
					
					Cursor catCursor = s1.getCursor();
					catCursor.moveToPosition(categorySpinner.getSelectedItemPosition());
	
					budget.EditCategoryName(catCursor.getInt(catCursor.getColumnIndex(_ID)), String.valueOf(nameText.getText()));
					budget.EditCategoryType(catCursor.getInt(catCursor.getColumnIndex(_ID)), categoryTypeSpinner.getSelectedItemPosition());
					
					if(activateCb.isChecked() == true)
						budget.ReactivateCategory(catCursor.getInt(catCursor.getColumnIndex(_ID)));
					
					else if(activateCb.isChecked() == false)
						budget.DeactivateCategory(catCursor.getInt(catCursor.getColumnIndex(_ID)));
				}
	
				fillCatDropDown(categorySpinner, REASON_TYPE_INCOME);
				setWidgetVisiblity(Default);
			}
			else ;
			//TODO add error message
			
		}
	};
	/**
	 * 
	 */
	AdapterView.OnItemSelectedListener spinnerListener = new OnItemSelectedListener(){

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1,
				int arg2, long arg3) {
			
			SimpleCursorAdapter s = (SimpleCursorAdapter) categorySpinner.getAdapter();
			Cursor spinCoursor = s.getCursor();
			
			spinCoursor.moveToPosition(arg2);
			Cursor cursor = budget.getCategory(spinCoursor.getInt(spinCoursor.getColumnIndex(_ID)));
			cursor.moveToFirst();
			
			String name = cursor.getString(cursor.getColumnIndex(CATEGORY_NAME));
			int type = cursor.getInt(cursor.getColumnIndex(CATEGORY_TYPE));
			String activity = cursor.getString(cursor.getColumnIndex(CATEGORY_ACTIVE));
			
			nameText.setText(name);
			if(type == REASON_TYPE_INCOME){
				categoryTypeSpinner.setSelection(0);
			}
			else {
				categoryTypeSpinner.setSelection(1);
			}
			
			if(activity.equals("active"))
				activateCb.setChecked(true);
			else
				activateCb.setChecked(false);
			
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// nothing needed here
			
		}
	};
	/** Fill in category drop down
	 *  @param s Spinner to be used to fill drop down
	 *  @param type fill for income(0) or expense(1)*/
	public void fillCatDropDown(Spinner s, int type) {
		Cursor cursor = budget.getAllCategorys();
		SimpleCursorAdapter s1 = new SimpleCursorAdapter(this,
				android.R.layout.simple_spinner_item, cursor, new String[] {
						CATEGORY_NAME, _ID }, new int[] { android.R.id.text1,
						android.R.id.text2 });
		s1
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		s.setAdapter(s1);
	}
	/**
	 * @param mode which visibility setting is used
	 * @see Default
	 * @see Update
	 * @see New
	 */
	private void setWidgetVisiblity(int mode)
	{
		switch(mode)
		{
			case(Default):
			{
				newCategory.setEnabled(true);
				editCategory.setEnabled(true);
					
				nameText.setText("");				
				
				categorySpinner.setVisibility(View.GONE);
				tvCategoryName.setVisibility(View.GONE);
				nameText.setVisibility(View.GONE);
				tvType.setVisibility(View.GONE);
				categoryTypeSpinner.setVisibility(View.GONE);
				saveCategory.setVisibility(View.GONE);
				tvActive.setVisibility(View.GONE);
				activateCb.setVisibility(View.GONE);
				break;
			}	
			case(Update):
			{
				spinnerListener.onItemSelected(categorySpinner, null, 0, 0);
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
				break;
			}
			case(New):
			{
				nameText.setText("");
			
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
				break;
			}
			default:
		}			
	}
}
