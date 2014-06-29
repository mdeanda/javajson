package net.sourceforge.javajson;

import java.io.InputStream;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import net.sourceforge.javajson.parser.ASTparse;
import net.sourceforge.javajson.parser.JsonParser;
import net.sourceforge.javajson.parser.ParseException;
import net.sourceforge.javajson.parser.TokenMgrError;

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
			throw new ClassCastException("Unrecognized class");
		return this;
	}

	public List<JsonValue> find(String... key) {
		List<JsonValue> ret = new ArrayList<JsonValue>();
		if (key != null && key.length > 0) {
			int index = -1;
			String firstKey = key[0];
			String[] nextkey = null;
			try {
				index = Integer.parseInt(firstKey);
				if (key.length > 1) {
					nextkey = new String[key.length - 1];
					for (int i = 1; i < key.length; i++) {
						nextkey[i - 1] = key[i];
					}
				}
			} catch (NumberFormatException nfe) {
				nextkey = key;
			}

			if (index >= 0) {
				ret.addAll(list.get(index).find(nextkey));
			} else {
				// no index, just find inside children with same key
				for (JsonValue val : this) {
					ret.addAll(val.find(nextkey));
				}
			}
		}
		return ret;
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
			return list.get(index).isDouble();
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
			return list.get(index).isFloat();
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