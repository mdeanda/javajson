package com.thedeanda.javajson.test;

import junit.framework.TestCase;

import com.thedeanda.javajson.JsonArray;
import com.thedeanda.javajson.JsonObject;
import com.thedeanda.javajson.JsonValue;

public class TestJsonValue extends TestCase {

	public void testIsMethodsBasic() throws Exception {
		JsonValue v;
		
		v = new JsonValue(true);
		assertTrue(v.isBoolean());
		assertFalse(v.isDouble());
		assertFalse(v.isFloat());
		assertFalse(v.isInt());
		assertFalse(v.isJsonArray());
		assertFalse(v.isJsonObject());
		assertFalse(v.isLong());
		assertTrue(v.isString());
		
		v = new JsonValue(4.14643534534434653463456d);
		assertFalse(v.isBoolean());
		assertTrue(v.isDouble());
		assertTrue(v.isFloat());
		assertFalse(v.isInt());
		assertFalse(v.isJsonArray());
		assertFalse(v.isJsonObject());
		assertFalse(v.isLong());
		assertTrue(v.isString());
		
		v = new JsonValue(2.5f);
		assertFalse(v.isBoolean());
		assertTrue(v.isDouble());
		assertTrue(v.isFloat());
		assertFalse(v.isInt());
		assertFalse(v.isJsonArray());
		assertFalse(v.isJsonObject());
		assertFalse(v.isLong());
		assertTrue(v.isString());
		
		v = new JsonValue(15);
		assertFalse(v.isBoolean());
		assertTrue(v.isDouble());
		assertTrue(v.isFloat());
		assertTrue(v.isInt());
		assertFalse(v.isJsonArray());
		assertFalse(v.isJsonObject());
		assertTrue(v.isLong());
		assertTrue(v.isString());
		
		v = new JsonValue(new JsonArray());
		assertFalse(v.isBoolean());
		assertFalse(v.isDouble());
		assertFalse(v.isFloat());
		assertFalse(v.isInt());
		assertTrue(v.isJsonArray());
		assertFalse(v.isJsonObject());
		assertFalse(v.isLong());
		assertFalse(v.isString());
		
		v = new JsonValue(new JsonObject());
		assertFalse(v.isBoolean());
		assertFalse(v.isDouble());
		assertFalse(v.isFloat());
		assertFalse(v.isInt());
		assertFalse(v.isJsonArray());
		assertTrue(v.isJsonObject());
		assertFalse(v.isLong());
		assertFalse(v.isString());
		
		v = new JsonValue(156l);
		assertFalse(v.isBoolean());
		assertTrue(v.isDouble());
		assertTrue(v.isFloat());
		assertTrue(v.isInt());
		assertFalse(v.isJsonArray());
		assertFalse(v.isJsonObject());
		assertTrue(v.isLong());
		assertTrue(v.isString());
		
		v = new JsonValue(2532263343453345289l);
		assertFalse(v.isBoolean());
		assertTrue(v.isDouble());
		assertTrue(v.isFloat());
		assertFalse(v.isInt());
		assertFalse(v.isJsonArray());
		assertFalse(v.isJsonObject());
		assertTrue(v.isLong());
		assertTrue(v.isString());
		
	}

	public void testEquals() throws Exception {
		JsonValue v;
		
		v = new JsonValue(3.167f);
		assertEquals(3.167f, v.getFloat());

		v = new JsonValue(3.1670);
		assertEquals(3.1670, v.getDouble());
		
		v = new JsonValue(36);
		assertEquals(36, v.getLong());
		assertEquals(36, v.getInt());

		v = new JsonValue(true);
		assertEquals(true, v.getBoolean());

	}
	
	public void testToString() throws Exception {
		JsonValue v = new JsonValue();
		assertEquals("null", v.toString());
		
		v = new JsonValue(true);
		assertEquals("true", v.toString());
		
		v = new JsonValue(false);
		assertEquals("false", v.toString());
		
		v = new JsonValue(15);
		assertEquals("15", v.toString());
		
		//strings should be the only ones quoted
		v = new JsonValue("simple string");
		assertEquals("\"simple string\"", v.toString());
		
		v = new JsonValue("simple \"quote\"");
		assertEquals("\"simple \\\"quote\\\"\"", v.toString());
		
		v = new JsonValue("line\nbreak");
		assertEquals("unecoded line break", "\"line\\nbreak\"", v.toString());
		
		v = new JsonValue("tab\tspace");
		assertEquals("unecoded tab", "\"tab\\tspace\"", v.toString());
		
		v = new JsonValue(15.3f);
		System.out.println(Float.toString(15.3f));
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
	
}
