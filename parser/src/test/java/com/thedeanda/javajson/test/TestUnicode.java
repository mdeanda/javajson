package com.thedeanda.javajson.test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import junit.framework.TestCase;

import com.thedeanda.javajson.JsonObject;

public class TestUnicode extends TestCase {

	private static final String PATH = "/languages/";

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
		assertEquals(json.toString(), JsonObject.parse(json.toString())
				.toString());
		// assertEquals(jsonstr, json.toString());
	}

	public void testUnicode() throws Exception {
		for (int i = 0; i < files.length; i++) {
			BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(PATH
					+ files[i] + ".txt"), "UTF-8"));
			String expected = br.readLine();

			Reader is = new InputStreamReader(getClass().getResourceAsStream(PATH
					+ files[i] + ".json"), "UTF-8");
			JsonObject obj = JsonObject.parse(is);
			System.out.println(expected);
			assertEquals("for file: " + files[i], expected,
					obj.getString("text"));
		}
	}
}
