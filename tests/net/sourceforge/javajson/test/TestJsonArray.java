package net.sourceforge.javajson.test;

import net.sourceforge.javajson.JsonArray;

import junit.framework.TestCase;

public class TestJsonArray extends TestCase {

	JsonArray array;
	
	@Override
	public void setUp() throws Exception {
		array = new JsonArray();
	}

	public void testArrayBasic() throws Exception {
		assertEquals(0, array.size());
		
		array.add(true);
		assertEquals(1, array.size());
		assertTrue(array.getBoolean(0));
		assertTrue(array.isBoolean(0));
		assertFalse(array.isJsonArray(0));
		assertFalse(array.isJsonObject(0));
		assertFalse(array.isNumber(0));
		assertTrue(array.isString(0));
		
		array.add("string");
		assertEquals(2, array.size());
		assertEquals("string", array.getString(1));
		assertFalse(array.isBoolean(1));
		assertFalse(array.isJsonArray(1));
		assertFalse(array.isJsonObject(1));
		assertFalse(array.isNumber(1));
		assertTrue(array.isString(1));
		
		array.add(3.1415);
		assertEquals(3, array.size());
		//assertEquals("string", array.getString(1));
		assertFalse(array.isBoolean(2));
		assertFalse(array.isJsonArray(2));
		assertFalse(array.isJsonObject(2));
		assertTrue(array.isNumber(2));
		assertTrue(array.isString(2));
		assertEquals("[true,\"string\",3.1415]", array.toString());
		
		
		
	}
}
