package net.sourceforge.javajson.converter.test;

import java.util.Calendar;

import net.sourceforge.javajson.JsonArray;
import net.sourceforge.javajson.JsonObject;
import net.sourceforge.javajson.JsonValue;
import net.sourceforge.javajson.converter.Converter;

import junit.framework.TestCase;

public class ConverterTest extends TestCase {

	public static void main(String[] args) {
		System.out.println("start of test ");
		junit.textui.TestRunner.run(ConverterTest.class);
	}

	public void testSimpleObject() throws Exception {
		JsonObject json;
		Converter c = new Converter();
		SimpleObject si = new SimpleObject();
	
		json = c.toJson(si);
		testSimpleObject(json);
	}
	
	public void testSimpleObjectValues() throws Exception {
		JsonObject json;
		Converter c = new Converter();
		SimpleObject si = new SimpleObject();

		si.setField1("s1");
		si.setField2(3);
		si.setFloatField(3.14f);
		Calendar cal = Calendar.getInstance();
		cal.set(2006, 10, 22); //this is actually november, month starts from 0.
		si.setToday(cal.getTime());
		
		json = c.toJson(si);
		testSimpleObject(json);
		
		assertEquals("s1", json.getString("field1"));
		assertEquals(3, json.getInt("field2"));
		assertEquals(3.14f, json.getFloat("floatField"));
		assertEquals("2006/11/22", json.getString("today"));
		assertEquals("net.sourceforge.javajson.converter.test.SimpleObject", json.getString("class"));
	}

	private void testSimpleObject(JsonObject json) {
		assertTrue(json.hasKey("field1"));
		assertTrue(json.isString("field1"));
		assertTrue(json.hasKey("field2"));
		assertTrue(json.isInt("field2"));
		assertTrue(json.hasKey("today"));
		assertTrue(json.isString("today"));
	}

	public void testComplexObject() throws Exception {
		JsonObject json;
		JsonArray array;
		Converter c = new Converter();
		ComplexObject co = new ComplexObject();

		json = c.toJson(co);
//		System.out.println(json.toString(2));

		assertTrue(json.hasKey("simpleObject"));
		testSimpleObject(json.getJsonObject("simpleObject"));
		
		assertTrue(json.hasKey("simpleList"));
		assertTrue(json.isJsonArray("simpleList"));
		array = json.getJsonArray("simpleList");
		assertEquals("size of simplelist", 2, array.size());
		for (JsonValue val : array) {
			//System.out.println("value of val:" + val);
			assertNotNull("val shouldn't be null" + val);
			assertTrue(val.isJsonObject());
			testSimpleObject(val.getJsonObject());
		}

		assertTrue(json.hasKey("intList"));
		assertTrue(json.isJsonArray("intList"));
		array = json.getJsonArray("intList");
		for (JsonValue val : array) {
			assertTrue(val.isFloat());
		}
	}

	public void testComplexObjectWithNull() throws Exception {
		JsonObject json;
		Converter c = new Converter();
		ComplexObject co = new ComplexObject();
		co.setSimpleObject(null);
		
		json = c.toJson(co);

		assertNotNull(json);
		assertTrue(json.hasKey("simpleObject"));
		assertNull(json.getString("simpleObject"));
	}

}
