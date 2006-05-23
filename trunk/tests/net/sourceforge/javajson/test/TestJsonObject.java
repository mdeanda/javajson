package net.sourceforge.javajson.test;

import net.sourceforge.javajson.JsonArray;
import net.sourceforge.javajson.JsonObject;
import junit.framework.TestCase;

public class TestJsonObject extends TestCase {

	JsonObject json;

	@Override
	public void setUp() throws Exception {
		json = new JsonObject();
	}

	public void testArray() throws Exception {
		JsonArray array1 = new JsonArray();
		JsonArray array2 = new JsonArray();
		json.put("key1", array1);
		json.put("key2", array2);

		assertTrue(json.isJsonArray("key1"));
		assertFalse(json.isJsonObject("key1"));
		assertFalse(json.isNumber("key1"));
		assertFalse(json.isString("key1"));

		assertNotNull(json.getJsonArray("key1"));
		assertEquals(array1, json.getJsonArray("key1"));
		assertNotSame(array2, json.getJsonArray("key1"));
		assertEquals(array2, json.getJsonArray("key2"));
		assertNotSame(array1, json.getJsonArray("key2"));
	}

	public void testNumber() throws Exception {
		json.put("pi", 3.1415);
		json.put("six", 6);

		assertEquals(2, json.size());
		assertTrue(json.hasKey("pi"));
		assertTrue(json.hasKey("six"));

		assertTrue(json.isNumber("pi"));
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

		assertTrue(json.isNumber("six"));
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

	public void testObject() throws Exception {
		JsonObject obj1 = new JsonObject();
		JsonObject obj2 = new JsonObject();
		json.put("key1", obj1);
		json.put("key2", obj2);

		assertFalse(json.isJsonArray("key1"));
		assertTrue(json.isJsonObject("key1"));
		assertFalse(json.isNumber("key1"));
		assertFalse(json.isString("key1"));

		assertNotNull(json.getJsonObject("key1"));
		assertEquals(obj1, json.getJsonObject("key1"));
		assertNotSame(obj2, json.getJsonObject("key1"));
		assertEquals(obj2, json.getJsonObject("key2"));
		assertNotSame(obj1, json.getJsonObject("key2"));
	}

	public void testString() throws Exception {
		json.put("key1", "value1");

		assertEquals(1, json.size());
		assertEquals("value1", json.getString("key1"));
		assertTrue(json.isString("key1"));
		assertFalse(json.isJsonArray("key1"));
		assertFalse(json.isJsonObject("key1"));

		try {
			json.getBoolean("key1");
			fail("Expected fail on getBool for string");
		} catch (ClassCastException cce) {

		}

		try {
			json.getInt("key1");
			fail("Expected fail on getInt for string");
		} catch (ClassCastException cce) {

		}

		try {
			json.getDouble("key1");
			fail("Expected fail on getDouble for string");
		} catch (ClassCastException cce) {

		}
	}

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
		assertEquals("{\n  \"key3\":{\n    \"key4\":\"value4\"\n  }\n}", json
				.toString(2));

		// Test for 2 different strings since output can be a little bit
		// different because internally it uses a set
		json2.put("key5", "value5");
		//System.out.println(json.toString(2));
		String s = json.toString(2);
		String s1 = "{\n  \"key3\":{\n    \"key5\":\"value5\",\n    \"key4\":\"value4\"\n  }\n}";
		String s2 = "{\n  \"key3\":{\n    \"key4\":\"value4\",\n    \"key5\":\"value5\"\n  }\n}";
		if (s1.equals(s))
			assertEquals(s1, s);
		else
			assertEquals(s2, s);
	}
}
