package com.thedeanda.javajson.converter;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.thedeanda.javajson.JsonArray;
import com.thedeanda.javajson.JsonObject;
import com.thedeanda.javajson.JsonValue;

/**
 * Converts pojos to json based on bean-style fields
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

	public JsonValue toJsonValue(Object object)
			throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		return toJsonValue(object, new HashSet<Object>());
	}

	private JsonValue toJsonValue(Object object, Set<Object> seen)
			throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		JsonValue value = new JsonValue();

		if (!duplicateOk(object) && seen.contains(object)) {
			throw new RuntimeException("object already seen: " + object);
		}
		seen.add(object);

		if (object != null) {
			Class<? extends Object> cls = object.getClass();

			if (cls.isArray()) {
				toJsonValueFromArray(value, object, cls, seen);
			} else if (Map.class.isAssignableFrom(cls)) {
				Map map = (Map) object;
				JsonObject json = new JsonObject();
				for (Object key : map.keySet()) {
					Object mapValue = map.get(key);
					JsonValue newMapValue = toJsonValue(mapValue, seen);
					json.put(String.valueOf(key), newMapValue);
				}
				value.setJsonObject(json);
			} else if (Collection.class.isAssignableFrom(cls)) {
				Collection<?> col = (Collection<?>) object;
				JsonArray arr = new JsonArray();
				for (Object o : col) {
					JsonValue newArrValue = toJsonValue(o, seen);
					arr.add(newArrValue);
				}
				value.setJsonArray(arr);
			} else if (Boolean.class.isAssignableFrom(cls)) {
				value.setBoolean((Boolean) object);
			} else if (Double.class.isAssignableFrom(cls)) {
				value.setDouble((Double) object);
			} else if (Float.class.isAssignableFrom(cls)) {
				value.setFloat((Float) object);
			} else if (Long.class.isAssignableFrom(cls)) {
				value.setLong((Long) object);
			} else if (Integer.class.isAssignableFrom(cls)) {
				value.setInt((Integer) object);
			} else if (Date.class.isAssignableFrom(cls)) {
				value.setDate((Date) object);
			} else if (String.class.isAssignableFrom(cls)) {
				value.setString((String) object);
			} else { // pojo
				Map<String, Object> fields = Reflection.getFields(object);
				JsonObject json = new JsonObject();
				for (String key : fields.keySet()) {
					Object val = fields.get(key);
					String jsonKey = Reflection.getFieldName(key);
					if (keyOk(jsonKey))
						json.put(jsonKey, toJsonValue(val, seen));
				}
				value.setJsonObject(json);
			}
		}

		return value;
	}

	private void toJsonValueFromArray(JsonValue value, Object object, Class<? extends Object> cls, Set<Object> seen)
			throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		JsonArray arr = new JsonArray();
		value.setJsonArray(arr);
		if (object instanceof short[]) {
			for (short i : (short[]) object) {
				arr.add(i);
			}
		} else if (object instanceof int[]) {
			for (int i : (int[]) object) {
				arr.add(i);
			}
		} else if (object instanceof long[]) {
			for (long i : (long[]) object) {
				arr.add(i);
			}
		} else if (object instanceof float[]) {
			for (float i : (float[]) object) {
				arr.add(i);
			}
		} else if (object instanceof double[]) {
			for (double i : (double[]) object) {
				arr.add(i);
			}
		} else if (object instanceof boolean[]) {
			for (boolean i : (boolean[]) object) {
				arr.add(i);
			}
		} else if (object instanceof char[]) {
			for (char i : (char[]) object) {
				arr.add(String.valueOf(i));
			}
		} else if (object instanceof String[]) {
			for (String i : (String[]) object) {
				arr.add(String.valueOf(i));
			}
		} else {
			// generic array
			for (Object i : (Object[]) object) {
				arr.add(toJsonValue(i, seen));
			}
		}
	}

	private boolean duplicateOk(Object object) {
		if (object == null)
			return true;
		Class<? extends Object> cls = object.getClass();

		if (Number.class.isAssignableFrom(cls) || Date.class.isAssignableFrom(cls) || String.class.isAssignableFrom(cls)
				|| Boolean.class.isAssignableFrom(cls)) {
			return true;
		} else if (object instanceof Collection) {
			// lists and sets can equal each other if they are empty
			return true;
		}
		return false;
	}

	private boolean keyOk(String key) {
		return !"class".equalsIgnoreCase(key);
	}
}
