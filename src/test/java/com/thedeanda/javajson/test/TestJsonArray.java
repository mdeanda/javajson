package com.thedeanda.javajson.test;

import junit.framework.TestCase;

import com.thedeanda.javajson.JsonArray;
import com.thedeanda.javajson.JsonObject;

public class TestJsonArray extends TestCase {

	JsonArray array;

	@Override
	public void setUp() throws Exception {
		array = new JsonArray();
	}

	public void testArrayBasic() throws Exception {
		assertEquals(0, array.size());

		array.add(true);
		assertEquals(1, array.size());
		assertTrue(array.getBoolean(0));

		array.add("string");
		assertEquals(2, array.size());
		assertEquals("string", array.getString(1));

		array.add(3.1415);
		assertEquals(3, array.size());
		// assertEquals("string", array.getString(1));
		assertEquals("[true,\"string\",3.1415]", array.toString());
	}

	public void testToString() throws Exception {
		assertEquals("[]", array.toString(2));

		array.add(2);
		assertEquals("[\n  2\n]", array.toString(2));

		array.add(false);
		assertEquals("[\n  2,\n  false\n]", array.toString(2));

		JsonObject json = new JsonObject();
		array.add(json);
		assertEquals("[\n  2,\n  false,\n  {}\n]", array.toString(2));

		JsonArray ar2 = new JsonArray();
		array.add(ar2);
		assertEquals("[\n  2,\n  false,\n  {},\n  []\n]", array.toString(2));

		ar2.add(3);
		assertEquals("[\n  2,\n  false,\n  {},\n  [\n    3\n  ]\n]", array
				.toString(2));

		json.put("key", 3);
		assertEquals(
				"[\n  2,\n  false,\n  {\n    \"key\":3\n  },\n  [\n    3\n  ]\n]",
				array.toString(2));

		json = new JsonObject();
		ar2.add(json);
		assertEquals(
				"[\n  2,\n  false,\n  {\n    \"key\":3\n  },\n  [\n    3,\n    {}\n  ]\n]",
				array.toString(2));

		json.put("test", "ok");
		assertEquals(
				"[\n  2,\n  false,\n  {\n    \"key\":3\n  },\n  [\n    3,\n    {\n      \"test\":\"ok\"\n    }\n  ]\n]",
				array.toString(2));
	}

	public void testIsMethods() throws Exception {
		array.add(true); // 0
		array.add(3.5d); // 1
		array.add(2.17f); // 2
		array.add(6); // 3
		array.add(new JsonArray()); // 4
		array.add(new JsonObject()); // 5
		array.add(15l); // 6
		array.add("string"); // 7

		assertEquals(8, array.size());

		assertTrue(array.isBoolean(0)); // boolean
		assertFalse(array.isBoolean(1)); // double
		assertFalse(array.isBoolean(2)); // float
		assertFalse(array.isBoolean(3)); // int
		assertFalse(array.isBoolean(4)); // array
		assertFalse(array.isBoolean(5)); // object
		assertFalse(array.isBoolean(6)); // long
		assertFalse(array.isBoolean(7)); // string

		assertFalse(array.isDouble(0));
		assertTrue(array.isDouble(1));
		assertTrue(array.isDouble(2));
		assertTrue(array.isDouble(3));
		assertFalse(array.isDouble(4));
		assertFalse(array.isDouble(5));
		assertTrue(array.isDouble(6));
		assertFalse(array.isDouble(7));

		assertFalse(array.isFloat(0));
		assertTrue(array.isFloat(1));
		assertTrue(array.isFloat(2));
		assertTrue(array.isFloat(3));
		assertFalse(array.isFloat(4));
		assertFalse(array.isFloat(5));
		assertTrue(array.isFloat(6));
		assertFalse(array.isFloat(7));

		assertFalse(array.isInt(0));
		assertFalse(array.isInt(1));
		assertFalse(array.isInt(2));
		assertTrue(array.isInt(3));
		assertFalse(array.isInt(4));
		assertFalse(array.isInt(5));
		assertTrue(array.isInt(6));
		assertFalse(array.isInt(7));

		assertFalse(array.isJsonArray(0));
		assertFalse(array.isJsonArray(1));
		assertFalse(array.isJsonArray(2));
		assertFalse(array.isJsonArray(3));
		assertTrue(array.isJsonArray(4));
		assertFalse(array.isJsonArray(5));
		assertFalse(array.isJsonArray(6));
		assertFalse(array.isJsonArray(7));

		assertFalse(array.isJsonObject(0));
		assertFalse(array.isJsonObject(1));
		assertFalse(array.isJsonObject(2));
		assertFalse(array.isJsonObject(3));
		assertFalse(array.isJsonObject(4));
		assertTrue(array.isJsonObject(5));
		assertFalse(array.isJsonObject(6));
		assertFalse(array.isJsonObject(7));

		assertFalse(array.isLong(0));
		assertFalse(array.isLong(1));
		assertFalse(array.isLong(2));
		assertTrue(array.isLong(3));
		assertFalse(array.isLong(4));
		assertFalse(array.isLong(5));
		assertTrue(array.isLong(6));
		assertFalse(array.isLong(7));

		assertTrue(array.isString(0));
		assertTrue(array.isString(1));
		assertTrue(array.isString(2));
		assertTrue(array.isString(3));
		assertFalse(array.isString(4));
		assertFalse(array.isString(5));
		assertTrue(array.isString(6));
		assertTrue(array.isString(7));
	}

	public void testRemove() {
		JsonArray arr = new JsonArray();
		arr.add(true);
		assertEquals("[true]", arr.toString());
		arr.remove(0);
		assertEquals("[]", arr.toString());
	}

	public void testEnum() throws Exception {
		JsonArray json = new JsonArray();
		json.add(TestEnum.VALUE_ONE);
		assertEquals("VALUE_ONE", json.getString(0));
		assertEquals(TestEnum.VALUE_ONE.name(), json.getString(0));
	}
	
	public void testLongArrayStackError() throws Exception {
		JsonArray a = new JsonArray();
		for (int i=0; i<100000; i++) {
			a.add(i);
		}
		JsonArray.parse(a.toString());
	}
}
