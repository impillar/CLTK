package edu.ntu.cltk.android.manifest;

import edu.ntu.cltk.android.manifest.ManifestElement;

public class ManifestIntentFilter extends ManifestElement {
	
	public final static String TAG = "intent-filter";
	
	private final String name = "intent-filter";
	protected String action;
	protected String category;
	protected String data;
	
	public ManifestIntentFilter(String action, String category, String data){
		this.action = action;
		this.category = category;
		this.data = data;
	}
	
	@Override
	public String getName() {
		return name;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
