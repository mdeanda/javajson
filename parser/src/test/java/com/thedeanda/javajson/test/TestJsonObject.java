package com.thedeanda.javajson.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.thedeanda.javajson.JsonArray;
import com.thedeanda.javajson.JsonObject;

public class TestJsonObject {

	JsonObject json;

	@Before
	public void setUp() throws Exception {
		json = new JsonObject();
	}

	@Test
	public void testArray() throws Exception {
		JsonArray array1 = new JsonArray();
		JsonArray array2 = new JsonArray();
		json.put("key1", array1);
		json.put("key2", array2);

		assertTrue(json.isJsonArray("key1"));
		assertFalse(json.isJsonObject("key1"));
		// assertFalse(json.isNumber("key1"));
		assertFalse(json.isString("key1"));

		assertNotNull(json.getJsonArray("key1"));
		assertEquals(array1, json.getJsonArray("key1"));
		assertNotSame(array2, json.getJsonArray("key1"));
		assertEquals(array2, json.getJsonArray("key2"));
		assertNotSame(array1, json.getJsonArray("key2"));
	}

	@Test
	public void testNumber() throws Exception {
		json.put("pi", 3.1415);
		json.put("six", 6);

		assertEquals(2, json.size());
		assertTrue(json.hasKey("pi"));
		assertTrue(json.hasKey("six"));

		// assertTrue(json.isNumber("pi"));
		assertFalse(json.isJsonArray("pi"));
		assertFalse(json.isJsonObject("pi"));
		assertTrue(json.isString("pi"));
		assertTrue(json.isDouble("pi"));
		assertTrue(json.isFloat("pi"));
		assertFalse(json.isInt("pi"));
		assertFalse(json.isLong("pi"));
		assertTrue(3.14149 < json.getDouble("pi"));
		assertTrue(3.14151 > json.getDouble("pi"));
		assertTrue(3.14149f < json.getFloat("pi"));
		assertTrue(3.14151f > json.getFloat("pi"));
		assertEquals("3.1415", json.getString("pi"));

		// assertTrue(json.isNumber("six"));
		assertFalse(json.isJsonArray("six"));
		assertFalse(json.isJsonObject("six"));
		assertTrue(json.isDouble("six"));
		assertTrue(json.isFloat("six"));
		assertTrue(json.isInt("six"));
		assertTrue(json.isLong("six"));
		assertTrue(json.isString("six"));
		assertEquals(6l, json.getLong("six"));
		assertEquals(6, json.getInt("six"));
		assertEquals("6", json.getString("six"));
	}

	@Test
	public void testObject() throws Exception {
		JsonObject obj1 = new JsonObject();
		JsonObject obj2 = new JsonObject();
		json.put("key1", obj1);
		json.put("key2", obj2);

		assertFalse(json.isJsonArray("key1"));
		assertTrue(json.isJsonObject("key1"));
		// assertFalse(json.isNumber("key1"));
		assertFalse(json.isString("key1"));

		assertNotNull(json.getJsonObject("key1"));
		assertEquals(obj1, json.getJsonObject("key1"));
		assertNotSame(obj2, json.getJsonObject("key1"));
		assertEquals(obj2, json.getJsonObject("key2"));
		assertNotSame(obj1, json.getJsonObject("key2"));
	}

	@Test
	public void testString() throws Exception {
		json.put("key1", "value1");

		assertEquals(1, json.size());
		assertEquals("value1", json.getString("key1"));
		assertTrue(json.isString("key1"));
		assertFalse(json.isJsonArray("key1"));
		assertFalse(json.isJsonObject("key1"));

		assertFalse(json.getBoolean("key1"));
		assertEquals(0, json.getInt("key1"));
	}

