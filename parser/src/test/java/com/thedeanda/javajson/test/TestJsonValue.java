package com.thedeanda.javajson.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.thedeanda.javajson.JsonArray;
import com.thedeanda.javajson.JsonNativeType;
import com.thedeanda.javajson.JsonObject;
import com.thedeanda.javajson.JsonValue;

public class TestJsonValue {

	@Test
	public void testIsMethodsBasic() throws Exception {
		JsonValue v;

		v = new JsonValue(true);
		assertEquals(JsonNativeType.BOOLEAN, v.getNativeType());
		assertTrue(v.isBoolean());
		assertFalse(v.isNumber());
		assertFalse(v.isInt());
		assertFalse(v.isJsonArray());
		assertFalse(v.isJsonObject());
		assertFalse(v.isLong());
		assertTrue(v.isString());

		v = new JsonValue(4.14643534534434653463456d);
		assertEquals(JsonNativeType.DOUBLE, v.getNativeType());
		assertFalse(v.isBoolean());
		assertTrue(v.isNumber());
		assertFalse(v.isInt());
		assertFalse(v.isJsonArray());
		assertFalse(v.isJsonObject());
		assertFalse(v.isLong());
		assertTrue(v.isString());

		v = new JsonValue(2.5f);
		assertEquals(JsonNativeType.FLOAT, v.getNativeType());
		assertFalse(v.isBoolean());
		assertTrue(v.isNumber());
		assertFalse(v.isInt());
		assertFalse(v.isJsonArray());
		assertFalse(v.isJsonObject());
		assertFalse(v.isLong());
		assertTrue(v.isString());

		v = new JsonValue(15);
		assertEquals(JsonNativeType.INTEGER, v.getNativeType());
		assertFalse(v.isBoolean());
		assertTrue(v.isNumber());
		assertTrue(v.isInt());
		assertFalse(v.isJsonArray());
		assertFalse(v.isJsonObject());
		assertTrue(v.isLong());
		assertTrue(v.isString());

		v = new JsonValue(new JsonArray());
		assertEquals(JsonNativeType.JSON_ARRAY, v.getNativeType());
		assertFalse(v.isBoolean());
		assertFalse(v.isNumber());
		assertFalse(v.isInt());
		assertTrue(v.isJsonArray());
		assertFalse(v.isJsonObject());
		assertFalse(v.isLong());
		assertFalse(v.isString());

		v = new JsonValue(new JsonObject());
		assertEquals(JsonNativeType.JSON_OBJECT, v.getNativeType());
		assertFalse(v.isBoolean());
		assertFalse(v.isNumber());
		assertFalse(v.isInt());
		assertFalse(v.isJsonArray());
		assertTrue(v.isJsonObject());
		assertFalse(v.isLong());
		assertFalse(v.isString());

		v = new JsonValue(156l);
		assertEquals(JsonNativeType.LONG, v.getNativeType());
		assertFalse(v.isBoolean());
		assertTrue(v.isNumber());
		assertTrue(v.isInt());
		assertFalse(v.isJsonArray());
		assertFalse(v.isJsonObject());
		assertTrue(v.isLong());
		assertTrue(v.isString());

		v = new JsonValue(2532263343453345289l);
		assertEquals(JsonNativeType.LONG, v.getNativeType());
		assertFalse(v.isBoolean());
		assertTrue(v.isNumber());
		assertFalse(v.isInt());
		assertFalse(v.isJsonArray());
		assertFalse(v.isJsonObject());
		assertTrue(v.isLong());
		assertTrue(v.isString());

	}

	@Test
	public void testEquals() throws Exception {
		JsonValue v;

		v = new JsonValue(3.167f);
		assertEquals(JsonNativeType.FLOAT, v.getNativeType());
		assertEquals(3.167f, v.getFloat(), 0.1f);

		v = new JsonValue(3.1670);
		assertEquals(JsonNativeType.DOUBLE, v.getNativeType());
		assertEquals(3.1670, v.getDouble(), 0.1d);

		v = new JsonValue(36);
		assertEquals(JsonNativeType.INTEGER, v.getNativeType());
		assertEquals(36, v.getLong());
		assertEquals(36, v.getInt());

		v = new JsonValue(true);
		assertEquals(true, v.getBoolean());

	}

