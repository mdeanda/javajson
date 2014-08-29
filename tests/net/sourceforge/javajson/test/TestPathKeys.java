package net.sourceforge.javajson.test;

import net.sourceforge.javajson.JsonArray;
import net.sourceforge.javajson.JsonObject;

import junit.framework.TestCase;

public class TestPathKeys extends TestCase {

	private JsonObject json;

	private JsonArray array;

	@Override
	public void setUp() throws Exception {
		json = JsonObject
				.parse("{s:'string1',b:true,i:1,o:{s2:'string2',b2:true,i2:2,o2:{s3:'string3',b3:true,i3:3}}}");
		array = new JsonArray();
	}

	public void testHasKey() throws Exception {
		assertTrue("1 level deep", json.hasKey("b"));
		assertTrue("2 levels deep", json.hasKey("o", "b2"));
		assertTrue("3 levels deep", json.hasKey("o", "o2", "s3"));
	}

	public void testBoolean() throws Exception {
		assertNotNull("1 level deep", json.get("b"));
		assertNotNull("2 levels deep", json.get("o", "b2"));
		assertNotNull("3 levels deep", json.get("o", "o2", "b3"));
	}
	
	public void testValue() throws Exception {
		assertEquals("1 level deep", true, json.get("b").getBoolean());
		assertEquals("2 levels deep", true, json.get("o", "b2").getBoolean());
		assertEquals("3 levels deep", "string3", json.get("o", "o2", "s3").getString());
	}
	
	public void testValueNotExist() {
		assertNull(json.get("a", "b").getString());
		assertTrue(json.get("a", "b").isNull());
	}

}
