package com.thedeanda.javajson.test;

import com.thedeanda.javajson.JsonObject;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestSpecialChars {

	private List<TestValue> tests = new LinkedList<TestValue>();

	public TestSpecialChars() {
		tests.add(new TestValue("aword", "aword", "a word"));
		tests.add(new TestValue("a", "a", "letter 'a'"));
		tests.add(new TestValue("\\n", "\n", "new line"));
		tests.add(new TestValue("\\t", "\t", "tab"));
		tests.add(new TestValue("\\r", "\r", "carriage return"));
		tests.add(new TestValue("\\/", "/", "slash"));
		tests.add(new TestValue("\\\\", "\\", "backslash"));
		tests.add(new TestValue("\\\"", "\"", "quotes"));
		tests.add(new TestValue("\\'", "'", "apostrophy"));
		tests
				.add(new TestValue("\\ufe85", "\ufe85",
						"unicode in \\u#### form"));
	}

	@Test
	public void testToStringThenParse() throws Exception {
		for (TestValue value : tests) {
			JsonObject json = new JsonObject();
			json.put("a", value.java);
			json = JsonObject.parse(json.toString());
			assertEquals(value.java, json.getString("a"), value.message);
		}
	}

	@Test
	public void testParseValueDoubleQuotesToStringAndBack() throws Exception {
		for (TestValue value : tests) {
			String input = "{\"a\":\"" + value.json + "\"}";
			System.out.println("before:" + input);
			JsonObject json = JsonObject.parse(input);
			System.out.println("after: " + json.toString());
			json = JsonObject.parse(json.toString());
			assertEquals(value.java, json.getString("a"), value.message);
		}
	}

	@Test
	public void testParseValueDoubleQuotes() throws Exception {
		String input;
		JsonObject json;

		for (TestValue value : tests) {
			input = "{\"a\":\"" + value.json + "\"}";
			json = JsonObject.parse(input);
			assertEquals(value.java, json.getString("a"), value.message);
		}
	}

	@Test
	public void testParseKeyDoubleQuotes() throws Exception {
		String input;
		JsonObject json;

		for (TestValue value : tests) {
			input = "{\"" + value.json + "\":true}";
			json = JsonObject.parse(input);
			assertTrue(json.hasKey(value.java), value.message);
			assertTrue(json.isBoolean(value.java), value.message);
			assertTrue(json.getBoolean(value.java), value.message);
		}
	}

	@Test
	public void testParseValueSingleQuotes() throws Exception {
		// add "x" + to avoid separate bug
		for (TestValue value : tests) {
			String input = "{\"aaa\":'x" + value.json + "'}";
			System.out.println("input: " + input + ", " + value.java);
			JsonObject json = JsonObject.parse(input);
			assertEquals("x" + value.java, json.getString("aaa"), value.message);
		}
	}

	@Test
	public void testParseKeySingleQuotes() throws Exception {
		// add "x" + to avoid separate bug
		for (TestValue value : tests) {
			String input = "{'x" + value.json + "':true}";
			JsonObject json = JsonObject.parse(input);
			assertTrue(json.hasKey("x" + value.java), value.message);
			assertTrue(json.isBoolean("x" + value.java), value.message);
			assertTrue(json.getBoolean("x" + value.java), value.message);
		}
	}

	private class TestValue {
		public String json;
		public String java;
		public String message;

		public TestValue(String json, String java, String message) {
			this.json = json;
			this.java = java;
			this.message = message;
		}
	}
}
