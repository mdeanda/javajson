package net.sourceforge.javajson.mappedconverter;

import junit.framework.TestCase;
import net.sourceforge.javajson.JsonObject;
import net.sourceforge.javajson.converter.test.SimpleObject;

public class BasicTest extends TestCase {

	private MappedConverter mc;

	@Override
	public void setUp() throws Exception {
		JsonObject json = new JsonObject();

		JsonObject tmp = new JsonObject();
		json.put("test1", tmp);
		tmp.put("field1", true);
		tmp.put("field2", true);
		tmp.put("field3", true);

		tmp = new JsonObject();
		json.put("test2", tmp);
		tmp.put("field2", true);

		mc = new MappedConverter(json);
	}

	public void testSimple() throws Exception {
		SimpleObject so = new SimpleObject();
		so.setField1("f1");
		so.setField2(3);
		so.setFloatField(3.5f);

		JsonObject expected1 = new JsonObject();
		expected1.put("field1", "f1");
		expected1.put("field2", 3);
		expected1.put("field3", 3.5f);

		JsonObject expected2 = new JsonObject();
		expected2.put("field2", 3);

		JsonObject ret;
		ret = mc.map(so, "test1");
		assertEquals(expected1.toString(), ret.toString());
		ret = mc.map(so, "test2");
		assertEquals(expected2.toString(), ret.toString());

	}
}
