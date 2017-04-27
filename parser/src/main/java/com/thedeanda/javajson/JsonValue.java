package com.thedeanda.javajson;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import com.thedeanda.javajson.parser.ASTstring;

public class JsonValue implements Serializable {
	private static final long serialVersionUID = 1L;
	private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	private static Map<Character, String> escapeMap = new HashMap<Character, String>();
	static {
		escapeMap.put('\\', "\\\\");
		escapeMap.put('\b', "\\b");
		escapeMap.put('\f', "\\f");
		escapeMap.put('\n', "\\n");
		escapeMap.put('\r', "\\r");
		escapeMap.put('\t', "\\t");
		escapeMap.put('\"', "\\\"");
		// escapeMap.put('\'', "\\\'");

		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
	}

	private JsonNativeType nativeType;

	private Boolean boolVal;

	private Double doubleVal;

	private Float floatVal;

	private JsonArray jsonArray;

	private JsonObject jsonObject;

	private Long longVal;

	private String stringVal;
	private String rawString;

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

	/**
	 * stores the raw string before escaping, used to make parsing a little
	 * quicker
	 */
	public JsonValue(String value, boolean raw) {
		this.rawString = value;
		nativeType = JsonNativeType.STRING;
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
		else if (val instanceof Date)
			setDate((Date) val);
		else if (val instanceof JsonObject)
			setJsonObject((JsonObject) val);
		else if (val instanceof JsonArray)
			setJsonArray((JsonArray) val);
		else
			throw new ClassCastException("Unrecognized class");
	}

