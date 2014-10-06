package com.thedeanda.javajson.test;

import java.io.FileInputStream;
import java.io.InputStream;

import junit.framework.TestCase;

import com.thedeanda.javajson.JsonObject;

public class TestNumberFormatException extends TestCase {

	private static final String PATH = "tests/net/sourceforge/javajson/test/";

	private static final String[] files = { "TestNumberFormatException" };

	public TestNumberFormatException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void testUnicode() throws Exception {
		for (int i = 0; i < files.length; i++) {
			InputStream is = new FileInputStream(PATH + files[i] + ".json");
			JsonObject obj = JsonObject.parse(is);
		}
	}
}
