package net.sourceforge.javajson.test;

import net.sourceforge.javajson.JsonArray;
import net.sourceforge.javajson.JsonObject;
import net.sourceforge.javajson.JsonValue;
import junit.framework.TestCase;

public class TestJsonValue extends TestCase {


	public void testToString() throws Exception {
		JsonValue v = new JsonValue();
		assertEquals("null", v.toString());
		
		v = new JsonValue(true);
		assertEquals("true", v.toString());
		
		v = new JsonValue(false);
		assertEquals("false", v.toString());
		
		v = new JsonValue(15);
		assertEquals("15", v.toString());
		
		//strings should be the only ones quoted
		v = new JsonValue("simple string");
		assertEquals("\"simple string\"", v.toString());
		
		v = new JsonValue(15.3f);
		assertEquals("15.3", v.toString());
		
		v = new JsonValue(new JsonArray());
		assertEquals("[]", v.toString());
		
		v = new JsonValue(new JsonObject());
		assertEquals("{}", v.toString());		

		v = new JsonValue((String) null);
		assertEquals("null", v.toString());

		v = new JsonValue((Object) null);
		assertEquals("null", v.toString());
			
	}
}
