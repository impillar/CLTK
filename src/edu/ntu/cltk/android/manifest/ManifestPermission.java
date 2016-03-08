package edu.ntu.cltk.android.manifest;

import edu.ntu.cltk.android.manifest.ManifestElement;

public class ManifestPermission extends ManifestElement {

	public final static String TAG = "permission";
	
	private final String name = "permission";
	
	private String permission;
	
	public ManifestPermission(String permission){
		this.permission = permission;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public String toString(){
		return permission;
	}

	@Override
	public String toXml() {
		return String.format("<%s android:name=\"%s\" />", ManifestPermission.TAG, name);
	}

}
