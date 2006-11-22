package net.sourceforge.javajson.converter.test;

import java.util.Locale;

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
	
		json = c.toJson(si, Locale.US);
		testSimpleObject(json);
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

		json = c.toJson(co, Locale.US);
//		System.out.println(json.toString(2));

		assertTrue(json.hasKey("simpleObject"));
		testSimpleObject(json.getJsonObject("simpleObject"));
		
		assertTrue(json.hasKey("simpleList"));
		assertTrue(json.isJsonArray("simpleList"));
		array = json.getJsonArray("simpleList");
		for (JsonValue val : array) {
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
		JsonArray array;
		Converter c = new Converter();
		ComplexObject co = new ComplexObject();
		co.setSimpleObject(null);
		
		json = c.toJson(co, Locale.US);

		assertNotNull(json);
		assertFalse(json.hasKey("simpleObject"));
	}

}
