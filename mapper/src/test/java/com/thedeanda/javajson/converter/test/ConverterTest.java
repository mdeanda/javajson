package com.thedeanda.javajson.converter.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.thedeanda.javajson.JsonArray;
import com.thedeanda.javajson.JsonObject;
import com.thedeanda.javajson.JsonValue;
import com.thedeanda.javajson.converter.Converter;

public class ConverterTest {

	private static final String STRING_VALUE = "value of field 1";
	private static final int INT_VALUE = 33425;
	private static final float FLOAT_VALUE = 3.14f;
	private static final float FLOAT_DELTA = 0.01f;

	@Test
	public void testSimpleValues() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Converter c = Converter.getInstance();
		JsonValue value;

		value = c.toJsonValue(INT_VALUE);
		assertNotNull(value);
		assertTrue(value.isInt());
		assertEquals(INT_VALUE, value.getInt());
		
		value = c.toJsonValue(FLOAT_VALUE);
		assertNotNull(value);
		assertTrue(value.isNumber());
		assertEquals(FLOAT_VALUE, value.getFloat(), FLOAT_DELTA);
		
		value = c.toJsonValue(STRING_VALUE);
		assertNotNull(value);
		assertTrue(value.isString());
		assertEquals(STRING_VALUE, value.getString());
		
		
	}

	@Test
	public void testStringArray() throws Exception {
		String[] vals = new String[] { "a", "b", "c" };
		JsonArray arr = new JsonArray();
		arr.add("a").add("b").add("c");

		Converter c = Converter.getInstance();
		JsonValue value = c.toJsonValue(vals);
		assertNotNull(value);
		assertTrue(value.isJsonArray());
		JsonArray outputJson = value.getJsonArray();

		assertEquals(arr.toString(), outputJson.toString());
	}

	@Test
	public void testCharArray() throws Exception {
		char[] vals = new char[] { 'a', 'b', 'c' };
		JsonArray arr = new JsonArray();
		arr.add('a').add('b').add('c');

		Converter c = Converter.getInstance();
		JsonValue value = c.toJsonValue(vals);
		assertNotNull(value);
		assertTrue(value.isJsonArray());
		JsonArray outputJson = value.getJsonArray();

		assertEquals(arr.toString(), outputJson.toString());
	}

	@Test
	public void testBoolArray() throws Exception {
		boolean[] vals = new boolean[] { true, true, false };
		JsonArray arr = new JsonArray();
		arr.add(true).add(true).add(false);

		Converter c = Converter.getInstance();
		JsonValue value = c.toJsonValue(vals);
		assertNotNull(value);
		assertTrue(value.isJsonArray());
		JsonArray outputJson = value.getJsonArray();

		assertEquals(arr.toString(), outputJson.toString());
	}

	@Test
	public void testShortArray() throws Exception {
		short[] vals = new short[] { 1, 2, 3 };
		JsonArray arr = new JsonArray();
		arr.add(1).add(2).add(3);

		Converter c = Converter.getInstance();
		JsonValue value = c.toJsonValue(vals);
		assertNotNull(value);
		assertTrue(value.isJsonArray());
		JsonArray outputJson = value.getJsonArray();

		assertEquals(arr.toString(), outputJson.toString());
	}

	@Test
	public void testIntArray() throws Exception {
		int[] vals = new int[] { 1, 2, 3 };
		JsonArray arr = new JsonArray();
		arr.add(1).add(2).add(3);

		Converter c = Converter.getInstance();
		JsonValue value = c.toJsonValue(vals);
		assertNotNull(value);
		assertTrue(value.isJsonArray());
		JsonArray outputJson = value.getJsonArray();

		assertEquals(arr.toString(), outputJson.toString());
	}

	@Test
	public void testLongArray() throws Exception {
		long[] vals = new long[] { 1, 2, 3 };
		JsonArray arr = new JsonArray();
		arr.add(1).add(2).add(3);

		Converter c = Converter.getInstance();
		JsonValue value = c.toJsonValue(vals);
		assertNotNull(value);
		assertTrue(value.isJsonArray());
		JsonArray outputJson = value.getJsonArray();

		assertEquals(arr.toString(), outputJson.toString());
	}

	@Test
	public void testDoubleArray() throws Exception {
		double[] vals = new double[] { 1, 2, 3 };
		JsonArray arr = new JsonArray();
		arr.add(1.0).add(2.0).add(3.0);

		Converter c = Converter.getInstance();
		JsonValue value = c.toJsonValue(vals);
		assertNotNull(value);
		assertTrue(value.isJsonArray());
		JsonArray outputJson = value.getJsonArray();

		assertEquals(arr.toString(), outputJson.toString());
	}

	@Test
	public void testFloatArray() throws Exception {
		float[] vals = new float[] { 1, 2, 3 };
		JsonArray arr = new JsonArray();
		arr.add(1.0).add(2.0).add(3.0);

		Converter c = Converter.getInstance();
		JsonValue value = c.toJsonValue(vals);
		assertNotNull(value);
		assertTrue(value.isJsonArray());
		JsonArray outputJson = value.getJsonArray();

		assertEquals(arr.toString(), outputJson.toString());
	}

	@Test
	public void testSimpleObject() throws Exception {
		Converter c = Converter.getInstance();
		SimpleObject si = new SimpleObject();

		JsonValue value = c.toJsonValue(si);
		assertNotNull(value);
		JsonObject outputJson = value.getJsonObject();

		JsonObject expected = new JsonObject();
		expected.put("field1", (String) null);
		expected.put("field2", 0);
		expected.put("floatField", 0.0f);
		expected.put("today", (Date) null);

		assertEquals(expected.toString(), outputJson.toString());
	}

	@Test
	public void testSimpleObjectValues() throws Exception {
		JsonObject json;
		Converter c = Converter.getInstance();
		SimpleObject si = new SimpleObject();

		Date dt = new Date(1471824000000l); // Mon, 22 Aug 2016 00:00:00 GMT
		si.setField1("s1");
		si.setField2(3);
		si.setFloatField(3.14f);
		si.setToday(dt);

		JsonValue value = c.toJsonValue(si);
		assertNotNull(value);
		JsonObject outputJson = value.getJsonObject();

		assertEquals("s1", outputJson.getString("field1"));
		assertEquals(3, outputJson.getInt("field2"));
		assertEquals(3.14f, outputJson.getFloat("floatField"), 0.02);
		assertEquals("2016-08-22T00:00:00.000Z", outputJson.getString("today"));

		JsonObject expected = new JsonObject();
		expected.put("field1", "s1");
		expected.put("field2", 3);
		expected.put("floatField", 3.14f);
		expected.put("today", dt);

		assertEquals(expected.toString(), outputJson.toString());
	}

	@Test
	public void testComplexObject() throws Exception {
		JsonObject json;
		JsonArray array;
		Converter c = Converter.getInstance();
		ComplexObject co = new ComplexObject();

		JsonValue value = c.toJsonValue(co);
		assertNotNull(value);
		assertTrue(value.isJsonObject());

		json = value.getJsonObject();

		JsonObject simpleObject1 = c.toJsonValue(new SimpleObject()).getJsonObject();
		JsonObject expected = new JsonObject();
		expected.put("simpleObject", simpleObject1);
		expected.put("intList", new JsonArray());
		expected.put("simpleList", new JsonArray());
		expected.put("objectList", (Object) null);

		assertEquals(expected.toString(), json.toString());
	}

	@Test
	public void testComplexObjectValues() throws Exception {
		JsonObject json;
		JsonArray array;
		Converter c = Converter.getInstance();
		ComplexObject co = new ComplexObject();
		JsonObject expected = new JsonObject();
		prepComplex(co, expected);

		JsonValue value = c.toJsonValue(co);
		assertNotNull(value);
		assertTrue(value.isJsonObject());

		json = value.getJsonObject();

		assertEquals(expected.toString(), json.toString());
	}

	@SuppressWarnings("unchecked")
	private void prepComplex(ComplexObject co, JsonObject json) {
		SimpleObject so = new SimpleObject();
		JsonObject soJson = new JsonObject();
		prepSimple(so, soJson);
		co.setSimpleObject(so);
		json.put("simpleObject", soJson);

		List<Object> ol = new ArrayList<>();
		JsonArray olJson = new JsonArray();
		ol.add(STRING_VALUE);
		olJson.add(STRING_VALUE);
		ol.add(new Integer(INT_VALUE));
		olJson.add(INT_VALUE);

		co.setObjectList(ol);
		json.put("objectList", olJson);

		List<Integer> il = new ArrayList<>();
		JsonArray ilJson = new JsonArray();
		il.add(5);
		ilJson.add(5);
		il.add(6);
		ilJson.add(6);
		co.setIntList(il);
		json.put("intList", ilJson);

		List<SimpleObject> sol = co.getSimpleList();
		JsonArray solJson = new JsonArray();
		SimpleObject sol1 = new SimpleObject();
		JsonObject sol1Json = new JsonObject();
		prepSimple(sol1, sol1Json);
		sol.add(sol1);
		solJson.add(sol1Json);
		co.setSimpleList(sol);
		json.put("simpleList", solJson);

	}

	private void prepSimple(SimpleObject so, JsonObject soJson) {
		Date dt = new Date();

		so.setField1(STRING_VALUE);
		soJson.put("field1", STRING_VALUE);
		so.setField2(INT_VALUE);
		soJson.put("field2", INT_VALUE);
		so.setFloatField(FLOAT_VALUE);
		soJson.put("floatField", FLOAT_VALUE);
		soJson.put("today", (String) null);
		so.setToday(dt);
		soJson.put("today", dt);
	}
}
