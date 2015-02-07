package edu.ntu.cltk.android.manifest;

import edu.ntu.cltk.android.manifest.ManifestElement;

public class ManifestLibrary extends ManifestElement {

	public final static String TAG = "uses-library";

	@Override
	public String getName() {
		return this.name;
	}
	
	public ManifestLibrary(String name){
		this.name = name;
	}

	@Override
	public String toString(){
		return this.name;
	}
}
