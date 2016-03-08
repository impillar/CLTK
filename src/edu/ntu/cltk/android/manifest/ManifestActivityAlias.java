package edu.ntu.cltk.android.manifest;


public class ManifestActivityAlias extends ManifestActivity {

	public final static String TAG = "activity-alias";
	
	private final String name = "activity-alias";
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public String toString(){
		return name;
	}
	
	@Override
	public String toXml(){
		return String.format("<%s android:name=\"%s\"></%s>", ManifestActivityAlias.TAG, name, ManifestActivityAlias.TAG);
	}

}
