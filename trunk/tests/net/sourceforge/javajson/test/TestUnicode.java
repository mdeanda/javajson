package net.sourceforge.javajson.test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;

import junit.framework.TestCase;
import net.sourceforge.javajson.JsonObject;

public class TestUnicode extends TestCase {

	private static final String PATH = "tests/net/sourceforge/javajson/test/";

	private static final String[] files = { "chinese_gb2312", "japanese_eucjp" };

	public TestUnicode() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void testUnicodeJavaStrings() throws Exception {
		String uni = "\u747b\u7865\u3a74\ub522\ucec4\uccca\ud4e2\ub5da\ud6ea\ubbf7\ub8d8\u22b4\u007d";
		String jsonstr = "{a:\"" + uni + "\"}";
		JsonObject json = JsonObject.parse(jsonstr);
		assertEquals(uni, json.getString("a"));
		assertEquals(json.toString(), JsonObject.parse(json.toString()).toString());
		//assertEquals(jsonstr, json.toString());
	}

	public void testUnicode() throws Exception {
		for (int i = 0; i < files.length; i++) {
			BufferedReader br = new BufferedReader(new FileReader(PATH
					+ files[i] + ".txt"));
			String expected = br.readLine();

			Reader is = new InputStreamReader(new FileInputStream(PATH
					+ files[i] + ".json"), "UTF-8");
			JsonObject obj = JsonObject.parse(is);
			System.out.println(expected);
			assertEquals(expected, obj.getString("text"));
		}
	}
}
