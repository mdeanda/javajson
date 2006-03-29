package net.sourceforge.javajson;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JsonArray implements Iterable<JsonValue> {
	private List<JsonValue> list;

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

	public JsonArray add(int value) {
		list.add(new JsonValue(value));
		return this;
	}

	public JsonArray add(long value) {
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
		else if (value == null)
			add((String) null);
		else
			throw new ClassCastException("Unrecognized class");
		return this;
	}

	/**
	 * @param index
	 * @throws ClassCastException
	 *             if the value cannot be converted to a boolean
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
	 * Checks if the item at an index is an number. See
	 * {@link JsonValue#isNumber()} for more information
	 * 
	 * @param index
	 *            The index of the array to check
	 * @return True if the object contains the item and is a json number
	 * @throws IndexOutOfBoundsException
	 */
	public boolean isNumber(int index) {
		if (index >= 0 && index < list.size())
			return list.get(index).isNumber();
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
}
