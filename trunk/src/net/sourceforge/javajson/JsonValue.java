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

	enum Type {
		STRING, BOOLEAN, DOUBLE, FLOAT, INT, LONG, OBJECT, ARRAY, NULL
	};

	Type actualType = Type.NULL;

	public JsonValue() {
		this((String) null);
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
		if (val instanceof Float)
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
		throw new ClassCastException("Not a valid float");
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
			actualType = Type.NULL;
		} else {
			if ("true".equals(stringVal) || "false".equals(stringVal))
				boolVal = new Boolean(stringVal);
			else
				boolVal = null;

			try {
				doubleVal = new Double(stringVal);
				floatVal = doubleVal.floatValue();
				intVal = doubleVal.intValue();
				longVal = doubleVal.longValue();
			} catch (NumberFormatException nfe) {
				doubleVal = null;
				floatVal = null;
				intVal = null;
				longVal = null;
			}
		}
	}

	public boolean isBoolean() {
		return boolVal != null;
	}

	public boolean isJsonArray() {
		return (actualType == Type.ARRAY);
	}

	public boolean isJsonObject() {
		return (actualType == Type.OBJECT);
	}

	/**
	 * Returns true if the value contained is a number. If the value is set as a
	 * string that can parse as a valid number, then true is still returned
	 */
	public boolean isNumber() {
		return (doubleVal != null);
	}

	/**
	 * Checks if the type of this value is similar to that of another value.
	 * 
	 * @param obj
	 * @return
	 */
	public boolean isSimilar(JsonValue obj) {
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
		else
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
		actualType = Type.BOOLEAN;
	}

	public void setDouble(double d) {
		setString(Double.toString(d));
		actualType = Type.DOUBLE;
	}

	public void setFloat(float f) {
		setString(Float.toString(f));
		actualType = Type.FLOAT;
	}

	public void setInt(int i) {
		setString(Integer.toString(i));
		actualType = Type.INT;
	}

	public void setJsonArray(JsonArray a) {
		setString(null);
		jsonArray = a;
		actualType = Type.ARRAY;
	}

	public void setJsonObject(JsonObject o) {
		setString(null);
		jsonObject = o;
		actualType = Type.OBJECT;
	}

	public void setLong(long l) {
		setString(Long.toString(l));
		actualType = Type.LONG;
	}

	public void setString(String s) {
		stringVal = s;
		initValues();
		actualType = Type.STRING;
	}

	@Override
	public String toString() {
		if (actualType == Type.BOOLEAN)
			return boolVal ? "true" : "false";
		else if (actualType == Type.DOUBLE)
			return Double.toString(doubleVal);
		else if (actualType == Type.FLOAT)
			return Float.toString(floatVal);
		else if (actualType == Type.INT)
			return Integer.toString(intVal);
		else if (actualType == Type.LONG)
			return Long.toString(longVal);
		else if (actualType == Type.OBJECT)
			return jsonObject.toString();
		else if (actualType == Type.ARRAY)
			return jsonArray.toString();
		if (actualType == Type.STRING && stringVal != null)
			return "\"" + stringVal + "\"";
		else
			return "null";
	}
}
