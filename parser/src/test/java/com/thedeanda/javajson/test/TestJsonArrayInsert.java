package com.thedeanda.javajson.test;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.thedeanda.javajson.JsonArray;
import com.thedeanda.javajson.JsonObject;
import com.thedeanda.javajson.JsonValue;

public class TestJsonArrayInsert {
	JsonArray ja;

	private static final long DT = 1509321600000l;
	
	public enum SomeEnum { FOO, BAR };

	@Before
	public void init() {
		ja = new JsonArray();

		assertEquals(0, ja.size());
		ja.add(1);
		ja.add(3);
		assertEquals("[1,3]", ja.toString());
	}

	@Test
	public void testInsertBounds() {
		try {
			ja.insert(10, 2);
			fail("expected exception");
		} catch (IndexOutOfBoundsException e) {

		}
		try {
			ja.insert(-1, 2);
			fail("expected exception");
		} catch (IndexOutOfBoundsException e) {

		}
		ja.insert(2, 2);
		assertEquals("[1,3,2]", ja.toString());
		ja.insert(0, 4);
		assertEquals("[4,1,3,2]", ja.toString());
	}

	@Test
	public void testInsert() {
		ja.insert(1, 2);
		assertEquals("[1,2,3]", ja.toString());
		ja.insert(1, (long) 4);
		assertEquals("[1,4,2,3]", ja.toString());
		ja.insert(1, 'c');
		assertEquals("[1,\"c\",4,2,3]", ja.toString());
		ja.insert(1, false);
		assertEquals("[1,false,\"c\",4,2,3]", ja.toString());
		ja.insert(1, "woot");
		assertEquals("[1,\"woot\",false,\"c\",4,2,3]", ja.toString());
		ja.insert(1, new JsonObject());
		assertEquals("[1,{},\"woot\",false,\"c\",4,2,3]", ja.toString());
		ja.insert(1, new JsonArray());
		assertEquals("[1,[],{},\"woot\",false,\"c\",4,2,3]", ja.toString());
		ja.insert(1, new Date(DT));
		assertEquals("[1,\"2017-10-30T00:00:00.000Z\",[],{},\"woot\",false,\"c\",4,2,3]", ja.toString());
	}

	@Test
	public void testInsertBoolean() {
		ja.insert(1, false);
		assertEquals("[1,false,3]", ja.toString());

		ja.insert(1, Boolean.TRUE);
		assertEquals("[1,true,false,3]", ja.toString());
	}

	@Test
	public void testInsertFloat() {
		ja.insert(1, 2f);
		assertEquals("[1,2.0,3]", ja.toString());

		Float v = Float.valueOf(4f);
		ja.insert(1, v);
		assertEquals("[1,4.0,2.0,3]", ja.toString());
	}

	@Test
	public void testInsertDouble() {
		ja.insert(1, 2d);
		assertEquals("[1,2.0,3]", ja.toString());

		Double v = Double.valueOf(4d);
		ja.insert(1, v);
		assertEquals("[1,4.0,2.0,3]", ja.toString());
	}

	@Test
	public void testInsertInteger() {
		ja.insert(1, 2);
		assertEquals("[1,2,3]", ja.toString());

		Integer v = Integer.valueOf(4);
		ja.insert(1, v);
		assertEquals("[1,4,2,3]", ja.toString());
	}

	@Test
	public void testInsertLong() {
		ja.insert(1, 2l);
		assertEquals("[1,2,3]", ja.toString());

		Long v = Long.valueOf(4l);
		ja.insert(1, v);
		assertEquals("[1,4,2,3]", ja.toString());
	}

	@Test
	public void testInsertNull() {
		ja.insert(1, (Object) null);
		assertEquals("[1,null,3]", ja.toString());
	}

	@Test
	public void testInsertEnum() {
		ja.insert(1, SomeEnum.BAR);
		assertEquals("[1,\"BAR\",3]", ja.toString());
	}

	@Test
	public void testInsertJsonArray() {
		ja.insert(1, (Object) new JsonArray());
		assertEquals("[1,[],3]", ja.toString());

		ja.insert(0, new JsonArray());
		assertEquals("[[],1,[],3]", ja.toString());
	}

	@Test
	public void testInsertJsonObject() {
		ja.insert(1, (Object) new JsonObject());
		assertEquals("[1,{},3]", ja.toString());

		ja.insert(0, new JsonObject());
		assertEquals("[{},1,{},3]", ja.toString());
	}

	@Test
	public void testInsertDate() {
		ja.insert(1, (Object) new Date(DT));
		assertEquals("[1,\"2017-10-30T00:00:00.000Z\",3]", ja.toString());

		ja.insert(0, (Object) new Date(DT));
		assertEquals("[\"2017-10-30T00:00:00.000Z\",1,\"2017-10-30T00:00:00.000Z\",3]", ja.toString());
	}

	@Test
	public void testInsertString() {
		ja.insert(1, "help");
		assertEquals("[1,\"help\",3]", ja.toString());

		ja.insert(0, (Object) "please");
		assertEquals("[\"please\",1,\"help\",3]", ja.toString());
	}
	@Test
	public void testInsertJsonValue() {
		ja.insert(1, new JsonValue(2));
		assertEquals("[1,2,3]", ja.toString());

		ja.insert(0, (Object) new JsonValue(0));
		assertEquals("[0,1,2,3]", ja.toString());
	}
	@Test
	public void testInsertBadType() {
		try {
			ja.insert(1, new SimpleDateFormat());
			fail("expected exception");
		} catch (IllegalArgumentException cce) {

		}
	}
}
