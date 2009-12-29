package net.sourceforge.javajson.test;

import junit.framework.TestCase;
import net.sourceforge.javajson.JsonObject;

public class TestBug_2916831 extends TestCase {

	/**
	 * Turns out the bug was in some code in string parsing that checked if it
	 * was a number and converted. Let's also make sure it still finds numbers.
	 * All of these return false for isNumber checks because the value is
	 * wrapped like a string. This fixes a different issue where string values
	 * like "0032" lose leading zeros and therefore change the data. Numbers
	 * should NOT be quoted when parsed.
	 * 
	 * @throws Exception
	 */
	public void testRealBug() throws Exception {
		String input;
		JsonObject json;

		input = "{a:\".\"}";
		json = JsonObject.parse(input);
		assertFalse(json.isDouble("a"));
		assertFalse(json.isBoolean("a"));
		assertFalse(json.isFloat("a"));
		assertFalse(json.isInt("a"));
		assertFalse(json.isLong("a"));
		assertTrue(json.isString("a"));

		input = "{a:\"0.1\"}";
		json = JsonObject.parse(input);
		assertFalse(json.isDouble("a"));
		assertFalse(json.isBoolean("a"));
		assertFalse(json.isFloat("a"));
		assertFalse(json.isInt("a"));
		assertFalse(json.isLong("a"));
		assertTrue(json.isString("a"));

		input = "{a:\".3\"}";
		json = JsonObject.parse(input);
		assertFalse(json.isDouble("a"));
		assertFalse(json.isBoolean("a"));
		assertFalse(json.isFloat("a"));
		assertFalse(json.isInt("a"));
		assertFalse(json.isLong("a"));
		assertTrue(json.isString("a"));

		input = "{a:\"5.\"}";
		json = JsonObject.parse(input);
		assertFalse(json.isDouble("a"));
		assertFalse(json.isBoolean("a"));
		assertFalse(json.isFloat("a"));
		assertFalse(json.isInt("a"));
		assertFalse(json.isLong("a"));
		assertTrue(json.isString("a"));
		assertEquals(5, json.getInt("a"));

		input = "{a:\"1.3\"}";
		json = JsonObject.parse(input);
		assertFalse(json.isDouble("a"));
		assertFalse(json.isBoolean("a"));
		assertFalse(json.isFloat("a"));
		assertFalse(json.isInt("a"));
		assertFalse(json.isLong("a"));
		assertTrue(json.isString("a"));
	}

	public void testValue() throws Exception {
		String input;
		JsonObject json;

		input = "{a:\"-\"}";
		json = JsonObject.parse(input);
		assertFalse(json.isDouble("a"));
		assertFalse(json.isBoolean("a"));
		assertFalse(json.isFloat("a"));
		assertFalse(json.isInt("a"));
		assertEquals("-", json.getString("a"));

		input = "{a:\".\"}";
		json = JsonObject.parse(input);
		assertFalse(json.isDouble("a"));
		assertEquals(".", json.getString("a"));
	}
}
