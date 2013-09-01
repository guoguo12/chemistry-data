package org.sparkfiregames.chemdata;

import java.util.List;

import org.sparkfiregames.chemdata.data.DataManager;
import org.sparkfiregames.chemdata.data.DataManager.ElementData;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author Allen Guo
 */
public class ListAllActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DataManager dataManager = (DataManager) getIntent().getSerializableExtra("org.sparkfiregames.chemdata.DataManager");
		setContentView(R.layout.list_all);
		ElementDataArrayAdapter adapter = new ElementDataArrayAdapter(this, R.layout.list_all, dataManager.getData());
		ListView list = (ListView) findViewById(R.id.list_listview);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new OnItemClickListener());
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.back, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case R.id.back_menuitem:
				finish();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * @author Allen Guo
	 */
	private class ElementDataArrayAdapter extends ArrayAdapter<ElementData> {

		private LayoutInflater inflater;
		private TextView title;
		private TextView subtitle;

		public ElementDataArrayAdapter(Context context, int textViewResourceId, List<ElementData> objects) {
			super(context, textViewResourceId, objects);
			inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;
			if (view == null) {
				view = inflater.inflate(R.layout.list_line, null);
			}
			ElementData element = getItem(position);
			if (element != null) {
				title = (TextView) view.findViewById(R.id.list_line_title);
				title.setText(element.get("na"));
				subtitle = (TextView) view.findViewById(R.id.list_line_subtitle);
				subtitle.setText(element.get("we"));
			}
			return view;
		}
		
	}
	
	/**
	 * @author Allen Guo
	 */
	private class OnItemClickListener implements AdapterView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Object data = parent.getItemAtPosition(position);
			if (data != null && data instanceof ElementData) {
				ElementData element = (ElementData) data;
				MainActivity.instance.showElementData(element.get("na"));
				finish();
			}
		}
		
	}

}
