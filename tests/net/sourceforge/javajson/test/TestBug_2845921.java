package net.sourceforge.javajson.test;

import junit.framework.TestCase;
import net.sourceforge.javajson.JsonObject;

/**
 * for example: json.put("foo", "007") is a string and toString should output
 * "007" instead of 7 (without quotes even)
 * 
 * same for parsing: {foo: "007"} should act the same but {foo: 007} should be
 * treated as a number and trailing zeros can go away
 * 
 * @author mdeanda
 * 
 */
public class TestBug_2845921 extends TestCase {

	public void testParseAsString() throws Exception {
		JsonObject json = JsonObject.parse("{a:'32'}");
		assertEquals("expected quotes around number", "{\"a\":\"32\"}", json
				.toString());

		json = JsonObject.parse("{a:\"32\"}");
		assertEquals("expected quotes around number", "{\"a\":\"32\"}", json
				.toString());

		json = JsonObject.parse("{a:'0032'}");
		assertEquals("expected quotes around number", "{\"a\":\"0032\"}", json
				.toString());

		json = JsonObject.parse("{a:\"0032\"}");
		assertEquals("expected quotes around number", "{\"a\":\"0032\"}", json
				.toString());
	}

	public void testParseAsNumber() throws Exception {
		JsonObject json = JsonObject.parse("{a:32}");
		assertEquals("expected normal number", "{\"a\":32}", json.toString());

		json = JsonObject.parse("{a: 0032}");
		assertEquals("expected normal number", "{\"a\":32}", json.toString());

		json = JsonObject.parse("{a:0.032}");
		assertEquals("expected normal number", "{\"a\":0.032}", json.toString());

		json = JsonObject.parse("{a:-00.32}");
		assertEquals("expected normal number", "{\"a\":-0.32}", json.toString());
	}

	public void testSetAsString() throws Exception {
		JsonObject json = new JsonObject();
		json.put("a", "007");
		assertEquals("expected string", "{\"a\":\"007\"}", json.toString());
	}

	public void testSetAsNumber() throws Exception {
		JsonObject json = new JsonObject();
		json.put("a", 007);
		assertEquals("expected number", "{\"a\":7}", json.toString());
	}

}
