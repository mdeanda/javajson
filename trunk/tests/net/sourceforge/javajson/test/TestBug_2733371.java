package net.sourceforge.javajson.test;

import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;
import net.sourceforge.javajson.JsonObject;

public class TestBug_2733371 extends TestCase {

	public void testSingleQuoteSingleLetterValue() throws Exception {
		String input;
		JsonObject json;

		input = "{a:\"a\"}";
		json = JsonObject.parse(input);
		assertEquals("a", json.getString("a"));

		input = "{a:'a'}";
		json = JsonObject.parse(input);
		assertEquals("a", json.getString("a"));
	}

	public void testSingleQuoteSingleLetterKey() throws Exception {
		String input;
		JsonObject json;

		input = "{a:\"abc\"}";
		json = JsonObject.parse(input);
		assertEquals("abc", json.getString("a"));

		input = "{\"a\":'abc'}";
		json = JsonObject.parse(input);
		assertEquals("abc", json.getString("a"));

		input = "{'a':'abc'}";
		json = JsonObject.parse(input);
		assertEquals("abc", json.getString("a"));
	}

	public void testEmptyString() throws Exception {
		String input;
		JsonObject json;

		input = "{abc:\"\"}";
		json = JsonObject.parse(input);
		assertEquals("", json.getString("abc"));

		input = "{abc:''}";
		json = JsonObject.parse(input);
		assertEquals("", json.getString("abc"));
	}
}
