package net.sourceforge.javajson.converter;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

import net.sourceforge.javajson.JsonArray;
import net.sourceforge.javajson.JsonObject;

/**
 * Maps objects to a json object TODO: addMapper as: String field, String alias
 * to map to an existing mapper TODO: addMapper to map a field to
 * objAtField.fieldName and maybe rename the field too
 */
public class Mapper {
	protected final Logger log = Logger.getLogger(getClass());

	/**
	 * Default mapper that can be used to map object all the way down the tree.
	 */
	public static Mapper DefaultMapper = new Mapper();

	private Mapper() {
	}

	/**
	 * Maps an object to json based on a class, this method only works when
	 * using the default mapper and is meant to be called via convenience method
	 * on Converter.
	 * 
	 * @param obj
	 *            The object to convert to json
	 * @param cls
	 * @param locale
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	protected JsonObject toJson(Object obj, Class cls)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		JsonObject ret = null;
		if (obj != null) {
			if (cls == null)
				ret = Utils.toJson(obj, obj.getClass());
			else
				ret = Utils.toJson(obj, cls);

			// TODO: go through each field.. if not simple type, bust a toJson
			// on it (or toArray)
			Map<String, Object> fields = Reflection.getFields(obj);
			for (String key : fields.keySet()) {
				Object val = fields.get(key);
				if (!(val instanceof Number || val instanceof Date
						|| val instanceof String || val instanceof Boolean || val instanceof Class)
						&& val != null) {
					// non basic types...
					String jsonKey = Reflection.getFieldName(key);
					if (val instanceof Collection) {
						JsonArray array = toJsonArray((Collection) val);
						ret.put(jsonKey, array);
					} else {
						ret.put(jsonKey, toJson(val));
					}
				}
			}
		}
		return ret;
	}

	public JsonObject toJson(Object obj) throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {

		if (obj != null)
			return toJson(obj, obj.getClass());
		else
			return toJson(obj, null);
	}

	/**
	 * Maps a collection to jsonarray based on a class, this method only works
	 * when using the default mapper and is meant to be called via convenience
	 * method on Converter.
	 * 
	 * @param col
	 * @param cls
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	protected JsonArray toJsonArray(Collection col, Class cls)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		JsonArray arr = new JsonArray();

		if (col != null) {
			for (Object o : col) {
				Class cls2 = cls;
				if (cls2 == null)
					cls2 = o.getClass();
				if (!Utils.objectIntoJsonArray(arr, o)) {
					JsonObject json = toJson(o, cls2);
					arr.add(json);
				}
			}
		}

		return arr;
	}

	public JsonArray toJsonArray(Collection col)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		return toJsonArray(col, null);
	}
}

class MapperStruct {
	Mapper mapper;

	String alias;

	public MapperStruct(String alias) {
		this.alias = alias;
	}

	public MapperStruct(Mapper mapper) {
		this.mapper = mapper;
	}
}
