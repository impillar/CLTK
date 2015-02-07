package edu.ntu.cltk.android.manifest;

import java.util.ArrayList;
import java.util.List;

import edu.ntu.cltk.android.manifest.ManifestElement;
import edu.ntu.cltk.android.manifest.ManifestIntentFilter;

public class ManifestProvider extends ManifestElement {

	public final static String TAG = "provider";
	protected List<ManifestIntentFilter> intentFilters = new ArrayList<ManifestIntentFilter>();
	private String name = "provider";
	private String grantUriPermission;
	private String metaData;
	private String pathPermission;
	
	public String getGrantUriPermission() {
		return grantUriPermission;
	}

	public void setGrantUriPermission(String grantUriPermission) {
		this.grantUriPermission = grantUriPermission;
	}

	public String getMetaData() {
		return metaData;
	}

	public void setMetaData(String metaData) {
		this.metaData = metaData;
	}

	public String getPathPermission() {
		return pathPermission;
	}

	public void setPathPermission(String pathPermission) {
		this.pathPermission = pathPermission;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

	public void addIntentFilter(ManifestIntentFilter intentFilter){
		this.intentFilters.add(intentFilter);
	}
	
	@Override
	public String toString(){
		return this.name;
	}
}
