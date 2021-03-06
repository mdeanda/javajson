package com.thedeanda.javajson.test;

import junit.framework.TestCase;

import com.thedeanda.javajson.JsonArray;
import com.thedeanda.javajson.JsonException;
import com.thedeanda.javajson.JsonObject;

public class TestParser extends TestCase {

	public void testParseNumbers() throws Exception {
		String input;
		JsonObject json;

		input = "{\"a\":3}";
		json = JsonObject.parse(input);
		assertEquals(3, json.getInt("a"));

		input = "{\"a\":-1}";
		json = JsonObject.parse(input);
		assertEquals(-1, json.getInt("a"));
		assertEquals(-1, json.getLong("a"));

		input = "{\"a\":-1.4}";
		json = JsonObject.parse(input);
		assertEquals(-1.4f, json.getFloat("a"));
		assertEquals(-1.4, json.getDouble("a"));
	}

	public void testParseNulls() throws Exception {
		String input;
		JsonObject json;

		input = "{\"a\": null}";
		json = JsonObject.parse(input);
		assertTrue(json.hasKey("a"));
		assertFalse(json.hasKey("b"));
		assertNull(json.getJsonObject("a"));
		assertNull(json.getString("a"));

		input = "{null: null}";
		try {
			json = JsonObject.parse(input);
			fail("missed null key");
		} catch (JsonException ex) {

		}

	}

	public void testParseArray() throws Exception {
		String input = "{\"array\":[1, 2, 3]}";
		JsonObject json = JsonObject.parse(input);

		assertEquals(1, json.size());
		assertTrue(json.isJsonArray("array"));
		assertEquals(1, json.getJsonArray("array").getInt(0));
		assertEquals(2, json.getJsonArray("array").getInt(1));
		assertEquals(3, json.getJsonArray("array").getInt(2));

		JsonArray arr;
		try {
			arr = JsonArray.parse("");
			fail("Expected parse exception");
		} catch (JsonException je) {

		}

		arr = JsonArray.parse("[]");
		assertEquals(0, arr.size());

		arr = JsonArray.parse("[1]");
		assertEquals(1, arr.size());

		arr = JsonArray.parse(" [ 3 ] ");
		assertEquals(1, arr.size());
	}

	public void testParseNestedArray() throws Exception {
		String input = "{\"array\":[1, [5, 6], 3]}";
		JsonObject json = JsonObject.parse(input);

		assertEquals(1, json.size());
		assertTrue(json.isJsonArray("array"));
		assertEquals(1, json.getJsonArray("array").getInt(0));
		assertTrue(json.getJsonArray("array").isJsonArray(1));
		assertNotNull(json.getJsonArray("array").getJsonArray(1));
		assertEquals(5, json.getJsonArray("array").getJsonArray(1).getInt(0));
		assertEquals(6, json.getJsonArray("array").getJsonArray(1).getInt(1));
		assertEquals(3, json.getJsonArray("array").getInt(2));
	}

	public void testParseArrayNestedObject() throws Exception {
		String input = "{\"array\":[1, {\"a\":5, \"b\" : 6}, 3]}";
		JsonObject json = JsonObject.parse(input);

		assertEquals(1, json.size());
		assertTrue(json.isJsonArray("array"));
		assertEquals(1, json.getJsonArray("array").getInt(0));
		assertTrue(json.getJsonArray("array").isJsonObject(1));
		assertNotNull(json.getJsonArray("array").getJsonObject(1));
		assertEquals(5, json.getJsonArray("array").getJsonObject(1).getInt("a"));
		assertEquals(6, json.getJsonArray("array").getJsonObject(1).getInt("b"));
		assertEquals(3, json.getJsonArray("array").getInt(2));
	}

	public void testParseObjectNestedObject() throws Exception {
		String input = "{\"obj1\":{ \"obj2\": {\"a\":5, \"b\" : 6}}}";
		JsonObject json = JsonObject.parse(input);

		assertEquals(1, json.size());
		assertTrue(json.isJsonObject("obj1"));
		assertNotNull(json.getJsonObject("obj1").getJsonObject("obj2"));
		assertEquals(5, json.getJsonObject("obj1").getJsonObject("obj2")
				.getInt("a"));
		assertEquals(6, json.getJsonObject("obj1").getJsonObject("obj2")
				.getInt("b"));

		json = JsonObject.parse("{}");
		json = JsonObject.parse("{ }");
	}