	@Test
	public void testToString() throws Exception {
		JsonValue v = new JsonValue();
		assertEquals(JsonNativeType.NULL, v.getNativeType());
		assertEquals("null", v.toString());

		v = new JsonValue(true);
		assertEquals(JsonNativeType.BOOLEAN, v.getNativeType());
		assertEquals("true", v.toString());

		v = new JsonValue(false);
		assertEquals(JsonNativeType.BOOLEAN, v.getNativeType());
		assertEquals("false", v.toString());

		v = new JsonValue(15);
		assertEquals("15", v.toString());

		// strings should be the only ones quoted
		v = new JsonValue("simple string");
		assertEquals(JsonNativeType.STRING, v.getNativeType());
		assertEquals("\"simple string\"", v.toString());

		v = new JsonValue("simple \"quote\"");
		assertEquals("\"simple \\\"quote\\\"\"", v.toString());

		v = new JsonValue("line\nbreak");
		assertEquals("unecoded line break", "\"line\\nbreak\"", v.toString());

		v = new JsonValue("tab\tspace");
		assertEquals("unecoded tab", "\"tab\\tspace\"", v.toString());

		v = new JsonValue(15.3f);
		assertEquals(Float.toString(15.3f), v.toString());

		v = new JsonValue(new JsonArray());
		assertEquals("[]", v.toString());

		v = new JsonValue(new JsonObject());
		assertEquals("{}", v.toString());

		v = new JsonValue((String) null);
		assertEquals("null", v.toString());

		v = new JsonValue((Object) null);
		assertEquals("null", v.toString());

		v = new JsonValue("not null");
		v.setJsonArray(null);
		assertEquals("null", v.toString());

		v = new JsonValue("not null");
		v.setJsonObject(null);
		assertEquals("null", v.toString());

		v = new JsonValue("\ufe85");
		assertEquals("quoted unicode", "\"\\ufe85\"", v.toString());
	}

	@Test
	public void testGetsOnInt() {
		JsonValue v = new JsonValue(5);
		assertEquals(5l, v.getLong());
		assertFalse(v.getBoolean());
		assertEquals(5f, v.getFloat(), 0.1f);
		assertEquals(5d, v.getDouble(), 0.1d);
		assertEquals(5, v.getInt());
		assertEquals("5", v.getString());
		assertNull(v.getJsonArray());
		assertNull(v.getJsonObject());
		assertTrue(v.isInt());
		assertTrue(v.isLong());
	}

	@Test
	public void testGetsOnLong() {
		JsonValue v = new JsonValue(5l);
		assertEquals(5l, v.getLong());

		v = new JsonValue(5l);
		assertFalse(v.getBoolean());

		v = new JsonValue(5l);
		assertEquals(5f, v.getFloat(), 0.1f);

		v = new JsonValue(5l);
		assertEquals(5d, v.getDouble(), 0.1d);

		v = new JsonValue(5l);
		assertEquals(5, v.getInt());

		v = new JsonValue(5l);
		assertEquals("5", v.getString());

		v = new JsonValue(5l);
		assertNull(v.getJsonArray());

		v = new JsonValue(5l);
		assertNull(v.getJsonObject());

		v = new JsonValue(5l);
		assertTrue(v.isLong());
		assertTrue(v.isInt());

		v = new JsonValue(-50000000000l);
		assertTrue(v.isLong());
		assertFalse(v.isInt());

		v = new JsonValue(50000000000l);
		assertTrue(v.isLong());
		assertFalse(v.isInt());
	}

