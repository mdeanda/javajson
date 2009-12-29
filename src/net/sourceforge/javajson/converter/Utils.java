package net.sourceforge.javajson.converter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sourceforge.javajson.JsonArray;
import net.sourceforge.javajson.JsonObject;
import net.sourceforge.javajson.JsonValue;

public class Utils {

	public static DateFormat dateFormat = new SimpleDateFormat(
			"yyyy/MM/dd HH:mm:ss");

	public static void fromJson(Object obj, JsonObject json)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, InstantiationException,
			ClassNotFoundException {
		if (obj != null && json != null) {
			List<Method> methods = Reflection.getSetterFieldMethods(obj
					.getClass());
			for (Method method : methods) {
				apply(obj, json, method);
			}
		}
	}

	/**
	 * Applies a method on an object
	 * 
	 * @param obj
	 * @param json
	 * @param method
	 * @return false if the method couldn't be applied
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 */
	private static boolean apply(Object obj, JsonObject json, Method method)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, InstantiationException,
			ClassNotFoundException {
		String fieldName = Reflection.getFieldName(method.getName());
		if (json.hasKey(fieldName) && !json.isNull(fieldName)) {
			Object[] args = prepareParameter(json, method, fieldName);
			if (args[0] != null)
				method.invoke(obj, args);
		} else if (json.hasKey(fieldName)) {
			System.out.println("null field:" + fieldName + ", "
					+ json.getString(fieldName));
		}

		return true;
	}

	@SuppressWarnings("unchecked")
	private static Object[] prepareParameter(JsonObject json, Method method,
			String fieldName) throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException,
			InstantiationException, ClassNotFoundException {
		Object[] ret = new Object[1];
		Class[] params = method.getParameterTypes();
		Class param = params[0];

		if (param == String.class) {
			ret[0] = json.getString(fieldName);
		} else if (param == Integer.class || param == int.class) {
			ret[0] = new Integer(json.getInt(fieldName));
		} else if (param == Long.class || param == long.class) {
			ret[0] = new Long(json.getLong(fieldName));
		} else if (param == Float.class || param == float.class) {
			ret[0] = new Float(json.getFloat(fieldName));
		} else if (param == Double.class || param == double.class) {
			ret[0] = new Double(json.getDouble(fieldName));
		} else if (param == Date.class) {
			try {
				// System.out.println("todate: " + json.getString(fieldName) + "
				// " + json.isNull(fieldName));
				ret[0] = dateFormat.parse(json.getString(fieldName));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// System.out.println("date, fieldname:" + fieldName + ", " +
			// ret[0]);
		} else if (Collection.class.isAssignableFrom(param)) {
			Type genericType = method.getGenericParameterTypes()[0];
			Matcher m = Pattern.compile(".*<(.+)>.*").matcher(
					genericType.toString());
			String typeName = null;
			if (m.matches())
				typeName = m.group(1);
			Collection c;
			if (List.class.isAssignableFrom(param)) {
				c = new ArrayList();
			} else {
				c = new HashSet();
			}
			ret[0] = c;
			if (json.isJsonArray(fieldName)) {
				for (JsonValue value : json.getJsonArray(fieldName)) {

					System.out.println("gt/int:" + genericType.toString() + " "
							+ Integer.class.getName() + "\n");

					if (value.isJsonObject()) {
						Object o = Converter.getInstance().fromJson(
								value.getJsonObject());
						c.add(o);
					} else if (value.isJsonArray()) {
						// TODO: do something about nested arrays
					} else if (value.isBoolean()
							&& (typeName == null || Boolean.class.getName()
									.equals(typeName))) {
						c.add(value.getBoolean());
					} else if (value.isFloat()
							&& (typeName == null || Float.class.getName()
									.equals(typeName))) {
						c.add(value.getFloat());
					} else if (value.isDouble()
							&& (typeName == null || Double.class.getName()
									.equals(typeName))) {
						c.add(value.getDouble());
					} else if (value.isInt()
							&& (typeName == null || Integer.class.getName()
									.equals(typeName))) {
						c.add(value.getInt());
					} else if (value.isLong()
							&& (typeName == null || Long.class.getName()
									.equals(typeName))) {
						c.add(value.getLong());
					} else if (value.isString()) {
						c.add(value.getString());
					}
				}
			} else {
				// throw some kind of exception or something
			}
			// System.out.println("do something with a list");
		} else {
			System.out.println("** object:" + fieldName + ", "
					+ param.getName());
			Object o = param.newInstance();
			Utils.fromJson(o, json.getJsonObject(fieldName));
			ret[0] = o;
		}

		return ret;
	}

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
	@SuppressWarnings("unchecked")
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
	@SuppressWarnings("unchecked")
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
