package edu.ntu.cltk.android.manifest;

import java.util.ArrayList;
import java.util.List;

import edu.ntu.cltk.android.manifest.ManifestElement;
import edu.ntu.cltk.android.manifest.ManifestIntentFilter;
import edu.ntu.cltk.data.ArrayUtil;

public class ManifestService extends ManifestElement {

	public final static String TAG = "service";

	protected List<ManifestIntentFilter> intentFilters = new ArrayList<ManifestIntentFilter>();
	private String name = "service";
	
	@Override
	public String getName() {
		return name;
	}
	/**
	 * We need to change the format of class from a.b.c.d to a/b/c/d
	 * Because the value from soot is under this format
	 * @param name
	 */
	public void setName(String name){
		this.name = name.replace('.','/');
	}
	
	public void addIntentFilter(ManifestIntentFilter intentFilter){
		this.intentFilters.add(intentFilter);
	}
	
	public void addAllIntentFilters(List<ManifestIntentFilter> intentFilters){
		this.intentFilters.addAll(intentFilters);
	}
	
	@Override
	public String toString(){
		return this.name;
	}
	
	public boolean containsAction(String action){
		for (ManifestIntentFilter filter : intentFilters){
			if (filter.getAllActions().contains(action)){
				return true;
			}
		}
		return false;
	}
	
	public List<String> getAllActions(){
		List<String> actions = new ArrayList<String>();
		for (ManifestIntentFilter filter : intentFilters){
			actions.addAll(filter.getAllActions());
		}
		return actions;
	}
	@Override
	public String toXml() {
		return String.format("<%s android:name=\"%s\">%s</%s>", ManifestService.TAG, this.name, ArrayUtil.serializeArray(intentFilters, ""), ManifestService.TAG);
	}
}
