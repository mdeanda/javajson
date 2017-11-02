package com.thedeanda.javajson;

import java.io.InputStream;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.thedeanda.javajson.parser.ASTparse;
import com.thedeanda.javajson.parser.JsonParser;
import com.thedeanda.javajson.parser.ParseException;
import com.thedeanda.javajson.parser.TokenMgrError;

public class JsonArray implements Iterable<JsonValue>, Serializable {
	private static final long serialVersionUID = 1L;
	private List<JsonValue> list;

	/** Parses a string to a json object. */
	public static JsonArray parse(String input) throws JsonException {
		return parse(new StringReader(input));
	}

	/** Parses a string to a json object. */
	public static JsonArray parse(Reader reader) throws JsonException {
		try {
			JsonParser parser = new JsonParser(reader);
			ASTparse root = (ASTparse) parser.parse();
			return root.getJsonArray();
		} catch (ParseException pe) {
			throw new JsonException(pe);
		} catch (TokenMgrError error) {
			throw new JsonException(error);
		}
	}

	/** Parses a string to a json object. */
	public static JsonArray parse(InputStream is) throws JsonException {
		try {
			JsonParser parser = new JsonParser(is);
			ASTparse root = (ASTparse) parser.parse();
			return root.getJsonArray();
		} catch (ParseException pe) {
			throw new JsonException(pe);
		} catch (TokenMgrError error) {
			throw new JsonException(error);
		}
	}

	public JsonArray() {
		list = new ArrayList<JsonValue>();
	}

	public JsonArray add(boolean value) {
		list.add(new JsonValue(value));
		return this;
	}

	public JsonArray add(double value) {
		list.add(new JsonValue(value));
		return this;
	}

	public JsonArray add(float value) {
		list.add(new JsonValue(value));
		return this;
	}

	public JsonArray add(char value) {
		list.add(new JsonValue(String.valueOf(value)));
		return this;
	}

	public JsonArray add(int value) {
		list.add(new JsonValue(value));
		return this;
	}

	public JsonArray add(long value) {
		list.add(new JsonValue(value));
		return this;
	}

	public JsonArray add(Date value) {
		list.add(new JsonValue(value));
		return this;
	}

	public JsonArray add(JsonArray value) {
		list.add(new JsonValue(value));
		return this;
	}

	public JsonArray add(JsonObject value) {
		list.add(new JsonValue(value));
		return this;
	}

	public JsonArray add(String value) {
		list.add(new JsonValue(value));
		return this;
	}

	public JsonArray add(Object value) {
		if (value instanceof Boolean)
			add(((Boolean) value).booleanValue());
		else if (value instanceof Double)
			add(((Double) value).doubleValue());
		else if (value instanceof Float)
			add(((Float) value).floatValue());
		else if (value instanceof Integer)
			add(((Integer) value).intValue());
		else if (value instanceof Long)
			add(((Long) value).longValue());
		else if (value instanceof JsonArray)
			add((JsonArray) value);
		else if (value instanceof JsonObject)
			add((JsonObject) value);
		else if (value instanceof JsonValue)
			list.add((JsonValue) value);
		else if (value instanceof String)
			add((String) value);
		else if (value instanceof Date)
			add((Date) value);
		else if (value == null)
			add((String) null);
		else if (value instanceof Enum)
			add(((Enum) value).name());
		else
			throw new IllegalArgumentException("Unrecognized class");
		return this;
	}

	public void clear() {
		list.clear();
	}

	public JsonValue get(Object... key) {
		if (key == null || key.length <= 0) {
			return null;
		}

		int index = 0;
		try {
			Object s = key[0];
			index = Integer.parseInt(String.valueOf(s));
		} catch (NumberFormatException nfe) {
			return null;
		}

		if (index >= this.list.size() || index < 0) {
			return null;
		}
		JsonValue ret = this.list.get(index);
		if (key.length > 1) {
			ret = ret.get(Arrays.copyOfRange(key, 1, key.length));
		}
		return ret;
	}

	/**
	 * @param index
	 * @throws IndexOutOfBoundsException
	 *             if its out of bounds
	 */
	public boolean getBoolean(int index) {
		if (index >= 0 && index < list.size())
			return list.get(index).getBoolean();
		else
			throw new IndexOutOfBoundsException();
	}

	/**
	 * @throws ClassCastException
	 *             if the value cannot be converted to a double
	 * @throws IndexOutOfBoundsException
	 *             if its out of bounds
	 */
	public double getDouble(int index) {
		if (index >= 0 && index < list.size())
			return list.get(index).getDouble();
		else
			throw new IndexOutOfBoundsException();
	}

