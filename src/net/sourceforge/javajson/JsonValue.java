package net.sourceforge.javajson;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class JsonValue implements Serializable {
	private static final long serialVersionUID = 1L;
	private static DateFormat dateFormat = new SimpleDateFormat(
			"yyyy:MM:dd'T'HH:mm:ssZ");
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
	}

	private JsonNativeType nativeType;

	private Boolean boolVal;

	private Double doubleVal;

	private Float floatVal;

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
		char hexDigit[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
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
		if (floatVal != null)
			return floatVal.floatValue();
		else if (doubleVal != null)
			return doubleVal.floatValue();
		else if (longVal != null)
			return longVal.floatValue();
		else if (stringVal != null)
			return Float.parseFloat(stringVal);
		else
			return 0f;
	}

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
		if (doubleVal != null)
			return doubleVal.longValue();
		else if (longVal != null)
			return longVal.longValue();
		// else if (stringVal != null)
		// return Long.parseLong(stringVal);
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
	 * Returns the class of the value being used. For example, if setFloat or
	 * setDouble were used, Double.class is returned. If setInt or setLong is
	 * used, Long.class is returned.
	 * 
	 * @deprecated User getNativeType instead
	 */
	@SuppressWarnings("unchecked")
	public Class getValueClass() {
		if (doubleVal != null)
			return Double.class;
		else if (longVal != null)
			return Long.class;
		else if (jsonArray != null)
			return JsonArray.class;
		else if (jsonObject != null)
			return JsonObject.class;
		else
			return null;
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
	 * Checks if the value can be safely converted to this type without losing
	 * data. This is true of any type of number
	 * 
	 * @return
	 */
	public boolean isDouble() {
		return nativeType == JsonNativeType.LONG
				|| nativeType == JsonNativeType.INTEGER
				|| nativeType == JsonNativeType.FLOAT
				|| nativeType == JsonNativeType.DOUBLE;
	}

	/**
	 * Checks if the value can be safely converted to this type without losing.
	 * It returns the same as isDouble as long as the value is not out of range
	 * TODO: check out of range errors
	 * 
	 * @return
	 */
	public boolean isFloat() {
		return isDouble();
	}

	/**
	 * Checks if the value can be safely converted to this type without losing
	 * data. It returns the same as isLong as long as the value is not out of
	 * range TODO: check out of range errors
	 * 
	 * @return
	 */
	public boolean isInt() {
		boolean ret = isLong();
		if (ret) {
			// check if in range
			long l = getLong();
			if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE)
				ret = false;
		}
		return ret;
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
		return nativeType == JsonNativeType.LONG
				|| nativeType == JsonNativeType.INTEGER;
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
	}

	public void setString(String s) {
		setNull();

		if (s != null) {
			// check if its a long or double
			if (Pattern.matches("-?\\d+", s))
				setLong(Long.parseLong(s));
			else if (Pattern.matches("-?(\\d+\\.\\d*)|(\\d*\\.\\d+)", s))
				setDouble(Double.parseDouble(s));
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
		if (nativeType == JsonNativeType.BOOLEAN)
			return boolVal ? "true" : "false";
		else if (nativeType == JsonNativeType.LONG
				|| nativeType == JsonNativeType.INTEGER)
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
