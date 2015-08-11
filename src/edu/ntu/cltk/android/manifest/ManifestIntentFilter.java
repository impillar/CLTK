package edu.ntu.cltk.android.manifest;


public class ManifestIntentFilter extends ManifestElement {
	
	public final static String TAG = "intent-filter";
	
	private final String name = "intent-filter";
	protected String action;
	protected String category;
	protected String data;
	
	/**
	 * Initialize a ManifestIntentFilter object with action, category and data
	 * @param action
	 * @param category
	 * @param data
	 */
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
	
	@Override
	public boolean equals(Object obj){
		ManifestIntentFilter mif = (ManifestIntentFilter) obj;
		if (action == null){
			if (mif.getAction() != null)
				return false;
		}else{
			if (mif.getAction() == null || !mif.getAction().equals(action))
				return false;
		}
		
		if (category == null){
			if (mif.getCategory() != null)
				return false;
		}else{
			if (mif.getCategory() == null || !mif.getCategory().equals(category))
				return false;
		}
		
		if (data == null){
			if (mif.getData() != null)
				return false;
		}else{
			if (mif.getData() == null || !mif.getData().equals(data))
				return false;
		}
		return true;
	}

}