	/**
	 * @throws ClassCastException
	 *             if the value cannot be converted to a double
	 * @throws IndexOutOfBoundsException
	 *             if its out of bounds
	 */
	public float getFloat(int index) {
		if (index >= 0 && index < list.size())
			return list.get(index).getFloat();
		else
			throw new IndexOutOfBoundsException();
	}

	/**
	 * @throws ClassCastException
	 *             if the value cannot be converted to a double
	 * @throws IndexOutOfBoundsException
	 *             if its out of bounds
	 */
	public int getInt(int index) {
		if (index >= 0 && index < list.size())
			return list.get(index).getInt();
		else
			throw new IndexOutOfBoundsException();
	}

	/**
	 * @throws ClassCastException
	 *             if the value cannot be converted to a double
	 * @throws IndexOutOfBoundsException
	 *             if its out of bounds
	 */
	public JsonArray getJsonArray(int index) {
		if (index >= 0 && index < list.size())
			return list.get(index).getJsonArray();
		else
			throw new IndexOutOfBoundsException();
	}

	/**
	 * @throws ClassCastException
	 *             if the value cannot be converted to a double
	 * @throws IndexOutOfBoundsException
	 *             if its out of bounds
	 */
	public JsonObject getJsonObject(int index) {
		if (index >= 0 && index < list.size())
			return list.get(index).getJsonObject();
		else
			throw new IndexOutOfBoundsException();
	}

	/**
	 * @throws ClassCastException
	 *             if the value cannot be converted to a double
	 * @throws IndexOutOfBoundsException
	 *             if its out of bounds
	 */
	public long getLong(int index) {
		if (index >= 0 && index < list.size())
			return list.get(index).getLong();
		else
			throw new IndexOutOfBoundsException();
	}

	/**
	 * @throws IndexOutOfBoundsException
	 *             if its out of bounds
	 */
	public String getString(int index) {
		if (index >= 0 && index < list.size())
			return list.get(index).getString();
		else
			throw new IndexOutOfBoundsException();
	}

	public JsonArray insert(int index, boolean value) {
		list.add(index, new JsonValue(value));
		return this;
	}

	public JsonArray insert(int index, double value) {
		list.add(index, new JsonValue(value));
		return this;
	}

	public JsonArray insert(int index, float value) {
		list.add(index, new JsonValue(value));
		return this;
	}

	public JsonArray insert(int index, char value) {
		list.add(index, new JsonValue(String.valueOf(value)));
		return this;
	}

	public JsonArray insert(int index, int value) {
		list.add(index, new JsonValue(value));
		return this;
	}

	public JsonArray insert(int index, long value) {
		list.add(index, new JsonValue(value));
		return this;
	}

	public JsonArray insert(int index, Date value) {
		list.add(index, new JsonValue(value));
		return this;
	}

	public JsonArray insert(int index, JsonArray value) {
		list.add(index, new JsonValue(value));
		return this;
	}

	public JsonArray insert(int index, JsonObject value) {
		list.add(index, new JsonValue(value));
		return this;
	}

	public JsonArray insert(int index, String value) {
		list.add(index, new JsonValue(value));
		return this;
	}

	public JsonArray insert(int index, Object value) {
		if (value instanceof Boolean)
			insert(index, ((Boolean) value).booleanValue());
		else if (value instanceof Double)
			insert(index, ((Double) value).doubleValue());
		else if (value instanceof Float)
			insert(index, ((Float) value).floatValue());
		else if (value instanceof Integer)
			insert(index, ((Integer) value).intValue());
		else if (value instanceof Long)
			insert(index, ((Long) value).longValue());
		else if (value instanceof JsonArray)
			insert(index, (JsonArray) value);
		else if (value instanceof JsonObject)
			insert(index, (JsonObject) value);
		else if (value instanceof JsonValue)
			list.add(index, (JsonValue) value);
		else if (value instanceof String)
			insert(index, (String) value);
		else if (value instanceof Date)
			insert(index, (Date) value);
		else if (value == null)
			insert(index, (String) null);
		else if (value instanceof Enum)
			insert(index, ((Enum) value).name());
		else
			throw new IllegalArgumentException("Unrecognized class");
		return this;
	}

	/**
	 * Checks if the item at an index is a boolean. See
	 * {@link JsonValue#isBoolean()} for more information
	 * 
	 * @param index
	 *            The index of the array to check
	 * @return True if the object contains the item and is a boolean
	 * @throws IndexOutOfBoundsException
	 */
	public boolean isBoolean(int index) {
		if (index >= 0 && index < list.size())
			return list.get(index).isBoolean();
		else
			throw new IndexOutOfBoundsException();
	}

