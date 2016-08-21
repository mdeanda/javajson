package com.thedeanda.javajson.converter.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.thedeanda.javajson.JsonObject;
import com.thedeanda.javajson.JsonValue;
import com.thedeanda.javajson.converter.Converter;

public class ConvertFromMap {

	@Test
	public void testSimpleMap() throws SecurityException, IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		Date d = new Date(1471305600000l);
		Map<String, Object> map = new HashMap<>();
		map.put("a", "value a");
		map.put("b", "value b");
		map.put("c", new Integer(42));
		map.put("d", 420l);
		map.put("date", d);
		

		Converter c = Converter.getInstance();
		JsonValue value = c.toJsonValue(map);

		assertNotNull(value);
		assertTrue(value.isJsonObject());

		JsonObject json = value.getJsonObject();
		assertNotNull(json);
		String output = json.toString();
		assertEquals("value a", json.getString("a"));
		assertEquals("value b", json.getString("b"));
		assertEquals(42, json.getInt("c"));
		assertTrue(json.isInt("c"));
		assertTrue(json.isLong("d"));
		assertEquals("2016-08-16T00:00:00.000Z", json.getString("date"));	
	}
	

	@Test
	public void testComplexMap() throws SecurityException, IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		Date d = new Date(1471305600000l);
		Map<String, Object> map = new HashMap<>();
		map.put("a", "value a");
		map.put("b", "value b");
		map.put("c", new Integer(42));
		map.put("d", 420l);
		map.put("date", d);
		Map<String, Object> childMap = new HashMap<>();
		map.put("child", childMap);
		childMap.put("foo", "bar");

		Converter c = Converter.getInstance();
		JsonValue value = c.toJsonValue(map);

		assertNotNull(value);
		assertTrue(value.isJsonObject());

		JsonObject json = value.getJsonObject();
		assertNotNull(json);
		String output = json.toString();
		assertEquals("value a", json.getString("a"));
		assertEquals("value b", json.getString("b"));
		assertEquals(42, json.getInt("c"));
		assertTrue(json.isInt("c"));
		assertTrue(json.isLong("d"));
		assertEquals("2016-08-16T00:00:00.000Z", json.getString("date"));	
		
		assertTrue(json.isJsonObject("child"));
		JsonObject child = json.getJsonObject("child");
		assertNotNull(child);
		assertEquals("bar", child.getString("foo"));
	}
}
