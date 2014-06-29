package net.sourceforge.javajson.test;

import java.util.List;

import junit.framework.TestCase;
import net.sourceforge.javajson.JsonArray;
import net.sourceforge.javajson.JsonObject;
import net.sourceforge.javajson.JsonValue;

public class TestFindArray extends TestCase {
	JsonObject json;
	private JsonObject json1;
	private JsonObject json2;
	private JsonObject json3;
	private JsonArray arr;

	@Override
	public void setUp() {
		json = new JsonObject();
		json.put("key1", "value 1");

		arr = new JsonArray();
		json.put("a", arr);

		json1 = new JsonObject();
		json1.put("json1key1", "json 1 key 1 value");
		json1.put("id", "json1key1id");
		arr.add(json1);

		json2 = new JsonObject();
		json2.put("json2key1", "json 2 key 1 value");
		json2.put("id", "json1key1id");
		arr.add(json2);

		json3 = new JsonObject();
		json3.put("json3key1", "json 3 key 1 value");
		json3.put("id", "json1key1id");
		arr.add(json3);
	}

	public void testIndex() {
		List<JsonValue> matches = json.find("a", "1");
		assertNotNull(matches);
		assertEquals(1, matches.size());
		assertEquals(json2.toString(), matches.get(0).toString());
	}

	public void testIndexField() {
		List<JsonValue> matches = json.find("a", "1", "id");
		assertNotNull(matches);
		assertEquals(1, matches.size());
		assertEquals(json2.get("id").toString(), matches.get(0).toString());
	}

	public void testBasicValue() {
		List<JsonValue> matches = json.find(".*", "id");
		assertNotNull(matches);
		assertEquals(3, matches.size());
		assertEquals(json1.getString("id"), matches.get(0).getString());
	}

	public void testDeepNonExistPath() {
		List<JsonValue> matches = json.find("a", "id", "monkey", "food");
		assertNotNull(matches);
		assertEquals(0, matches.size());
	}
}
