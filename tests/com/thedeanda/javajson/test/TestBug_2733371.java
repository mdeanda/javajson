package com.thedeanda.javajson.test;

import junit.framework.TestCase;

import com.thedeanda.javajson.JsonObject;

/**
 * { a: 'a' } doesn't parse but { a: "a" } does.
 * 
 * @author mdeanda
 * 
 */
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
