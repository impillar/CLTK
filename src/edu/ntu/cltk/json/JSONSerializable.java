package edu.ntu.cltk.json;

import org.json.JSONException;
import org.json.JSONObject;

public interface JSONSerializable {

	public JSONObject dump() throws JSONException;
	
	public void load(JSONObject obj) throws JSONException;
	
}
