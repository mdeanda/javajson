package com.thedeanda.javajson.test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.thedeanda.javajson.JsonArray;
import com.thedeanda.javajson.JsonObject;
import com.thedeanda.javajson.JsonValue;

public class TestPathKeys {

	private JsonObject json;
	private JsonArray jsonArray;

	@Before
	public void setUp() throws Exception {
		json = JsonObject.parse(
				"{s:'string1',b:true,i:1,o:{s2:'string2',b2:true,i2:2,o2:{s3:'string3',b3:true,i3:3,ar:[1,2,3,{a:'a value'}]}}}");

		jsonArray = JsonArray.parse("[0, [2, [ 4 ] ] ]");
	}

	@Test
	public void testArray() {
		JsonValue val = null;
		val = jsonArray.get();
		assertNull(val);

		val = jsonArray.get(null);
		assertNull(val);

		val = jsonArray.get("a");
		assertNull(val);

		val = jsonArray.get(0);
		assertNotNull(val);
		assertEquals(0, val.getInt());

		val = jsonArray.get(1);
		assertNotNull(val);
		assertTrue(val.isJsonArray());

		val = jsonArray.get(1, 0);
		assertNotNull(val);
		assertEquals(2, val.getInt());

		val = jsonArray.get(1, 1, 0);
		assertNotNull(val);
		assertEquals(4, val.getInt());

		val = jsonArray.get(1);
		assertNull(val.get());
		assertNull(val.get(null));
		assertNull(val.get("abc"));

		val = val.get(1, 0);
		assertNotNull(val);
		assertEquals(4, val.getInt());

		val = jsonArray.get(1, 1, 1);
		assertNull(val);

		val = jsonArray.get(1, -1);
		assertNull(val);

		val = jsonArray.get(1, 1, 5);
		assertNull(val);
	}

	@Test
	public void testHasKey() throws Exception {
		assertTrue("1 level deep", json.hasKey("b"));
		assertTrue("2 levels deep", json.hasKey("o", "b2"));
		assertTrue("3 levels deep", json.hasKey("o", "o2", "s3"));

		assertTrue(json.hasKey(new String[] { "b" }));
		assertFalse(json.hasKey(new String[] { "b", "somethingelse" }));
		assertFalse(json.hasKey(new String[] {}));
	}

	@Test
	public void arrayItems() {
		JsonValue val = json.get("o", "o2", "ar");
		assertNotNull(val);
		assertTrue(val.isJsonArray());
		assertEquals(2, val.getJsonArray().getInt(1));

		val = json.get("o", "o2", "ar", 1);
		assertNotNull(val);
		assertEquals(2, val.getInt());

		val = json.get("o", "o2", "ar", 3, "a");
		assertNotNull(val);
		assertEquals("a value", val.getString());

		val = json.get("o", "o2", "ar", 3, 3, "a");
		assertNull(val);

		val = json.get("o", "o2", "ar", 1, 2, "j");
		assertNull(val);
	}

	@Test
	public void testEdgeBaseCases() {
		JsonValue val;
		val = json.get();
		assertNull(val);
		
		val = json.get(null);
		assertNull(val);
	}
	@Test
	public void testBoolean() throws Exception {
		JsonValue val = json.get("b");
		assertNotNull("1 level deep", val);
		assertTrue(val.isBoolean());
		assertTrue(val.getBoolean());

		val = json.get("o", "b2");
		assertNotNull("2 levels deep", val);
		assertTrue(val.isBoolean());
		assertTrue(val.getBoolean());

		val = json.get("o", "o2", "b3");
		assertNotNull("3 levels deep", val);
		assertTrue(val.isBoolean());
		assertTrue(val.getBoolean());

		//not found...
		val = json.get("o", "something", "o2", "b3");
		assertNull(val);
	}

	@Test
	public void testValue() throws Exception {
		JsonValue val = json.get("s");
		assertNotNull(val);
		assertFalse(val.isBoolean());
		assertTrue(val.isString());
		assertEquals("string1", val.getString());

		val = json.get("o", "o2", "s3");
		assertNotNull(val);
		assertFalse(val.isBoolean());
		assertFalse(val.isInt());
		assertFalse(val.isJsonArray());
		assertFalse(val.isJsonObject());
		assertFalse(val.isLong());
		assertFalse(val.isNull());
		assertFalse(val.isNumber());
		assertTrue(val.isString());
		assertEquals("string3", val.getString());
	}

	@Test
	public void testNullKey() throws Exception {
		JsonValue val = json.get((String[]) null);
		assertNull(val);

		val = json.get("b", null);
		assertNull(val);
	}

}
