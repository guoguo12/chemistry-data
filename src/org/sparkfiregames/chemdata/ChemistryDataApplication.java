package org.sparkfiregames.chemdata;

import org.sparkfiregames.chemdata.data.DataManager;

import android.app.Application;

public class ChemistryDataApplication extends Application {
	
	private DataManager data;

	public DataManager getData() {
		return data;
	}

	public void setData(DataManager data) {
		this.data = data;
	}
	
}
