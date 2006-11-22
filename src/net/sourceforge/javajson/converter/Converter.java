package net.sourceforge.javajson.converter;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;

import net.sourceforge.javajson.JsonArray;
import net.sourceforge.javajson.JsonObject;

/**
 * Converts java objects to json based on bean-style fields and classes/interfaces (to filter)
 * 
 * @author miguel de anda
 * 
 */
public class Converter {
	protected final Logger log = Logger.getLogger(getClass());

	/** Mappers to use for mapping objects */
	private Map<String, Mapper> mappers;

	public Converter() {
		mappers = new HashMap<String, Mapper>();
	}

	public void addMapper(String alias, Mapper mapper) {
		mappers.put(alias, mapper);
		mapper.setConverter(this);
	}

	public Mapper getMapper(String alias) {
		return mappers.get(alias);
	}

	/**
	 * Convenience method for converting objects without a predefined
	 * mapper. This method does not allow objects to be filtered in any
	 * way and will inspect every public getter and return its value, 
	 * if the value is not a Number, String or Date, it will then
	 * do the conversion on that object as well. If the return type is
	 * a Collection, it will be converted to a JsonArray
	 * 
	 * @param o
	 * @return
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public JsonObject toJson(Object o) throws SecurityException,
			IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		return Mapper.DefaultMapper.toJson(o);
	}

	/**
	 * Convenience method for converting objects without a predefined
	 * mapper. This method does not allow objects to be filtered in any
	 * way and will inspect every public getter and return its value, 
	 * if the value is not a Number, String or Date, it will be skipped.
	 * 
	 * @param o
	 * @param cls The class to read the fields from
	 * @return
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public JsonObject toJson(Object o, Class cls) throws SecurityException,
			IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		return Mapper.DefaultMapper.toJson(o, cls);
	}

	/**
	 * Convenience method for converting objects without a predefined
	 * mapper. This method does not allow objects to be filtered in any
	 * way and will inspect every public getter and return its value, 
	 * if the value is not a Number, String or Date, it will then
	 * do the conversion on that object as well. If the return type is
	 * a Collection, it will be converted to a JsonArray
	 * 
	 * @param o
	 * @return
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public JsonArray toJsonArray(Collection c) throws SecurityException,
			IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		return Mapper.DefaultMapper.toJsonArray(c);
	}

	/**
	 * Convenience method for converting collections without a predefined
	 * mapper. This method does not allow objects to be filtered in any
	 * way and will inspect every public getter and return its value, 
	 * if the value is not a Number, String or Date, it will be skipped.
	 * 
	 * @param o
	 * @param cls The class to read the fields from
	 * @return
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public JsonArray toJsonArray(Collection c, Class cls) throws SecurityException,
			IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		return Mapper.DefaultMapper.toJsonArray(c, cls);
	}

	/**
	 * Maps an object to json using the mapper's alias
	 * 
	 * @param alias
	 * @return The JsonObject
	 * @throws JSONException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 */
	public JsonObject toJson(String alias, Object o)
			throws SecurityException, IllegalArgumentException,
			NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {
		if (mappers.containsKey(alias))
			return mappers.get(alias).toJson(o);
		else {
			log.warn("alias key not found:" + alias);
			return null;
		}
	}

	public JsonArray toJsonArray(String alias, Collection o)
			throws SecurityException, IllegalArgumentException,
			NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {
		if (mappers.containsKey(alias))
			return mappers.get(alias).toJsonArray(o);
		else {
			log.warn("alias key not found:" + alias);
			return null;
		}
	}
}
