package net.sourceforge.javajson.test;

import net.sourceforge.javajson.JsonException;
import net.sourceforge.javajson.JsonObject;
import junit.framework.TestCase;

public class TestUnicode extends TestCase {

	public TestUnicode() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void testChinese() throws Exception {
		JsonObject json = JsonObject.parse("{text:???????????}");
		assertEquals("???????????", json.getString("text"));

		json = JsonObject.parse("{text:???????????????}");
		assertEquals("???????????????", json.getString("text"));
}
}
