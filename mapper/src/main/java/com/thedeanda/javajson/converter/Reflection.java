package com.thedeanda.javajson.converter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A set of utility functions for reflection related things used in this json
 * package
 */
public class Reflection {

	private static Pattern setterPattern = Pattern.compile("^set[A-Z].*");

	private static Pattern getterPattern = Pattern.compile("^(is|get)[A-Z].*");

	@SuppressWarnings("unchecked")
	public static List<Method> getSetterFieldMethods(Class cls) {
		List<Method> methods = new LinkedList<Method>();

		Method[] a = cls.getMethods();
		for (int i = 0; i < a.length; i++) {

			if (a[i].getParameterTypes().length == 1
					&& isSetterMethod(a[i].getName())) {
				// TODO: make sure method isn't static or protected/private
				methods.add(a[i]);
			}
		}

		return methods;
	}

	/**
	 * Returns the public getter methods that are normal getters, this includes
	 * getX and isX methods as long as the isX methods return a boolean.
	 * 
	 * @param cls
	 * @return
	 */
	@SuppressWarnings("unchecked")
	static List<Method> getGetterFieldMethods(Class cls) {
		List<Method> methods = new LinkedList<Method>();

		Method[] a = cls.getMethods();
		for (int i = 0; i < a.length; i++) {

			if (a[i].getParameterTypes().length == 0
					&& isGetterMethod(a[i].getName())) {
				// TODO: check that 'is' methods are boolean
				// TODO: make sure method isn't static or protected/private
				methods.add(a[i]);
			}
		}

		return methods;
	}

	public static Map<String, Object> getFields(Object o)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		List<Method> methods = getGetterFieldMethods(o.getClass());
		Map<String, Object> ret = new HashMap<String, Object>();

		Object[] ARGS = new Object[0];
		for (Method m : methods) {
			String name = m.getName();
			Object value = m.invoke(o, ARGS);
			ret.put(name, value);
		}

		return ret;
	}

	/**
	 * Converts a methodname into a field name. Return null if its not a
	 * proper/normal method name that starts with get/set/is.
	 * 
	 * @param methodName
	 * @return
	 */
	public static String getFieldName(String methodName) {
		int l = methodName.length();
		if (l >= 2 && Character.isUpperCase(methodName.charAt(2))
				&& methodName.startsWith("is")) {
			return Character.toLowerCase(methodName.charAt(2))
					+ methodName.substring(3);

		}
		if (l >= 3
				&& Character.isUpperCase(methodName.charAt(3))
				&& (methodName.startsWith("get") || methodName
						.startsWith("set"))) {
			return Character.toLowerCase(methodName.charAt(3))
					+ methodName.substring(4);

		}

		return null;
	}

	/**
	 * Returns true if the name starts with get or is followed by a capital
	 * letter
	 */
	private static boolean isGetterMethod(String name) {
		Matcher m = getterPattern.matcher(name);
		return m.matches();
	}

	private static boolean isSetterMethod(String name) {
		Matcher m = setterPattern.matcher(name);
		return m.matches();
	}
}
