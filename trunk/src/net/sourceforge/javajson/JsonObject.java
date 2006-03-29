package net.sourceforge.javajson;

import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import net.sourceforge.javajson.parser.ASTparse;

import net.sourceforge.javajson.parser.JsonParser;
import net.sourceforge.javajson.parser.ParseException;

/**
 * Simpler implementation of Json that throws less exceptions. For all the
 * getXXX method, if the item is not found, it returns null, false, or 0. If
 * they key is null or otherwise invalid, it throws an exception.
 * 
 * @author mdeanda
 */
public class JsonObject implements Iterable<String> {
	private Map<String, JsonValue> map;

	/** Parses a string to a json object. */
	public static JsonObject parse(String input) throws ParseException {
		return parse(new StringReader(input));
	}

	/** Parses a string to a json object. */
	public static JsonObject parse(Reader reader) throws ParseException {
		JsonParser parser = new JsonParser(reader);
		ASTparse root = (ASTparse) parser.parse();
		return root.getJsonObject();
	}

	/** Parses a string to a json object. */
	public static JsonObject parse(InputStream is) throws ParseException {
		JsonParser parser = new JsonParser(is);
		ASTparse root = (ASTparse) parser.parse();
		return root.getJsonObject();
	}

	public JsonObject() {
		map = new HashMap<String, JsonValue>();
	}

	/**
	 * Accumlates multiple values into an array. This is a convenience function
	 * for creating an array, adding it to the object, then adding to the array.
	 * If there is already a value for this key, it will be the first item in
	 * the array
	 * 
	 * @param key
	 * @param val
	 */
	public void accumulate(String key, boolean val) {
		if (!map.containsKey(key)) {
			put(key, new JsonArray());
		} else if (!map.get(key).isJsonArray()) {
			JsonValue old = map.get(key);
			put(key, new JsonArray());
			getJsonArray(key).add(old);
		}
		
		getJsonArray(key).add(val);
	}

	/**
	 * Accumlates multiple values into an array. This is a convenience function
	 * for creating an array, adding it to the object, then adding to the array.
	 * If there is already a value for this key, it will be the first item in
	 * the array
	 * 
	 * @param key
	 * @param val
	 */
	public JsonObject accumulate(String key, double val) {
		if (!map.containsKey(key)) {
			put(key, new JsonArray());
		} else if (!map.get(key).isJsonArray()) {
			JsonValue old = map.get(key);
			put(key, new JsonArray());
			getJsonArray(key).add(old);
		}
		
		getJsonArray(key).add(val);
		return this;
	}

	/**
	 * Accumlates multiple values into an array. This is a convenience function
	 * for creating an array, adding it to the object, then adding to the array.
	 * If there is already a value for this key, it will be the first item in
	 * the array
	 * 
	 * @param key
	 * @param val
	 */
	public JsonObject accumulate(String key, float val) {
		if (!map.containsKey(key)) {
			put(key, new JsonArray());
		} else if (!map.get(key).isJsonArray()) {
			JsonValue old = map.get(key);
			put(key, new JsonArray());
			getJsonArray(key).add(old);
		}
		
		getJsonArray(key).add(val);
		return this;
	}

	/**
	 * Accumlates multiple values into an array. This is a convenience function
	 * for creating an array, adding it to the object, then adding to the array.
	 * If there is already a value for this key, it will be the first item in
	 * the array
	 * 
	 * @param key
	 * @param val
	 */
	public JsonObject accumulate(String key, int val) {
		if (!map.containsKey(key)) {
			put(key, new JsonArray());
		} else if (!map.get(key).isJsonArray()) {
			JsonValue old = map.get(key);
			put(key, new JsonArray());
			getJsonArray(key).add(old);
		}
		
		getJsonArray(key).add(val);
		return this;
	}

	/**
	 * Accumlates multiple values into an array. This is a convenience function
	 * for creating an array, adding it to the object, then adding to the array.
	 * If there is already a value for this key, it will be the first item in
	 * the array
	 * 
	 * @param key
	 * @param val
	 */
	public JsonObject accumulate(String key, JsonArray val) {
		if (!map.containsKey(key)) {
			put(key, new JsonArray());
		} else if (!map.get(key).isJsonArray()) {
			JsonValue old = map.get(key);
			put(key, new JsonArray());
			getJsonArray(key).add(old);
		}
		
		getJsonArray(key).add(val);
		return this;
	}

	/**
	 * Accumlates multiple values into an array. This is a convenience function
	 * for creating an array, adding it to the object, then adding to the array.
	 * If there is already a value for this key, it will be the first item in
	 * the array
	 * 
	 * @param key
	 * @param val
	 */
	public JsonObject accumulate(String key, JsonObject val) {
		if (!map.containsKey(key)) {
			put(key, new JsonArray());
		} else if (!map.get(key).isJsonArray()) {
			JsonValue old = map.get(key);
			put(key, new JsonArray());
			getJsonArray(key).add(old);
		}
		
		getJsonArray(key).add(val);
		return this;
	}

	/**
	 * Accumlates multiple values into an array. This is a convenience function
	 * for creating an array, adding it to the object, then adding to the array.
	 * If there is already a value for this key, it will be the first item in
	 * the array
	 * 
	 * @param key
	 * @param val
	 */
	public JsonObject accumulate(String key, String val) {
		if (!map.containsKey(key)) {
			put(key, new JsonArray());
		} else if (!map.get(key).isJsonArray()) {
			JsonValue old = map.get(key);
			put(key, new JsonArray());
			getJsonArray(key).add(old);
		}
		
		getJsonArray(key).add(val);
		return this;
	}

