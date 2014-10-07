package com.thedeanda.javajson.test;

import java.util.List;

import junit.framework.TestCase;

import com.thedeanda.javajson.JsonObject;
import com.thedeanda.javajson.JsonValue;

public class TestFindBasic extends TestCase {
	JsonObject json;
	private JsonObject json1;
	private JsonObject json2;
	private JsonObject json3;

	@Override
	public void setUp() {
		json = new JsonObject();
		json.put("key1", "value 1");

		json1 = new JsonObject();
		json1.put("json1key1", "json 1 key 1 value");
		json1.put("id", "json1key1id");
		json.put("obj1", json1);

		json2 = new JsonObject();
		json2.put("json2key1", "json 2 key 1 value");
		json2.put("id", "json1key1id");
		json.put("obj2", json2);

		json3 = new JsonObject();
		json3.put("json3key1", "json 3 key 1 value");
		json3.put("id", "json1key1id");
		json.put("obj3", json3);
	}

	public void testBasic() {
		List<JsonValue> matches = json.find(".*");
		assertNotNull(matches);
		assertEquals(4, matches.size());
		assertEquals(json.getString("key1"), matches.get(0).getString());
		assertEquals(json1.toString(), matches.get(1).toString());
	}

	public void testBasicValue() {
		List<JsonValue> matches = json.find(".*", "id");
		assertNotNull(matches);
		assertEquals(3, matches.size());
		assertEquals(json1.getString("id"), matches.get(0).getString());
	}

	public void testDeepNonExistPath() {
		List<JsonValue> matches = json.find(".*", "id", "monkey", "food");
		assertNotNull(matches);
		assertEquals(0, matches.size());
	}
}
