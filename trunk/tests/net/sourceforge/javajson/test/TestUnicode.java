package net.sourceforge.javajson.test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;

import net.sourceforge.javajson.JsonObject;
import junit.framework.TestCase;

public class TestUnicode extends TestCase {

	private static final String PATH = "tests/net/sourceforge/javajson/test/";

	private static final String[] files = { "chinese_gb2312", "japanese_eucjp" };

	public TestUnicode() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void testUnicode() throws Exception {
		for (int i = 0; i < files.length; i++) {
				BufferedReader br = new BufferedReader(new FileReader(PATH
						+ files[i] + ".txt"));
				String expected = br.readLine();

				InputStream is = new FileInputStream(PATH + files[i] + ".json");
				JsonObject obj = JsonObject.parse(is);
				System.out.println(expected);
				assertEquals(expected, obj.getString("text"));
		}
	}
}
