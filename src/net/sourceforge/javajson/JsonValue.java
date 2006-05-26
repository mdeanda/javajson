package net.sourceforge.javajson;

public class JsonValue {
	private Boolean boolVal;

	private Double doubleVal;

	private JsonArray jsonArray;

	private JsonObject jsonObject;

	private Long longVal;

	private String stringVal;

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
		else if (stringVal != null)
			return "true".equals(stringVal);
		else
			return false;
	}

	public double getDouble() {
		if (doubleVal != null)
			return doubleVal.doubleValue();
		else if (longVal != null)
			return longVal.doubleValue();
		else if (stringVal != null)
			return Double.parseDouble(stringVal);
		else
			return 0;
	}

	public float getFloat() {
		if (doubleVal != null)
			return doubleVal.floatValue();
		else if (longVal != null)
			return longVal.floatValue();
		else if (stringVal != null)
			return Float.parseFloat(stringVal);
		else
			return 0f;
	}

	public int getInt() {
		if (doubleVal != null)
			return doubleVal.intValue();
		else if (longVal != null)
			return longVal.intValue();
		else if (stringVal != null)
			return Integer.parseInt(stringVal);
		else
			return 0;
	}

	public JsonArray getJsonArray() {
		if (jsonArray != null)
			return jsonArray;
		else
			return null;
	}

	public JsonObject getJsonObject() {
		if (jsonObject != null)
			return jsonObject;
		else
			return null;
	}

	public long getLong() {
		if (doubleVal != null)
			return doubleVal.longValue();
		else if (longVal != null)
			return longVal.longValue();
		else if (stringVal != null)
			return Long.parseLong(stringVal);
		else
			return 0;
	}

	public String getString() {
		if (doubleVal != null)
			return doubleVal.toString();
		else if (longVal != null)
			return longVal.toString();
		else if (stringVal != null)
			return stringVal;
		else
			return null;
	}

	/**
	 * Checks if the value is a boolean
	 * 
	 * @return
	 */
	public boolean isBoolean() {
		return (boolVal != null);
	}

	/**
	 * Checks if the value can be safely converted to this type without losing
	 * data. This is true of any type of number
	 * 
	 * @return
	 */
	public boolean isDouble() {
		return (doubleVal != null || longVal != null);
	}

	/**
	 * Checks if the value can be safely converted to this type without losing.
	 * It returns the same as isDouble as long as the value is not out of range
	 * 
	 * @return
	 */
	public boolean isFloat() {
		if (isDouble()) {
			if (doubleVal != null) {
				//convert to float, then back to double, compare with original
				float f1 = (float)doubleVal.doubleValue();
				float f2 = doubleVal.floatValue();
				System.out.println(f1 + "\n" + f2 + "\n" + (f1 == f2));
				return f1 == f2;
			} else if (longVal != null) {
				// convert to float, then back to long, compare with original
				// long
				return (new Float(longVal.floatValue()).longValue() == longVal
						.longValue());
			}
			//TODO: consider converting strings
		}

		return false;
	}

	/**
	 * Checks if the value can be safely converted to this type without losing
	 * data. It returns the same as isLong as long as the value is not out of
	 * range
	 * 
	 * @return
	 */
	public boolean isInt() {
		if (isLong()) {
			return new Long(longVal.intValue()).equals(longVal);
		} else
			return false;
	}

	public boolean isJsonArray() {
		return (jsonArray != null);
	}

	public boolean isJsonObject() {
		return (jsonObject != null);
	}

	/**
	 * Checks if the value can be safely converted to this type without losing
	 * data
	 * 
	 * @return
	 */
	public boolean isLong() {
		if (longVal == null) {
			if (doubleVal != null) {
				longVal = new Long(doubleVal.longValue());
				if (longVal.doubleValue() != doubleVal.doubleValue()) {
					longVal = null;
				}
			}
			// TODO: consider converting strings
		}

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
		 * if (actualType != obj.actualType) { // only a few exceptions... //
		 * both are numbers if (doubleVal != null && obj.doubleVal != null)
		 * return true; return false; } // if its a json object or array, do a
		 * deep test if (actualType == Type.ARRAY) return
		 * jsonArray.isSimilar(obj.jsonArray); else if (actualType ==
		 * Type.OBJECT) return jsonObject.isSimilar(obj.jsonObject); else
		 */
		return true;
	}

	/**
	 * Returns true if the value is a number, boolean or string
	 */
	public boolean isString() {
		return (boolVal != null || doubleVal != null || longVal != null || stringVal != null);
	}

	public void setBoolean(boolean b) {
		setNull();
		boolVal = b ? Boolean.TRUE : Boolean.FALSE;
	}

	public void setDouble(double d) {
		setNull();
		doubleVal = new Double(d);
	}

	public void setFloat(float f) {
		setDouble(f);
	}

	public void setInt(int i) {
		setLong(i);
	}

	public void setJsonArray(JsonArray array) {
		setNull();
		jsonArray = array;
	}

	public void setJsonObject(JsonObject object) {
		setNull();
		jsonObject = object;
	}

	public void setLong(long l) {
		setNull();
		longVal = new Long(l);
	}

	/** Just sets the value to null */
	protected void setNull() {
		boolVal = null;
		doubleVal = null;
		longVal = null;
		jsonArray = null;
		jsonObject = null;
		stringVal = null;
	}

	public void setString(String s) {
		setNull();
		stringVal = s;
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
		else if (longVal != null)
			return longVal.toString();
		else if (doubleVal != null)
			return doubleVal.toString();
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
