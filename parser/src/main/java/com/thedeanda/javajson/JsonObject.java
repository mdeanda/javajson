package com.thedeanda.javajson;

import java.io.InputStream;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.thedeanda.javajson.parser.ASTparse;
import com.thedeanda.javajson.parser.JsonParser;
import com.thedeanda.javajson.parser.ParseException;
import com.thedeanda.javajson.parser.TokenMgrError;

/**
 * Simpler implementation of Json that throws less exceptions. For all the
 * getXXX method, if the item is not found, it returns null, false, or 0. If
 * they key is null or otherwise invalid, it throws an exception.
 * 
 * @author mdeanda
 */
public class JsonObject implements Iterable<String>, Serializable {
	private static final long serialVersionUID = 1L;
	private Map<String, JsonValue> map;

	/** Parses a string to a json object. */
	public static JsonObject parse(String input) throws JsonException {
		return parse(new StringReader(input));
	}

	/** Parses a string to a json object. */
	public static JsonObject parse(Reader reader) throws JsonException {
		try {
			JsonParser parser = new JsonParser(reader);
			ASTparse root = (ASTparse) parser.parse();
			return root.getJsonObject();
		} catch (ParseException pe) {
			throw new JsonException(pe);
		} catch (TokenMgrError error) {
			throw new JsonException(error);
		}
	}

	/** Parses a string to a json object. */
	public static JsonObject parse(InputStream is) throws JsonException {
		try {
			JsonParser parser = new JsonParser(is);
			ASTparse root = (ASTparse) parser.parse();
			return root.getJsonObject();
		} catch (ParseException pe) {
			throw new JsonException(pe);
		} catch (TokenMgrError error) {
			throw new JsonException(error);
		}
	}

	public JsonObject() {
		map = new TreeMap<String, JsonValue>();
	}

	/**
	 * creates an array at a given field, if a non-array value existed there
	 * before, put it in as the first item in the array
	 * 
	 * @param key
	 * @return
	 */
	private JsonArray accumulate_prep(String key) {
		JsonArray ret = null;
		if (!map.containsKey(key)) {
			ret = new JsonArray();
			put(key, ret);
		} else if (!map.get(key).isJsonArray()) {
			ret = new JsonArray();
			JsonValue old = map.get(key);
			put(key, ret);
			ret.add(old);
		} else {
			ret = getJsonArray(key);
		}

		return ret;
	}

	/**
	 * Accumulates multiple values into an array. This is a convenience function
	 * for creating an array, adding to the array, then adding array to the
	 * object. If there is already a value for this key, it will be the first
	 * item in the array and a second value will be added with the passed in
	 * value
	 * 
	 * @param key
	 * @param val
	 */
	public JsonObject accumulate(String key, boolean val) {
		accumulate_prep(key).add(val);
		return this;
	}

	/**
	 * Accumulates multiple values into an array. This is a convenience function
	 * for creating an array, adding to the array, then adding array to the
	 * object. If there is already a value for this key, it will be the first
	 * item in the array and a second value will be added with the passed in
	 * value
	 * 
	 * @param key
	 * @param val
	 */
	public JsonObject accumulate(String key, double val) {
		accumulate_prep(key).add(val);
		return this;
	}

	/**
	 * Accumulates multiple values into an array. This is a convenience function
	 * for creating an array, adding to the array, then adding array to the
	 * object. If there is already a value for this key, it will be the first
	 * item in the array and a second value will be added with the passed in
	 * value
	 * 
	 * @param key
	 * @param val
	 */
	public JsonObject accumulate(String key, float val) {
		accumulate_prep(key).add(val);
		return this;
	}

	/**
	 * Accumulates multiple values into an array. This is a convenience function
	 * for creating an array, adding to the array, then adding array to the
	 * object. If there is already a value for this key, it will be the first
	 * item in the array and a second value will be added with the passed in
	 * value
	 * 
	 * @param key
	 * @param val
	 */
	public JsonObject accumulate(String key, int val) {
		accumulate_prep(key).add(val);
		return this;
	}

	/**
	 * Accumulates multiple values into an array. This is a convenience function
	 * for creating an array, adding to the array, then adding array to the
	 * object. If there is already a value for this key, it will be the first
	 * item in the array and a second value will be added with the passed in
	 * value
	 * 
	 * @param key
	 * @param val
	 */
	public JsonObject accumulate(String key, long val) {
		accumulate_prep(key).add(val);
		return this;
	}

	/**
	 * Accumulates multiple values into an array. This is a convenience function
	 * for creating an array, adding to the array, then adding array to the
	 * object. If there is already a value for this key, it will be the first
	 * item in the array and a second value will be added with the passed in
	 * value
	 * 
	 * @param key
	 * @param val
	 */
	public JsonObject accumulate(String key, JsonArray val) {
		accumulate_prep(key).add(val);
		return this;
	}

	/**
	 * Accumulates multiple values into an array. This is a convenience function
	 * for creating an array, adding to the array, then adding array to the
	 * object. If there is already a value for this key, it will be the first
	 * item in the array and a second value will be added with the passed in
	 * value
	 * 
	 * @param key
	 * @param val
	 */
	public JsonObject accumulate(String key, JsonObject val) {
		accumulate_prep(key).add(val);
		return this;
	}

