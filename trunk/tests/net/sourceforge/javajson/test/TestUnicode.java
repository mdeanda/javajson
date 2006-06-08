package net.sourceforge.javajson.test;

import java.io.InputStream;

import net.sourceforge.javajson.JsonObject;
import junit.framework.TestCase;

public class TestUnicode extends TestCase {

	private static final String PATH = "net/sourceforge/javajson/test/";
	private static final String[] files = { "chinese_1", "japanese_1" };

	public TestUnicode() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void testUnicode() throws Exception {
		for (int i = 0; i < files.length; i++) {
			try {
				InputStream is = TestUnicode.class.getResourceAsStream(PATH + files[i] + ".json");
				JsonObject obj = JsonObject.parse(is);
			} catch (Exception e) {
				throw e;
			}
		}
	}
}