	static public String byteToHex(byte b) {
		char hexDigit[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		char[] array = { hexDigit[(b >> 4) & 0x0f], hexDigit[b & 0x0f] };
		return new String(array);
	}

	static public String charToEscaped(char c) {
		byte hiByte = (byte) (c >>> 8);
		byte loByte = (byte) (c & 0xff);
		if (hiByte == 0) // if hi byte is 0, then assume not unicode
			return String.valueOf(c);
		else
			return "\\u" + byteToHex(hiByte) + byteToHex(loByte);
	}

	private void init() {
		if (rawString != null) {
			stringVal = ASTstring.fixString(rawString);
			rawString = null;
		}
	}

	public JsonValue get(Object... key) {
		if (key == null || key.length <= 0) {
			return null;
		}

		if (this.isJsonArray()) {
			return getJsonArray().get(key);
		} else if (this.isJsonObject()) {
			return getJsonObject().get(key);
		}
		return null;
	}

	public boolean getBoolean() {
		init();
		boolean ret = false;
		if (boolVal != null)
			ret = boolVal.booleanValue();
		else if (stringVal != null) {
			ret = "true".equals(stringVal);
		}

		boolVal = ret;
		return ret;
	}

	public double getDouble() {
		init();
		double ret = 0d;
		if (doubleVal != null)
			ret = doubleVal.doubleValue();
		else if (floatVal != null)
			ret = floatVal.doubleValue();
		else if (longVal != null)
			ret = longVal.doubleValue();
		else if (stringVal != null)
			ret = Double.parseDouble(stringVal);

		doubleVal = ret;
		return ret;
	}

	/**
	 * NOTE: this method currently doesn't check for overflows
	 * 
	 * @return
	 */
	public float getFloat() {
		init();
		float ret = 0f;

		if (floatVal != null)
			ret = floatVal.floatValue();
		else if (doubleVal != null)
			ret = doubleVal.floatValue();
		else if (longVal != null)
			ret = longVal.floatValue();
		else if (stringVal != null)
			ret = Float.parseFloat(stringVal);
		floatVal = ret;
		return ret;
	}

	/**
	 * NOTE: this method currently doesn't check for overflows
	 * 
	 * @return
	 */
	public int getInt() {
		return (int) getLong();
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
		init();
		long ret = 0;
		if (longVal != null)
			ret = longVal.longValue();
		else if (doubleVal != null)
			ret = doubleVal.longValue();
		else if (floatVal != null)
			ret = floatVal.longValue();
		else if (stringVal != null) {
			try {
				ret = Long.parseLong(stringVal);
			} catch (NumberFormatException nfe) {
				ret = 0;
			}
		}
		longVal = ret;
		return ret;
	}

	public String getString() {
		init();
		String retVal = null;
		switch (nativeType) {
		case BOOLEAN:
			retVal = boolVal.toString();
			break;
		case DOUBLE:
			retVal = doubleVal.toString();
			break;
		case FLOAT:
			retVal = floatVal.toString();
			break;
		case INTEGER:
		case LONG:
			retVal = longVal.toString();
			break;
		case STRING:
			retVal = stringVal;
			break;
		case JSON_ARRAY:
		case JSON_OBJECT:
		case NULL:
			retVal = null;
		}
		return retVal;
	}

	public String getString(String defaultValue) {
		String ret = getString();
		return ret == null ? defaultValue : ret;
	}

	public JsonNativeType getNativeType() {
		return this.nativeType;
	}

	/**
	 * Checks if the value is a boolean
	 * 
	 * @return
	 */
	public boolean isBoolean() {
		return nativeType == JsonNativeType.BOOLEAN;
	}

	/**
	 * Checks if the value is any type of number and not a quoted number as that
	 * is considered a string
	 * 
	 * @return
	 */
	public boolean isNumber() {
		boolean ret = nativeType == JsonNativeType.LONG || nativeType == JsonNativeType.INTEGER
				|| nativeType == JsonNativeType.FLOAT || nativeType == JsonNativeType.DOUBLE;

		return ret;
	}

	/**
	 * Returns true if the native value is a long or int
	 * 
	 * @return
	 */
	public boolean isInt() {
		boolean ret = nativeType == JsonNativeType.LONG || nativeType == JsonNativeType.INTEGER;
		if (nativeType == JsonNativeType.LONG) {
			ret = longVal < Integer.MAX_VALUE && longVal > Integer.MIN_VALUE;
		}
		return ret;
	}

	public boolean isJsonArray() {
		return jsonArray != null;
	}

	public boolean isJsonObject() {
		return jsonObject != null;
	}

	/**
	 * Returns true if native time is long or int
	 * 
	 * @return
	 */
	public boolean isLong() {
		boolean ret = nativeType == JsonNativeType.LONG || nativeType == JsonNativeType.INTEGER;

		return ret;
	}

	public boolean isNull() {
		return nativeType == JsonNativeType.NULL;
	}

	/**
	 * Checks if the type of this value is similar to that of another value.
	 * 
	 * @param obj
	 * @return
	 */
	public boolean isSimilar(JsonValue obj) {
		if (obj.nativeType != nativeType)
			return false;
		if (nativeType == JsonNativeType.JSON_OBJECT)
			return jsonObject.isSimilar(obj.jsonObject);
		else if (nativeType == JsonNativeType.JSON_ARRAY)
			return jsonArray.isSimilar(obj.jsonArray);
		else
			return true;
	}

	/**
	 * Returns true if the value is a number, boolean or string
	 */
	public boolean isString() {
		return !isNull() && !isJsonArray() && !isJsonObject();
	}

	public void setBoolean(boolean b) {
		setNull();
		nativeType = JsonNativeType.BOOLEAN;
		boolVal = b ? Boolean.TRUE : Boolean.FALSE;
	}

	public void setDate(Date d) {
		setString(dateFormat.format(d));
	}

	public void setDouble(double d) {
		setNull();
		nativeType = JsonNativeType.DOUBLE;
		doubleVal = new Double(d);
	}

	public void setFloat(float f) {
		setNull();
		floatVal = new Float(f);
		nativeType = JsonNativeType.FLOAT;
	}

	public void setInt(int i) {
		setLong(i);
		nativeType = JsonNativeType.INTEGER;
	}

	public void setJsonArray(JsonArray array) {
		setNull();
		if (array != null) {
			nativeType = JsonNativeType.JSON_ARRAY;
			jsonArray = array;
		}
	}

	public void setJsonObject(JsonObject object) {
		setNull();
		if (object != null) {
			nativeType = JsonNativeType.JSON_OBJECT;
			jsonObject = object;
		}
	}

	public void setLong(long l) {
		setNull();
		nativeType = JsonNativeType.LONG;
		longVal = new Long(l);
	}

	/** Just sets the value to null */
	protected void setNull() {
		nativeType = JsonNativeType.NULL;
		boolVal = null;
		doubleVal = null;
		floatVal = null;
		longVal = null;
		jsonArray = null;
		jsonObject = null;
		stringVal = null;
		rawString = null;
	}

	public void setString(String s) {
		setNull();

		if (s != null) {
			stringVal = s;
			nativeType = JsonNativeType.STRING;
		}
	}

	/**
	 * Escapes strings for toString
	 * 
	 * @param str
	 * @return
	 */
	public static String escape(String str) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (escapeMap.containsKey(c)) {
				sb.append(escapeMap.get(c));
			} else {
				// sb.append(c);
				sb.append(charToEscaped(c));
			}
		}
		return sb.toString();
	}

	@Override
	public String toString() {
		init();
		if (nativeType == JsonNativeType.BOOLEAN)
			return boolVal ? "true" : "false";
		else if (nativeType == JsonNativeType.LONG || nativeType == JsonNativeType.INTEGER)
			return longVal.toString();
		else if (nativeType == JsonNativeType.DOUBLE)
			return doubleVal.toString();
		else if (nativeType == JsonNativeType.FLOAT)
			return floatVal.toString();
		else if (nativeType == JsonNativeType.JSON_OBJECT)
			return jsonObject.toString();
		else if (nativeType == JsonNativeType.JSON_ARRAY)
			return jsonArray.toString();
		if (nativeType == JsonNativeType.STRING)
			return "\"" + JsonValue.escape(stringVal) + "\"";
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
