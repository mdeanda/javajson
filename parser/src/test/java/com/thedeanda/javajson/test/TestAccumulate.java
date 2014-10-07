package com.thedeanda.javajson.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.thedeanda.javajson.JsonArray;
import com.thedeanda.javajson.JsonObject;

public class TestAccumulate {
	private static final String key = "a";
	private JsonObject json;

	@Before
	public void init() {
		json = new JsonObject();
	}

	@Test
	public void testBoolean() {
		json.accumulate(key, true);
		json.accumulate(key, false);

		JsonArray a = json.getJsonArray(key);
		assertNotNull(a);
		assertTrue(a.getBoolean(0));
		assertFalse(a.getBoolean(1));
	}

	@Test
	public void testBoolean2() {
		json.put(key, "");
		json.accumulate(key, true);
		json.accumulate(key, false);

		JsonArray a = json.getJsonArray(key);
		assertNotNull(a);
		assertEquals("", a.getString(0));
		assertTrue(a.getBoolean(1));
		assertFalse(a.getBoolean(2));
	}

	@Test
	public void testInt() {
		json.accumulate(key, 4);
		json.accumulate(key, 2);

		JsonArray a = json.getJsonArray(key);
		assertNotNull(a);
		assertEquals(4, a.getInt(0));
		assertEquals(2, a.getInt(1));
	}

	@Test
	public void testInt2() {
		json.put(key, "");
		json.accumulate(key, 4);
		json.accumulate(key, 2);

		JsonArray a = json.getJsonArray(key);
		assertNotNull(a);
		assertEquals("", a.getString(0));
		assertEquals(4, a.getInt(1));
		assertEquals(2, a.getInt(2));
	}

	@Test
	public void testString() {
		json.accumulate(key, "a");
		json.accumulate(key, "b");

		JsonArray a = json.getJsonArray(key);
		assertNotNull(a);
		assertEquals("a", a.getString(0));
		assertEquals("b", a.getString(1));
	}

	@Test
	public void testString2() {
		json.put(key, true);
		json.accumulate(key, "a");
		json.accumulate(key, "b");

		JsonArray a = json.getJsonArray(key);
		assertNotNull(a);
		assertTrue(a.getBoolean(0));
		assertEquals("a", a.getString(1));
		assertEquals("b", a.getString(2));
	}

	@Test
	public void testDouble() {
		double d1 = 3.14;
		double d2 = 2.72;
		json.accumulate(key, d1);
		json.accumulate(key, d2);

		JsonArray a = json.getJsonArray(key);
		assertNotNull(a);
		assertTrue(d1 == a.getDouble(0));
		assertTrue(d2 == a.getDouble(1));
	}

	@Test
	public void testDouble2() {
		json.put(key, "");
		double d1 = 3.14;
		double d2 = 2.72;
		json.accumulate(key, d1);
		json.accumulate(key, d2);

		JsonArray a = json.getJsonArray(key);
		assertNotNull(a);
		assertEquals("", a.getString(0));
		assertTrue(d1 == a.getDouble(1));
		assertTrue(d2 == a.getDouble(2));
	}

	@Test
	public void testFloat() {
		float d1 = 3.14f;
		float d2 = 2.72f;
		json.accumulate(key, d1);
		json.accumulate(key, d2);

		JsonArray a = json.getJsonArray(key);
		assertNotNull(a);
		assertTrue(d1 == a.getFloat(0));
		assertTrue(d2 == a.getFloat(1));
	}

	@Test
	public void testFloat2() {
		json.put(key, "");
		float d1 = 3.14f;
		float d2 = 2.72f;
		json.accumulate(key, d1);
		json.accumulate(key, d2);

		JsonArray a = json.getJsonArray(key);
		assertNotNull(a);
		assertEquals("", a.getString(0));
		assertTrue(d1 == a.getFloat(1));
		assertTrue(d2 == a.getFloat(2));
	}

	@Test
	public void testArray() {
		JsonArray a1 = new JsonArray();
		JsonArray a2 = new JsonArray();

		json.accumulate(key, a1);
		json.accumulate(key, a2);

		JsonArray a = json.getJsonArray(key);
		assertNotNull(a);
		assertEquals(a1, a.getJsonArray(0));
		assertEquals(a2, a.getJsonArray(1));
	}

	@Test
	public void testArray2() {
		json.put(key, "");
		JsonArray a1 = new JsonArray();
		JsonArray a2 = new JsonArray();

		json.accumulate(key, a1);
		json.accumulate(key, a2);

		JsonArray a = json.getJsonArray(key);
		assertNotNull(a);
		assertEquals("", a.getString(0));
		assertEquals(a1, a.getJsonArray(1));
		assertEquals(a2, a.getJsonArray(2));
	}

	@Test
	public void testObject() {
		JsonObject a1 = new JsonObject();
		JsonObject a2 = new JsonObject();

		json.accumulate(key, a1);
		json.accumulate(key, a2);

		JsonArray a = json.getJsonArray(key);
		assertNotNull(a);
		assertEquals(a1, a.getJsonObject(0));
		assertEquals(a2, a.getJsonObject(1));
	}

	@Test
	public void testObject2() {
		json.put(key, "");
		JsonObject a1 = new JsonObject();
		JsonObject a2 = new JsonObject();

		json.accumulate(key, a1);
		json.accumulate(key, a2);

		JsonArray a = json.getJsonArray(key);
		assertNotNull(a);
		assertEquals("", a.getString(0));
		assertEquals(a1, a.getJsonObject(1));
		assertEquals(a2, a.getJsonObject(2));
	}
}
