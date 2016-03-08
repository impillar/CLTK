package edu.ntu.cltk.android.manifest;

public abstract class ManifestElement {

	protected String name;
	
	abstract public String getName();
	
	abstract public String toXml();

}
