package net.sourceforge.javajson.test;

import junit.framework.TestCase;
import net.sourceforge.javajson.JsonObject;

public class TestNumbersAsStringOrNumber extends TestCase {

	public void testAsString() throws Exception {
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

	public void testAsNumber() throws Exception {
		JsonObject json = JsonObject.parse("{a:32}");
		assertEquals("expected normal number", "{\"a\":32}", json.toString());

		json = JsonObject.parse("{a: 0032}");
		assertEquals("expected normal number", "{\"a\":32}", json.toString());

		json = JsonObject.parse("{a:0.032}");
		assertEquals("expected normal number", "{\"a\":0.032}", json.toString());

		json = JsonObject.parse("{a:-00.32}");
		assertEquals("expected normal number", "{\"a\":-0.32}", json.toString());
	}

}
