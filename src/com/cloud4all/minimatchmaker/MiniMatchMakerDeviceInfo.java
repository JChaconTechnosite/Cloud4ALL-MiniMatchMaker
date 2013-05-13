package com.cloud4all.minimatchmaker;

import org.json.JSONException;
import org.json.JSONObject;

public class MiniMatchMakerDeviceInfo {

	
	private JSONObject data = null;
	
	public MiniMatchMakerDeviceInfo(JSONObject fromJSONData) {
		data = fromJSONData; 
	}
	
	
	public String getStringValueForKey(String key) {
		try {
		return data.getString(key);
		} catch (JSONException e) {
			return null;
		}
	}
	
	
	
	
	
}
