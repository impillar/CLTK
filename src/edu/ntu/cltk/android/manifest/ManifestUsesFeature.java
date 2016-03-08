package edu.ntu.cltk.android.manifest;

import edu.ntu.cltk.android.manifest.ManifestElement;

public class ManifestUsesFeature extends ManifestElement{

	public final static String TAG = "uses-feature";
	@Override
	public String getName() {
		return this.name;
	}
	
	public ManifestUsesFeature(String name){
		this.name = name;
	}
	
	public String toString(){
		return this.name;
	}

	@Override
	public String toXml() {
		return String.format("<%s android:name=\"%s\" />", ManifestUsesFeature.TAG, name);
	}

}