	/**
	 * Accumulates multiple values into an array. This is a convenience function
	 * for creating an array, adding to the array, then adding array to the
	 * object. If there is already a value for this key, it will be the first
	 * item in the array and a second value will be added with the passed in
	 * value
	 * 
	 * @param key
	 * @param val
	 */
	public JsonObject accumulate(String key, String val) {
		accumulate_prep(key).add(val);
		return this;
	}

	/**
	 * Get a JsonValue object via one ore more keys. This can be used as a
	 * shortcut to objects deep in the json object similar to xpath. For
	 * example: <code>
	 * get("a", "b");
	 * </code> is the same as: <code>
	 * get("a").get("b");
	 * </code> This method will not fail if the path doesn't exist and should
	 * return null if nothing matches the path.
	 * 
	 * @return the value
	 */
	public JsonValue get(Object... key) {
		return get(0, key);
	}

	public void clear() {
		map.clear();
	}

	private JsonValue get(int offset, Object... key) {
		JsonValue ret = null;
		if (key == null || key.length == 0) {
			ret = null;
		} else if (offset == key.length - 1) {
			// last one, just get the value
			ret = map.get(key[offset]);
		} else {
			String keyVal = String.valueOf(key[offset]);
			JsonValue tmp = map.get(keyVal);
			if (tmp != null) {
				if (tmp.isJsonObject()) {
					ret = tmp.getJsonObject().get(offset + 1, key);
				} else if (tmp.isJsonArray()) {
					ret = tmp.getJsonArray().get(Arrays.copyOfRange(key, offset + 1, key.length));
				}
			}
		}

		return ret;
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

	public boolean hasKey(Object... key) {
		return get(key) != null;
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
	 * Checks if the item at a key is a double. See {@link JsonValue#isNumber()}
	 * for more information
	 * 
	 * @param key
	 */
	public boolean isDouble(String key) {
		if (map.containsKey(key))
			return map.get(key).isNumber();
		else
			return false;
	}

	/**
	 * Checks if the item at a key is a float. See {@link JsonValue#isNumber()}
	 * for more information
	 * 
	 * @param key
	 */
	public boolean isFloat(String key) {
		if (map.containsKey(key))
			return map.get(key).isNumber();
		else
			return false;
	}

	/**
	 * Checks if the item at a key is a int. See {@link JsonValue#isInt()} for
	 * more information
	 * 
	 * @param key
	 */
	public boolean isInt(String key) {
		if (map.containsKey(key))
			return map.get(key).isInt();
		else
			return false;
	}

	/**
	 * Checks if the item at a key is a long. See {@link JsonValue#isLong()} for
	 * more information
	 * 
	 * @param key
	 */
	public boolean isLong(String key) {
		if (map.containsKey(key))
			return map.get(key).isLong();
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
	 * Checks if the field contains a null value (different than hasKey because
	 * it can have the key but be null). No value is assumed to be equal to null
	 */
	public boolean isNull(String key) {
		if (map.containsKey(key)) {
			JsonValue jv = map.get(key);
			return jv.isNull();
		}

		return true;
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

	/**
	 * puts an unparsed token as the value, it will get interpretted when
	 * accessed
	 */
	public void putRaw(String key, String value) {
		map.put(key, new JsonValue(value, true));
	}

	/**
	 * 
	 * @param key
	 * @param value
	 * @throws IllegalArgumentException
	 *             if its not a supported object type
	 */
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
		else if (value instanceof Date)
			put(key, (Date) value);
		else if (value == null)
			put(key, (String) null);
		else if (value instanceof Enum)
			put(key, ((Enum) value).name());
		else if (value instanceof JsonValue)
			map.put(key, (JsonValue) value);
		else
			throw new IllegalArgumentException("Unrecognized class");
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

	public JsonObject put(String key, Date value) {
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

	public JsonValue remove(String key) {
		return map.remove(key);
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
			sb.append("\"" + JsonValue.escape(key) + "\":");
			sb.append(val);
			hadSome = true;
		}
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Returns a nicely formatted string
	 * 
	 * @param spacing
	 * @return
	 */
	public String toString(int spacing) {
		return toString(spacing, 0);
	}

	/**
	 * Called by toString(int) to keep track of the spacing
	 * 
	 * @param spacing
	 * @param margin
	 * @return
	 */
	protected String toString(int spacing, int margin) {
		if (map.isEmpty())
			return "{}";
		else {
			StringBuffer sb = new StringBuffer();
			boolean hadSome = false;
			// sb.append(getSpaces(margin));
			sb.append("{\n");
			for (String key : map.keySet()) {
				JsonValue val = map.get(key);
				if (hadSome)
					sb.append(",\n");

				sb.append(getSpaces(spacing + margin));
				sb.append("\"" + JsonValue.escape(key) + "\":");
				sb.append(val.toString(spacing, margin + spacing));
				hadSome = true;
			}
			sb.append("\n");
			sb.append(getSpaces(margin));
			sb.append("}");

			return sb.toString();
		}
	}

	private String getSpaces(int spaces) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < spaces; i++)
			sb.append(" ");
		return sb.toString();
	}

}
