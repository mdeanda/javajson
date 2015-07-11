package com.thedeanda.javajson.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import com.thedeanda.javajson.JsonException;
import com.thedeanda.javajson.JsonObject;

/**
 * confirms that comments embedded in json gets ignored properly
 * 
 * @author mdeanda
 *
 */
public class TestComments {

	public String loadResource(String classpath) throws IOException {
		StringWriter writer = new StringWriter();
		IOUtils.copy(getClass().getResourceAsStream(classpath), writer);
		return writer.toString();
	}

	@Test
	public void testComments() throws Exception {
		JsonObject expected = JsonObject
				.parse(loadResource("/comments/comments1clean.json"));
		JsonObject json = JsonObject
				.parse(loadResource("/comments/comments1.json"));

		assertNotNull(expected);
		assertNotNull(json);
		assertEquals(expected.toString(), json.toString());
	}
}
