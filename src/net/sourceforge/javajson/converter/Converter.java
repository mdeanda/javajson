package net.sourceforge.javajson.converter;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

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

	public Converter() {

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
}
