package edu.ntu.cltk.data;

import java.util.Map;

public class MapUtil {
	
	/**
	 * Get the count for one map
	 * @param map
	 * @return
	 */
	public static int mapCount(Map<?, ?> map){
		if (map == null)	return 0;
		return map.keySet().size();
	}

}
