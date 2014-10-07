package com.thedeanda.javajson.test;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.junit.Test;

import com.thedeanda.javajson.JsonArray;
import com.thedeanda.javajson.JsonObject;

public class TestFeature_2883708 {
	private static DateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	private static Date date = new GregorianCalendar(1980, 5, 27, 7, 18, 30)
			.getTime();
	static {
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
	}

	@Test
	public void testSetDateObject() throws Exception {

		JsonObject json = new JsonObject();
		json.put("a", date);

		String stringDate = dateFormat.format(date);
		stringDate = "{\"a\":\"" + stringDate + "\"}";
		assertEquals(stringDate, json.toString());
	}

	@Test
	public void testSetDateArray() throws Exception {

		JsonArray json = new JsonArray();
		json.add(date);

		String stringDate = dateFormat.format(date);
		stringDate = "[\"" + stringDate + "\"]";
		assertEquals(stringDate, json.toString());
	}
}
