package net.sourceforge.javajson.test;

import junit.framework.TestCase;
import net.sourceforge.javajson.JsonObject;

/**
 * 
 number format exception:
 * 
 * at sun.misc.FloatingDecimal.readJavaFormatString(FloatingDecimal.java:1224)
 * at java.lang.Double.parseDouble(Double.java:510) at
 * net.sourceforge.javajson.JsonValue.setString(JsonValue.java:371) at
 * net.sourceforge.javajson.JsonValue.<init>(JsonValue.java:69) at
 * net.sourceforge.javajson.JsonObject.put(JsonObject.java:510) at
 * net.sourceforge.javajson.JsonObject.put(JsonObject.java:467) at
 * net.sourceforge.javajson.parser.ASTobject.push(ASTobject.java:41)
 * 
 * @author mdeanda
 * 
 */
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

		input = "{\"a\":\".\"}";
		json = JsonObject.parse(input);
		assertFalse(json.isDouble("a"));
		assertFalse(json.isBoolean("a"));
		assertFalse(json.isFloat("a"));
		assertFalse(json.isInt("a"));
		assertFalse(json.isLong("a"));
		assertTrue(json.isString("a"));
		assertEquals(input, json.toString());

		input = "{\"a\":\"0.1\"}";
		json = JsonObject.parse(input);
		assertTrue(json.isDouble("a"));
		assertFalse(json.isBoolean("a"));
		assertTrue(json.isFloat("a"));
		assertFalse(json.isInt("a"));
		assertFalse(json.isLong("a"));
		assertTrue(json.isString("a"));
		assertEquals(input, json.toString());

		input = "{\"a\":\".3\"}";
		json = JsonObject.parse(input);
		assertTrue(json.isDouble("a"));
		assertFalse(json.isBoolean("a"));
		assertTrue(json.isFloat("a"));
		assertFalse(json.isInt("a"));
		assertFalse(json.isLong("a"));
		assertTrue(json.isString("a"));
		assertEquals(input, json.toString());

		input = "{\"a\":\"5.\"}";
		json = JsonObject.parse(input);
		assertTrue(json.isDouble("a"));
		assertFalse(json.isBoolean("a"));
		assertTrue(json.isFloat("a"));
		assertFalse(json.isInt("a"));
		assertFalse(json.isLong("a"));
		assertTrue(json.isString("a"));
		assertEquals(5.0d, json.getDouble("a"));
		assertEquals(input, json.toString());

		input = "{\"a\":\"1.3\"}";
		json = JsonObject.parse(input);
		assertTrue(json.isDouble("a"));
		assertFalse(json.isBoolean("a"));
		assertTrue(json.isFloat("a"));
		assertFalse(json.isInt("a"));
		assertFalse(json.isLong("a"));
		assertTrue(json.isString("a"));
		assertEquals(input, json.toString());
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
	
	public void testVerifyReturnedData() throws Exception {
		String input;
		JsonObject json;

		input = "{a:\"-\"}";
		json = JsonObject.parse(input);
		assertEquals("-", json.getString("a"));

		input = "{a:\"003\"}";
		json = JsonObject.parse(input);
		assertEquals(3, json.getInt("a"));
		assertEquals("003", json.getString("a"));

		double pi = 3.141526;
		String pis = String.valueOf(pi);
		input = "{a:\"" + pis + "\"}";
		json = JsonObject.parse(input);
		assertEquals(pis, json.getString("a"));
		assertEquals(pi, json.getDouble("a"));
}
}
