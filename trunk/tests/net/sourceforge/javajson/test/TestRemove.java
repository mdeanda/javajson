package net.sourceforge.javajson.test;

import junit.framework.TestCase;
import net.sourceforge.javajson.JsonArray;
import net.sourceforge.javajson.JsonObject;

public class TestRemove extends TestCase {
	private JsonObject json;

	private JsonArray array;

	@Override
	public void setUp() throws Exception {
		json = new JsonObject();
		array = new JsonArray();
	}

	public void testRemoveFromObject() {
		json.put("test", "value");
		assertEquals("value", json.getString("test"));
		assertTrue("has key", json.hasKey("test"));
		json.remove("test");
		assertFalse("remove key failed", json.hasKey("test"));
	}
	
	public void testRemoveFromArray() {
		array.add(true);
		assertEquals("item in array", 1, array.size());
		array.remove(0);
		assertEquals("remove failed", 0, array.size());
	}
	
	public void testRemoveFromMiddleOfArray() {
		array.add(0);
		array.add(1);
		array.add(2);
		assertEquals(0, array.getInt(0));
		assertEquals(1, array.getInt(1));
		assertEquals(2, array.getInt(2));
		array.remove(1);
		assertEquals(0, array.getInt(0));
		assertEquals(2, array.getInt(1));
	}
}
