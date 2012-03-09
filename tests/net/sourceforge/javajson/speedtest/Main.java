package net.sourceforge.javajson.speedtest;

import java.io.FileInputStream;
import java.io.StringWriter;

import net.sourceforge.javajson.JsonObject;

import org.apache.commons.io.IOUtils;

public class Main {

	public static void main(String[] args) throws Exception {
		int repeats = 2000;
		long start = System.currentTimeMillis();

		FileInputStream fis = new FileInputStream("data/samplefile.json");
		StringWriter sw = new StringWriter();
		IOUtils.copy(fis, sw);
		String content = sw.toString();

		for (int i = 0; i < repeats; i++) {
			parseJson(content);
		}

		long end = System.currentTimeMillis();
		System.out.println("JavaJson Duration: " + (end - start));
	}

	private static final void parseJson(String content) throws Exception {
		JsonObject json = JsonObject.parse(content);
	}

}
