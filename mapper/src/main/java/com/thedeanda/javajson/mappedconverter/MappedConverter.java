package com.thedeanda.javajson.mappedconverter;

import java.util.Map;

import com.thedeanda.javajson.JsonObject;
import com.thedeanda.javajson.JsonValue;
import com.thedeanda.javajson.converter.Reflection;

public class MappedConverter {
	private JsonObject map;

	public MappedConverter(JsonObject map) {
		this.map = map;
	}

	//not working yet
	private JsonObject map(Object object, String mapKey) throws Exception {
		JsonObject ret = null;
		JsonObject map = this.map.getJsonObject(mapKey);
		if (map != null) {
			ret = new JsonObject();
			// get all field values (fails b/c field names are incorrect, not
			// using "field1" but "getField1"
			Map<String, Object> flds = Reflection.getFields(object);
			for (String key : map) {
				boolean accept = map.getBoolean(key);

				if (!accept)
					continue;
				
				if (flds.containsKey(key)) {
					ret.put(key, flds.get(key));
				}
			}
		}
		return ret;
	}
}