	@Test
	public void testSimilar() throws Exception {
		JsonObject simObj = new JsonObject();

		assertTrue(simObj.isSimilar(json));
		assertTrue(json.isSimilar(simObj));

		json.put("key1", 5);
		assertFalse(simObj.isSimilar(json));
		assertFalse(json.isSimilar(simObj));
		simObj.put("key1", false);
		assertFalse(simObj.isSimilar(json));
		assertFalse(json.isSimilar(simObj));
		simObj.put("key1", "false");
		assertFalse(simObj.isSimilar(json));
		assertFalse(json.isSimilar(simObj));
		simObj.put("key1", 8);
		assertTrue(simObj.isSimilar(json));
		assertTrue(json.isSimilar(simObj));

		json.put("key2", new JsonObject());
		assertFalse(simObj.isSimilar(json));
		assertFalse(json.isSimilar(simObj));
		simObj.put("key2", 8);
		assertFalse(simObj.isSimilar(json));
		assertFalse(json.isSimilar(simObj));
		simObj.put("key2", new JsonObject());
		assertTrue(simObj.isSimilar(json));
		assertTrue(json.isSimilar(simObj));

		json.put("key3", new JsonArray());
		assertFalse(simObj.isSimilar(json));
		assertFalse(json.isSimilar(simObj));
		simObj.put("key3", 8);
		assertFalse(simObj.isSimilar(json));
		assertFalse(json.isSimilar(simObj));
		simObj.put("key3", new JsonObject());
		assertFalse(simObj.isSimilar(json));
		assertFalse(json.isSimilar(simObj));
		simObj.put("key3", new JsonArray());
		assertTrue(simObj.isSimilar(json));
		assertTrue(json.isSimilar(simObj));

		json.getJsonObject("key2").put("key1", 3);
		assertFalse(simObj.isSimilar(json));
		assertFalse(json.isSimilar(simObj));
		simObj.getJsonObject("key2").put("key1", 8);
		assertTrue(simObj.isSimilar(json));
		assertTrue(json.isSimilar(simObj));

		json.getJsonArray("key3").add(3);
		assertFalse(simObj.isSimilar(json));
		assertFalse(json.isSimilar(simObj));
		simObj.getJsonArray("key3").add(8);
		assertTrue(simObj.isSimilar(json));
		assertTrue(json.isSimilar(simObj));

		json.getJsonArray("key3").add(8);
		assertFalse(simObj.isSimilar(json));
		assertFalse(json.isSimilar(simObj));
		simObj.accumulate("key3", 18);
		assertTrue(simObj.isSimilar(json));
		assertTrue(json.isSimilar(simObj));

		json.put("key4", new JsonArray());
		json.getJsonArray("key4").add(3);
		assertFalse(simObj.isSimilar(json));
		assertFalse(json.isSimilar(simObj));
		simObj.accumulate("key4", 18);
		assertTrue(simObj.isSimilar(json));
		assertTrue(json.isSimilar(simObj));
	}

	@Test
	public void testToString() throws Exception {
		json.put("key", "value");
		assertEquals("{\n  \"key\":\"value\"\n}", json.toString(2));

		json = new JsonObject();
		JsonObject json2 = new JsonObject();
		json.put("key3", json2);
		// System.out.println(json.toString(2));
		assertEquals("{\n  \"key3\":{}\n}", json.toString(2));

		json2.put("key4", "value4");
		// System.out.println(json.toString(2));
		assertEquals("{\n  \"key3\":{\n    \"key4\":\"value4\"\n  }\n}",
				json.toString(2));

		// Test for 2 different strings since output can be a little bit
		// different because internally it uses a set
		json2.put("key5", "value5");
		// System.out.println(json.toString(2));
		String s = json.toString(2);
		String s1 = "{\n  \"key3\":{\n    \"key5\":\"value5\",\n    \"key4\":\"value4\"\n  }\n}";
		String s2 = "{\n  \"key3\":{\n    \"key4\":\"value4\",\n    \"key5\":\"value5\"\n  }\n}";
		if (s1.equals(s))
			assertEquals(s1, s);
		else
			assertEquals(s2, s);
	}

	@Test
	public void testIterator() {
		JsonObject json = new JsonObject();
		List<String> items = new ArrayList<String>();
		items.add("a");
		items.add("b");
		items.add("c");
		for (String i : items)
			json.put(i, i);
		assertEquals(items.size(), json.size());
		for (String key : json) {
			items.remove(key);
		}
		assertEquals(0, items.size());
	}

	@Test
	public void testRemove() throws Exception {
		JsonObject j = new JsonObject();
		j.put("a", "a");
		assertEquals("{\"a\":\"a\"}", j.toString());
		assertEquals("a", j.getString("a"));

		j.remove("a");
		assertNull(j.getString("a"));
		assertEquals("{}", j.toString());
	}

	@Test
	public void testEnum() throws Exception {
		JsonObject json = new JsonObject();
		json.put("a", TestEnum.VALUE_ONE);
		assertEquals("VALUE_ONE", json.getString("a"));
		assertEquals(TestEnum.VALUE_ONE.name(), json.getString("a"));
	}

	@Test
	public void testLongObjectStackError() throws Exception {
		JsonObject a = new JsonObject();
		for (int i = 0; i < 100000; i++) {
			a.put("o_" + i, new JsonObject());
		}
		JsonArray.parse(a.toString());
	}

	@Test
	public void testPut() {
		String key = "key";
		Float fpi = new Float(3.14f);
		Integer answer = new Integer(42);
		Date date = new Date(0);

		json.put(key, fpi);
		assertTrue(fpi == json.getFloat(key));
		json.put(key, answer);
		assertTrue(answer == json.getInt(key));
		json.put(key, (Object) date);
		assertTrue("1970-01-01T00:00:00.000Z".equals(json.getString(key)));

		try {
			json.put(key, new HashSet<String>());
			fail("expect error");
		} catch (IllegalArgumentException ex) {

		}
	}

	@Test
	public void testNull() {
		assertTrue(json.isNull("monkey"));

		json.put("test", (Object) null);
		assertTrue(json.isNull("test"));
	}
}
