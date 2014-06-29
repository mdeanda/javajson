package net.sourceforge.javajson.test;

import java.io.FileInputStream;
import java.io.InputStream;

import junit.framework.TestCase;
import net.sourceforge.javajson.JsonObject;

public class TestNumberFormatException extends TestCase {

	private static final String PATH = "tests/net/sourceforge/javajson/test/";

	private static final String[] files = { "TestNumberFormatException" };

	public TestNumberFormatException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void testUnicode() throws Exception {
		for (int i = 0; i < files.length; i++) {
			InputStream is = getClass().getResourceAsStream("/" + files[i] + ".json");
			JsonObject obj = JsonObject.parse(is);
		}
	}
}
