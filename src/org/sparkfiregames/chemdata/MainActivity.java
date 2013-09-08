package org.sparkfiregames.chemdata;

import java.io.IOException;

import org.sparkfiregames.chemdata.data.DataManager;
import org.sparkfiregames.chemdata.data.DataManager.ElementData;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Chemistry Data is an Android app that provides basic data for all chemical elements.
 * 
 * @author Allen Guo
 */
public class MainActivity extends Activity {

	public static MainActivity instance;
	
	private DataManager dataManager;
	private EditText input;
	private ScrollView scroll;
	private TextView data;
	private TextView details;
	private Button submit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		instance = this;
		dataManager = new DataManager();
		((ChemistryDataApplication) getApplication()).setData(dataManager);
		try {
			dataManager.load(getApplicationContext());
		} catch (IOException e) {
			Toast toast = Toast.makeText(getApplicationContext(), R.string.load_error, Toast.LENGTH_LONG);
			toast.show();
		}
		input = (EditText) findViewById(R.id.input_edittext);
		scroll = (ScrollView) findViewById(R.id.data_scrollview);
		data = (TextView) findViewById(R.id.data_textview);
		details = (TextView) findViewById(R.id.data_details);
		submit = (Button) findViewById(R.id.submit_button);
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				showElementData(input.getText().toString());
			}
			
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
		switch(item.getItemId()) {
			case R.id.about_menuitem:
				intent = new Intent(this, AboutActivity.class);
				startActivityForResult(intent, 1);
				return true;
			case R.id.show_all_menuitem:
				intent = new Intent(this, ListAllActivity.class);
				startActivityForResult(intent, 2);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Shows data for the given input string (atomic number, symbol,
	 * name, or name part of 4+ characters), if possible.
	 */
	public void showElementData(String inputText) {
		ElementData element = dataManager.getData(inputText);
		if (element == null) {
			data.setText(R.string.element_not_found);
			details.setVisibility(View.INVISIBLE);
		} else {
			data.setText(Html.fromHtml(element.toString()));
			details.setVisibility(View.VISIBLE);
			input.setText("");
			closeInputKeyboard();
		}
		scroll.setVisibility(View.VISIBLE);
	}
	
	public void closeInputKeyboard() {
		InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
	}

}