	@Test
	public void testGetsOnDouble() {
		JsonValue v = new JsonValue(5.0d);
		assertEquals(5l, v.getLong());
		assertFalse(v.getBoolean());
		assertEquals(5f, v.getFloat(), 0.1f);
		assertEquals(5d, v.getDouble(), 0.1d);
		assertEquals(5, v.getInt());
		assertEquals("5.0", v.getString());
		assertNull(v.getJsonArray());
		assertNull(v.getJsonObject());
		assertTrue(v.isDouble());
	}

	@Test
	public void testGetsOnFloat() {
		JsonValue v = new JsonValue(5.0f);
		assertEquals(5l, v.getLong());
		assertFalse(v.getBoolean());
		assertEquals(5f, v.getFloat(), 0.1f);
		assertEquals(5d, v.getDouble(), 0.1d);
		assertEquals(5, v.getInt());
		assertEquals("5.0", v.getString());
		assertNull(v.getJsonArray());
		assertNull(v.getJsonObject());
		assertTrue(v.isDouble());
		assertTrue(v.isFloat());
	}

	@Test
	public void testGetsOnStringNumber() {
		JsonValue v = new JsonValue("5");
		assertEquals(5l, v.getLong());
		assertFalse(v.getBoolean());
		assertEquals(5f, v.getFloat(), 0.1f);
		assertEquals(5d, v.getDouble(), 0.1d);
		assertEquals(5, v.getInt());
		assertEquals("5", v.getString());
		assertNull(v.getJsonArray());
		assertNull(v.getJsonObject());
	}

	@Test
	public void testGetsOnNull() {
		JsonValue v = new JsonValue();
		assertEquals(0l, v.getLong());
		assertFalse(v.getBoolean());
		assertEquals(0f, v.getFloat(), 0.1f);
		assertEquals(0d, v.getDouble(), 0.1d);
		assertEquals(0, v.getInt());
		assertNull(v.getString());
		assertNull(v.getJsonArray());
		assertNull(v.getJsonObject());
	}

	@Test
	public void testGetsOnJsonObject() {
		JsonValue v = new JsonValue(new JsonObject());
		assertEquals(0l, v.getLong());
		assertFalse(v.getBoolean());
		assertEquals(0f, v.getFloat(), 0.1f);
		assertEquals(0d, v.getDouble(), 0.1d);
		assertEquals(0, v.getInt());
		assertNull(v.getString());
		assertNull(v.getJsonArray());
		assertNotNull(v.getJsonObject());
	}

	@Test
	public void testGetsOnJsonArray() {
		JsonValue v = new JsonValue(new JsonArray());
		assertEquals(0l, v.getLong());
		assertFalse(v.getBoolean());
		assertEquals(0f, v.getFloat(), 0.1f);
		assertEquals(0d, v.getDouble(), 0.1d);
		assertEquals(0, v.getInt());
		assertNull(v.getString());
		assertNotNull(v.getJsonArray());
		assertNull(v.getJsonObject());
	}

	@Test
	public void testGetStringWithDefault() {
		JsonValue v = new JsonValue();
		assertEquals("a", v.getString("a"));
		assertNull(v.getString(null));
		assertNull(v.getString());

		v = new JsonValue("b");
		assertEquals("b", v.getString("a"));
	}

	@Test
	public void testIsMethodsWithAutoConversion() {
		JsonValue v = new JsonValue("3.14");
		assertFalse(v.isNumber());

		v = new JsonValue("3");
		assertFalse(v.isNumber());
		assertFalse(v.isInt());
		assertFalse(v.isLong());
		assertFalse(v.isDouble());
		assertFalse(v.isFloat());

		v = new JsonValue("300000000000000000000000000000000000000000000000000000000000000");
		assertFalse(v.isNumber());
		assertFalse(v.isInt());
		assertFalse(v.isLong());
	}

	@Test
	public void testIsString() {
		JsonValue v;
		v = new JsonValue(5l);
		assertTrue(v.isString());

		v = new JsonValue();
		assertFalse(v.isString());

		v = new JsonValue(new JsonObject());
		assertFalse(v.isString());

		v = new JsonValue(new JsonArray());
		assertFalse(v.isString());

	}
}
