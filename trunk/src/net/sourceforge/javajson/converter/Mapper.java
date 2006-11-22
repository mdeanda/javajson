package net.sourceforge.javajson.converter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
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

	private Map<String, MapperStruct> map;

	private Class cls;

	private Converter converter;

	/**
	 * Default mapper that can be used to map object all the way down the tree.
	 */
	public static Mapper DefaultMapper = new Mapper();

	private Mapper() {
		cls = null;
	}

	public Mapper(Class cls) {
		log.debug("mapper for class:" + cls);
		map = new HashMap<String, MapperStruct>();
		this.cls = cls;
	}

	public Mapper(String cls) throws ClassNotFoundException {
		map = new HashMap<String, MapperStruct>();
		this.cls = Class.forName(cls);
	}

	public void addMapper(String field, Mapper mapper) {
		map.put(field, new MapperStruct(mapper));
		if (converter != null)
			mapper.setConverter(converter);
	}

	public void addMapper(String field, String alias) {
		map.put(field, new MapperStruct(alias));
	}

	protected Converter getConverter() {
		return converter;
	}

	/**
	 * Maps an object to json based on a class, this method only works when
	 * using the default mapper and is meant to be called via convenience method
	 * on Converter.
	 * 
	 * @param obj
	 * @param cls
	 * @param locale
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	protected JsonObject toJson(Object obj, Class cls, Locale locale)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		JsonObject ret = null;
		if (this.cls == null) {
			// default mapper
			ret = Utils.toJson(cls, obj, locale);
		}
		return ret;
	}

	public JsonObject toJson(Object obj, Locale locale)
			throws SecurityException, NoSuchMethodException,
			IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {

		JsonObject ret = null;
		if (obj == null) {
			// return null
		} else if (cls == null) {
			// default mapper
			ret = Utils.toJson(obj.getClass(), obj, locale);

			// TODO: go through each field.. if not simple type, bust a toJson
			// on it (or toArray)
			Map<String, Object> fields = Reflection.getFields(obj);
			for (String key : fields.keySet()) {
				Object val = fields.get(key);
				if (!(val instanceof Number || val instanceof Date
						|| val instanceof String || val instanceof Boolean)
						&& val != null) {
					// non basic types...
					String jsonKey = Reflection.getFieldName(key);
					if (val instanceof Collection) {
						ret.put(jsonKey, toJsonArray((Collection) val, locale));
					} else if (!(val instanceof Class)) {
						ret.put(jsonKey, toJson(val, locale));
					}
				}
			}
		} else {
			ret = Utils.toJson(cls, obj, locale);

			for (Iterator<String> keysIt = map.keySet().iterator(); keysIt
					.hasNext();) {
				String key = keysIt.next();

				Method method = cls.getMethod(fieldToMethodName(key, "get"));
				if (method != null) {
					Object o = method.invoke(obj);
					if (o instanceof Collection) {
						JsonArray arr = new JsonArray();
						ret.put(key, arr);
						for (Iterator it = ((Collection) o).iterator(); it
								.hasNext();) {
							Object o2 = it.next();
							JsonObject tmp = map.get(key).getMapper(converter)
									.toJson(o2, locale);
							arr.add(tmp);
						}
					} else {
						JsonObject tmp = map.get(key).getMapper(converter)
								.toJson(o, locale);
						ret.put(key, tmp);
					}
				}
			}
		}
		return ret;
	}

	/**
	 * Maps a collection to jsonarray based on a class, this method only works
	 * when using the default mapper and is meant to be called via convenience
	 * method on Converter.
	 * 
	 * @param col
	 * @param cls
	 * @param locale
	 * @return
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	protected JsonArray toJsonArray(Collection col, Class cls, Locale locale)
			throws SecurityException, IllegalArgumentException,
			NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {
		JsonArray arr = new JsonArray();

		if (this.cls == null) {
			for (Object o : col) {
				if (!Utils.objectIntoJsonArray(arr, o, locale))
					arr.add(toJson(o, cls, locale));
			}
		}

		return arr;
	}

	public JsonArray toJsonArray(Collection col, Locale locale)
			throws SecurityException, IllegalArgumentException,
			NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {
		JsonArray arr = new JsonArray();

		for (Object o : col) {
			if (!Utils.objectIntoJsonArray(arr, o, locale))
				arr.add(toJson(o, locale));
		}

		return arr;
	}

	private String fieldToMethodName(String fld, String mode) {
		String ret = mode + Character.toString(fld.charAt(0)).toUpperCase()
				+ fld.substring(1);
		// System.out.println("fld:" + fld + ", method:" + ret);
		return ret;
	}

	protected void setConverter(Converter converter) {
		this.converter = converter;

		for (MapperStruct mapper : map.values()) {
			mapper.setConverter(converter);
		}
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

	public Mapper getMapper(Converter converter) {
		if (mapper != null)
			return mapper;
		else
			return converter.getMapper(alias);
	}

	public void setConverter(Converter converter) {
		if (mapper != null)
			mapper.setConverter(converter);
	}
}
