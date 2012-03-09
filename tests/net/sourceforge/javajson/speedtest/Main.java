package net.sourceforge.javajson.speedtest;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import net.sourceforge.javajson.JsonObject;

public class Main {

	public static void main(String[] args) throws Exception {
		int repeats = 2000;
		String content = readFile();
		long start = System.currentTimeMillis();

		for (int i = 0; i < repeats; i++) {
			parseJson(content);
		}

		long end = System.currentTimeMillis();
		System.out.println("JavaJson Duration: " + (end - start));
	}

	private static String readFile() throws Exception {
		InputStream is = Main.class.getResourceAsStream("samplefile.json");
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line;
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null) {
			sb.append(line);
			sb.append("\n");
		}
		return sb.toString();
	}

	private static final void parseJson(String content) throws Exception {
		JsonObject json = JsonObject.parse(content);
	}

}