	public boolean getBoolean(String key) {
		if (map.containsKey(key))
			return map.get(key).getBoolean();
		return false;
	}

	public double getDouble(String key) {
		if (map.containsKey(key))
			return map.get(key).getDouble();
		return 0.0;
	}

	public float getFloat(String key) {
		if (map.containsKey(key))
			return map.get(key).getFloat();
		return 0.0f;
	}

	public int getInt(String key) {
		if (map.containsKey(key))
			return map.get(key).getInt();
		return 0;
	}

	public JsonArray getJsonArray(String key) {
		if (map.containsKey(key))
			return map.get(key).getJsonArray();
		return null;
	}

	public JsonObject getJsonObject(String key) {
		if (map.containsKey(key))
			return map.get(key).getJsonObject();
		return null;
	}

	public long getLong(String key) {
		if (map.containsKey(key))
			return map.get(key).getLong();
		return 0l;
	}

	public String getString(String key) {
		if (map.containsKey(key))
			return map.get(key).getString();
		return null;
	}

	public boolean hasKey(String key) {
		return map.containsKey(key);
	}

	/**
	 * Checks if the item at a key is a boolean. See
	 * {@link JsonValue#isBoolean()} for more information
	 * 
	 * @param key
	 * @return True if the object contains the item and is a boolean
	 */
	public boolean isBoolean(String key) {
		if (map.containsKey(key))
			return map.get(key).isBoolean();
		else
			return false;
	}

	/**
	 * Checks if the item at a key is an array. See
	 * {@link JsonValue#isJsonArray()} for more information
	 * 
	 * @param key
	 * @return True if the object contains the item and is a json array
	 */
	public boolean isJsonArray(String key) {
		if (map.containsKey(key))
			return map.get(key).isJsonArray();
		else
			return false;
	}

	/**
	 * Checks if the item at a key is an object. See
	 * {@link JsonValue#isJsonObject()} for more information
	 * 
	 * @param key
	 * @return True if the object contains the item and is a json object
	 */
	public boolean isJsonObject(String key) {
		if (map.containsKey(key))
			return map.get(key).isJsonObject();
		else
			return false;
	}

	/**
	 * Checks if the item at a key is an number. See
	 * {@link JsonValue#isNumber()} for more information
	 * 
	 * @param key
	 * @return True if the object contains the item and is a number
	 */
	public boolean isNumber(String key) {
		if (map.containsKey(key))
			return map.get(key).isNumber();
		else
			return false;
	}

	/**
	 * Checks if the structure of this json object is similar to that of another
	 * object. It simply checks that each field exists in both objects and that
	 * subobjects (json object or json array) are also similar.
	 * 
	 * @param obj
	 * @return
	 */
	public boolean isSimilar(JsonObject obj) {
		if (obj == null)
			return false;

		Set<String> checked = new HashSet<String>();

		for (String key : this) {
			checked.add(key);
			if (!obj.hasKey(key))
				return false;
			else if (!map.get(key).isSimilar(obj.map.get(key)))
				return false;
		}

		for (String key : obj) {
			if (!checked.contains(key))
				return false;
		}

		return true;
	}

	/**
	 * Checks if the item at a key is an string. See
	 * {@link JsonValue#isString()} for more information
	 * 
	 * @param key
	 * @return True if the object contains the item and is a string
	 */
	public boolean isString(String key) {
		if (map.containsKey(key))
			return map.get(key).isString();
		else
			return false;
	}

	public Iterator<String> iterator() {
		return map.keySet().iterator();
	}

	public void put(String key, Object value) {
		if (value instanceof Boolean)
			put(key, ((Boolean) value).booleanValue());
		else if (value instanceof Double)
			put(key, ((Double) value).doubleValue());
		else if (value instanceof Float)
			put(key, ((Float) value).floatValue());
		else if (value instanceof Integer)
			put(key, ((Integer) value).intValue());
		else if (value instanceof Long)
			put(key, ((Long) value).longValue());
		else if (value instanceof JsonObject)
			put(key, (JsonObject) value);
		else if (value instanceof JsonArray)
			put(key, (JsonArray) value);
		else if (value instanceof String)
			put(key, (String) value);
		else if (value == null)
			put(key, (String) null);
		else
			throw new ClassCastException("Unrecognized class");
	}

	public JsonObject put(String key, boolean value) {
		map.put(key, new JsonValue(value));
		return this;
	}

	public JsonObject put(String key, double value) {
		map.put(key, new JsonValue(value));
		return this;
	}

	public JsonObject put(String key, float value) {
		map.put(key, new JsonValue(value));
		return this;
	}

	public JsonObject put(String key, int value) {
		map.put(key, new JsonValue(value));
		return this;
	}

	public JsonObject put(String key, JsonArray value) {
		map.put(key, new JsonValue(value));
		return this;
	}

	public JsonObject put(String key, JsonObject value) {
		map.put(key, new JsonValue(value));
		return this;
	}

	public JsonObject put(String key, long value) {
		map.put(key, new JsonValue(value));
		return this;
	}

	public JsonObject put(String key, String value) {
		map.put(key, new JsonValue(value));
		return this;
	}

	public int size() {
		return map.size();
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		boolean hadSome = false;
		sb.append("{");
		for (String key : map.keySet()) {
			JsonValue val = map.get(key);
			if (hadSome)
				sb.append(",");
			// TODO: escape keys
			sb.append("\"" + key + "\":");
			sb.append(val);
			hadSome = true;
		}
		sb.append("}");
		return sb.toString();
	}

}
