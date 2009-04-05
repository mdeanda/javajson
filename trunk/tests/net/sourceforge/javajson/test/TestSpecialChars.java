package net.sourceforge.javajson.test;

import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;
import net.sourceforge.javajson.JsonObject;

public class TestSpecialChars extends TestCase {

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
	}

	public void testParseValueDoubleQuotes() throws Exception {
		String input;
		JsonObject json;

		for (TestValue value : tests) {
			input = "{\"a\":\"" + value.json + "\"}";
			json = JsonObject.parse(input);
			assertEquals(value.message, value.string, json.getString("a"));
		}
	}

	public void testParseKeyDoubleQuotes() throws Exception {
		String input;
		JsonObject json;

		for (TestValue value : tests) {
			input = "{\"" + value.json + "\":true}";
			json = JsonObject.parse(input);
			//System.out.println(json);
			assertTrue(value.message, json.hasKey(value.string));
			assertTrue(value.message, json.isBoolean(value.string));
			assertTrue(value.message, json.getBoolean(value.string));
		}
	}

	public void testParseValueSingleQuotes() throws Exception {
		for (TestValue value : tests) {
			String input = "{\"aaa\":'" + value.json + "'}";
			System.out.println("input: " + input);
			JsonObject json = JsonObject.parse(input);
			assertEquals(value.message, value.string, json.getString("aaa"));
		}
	}

	public void testParseKeySingleQuotes() throws Exception {
		String input;
		JsonObject json;

		for (TestValue value : tests) {
			input = "{'" + value.json + "':true}";
			json = JsonObject.parse(input);
			assertTrue(value.message, json.hasKey(value.string));
			assertTrue(value.message, json.isBoolean(value.string));
			assertTrue(value.message, json.getBoolean(value.string));
		}
	}

	private class TestValue {
		public String json;
		public String string;
		public String message;

		public TestValue(String json, String string, String message) {
			this.json = json;
			this.string = string;
			this.message = message;
		}
	}
}
