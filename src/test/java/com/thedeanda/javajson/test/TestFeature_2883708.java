package com.thedeanda.javajson.test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import junit.framework.TestCase;

import com.thedeanda.javajson.JsonArray;
import com.thedeanda.javajson.JsonObject;

public class TestFeature_2883708 extends TestCase {
	public void testSetDateObject() throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd'T'HH:mm:ssZ");

		JsonObject json = new JsonObject();
		Date date = new GregorianCalendar(1980, 5, 27, 7, 18, 30).getTime();
		json.put("a", date);

		String stringDate = dateFormat.format(date);
		stringDate = "{\"a\":\"" + stringDate + "\"}";
		assertEquals(stringDate, json.toString());
	}

	public void testSetDateArray() throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd'T'HH:mm:ssZ");

		JsonArray json = new JsonArray();
		Date date = new GregorianCalendar(1980, 5, 27, 7, 18, 30).getTime();
		json.add(date);

		String stringDate = dateFormat.format(date);
		stringDate = "[\"" + stringDate + "\"]";
		assertEquals(stringDate, json.toString());
	}
}
