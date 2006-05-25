package net.sourceforge.javajson;

public class JsonValue {
	Boolean boolVal;

	Double doubleVal;

	Float floatVal;

	Integer intVal;

	JsonArray jsonArray;

	JsonObject jsonObject;

	Long longVal;

	String stringVal;

	public JsonValue() {
		setNull();
	}

	public JsonValue(boolean val) {
		setBoolean(val);
	}

	public JsonValue(double val) {
		setDouble(val);
	}

	public JsonValue(float val) {
		setFloat(val);
	}

	public JsonValue(int val) {
		setInt(val);
	}

	public JsonValue(JsonArray val) {
		setJsonArray(val);
	}

	public JsonValue(JsonObject val) {
		setJsonObject(val);
	}

	public JsonValue(long val) {
		setLong(val);
	}

	public JsonValue(String val) {
		setString(val);
	}

	public JsonValue(Object val) {
		if (val == null)
			setNull();
		else if (val instanceof Float)
			setFloat(((Float) val).floatValue());
		else if (val instanceof Double)
			setDouble(((Double) val).doubleValue());
		else if (val instanceof Integer)
			setInt(((Integer) val).intValue());
		else if (val instanceof Long)
			setLong(((Long) val).longValue());
		else if (val instanceof Boolean)
			setBoolean(((Boolean) val).booleanValue());
		else if (val instanceof String)
			setString((String) val);
		else if (val instanceof JsonObject)
			setJsonObject((JsonObject) val);
		else if (val instanceof JsonArray)
			setJsonArray((JsonArray) val);
		else
			throw new ClassCastException("Unrecognized class");
	}

	public boolean getBoolean() {
		if (boolVal != null)
			return boolVal.booleanValue();
		throw new ClassCastException("Not a valid boolean");
	}

	public double getDouble() {
		if (doubleVal != null)
			return doubleVal.doubleValue();
		throw new ClassCastException("Not a valid double");
	}

	public float getFloat() {
		if (floatVal != null)
			return floatVal.floatValue();
		else
			return 0;
	}

	public int getInt() {
		if (intVal != null)
			return intVal.intValue();
		throw new ClassCastException("Not a valid int");
	}

	public JsonArray getJsonArray() {
		if (jsonArray != null)
			return jsonArray;
		throw new ClassCastException("Not a valid json array");
	}

	public JsonObject getJsonObject() {
		if (jsonObject != null)
			return jsonObject;
		throw new ClassCastException("Not a valid json object");
	}

	public long getLong() {
		if (longVal != null)
			return longVal.longValue();
		throw new ClassCastException("Not a valid long");
	}

	public String getString() {
		return stringVal;
	}

	private void initValues() {
		if (stringVal == null) {
			boolVal = null;
			doubleVal = null;
			floatVal = null;
			intVal = null;
			longVal = null;
		} else {
			if ("true".equals(stringVal) || "false".equals(stringVal))
				boolVal = new Boolean(stringVal);
			else
				boolVal = null;

			try {
				longVal = new Long(stringVal);
				// if long is in int range
				if (longVal < Integer.MAX_VALUE && longVal > Integer.MIN_VALUE)
					intVal = longVal.intValue();
			} catch (NumberFormatException nfe) {
				intVal = null;
				longVal = null;
			}
			try {
				doubleVal = new Double(stringVal);
				if (doubleVal < Float.MAX_VALUE && doubleVal > Float.MIN_VALUE)
					floatVal = doubleVal.floatValue();
			} catch (NumberFormatException nfe) {
				if (longVal != null)
					doubleVal = new Double(longVal.doubleValue());
				else
					doubleVal = null;
				if (doubleVal != null)
					floatVal = doubleVal.floatValue();
				else
					floatVal = null;
			}
		}
	}

	public boolean isBoolean() {
		return (boolVal != null);
	}

	public boolean isDouble() {
		return (doubleVal != null);
	}

	public boolean isFloat() {
		return (floatVal != null);
	}

	public boolean isInt() {
		return (intVal != null);
	}

	public boolean isJsonArray() {
		return (jsonArray != null);
	}

	public boolean isJsonObject() {
		return (jsonObject != null);
	}

	public boolean isLong() {
		return (longVal != null);
	}

	/**
	 * Checks if the type of this value is similar to that of another value.
	 * 
	 * @param obj
	 * @return
	 */
	public boolean isSimilar(JsonValue obj) {
		/*
		if (actualType != obj.actualType) {
			// only a few exceptions...
			// both are numbers
			if (doubleVal != null && obj.doubleVal != null)
				return true;

			return false;
		}

		// if its a json object or array, do a deep test
		if (actualType == Type.ARRAY)
			return jsonArray.isSimilar(obj.jsonArray);
		else if (actualType == Type.OBJECT)
			return jsonObject.isSimilar(obj.jsonObject);
		else */
			return true;
	}

	/**
	 * Returns true if the value contained is a string. If the value is set as a
	 * number, it will still return true because you can do a getString on it.
	 * Only JsonObject and JsonArray values should return false;
	 */
	public boolean isString() {
		return (stringVal != null);
	}

	public void setBoolean(boolean b) {
		setString(Boolean.toString(b));
	}

	public void setDouble(double d) {
		boolVal = null;
		doubleVal = new Double(d);
		floatVal = doubleVal.floatValue();
		longVal = doubleVal.longValue();
		intVal = longVal.intValue();
		stringVal = doubleVal.toString();
		jsonArray = null;
		jsonObject = null;
	}

	public void setFloat(float f) {
		setDouble(f);
	}

	public void setInt(int i) {
		setLong(i);
	}

	public void setJsonArray(JsonArray a) {
		if (a != null) {
			setNull();
			jsonArray = a;
		} else {
			setNull();
		}
	}

	public void setJsonObject(JsonObject o) {
		if (o != null) {
			setNull();
			jsonObject = o;
		} else {
			setNull();
		}
	}

	public void setLong(long l) {
		boolVal = null;
		longVal = new Long(l);
		intVal = longVal.intValue();
		doubleVal = longVal.doubleValue();
		floatVal = doubleVal.floatValue();
		stringVal = longVal.toString();
		jsonArray = null;
		jsonObject = null;
	}

	/** Just sets the value to null */
	protected void setNull() {
		setString(null);
	}

	public void setString(String s) {
		stringVal = s;
		initValues();
	}

	/**
	 * Escapes strings for toString
	 * 
	 * @param str
	 * @return
	 */
	public String escape(String str) {
		return str.replaceAll("\"", "&quot;");
	}

	@Override
	public String toString() {
		if (boolVal != null)
			return boolVal ? "true" : "false";
		else if (doubleVal != null)
			return doubleVal.toString();
		else if (floatVal != null)
			return floatVal.toString();
		else if (intVal != null)
			return intVal.toString();
		else if (longVal != null)
			return longVal.toString();
		else if (jsonObject != null)
			return jsonObject.toString();
		else if (jsonArray != null)
			return jsonArray.toString();
		if (stringVal != null)
			return "\"" + escape(stringVal) + "\"";
		else
			return "null";
	}

	protected String toString(int spacing, int margin) {
		StringBuffer sb = new StringBuffer();
		if (this.isJsonObject())
			sb.append(jsonObject.toString(spacing, margin));
		else if (this.isJsonArray())
			sb.append(jsonArray.toString(spacing, margin));
		else
			sb.append(toString());
		return sb.toString();
	}
}