	public void testParseStrings() throws Exception {
		assertEquals("value", JsonObject.parse("{\"key\": \"value\"}")
				.getString("key"));
		assertEquals("value", JsonObject.parse("{\"key\": 'value'}").getString(
				"key"));

		assertEquals("val'ue", JsonObject.parse("{\"key\": \"val'ue\"}")
				.getString("key"));
		assertEquals("va\"lue", JsonObject.parse("{\"key\": 'va\"lue'}")
				.getString("key"));
		assertEquals("va\"lue", JsonObject.parse("{\"key\": \"va\\\"lue\"}")
				.getString("key"));
		assertEquals("va'lue", JsonObject.parse("{\"key\": 'va\\'lue'}")
				.getString("key"));

		assertEquals("val\bue", JsonObject.parse("{\"key\": \"val\\bue\"}")
				.getString("key"));
		assertEquals("val\bue", JsonObject.parse("{\"key\": 'val\\bue'}")
				.getString("key"));

		assertEquals("val\fue", JsonObject.parse("{\"key\": \"val\\fue\"}")
				.getString("key"));
		assertEquals("val\fue", JsonObject.parse("{\"key\": 'val\\fue'}")
				.getString("key"));

		assertEquals("val\nue", JsonObject.parse("{\"key\": \"val\\nue\"}")
				.getString("key"));
		assertEquals("val\nue", JsonObject.parse("{\"key\": 'val\\nue'}")
				.getString("key"));

		assertEquals("val\rue", JsonObject.parse("{\"key\": \"val\\rue\"}")
				.getString("key"));
		assertEquals("val\rue", JsonObject.parse("{\"key\": 'val\\rue'}")
				.getString("key"));

		assertEquals("val\tue", JsonObject.parse("{\"key\": \"val\\tue\"}")
				.getString("key"));
		assertEquals("val\tue", JsonObject.parse("{\"key\": 'val\\tue'}")
				.getString("key"));

	}

	public void testParseKeys() throws JsonException {
		// simple keys, different quotes
		assertTrue(JsonObject.parse("{\"key\": 1}").hasKey("key"));
		assertTrue(JsonObject.parse("{'key': 1}").hasKey("key"));
		assertTrue(JsonObject.parse("{key: 1}").hasKey("key"));

		assertTrue(JsonObject.parse("{ke_y: 1}").hasKey("ke_y"));
		assertTrue(JsonObject.parse("{ke3y: 1}").hasKey("ke3y"));

		try {
			JsonObject.parse("{ke y: 1}");
			fail("invalid key not caught");
		} catch (JsonException ex) {
		}

		try {
			JsonObject.parse("{'key: 1}");
			fail("invalid key not caught");
		} catch (JsonException ex) {
		}

		try {
			JsonObject.parse("{key': 1}");
			fail("invalid key not caught");
		} catch (JsonException ex) {
		}

		try {
			JsonObject.parse("{0key: 1}");
			fail("invalid key not caught");
		} catch (JsonException ex) {
		}

	}

	public void testParseArrayAsRoot() throws JsonException {
		assertTrue(JsonArray.parse("[\"key\", 1]").size() == 2);
		assertEquals("key", JsonArray.parse("[\"key\", 1]").getString(0));
		assertEquals(1, JsonArray.parse("[\"key\", 1]").getInt(1));
	}

	public void testParseTypes() throws JsonException {
		JsonObject json = JsonObject.parse("{key:1146206482115}");
		assertEquals(1146206482115l, json.getLong("key"));

		assertTrue(json.isLong("key"));
		assertFalse(json.isInt("key"));
		assertEquals(new Long(1146206482115l).intValue(), json.getInt("key"));

		// make sure strings can parse to longs properly
		json = JsonObject.parse("{key:\"1146206482115\"}");
		assertEquals(1146206482115l, json.getLong("key"));

	}

	public void testParseSpacePadded() throws JsonException {
		JsonObject json = JsonObject.parse("\n\n  \t    {key:1} ");
		assertEquals(1, json.getInt("key"));

		JsonArray arr = JsonArray.parse("\n\n \t   [1] ");
		assertEquals(1, arr.getInt(0));
	}

	public void testParseEmpty() throws JsonException {
		JsonObject json = JsonObject.parse("{}");
		assertNotNull(json);

		json = JsonObject.parse("{ }");
		assertNotNull(json);

		json = JsonObject.parse("{\n\n}");
		assertNotNull(json);

		JsonArray arr = JsonArray.parse("[]");
		assertNotNull(arr);

		arr = JsonArray.parse("[ ]");
		assertNotNull(arr);

		arr = JsonArray.parse("[\n\n]");
		assertNotNull(arr);
	}
}
