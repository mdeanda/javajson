package net.sourceforge.javajson.test;

import net.sourceforge.javajson.JsonArray;
import net.sourceforge.javajson.JsonObject;

import junit.framework.TestCase;

public class TestBasicProperties extends TestCase {

	private JsonObject json;

	private JsonArray array;

	@Override
	public void setUp() throws Exception {
		json = new JsonObject();
		array = new JsonArray();
	}

	public void testString() throws Exception {
		json.put("key1", "value1");
		assertEquals("value1", json.getString("key1"));
		assertNotSame("value2", json.getString("key1"));

		json.put("key2", (String) null);
		assertNull(json.getString("key2"));

		json.put("key3", (Object) null);
		assertNull(json.getString("key3"));

		array.add("value2");
		assertEquals("value2", array.getString(0));

		array.add("value3");
		assertEquals("value3", array.getString(1));
		assertNotSame("value3", array.getString(0));

		array.add((String) null);
		assertNull(array.getString(2));
	}

	public void testToString() throws Exception {
		assertEquals("{}", json.toString());
		json.put("key1", "value1");
		assertEquals("{\"key1\":\"value1\"}", json.toString());

		assertEquals("[]", array.toString());
		array.add("value2");
		assertEquals("[\"value2\"]", array.toString());
		array.add("value4");
		assertEquals("[\"value2\",\"value4\"]", array.toString());
	}

	public void testParserSimple() throws Exception {
		String input = "{\"key\":\"value\",\"key2\":23,key3:3.1415,\"key4\":true}";
		JsonObject json = JsonObject.parse(input);

		// System.out.println(json);

		assertTrue("Expected key 'key' not found", json.hasKey("key"));
		assertEquals("value", json.getString("key"));

		assertTrue("Expected key 'key2' not found", json.hasKey("key2"));
		assertEquals(23, json.getInt("key2"));

		assertTrue("Expected key 'key3' not found", json.hasKey("key3"));
		assertTrue(3.141499f < json.getFloat("key3"));
		assertTrue(3.141501f > json.getFloat("key3"));
		assertTrue(3.141499 < json.getDouble("key3"));
		assertTrue(3.141501 > json.getDouble("key3"));

		assertTrue("Expected key 'key4' not found", json.hasKey("key4"));
		assertTrue(json.getBoolean("key4"));
	}
}
