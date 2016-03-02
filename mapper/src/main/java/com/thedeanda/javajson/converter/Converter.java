package com.thedeanda.javajson.converter;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import com.thedeanda.javajson.JsonArray;
import com.thedeanda.javajson.JsonObject;

/**
 * Converts java objects to json based on bean-style fields and
 * classes/interfaces (to filter)
 * 
 * @author miguel de anda
 */
public class Converter {
	public static final Converter instance = new Converter();

	private Converter() {
	}

	public static Converter getInstance() {
		return instance;
	}

	@SuppressWarnings("unchecked")
	public Object fromJson(JsonObject json) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Object o = null;
		Class cls = Class.forName(json.getString("class"));
		o = cls.newInstance();
		Utils.fromJson(o, json);
		return o;
	}

	/**
	 * Convenience method for converting objects without a predefined mapper.
	 * This method does not allow objects to be filtered in any way and will
	 * inspect every public getter and return its value, if the value is not a
	 * Number, String or Date, it will then do the conversion on that object as
	 * well. If the return type is a Collection, it will be converted to a
	 * JsonArray
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
	 * Convenience method for converting objects without a predefined mapper.
	 * This method does not allow objects to be filtered in any way and will
	 * inspect every public getter and return its value, if the value is not a
	 * Number, String or Date, it will then do the conversion on that object as
	 * well. If the return type is a Collection, it will be converted to a
	 * JsonArray
	 * 
	 * @param o
	 * @param flat
	 *            Don't look at non basic fields (numbers, strings, dates,
	 *            boolean)
	 * @return
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public JsonObject toJson(Object o, boolean flat) throws SecurityException,
			IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		return Mapper.DefaultMapper.toJson(o, flat);
	}

	/**
	 * Convenience method for converting objects without a predefined mapper.
	 * This method does not allow objects to be filtered in any way and will
	 * inspect every public getter and return its value, if the value is not a
	 * Number, String or Date, it will be skipped.
	 * 
	 * @param o
	 * @param cls
	 *            The class to read the fields from
	 * @return
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings("unchecked")
	public JsonObject toJson(Object o, Class cls) throws SecurityException,
			IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		return Mapper.DefaultMapper.toJson(o, cls);
	}

	/**
	 * Convenience method for converting objects without a predefined mapper.
	 * This method does not allow objects to be filtered in any way and will
	 * inspect every public getter and return its value, if the value is not a
	 * Number, String or Date, it will then do the conversion on that object as
	 * well. If the return type is a Collection, it will be converted to a
	 * JsonArray
	 * 
	 * @param c
	 * @return
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings("unchecked")
	public JsonArray toJsonArray(Collection c) throws SecurityException,
			IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		return Mapper.DefaultMapper.toJsonArray(c);
	}

	/**
	 * Convenience method for converting objects without a predefined mapper.
	 * This method does not allow objects to be filtered in any way and will
	 * inspect every public getter and return its value, if the value is not a
	 * Number, String or Date, it will then do the conversion on that object as
	 * well. If the return type is a Collection, it will be converted to a
	 * JsonArray
	 * 
	 * @param c
	 * @param flat
	 * @return
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings("unchecked")
	public JsonArray toJsonArray(Collection c, boolean flat)
			throws SecurityException, IllegalArgumentException,
			NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {
		return Mapper.DefaultMapper.toJsonArray(c, flat);
	}

	/**
	 * Convenience method for converting collections without a predefined
	 * mapper. This method does not allow objects to be filtered in any way and
	 * will inspect every public getter and return its value, if the value is
	 * not a Number, String or Date, it will be skipped.
	 * 
	 * @param c
	 *            The collection
	 * @param cls
	 *            The class to read the fields from
	 * @return
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings("unchecked")
	public JsonArray toJsonArray(Collection c, Class cls)
			throws SecurityException, IllegalArgumentException,
			NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {
		return Mapper.DefaultMapper.toJsonArray(c, cls);
	}

	/**
	 * Convenience method for converting collections without a predefined
	 * mapper. This method does not allow objects to be filtered in any way and
	 * will inspect every public getter and return its value, if the value is
	 * not a Number, String or Date, it will be skipped.
	 * 
	 * @param c
	 * @param cls
	 *            The class to read the fields from
	 * @param flat
	 * @return
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings("unchecked")
	public JsonArray toJsonArray(Collection c, Class cls, boolean flat)
			throws SecurityException, IllegalArgumentException,
			NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {
		return Mapper.DefaultMapper.toJsonArray(c, cls, flat);
	}
}