	/**
	 * Checks if the item at an index is a double. See
	 * {@link JsonValue#isLong()} for more information
	 * 
	 * @param index
	 *            The index of the array to check
	 * @return True if the object contains the item and is a double
	 * @throws IndexOutOfBoundsException
	 */
	public boolean isDouble(int index) {
		if (index >= 0 && index < list.size())
			return list.get(index).isNumber();
		else
			throw new IndexOutOfBoundsException();
	}

	/**
	 * Checks if the item at an index is a float. See {@link JsonValue#isLong()}
	 * for more information
	 * 
	 * @param index
	 *            The index of the array to check
	 * @return True if the object contains the item and is a float
	 * @throws IndexOutOfBoundsException
	 */
	public boolean isFloat(int index) {
		if (index >= 0 && index < list.size())
			return list.get(index).isNumber();
		else
			throw new IndexOutOfBoundsException();
	}

	/**
	 * Checks if the item at an index is a int. See {@link JsonValue#isLong()}
	 * for more information
	 * 
	 * @param index
	 *            The index of the array to check
	 * @return True if the object contains the item and is a int
	 * @throws IndexOutOfBoundsException
	 */
	public boolean isInt(int index) {
		if (index >= 0 && index < list.size())
			return list.get(index).isInt();
		else
			throw new IndexOutOfBoundsException();
	}

	/**
	 * Checks if the item at an index is an array. See
	 * {@link JsonValue#isJsonArray()} for more information
	 * 
	 * @param index
	 *            The index of the array to check
	 * @return True if the object contains the item and is a json array
	 * @throws IndexOutOfBoundsException
	 */
	public boolean isJsonArray(int index) {
		if (index >= 0 && index < list.size())
			return list.get(index).isJsonArray();
		else
			throw new IndexOutOfBoundsException();
	}

	/**
	 * Checks if the item at an index is an object. See
	 * {@link JsonValue#isJsonObject()} for more information
	 * 
	 * @param index
	 *            The index of the array to check
	 * @return True if the object contains the item and is a json object
	 * @throws IndexOutOfBoundsException
	 */
	public boolean isJsonObject(int index) {
		if (index >= 0 && index < list.size())
			return list.get(index).isJsonObject();
		else
			throw new IndexOutOfBoundsException();
	}

	/**
	 * Checks if the item at an index is a long. See {@link JsonValue#isLong()}
	 * for more information
	 * 
	 * @param index
	 *            The index of the array to check
	 * @return True if the object contains the item and is a long
	 * @throws IndexOutOfBoundsException
	 */
	public boolean isLong(int index) {
		if (index >= 0 && index < list.size())
			return list.get(index).isLong();
		else
			throw new IndexOutOfBoundsException();
	}

	/**
	 * Checks if the item at an index is an string. See
	 * {@link JsonValue#isString()} for more information
	 * 
	 * @param index
	 *            The index of the array to check
	 * @return True if the object contains the item and is a json string
	 * @throws IndexOutOfBoundsException
	 */
	public boolean isString(int index) {
		if (index >= 0 && index < list.size())
			return list.get(index).isString();
		else
			throw new IndexOutOfBoundsException();
	}

	/**
	 * Checks if the structure of this json array is similar to that of another
	 * array. It simply checks that each item exists in both objects (same
	 * length of array) and that subobjects (json object or json array) are also
	 * similar.
	 * 
	 * @param obj
	 * @return
	 */
	public boolean isSimilar(JsonArray obj) {
		if (obj == null)
			return false;

		if (obj.size() != size())
			return false;

		for (int i = 0; i < size(); i++) {
			if (!list.get(i).isSimilar(obj.list.get(i)))
				return false;
		}

		return true;
	}

	public Iterator<JsonValue> iterator() {
		return list.iterator();
	}

	public JsonValue remove(int index) {
		return list.remove(index);
	}

	public int size() {
		return list.size();
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		boolean hadSome = false;
		sb.append("[");
		for (JsonValue val : list) {
			if (hadSome)
				sb.append(",");
			sb.append(val);
			hadSome = true;
		}
		sb.append("]");
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
		if (list.isEmpty())
			return "[]";
		else {
			StringBuffer sb = new StringBuffer();
			boolean hadSome = false;
			sb.append("[\n");
			for (JsonValue val : list) {
				if (hadSome)
					sb.append(",\n");
				sb.append(getSpaces(margin + spacing));
				sb.append(val.toString(spacing, margin + spacing));
				hadSome = true;
			}
			sb.append("\n");
			sb.append(getSpaces(margin));
			sb.append("]");

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
