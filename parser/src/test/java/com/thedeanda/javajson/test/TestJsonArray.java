package com.thedeanda.javajson.test;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import com.thedeanda.javajson.JsonArray;
import com.thedeanda.javajson.JsonNativeType;
import com.thedeanda.javajson.JsonObject;
import com.thedeanda.javajson.JsonValue;

public class TestJsonArray {
	private static final Date dt = new Date(1510365107000l);
	private static final String dts = "2017-11-11T01:51:47.000Z";

	JsonArray array;

	@Before
	public void setUp() throws Exception {
		array = new JsonArray();
	}

	@Test
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

	@Test
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
		assertEquals("[\n  2,\n  false,\n  {},\n  [\n    3\n  ]\n]", array.toString(2));

		json.put("key", 3);
		assertEquals("[\n  2,\n  false,\n  {\n    \"key\":3\n  },\n  [\n    3\n  ]\n]", array.toString(2));

		json = new JsonObject();
		ar2.add(json);
		assertEquals("[\n  2,\n  false,\n  {\n    \"key\":3\n  },\n  [\n    3,\n    {}\n  ]\n]", array.toString(2));

		json.put("test", "ok");
		assertEquals(
				"[\n  2,\n  false,\n  {\n    \"key\":3\n  },\n  [\n    3,\n    {\n      \"test\":\"ok\"\n    }\n  ]\n]",
				array.toString(2));
	}

	@Test
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

	@Test
	public void testRemove() {
		JsonArray arr = new JsonArray();
		arr.add(true);
		assertEquals("[true]", arr.toString());
		arr.remove(0);
		assertEquals("[]", arr.toString());
	}

	@Test
	public void testEnum() throws Exception {
		JsonArray json = new JsonArray();
		json.add(TestEnum.VALUE_ONE);
		assertEquals("VALUE_ONE", json.getString(0));
		assertEquals(TestEnum.VALUE_ONE.name(), json.getString(0));
	}

	@Test
	public void testLongArrayStackError() throws Exception {
		JsonArray a = new JsonArray();
		for (int i = 0; i < 100000; i++) {
			a.add(i);
		}
		JsonArray.parse(a.toString());
	}

	@Test
	public void testBoolean() {
		array.add(true);
		array.add("true");
		array.add("t");

		assertTrue(array.getBoolean(0));
		assertTrue(array.isBoolean(0));

		assertTrue(array.getBoolean(1));
		assertFalse(array.isBoolean(1));

		assertFalse(array.getBoolean(2));
		assertFalse(array.isBoolean(2));

		try {
			array.getBoolean(-1);
			fail("expected out of bounds exception");
		} catch (IndexOutOfBoundsException e) {
		}

		try {
			array.isBoolean(-1);
			fail("expected out of bounds exception");
		} catch (IndexOutOfBoundsException e) {
		}

		try {
			array.getBoolean(3);
			fail("expected out of bounds exception");
		} catch (IndexOutOfBoundsException e) {
		}
		try {
			array.isBoolean(3);
			fail("expected out of bounds exception");
		} catch (IndexOutOfBoundsException e) {
		}
	}

	@Test
	public void testDouble() {
		array.add(2d);
		array.add("2.0");
		array.add(false);

		assertEquals(2d, array.getDouble(0), 1);
		assertTrue(array.isDouble(0));

		assertEquals(2d, array.getDouble(1), 1);
		assertFalse(array.isDouble(1));

		assertEquals(0d, array.getDouble(2), 1);
		assertFalse(array.isDouble(2));

		try {
			array.getDouble(-1);
			fail("expected out of bounds exception");
		} catch (IndexOutOfBoundsException e) {
		}

		try {
			array.isDouble(-1);
			fail("expected out of bounds exception");
		} catch (IndexOutOfBoundsException e) {
		}

		try {
			array.getDouble(3);
			fail("expected out of bounds exception");
		} catch (IndexOutOfBoundsException e) {
		}
		try {
			array.isDouble(3);
			fail("expected out of bounds exception");
		} catch (IndexOutOfBoundsException e) {
		}
	}

	@Test
	public void testFloat() {
		array.add(2f);
		array.add("2.0");
		array.add(false);

		assertEquals(2d, array.getFloat(0), 1);
		assertTrue(array.isFloat(0));

		assertEquals(2d, array.getFloat(1), 1);
		assertFalse(array.isFloat(1));

		assertEquals(0d, array.getFloat(2), 1);
		assertFalse(array.isFloat(2));

		try {
			array.getFloat(-1);
			fail("expected out of bounds exception");
		} catch (IndexOutOfBoundsException e) {
		}

		try {
			array.isFloat(-1);
			fail("expected out of bounds exception");
		} catch (IndexOutOfBoundsException e) {
		}

		try {
			array.getFloat(3);
			fail("expected out of bounds exception");
		} catch (IndexOutOfBoundsException e) {
		}
		try {
			array.isFloat(3);
			fail("expected out of bounds exception");
		} catch (IndexOutOfBoundsException e) {
		}
	}

	@Test
	public void testInt() {
		array.add(2);
		array.add("2");
		array.add(false);

		assertEquals(2, array.getInt(0));
		assertTrue(array.isInt(0));

		assertEquals(2, array.getInt(1));
		assertFalse(array.isInt(1));

		assertEquals(0, array.getInt(2));
		assertFalse(array.isInt(2));

		try {
			array.getInt(-1);
			fail("expected out of bounds exception");
		} catch (IndexOutOfBoundsException e) {
		}

		try {
			array.isInt(-1);
			fail("expected out of bounds exception");
		} catch (IndexOutOfBoundsException e) {
		}

		try {
			array.getInt(3);
			fail("expected out of bounds exception");
		} catch (IndexOutOfBoundsException e) {
		}
		try {
			array.isInt(3);
			fail("expected out of bounds exception");
		} catch (IndexOutOfBoundsException e) {
		}
	}

	@Test
	public void testLong() {
		array.add(2);
		array.add("2");
		array.add(false);

		assertEquals(2, array.getLong(0));
		assertTrue(array.isLong(0));

		assertEquals(2, array.getLong(1));
		assertFalse(array.isLong(1));

		assertEquals(0, array.getLong(2));
		assertFalse(array.isLong(2));

		try {
			array.getLong(-1);
			fail("expected out of bounds exception");
		} catch (IndexOutOfBoundsException e) {
		}

		try {
			array.isLong(-1);
			fail("expected out of bounds exception");
		} catch (IndexOutOfBoundsException e) {
		}

		try {
			array.getLong(3);
			fail("expected out of bounds exception");
		} catch (IndexOutOfBoundsException e) {
		}
		try {
			array.isLong(3);
			fail("expected out of bounds exception");
		} catch (IndexOutOfBoundsException e) {
		}
	}

	@Test
	public void testString() {
		array.add(2);
		array.add("2");
		array.add(false);

		assertEquals("2", array.getString(0));
		assertTrue(array.isString(0));

		assertEquals("2", array.getString(1));
		assertTrue(array.isString(1));

		assertEquals("false", array.getString(2));
		assertTrue(array.isString(2));

		try {
			array.getString(-1);
			fail("expected out of bounds exception");
		} catch (IndexOutOfBoundsException e) {
		}

		try {
			array.isString(-1);
			fail("expected out of bounds exception");
		} catch (IndexOutOfBoundsException e) {
		}

		try {
			array.getString(3);
			fail("expected out of bounds exception");
		} catch (IndexOutOfBoundsException e) {
		}
		try {
			array.isString(3);
			fail("expected out of bounds exception");
		} catch (IndexOutOfBoundsException e) {
		}
	}

	@Test
	public void testJson() {
		JsonObject jo = new JsonObject();
		JsonArray ja = new JsonArray();

		array.add(2);
		array.add(jo);
		array.add(ja);
		array.add(false);

		assertFalse(array.isJsonObject(0));

		assertEquals(jo, array.getJsonObject(1));
		assertTrue(array.isJsonObject(1));

		assertEquals(ja, array.getJsonArray(2));
		assertTrue(array.isJsonArray(2));

		assertEquals("false", array.getString(3));
		assertTrue(array.isString(3));

		try {
			array.getJsonObject(-1);
			fail("expected out of bounds exception");
		} catch (IndexOutOfBoundsException e) {
		}

		try {
			array.isJsonObject(-1);
			fail("expected out of bounds exception");
		} catch (IndexOutOfBoundsException e) {
		}

		try {
			array.getJsonObject(4);
			fail("expected out of bounds exception");
		} catch (IndexOutOfBoundsException e) {
		}

		try {
			array.isJsonObject(4);
			fail("expected out of bounds exception");
		} catch (IndexOutOfBoundsException e) {
		}

		try {
			array.getJsonArray(-1);
			fail("expected out of bounds exception");
		} catch (IndexOutOfBoundsException e) {
		}

		try {
			array.isJsonArray(-1);
			fail("expected out of bounds exception");
		} catch (IndexOutOfBoundsException e) {
		}

		try {
			array.getJsonArray(4);
			fail("expected out of bounds exception");
		} catch (IndexOutOfBoundsException e) {
		}
		try {
			array.isJsonArray(4);
			fail("expected out of bounds exception");
		} catch (IndexOutOfBoundsException e) {
		}
	}

	@Test
	public void testClear() {
		assertEquals(0, array.size());
		array.add(true);
		assertEquals(1, array.size());
		array.clear();
		assertEquals(0, array.size());
	}

	@Test
	public void testObjectAdd() {
		JsonArray v;

		v = new JsonArray();
		v.add(new Integer(3));
		assertEquals(1, v.size());
		assertTrue(v.isInt(0));
		assertEquals(3, v.getInt(0));

		v = new JsonArray();
		v.add(new Float(3.0f));
		assertEquals(1, v.size());
		assertTrue(v.isFloat(0));
		assertEquals(3.0f, v.getFloat(0), 0.01f);

		v = new JsonArray();
		v.add(new Double(3.0));
		assertEquals(1, v.size());
		assertTrue(v.isDouble(0));
		assertEquals(3.0, v.getDouble(0), 0.01);

		v = new JsonArray();
		v.add(new Long(3));
		assertEquals(1, v.size());
		assertTrue(v.isLong(0));
		assertEquals(3, v.getLong(0), 0.01);

		v = new JsonArray();
		v.add(Boolean.TRUE);
		assertEquals(1, v.size());
		assertTrue(v.isBoolean(0));
		assertEquals(true, v.getBoolean(0));

		v = new JsonArray();
		v.add(Boolean.FALSE);
		assertEquals(1, v.size());
		assertTrue(v.isBoolean(0));
		assertEquals(false, v.getBoolean(0));

		v = new JsonArray();
		v.add((Object) "s");
		assertEquals(1, v.size());
		assertTrue(v.isString(0));
		assertEquals("s", v.getString(0));

		JsonArray ja = new JsonArray();
		v = new JsonArray();
		v.add((Object) ja);
		assertEquals(1, v.size());
		assertTrue(v.isJsonArray(0));
		assertEquals(ja, v.getJsonArray(0));

		JsonObject jo = new JsonObject();
		v = new JsonArray();
		v.add((Object) jo);
		assertEquals(1, v.size());
		assertTrue(v.isJsonObject(0));
		assertEquals(jo, v.getJsonObject(0));

		v = new JsonArray();
		v.add((Object) dt);
		assertEquals(1, v.size());
		assertTrue(v.isString(0));
		assertEquals(dts, v.getString(0));

		try {
			v = new JsonArray();
			v.add(new Object());
			assertEquals(1, v.size());
			fail("expecting error");
		} catch (Exception e) {

		}
		
		v = new JsonArray();
		v.add((Object)null);
		assertEquals(1, v.size());
		assertFalse(v.isString(0));
		assertEquals(null, v.getJsonObject(0));
		assertEquals(null, v.getString(0));
		
		JsonValue jv = new JsonValue(3);
		v = new JsonArray();
		v.add(jv);
		assertEquals(1, v.size());
		assertTrue(v.isString(0));
		assertTrue(v.isInt(0));
		assertEquals(null, v.getJsonObject(0));
		assertEquals("3", v.getString(0));
		assertEquals(3, v.getInt(0));
	}
	
	@Test
	public void testIterator() {
		Iterator<JsonValue> it = array.iterator();
		
		assertNotNull(it);
		assertFalse(it.hasNext());
		
		array.add(true);
		
		it = array.iterator();
		assertNotNull(it);
		assertTrue(it.hasNext());
		assertEquals(true, it.next().getBoolean());
		assertFalse(it.hasNext());
	}
	
	@Test
	public void testAddChar() {
		array.add('c');
		assertEquals("c", array.getString(0));
	}
}
