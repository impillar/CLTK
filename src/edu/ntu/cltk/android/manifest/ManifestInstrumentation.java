package edu.ntu.cltk.android.manifest;

import edu.ntu.cltk.android.manifest.ManifestElement;

public class ManifestInstrumentation extends ManifestElement{

	public final static String TAG = "instrumentation";
	
	private final String name = "instrumentation";
	@Override
	public String getName() {
		return name;
	}
	@Override
	public String toXml() {
		// TODO Auto-generated method stub
		return null;
	}
}
