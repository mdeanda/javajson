package net.sourceforge.javajson.converter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import net.sourceforge.javajson.JsonArray;
import net.sourceforge.javajson.JsonObject;

public class Utils {

	public static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

	/**
	 * Convers an object to a simple (flat) json object. For depth, use the
	 * Converter with proper mappers
	 * 
	 * @param cls
	 * @param obj
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings("unchecked")
	public static JsonObject toJson(Object obj, Class cls)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		JsonObject ret = null;
		if (obj != null && cls.isAssignableFrom(obj.getClass())) {
			ret = new JsonObject();
			List<Method> methods = Reflection.getGetterFieldMethods(cls);
			Object[] ARGS = new Object[0];

			for (Iterator<Method> it = methods.iterator(); it.hasNext();) {
				Method m = it.next();
				String fld = Reflection.getFieldName(m.getName());
				Object o = m.invoke(obj, ARGS);
				objectIntoJsonObject(ret, fld, o);
			}
		} else if (!cls.isAssignableFrom(obj.getClass())) {
			
			System.out.println("\n\noops!\n\n");
			
			System.out.println(cls.getName());
			System.out.println(obj.getClass().getName());
			
		}

		return ret;
	}

	/**
	 * Adds known simple types to the json object
	 * 
	 * @param obj
	 * @param key
	 * @param o
	 * @return True if the object as of a simple type
	 */
	public static boolean objectIntoJsonObject(JsonObject obj, String key,
			Object o) {
		if (o instanceof Long) {
			obj.put(key, ((Long) o).longValue());
		} else if (o instanceof Integer) {
			obj.put(key, ((Integer) o).intValue());
		} else if (o instanceof Double) {
			obj.put(key, ((Double) o).doubleValue());
		} else if (o instanceof Float) {
			obj.put(key, ((Float) o).doubleValue());
		} else if (o instanceof Boolean) {
			obj.put(key, ((Boolean) o).booleanValue());
		} else if (o instanceof Date) {
			obj.put(key, dateFormat.format((Date) o));
		} else if (o instanceof String) {
			obj.put(key, (String) o);
		} else if (o instanceof Class) {
			obj.put(key, ((Class) o).getName());
		} else if (o == null) {
			obj.put(key, (String) null);
		} else {
			return false;
		}

		return true;
	}

	/**
	 * Adds known simple types to the json array
	 * 
	 * @param obj
	 * @param key
	 * @param o
	 * @return True if the object as of a simple type
	 */
	public static boolean objectIntoJsonArray(JsonArray arr, Object o) {
		if (o instanceof Long) {
			arr.add(((Long) o).longValue());
		} else if (o instanceof Integer) {
			arr.add(((Integer) o).intValue());
		} else if (o instanceof Double) {
			arr.add(((Double) o).doubleValue());
		} else if (o instanceof Float) {
			arr.add(((Float) o).doubleValue());
		} else if (o instanceof Boolean) {
			arr.add(((Boolean) o).booleanValue());
		} else if (o instanceof Date) {
			arr.add(dateFormat.format((Date) o));
		} else if (o instanceof String) {
			arr.add((String) o);
		} else if (o instanceof Class) {
			arr.add(((Class) o).getName());
		} else if (o == null) {
			arr.add((String) null);
		} else {
			return false;
		}

		return true;
	}
}
