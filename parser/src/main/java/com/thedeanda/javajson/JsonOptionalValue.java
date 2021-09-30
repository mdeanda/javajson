package com.thedeanda.javajson;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class JsonOptionalValue {
    public static JsonOptionalValue EMPTY = new JsonOptionalValue();

    private final JsonValue jsonValue;

    private JsonOptionalValue() {
        jsonValue = null;
    }
    private JsonOptionalValue(JsonArray jsonArray) {
        jsonValue = new JsonValue(jsonArray);
    }
    private JsonOptionalValue(JsonObject jsonObject) {
        jsonValue = new JsonValue(jsonObject);
    }
    private JsonOptionalValue(JsonValue jsonValue) {
        this.jsonValue = jsonValue;
    }

    /**
     * If a value is present, and the value matches the given predicate, return an Optional describing the value,
     * otherwise return an empty JsonOptionalValue.
     *
     * @param predicate
     * @return
     */
    public JsonOptionalValue filter(Predicate<JsonValue> predicate) {
        if (isPresent() && predicate.test(jsonValue))
            return this;
        return EMPTY;
    }

    /**
     * If a value is present, apply the provided Optional-bearing mapping function to it, return that result,
     * otherwise return an empty Optional.
     *
     * @param mapper
     * @param <U>
     * @return
     */
    public <U> Optional<U> flatMap(Function<? super JsonValue, Optional<U>> mapper) {
        if (!isPresent())
            return Optional.empty();
        else
            return mapper.apply(jsonValue);
    }

    /**
     * If a value is present in this JsonOptionalValue, returns the value, otherwise throws NoSuchElementException.
     * @return
     */
    public JsonValue get() {
        if (isPresent())
            return jsonValue;
        else
            throw new NoSuchElementException();
    }
    public JsonOptionalValue get(String key) {
        if (isPresent()) {
            if (jsonValue.isJsonObject()) {
                JsonObject json = jsonValue.getJsonObject();
                if (json.hasKey(key))
                    return new JsonOptionalValue(json.get(key));
            }
        }

        return EMPTY;
    }
    public JsonOptionalValue get(int index) {
        if (isPresent()) {
            if (jsonValue.isJsonArray()) {
                JsonArray array = jsonValue.getJsonArray();
                if (index >= 0 && index < array.size())
                    return new JsonOptionalValue(array.getJsonValue(index));
            }
        }

        return EMPTY;
    }

    public void ifPreset(Consumer<? super JsonValue> consumer) {
        if (isPresent())
            consumer.accept(jsonValue);
    }

    /**
     * Return true if there is a value present, otherwise false.
     *
     * @return
     */
    public boolean isPresent() {
        return (jsonValue != null);
    }

    /**
     * If a value is present, apply the provided mapping function to it, and if the result is non-null,
     * return an Optional describing the result.
     *
     * @param mapper
     * @param <U>
     * @return
     */
    public <U> Optional<U> map(Function<? super JsonValue,? extends U> mapper) {
        if (!isPresent())
            return Optional.empty();
        else
            return Optional.ofNullable(mapper.apply(jsonValue));
    }

    /**
     * Returns a JsonOptionalValue with the specified present non-null value.
     *
     * @param jsonArray
     * @return
     */
    public static JsonOptionalValue of(JsonArray jsonArray) {
        if (jsonArray == null)
            throw new NullPointerException("JsonArray must not be null");
        return new JsonOptionalValue(jsonArray);
    }

    /**
     * Returns a JsonOptionalValue with the specified present non-null value.
     *
     * @param jsonObject
     * @return
     */
    public static JsonOptionalValue of(JsonObject jsonObject) {
        if (jsonObject == null)
            throw new NullPointerException("JsonObject must not be null");
        return new JsonOptionalValue(jsonObject);
    }

    /**
     * Returns an JsonOptionalValue describing the specified value, if non-null, otherwise returns an empty
     * JsonOptionalValue.
     *
     * @param jsonArray
     * @return
     */
    public static JsonOptionalValue ofNullable(JsonArray jsonArray) {
        if (jsonArray == null)
            return EMPTY;
        return new JsonOptionalValue(jsonArray);
    }

    /**
     * Returns an JsonOptionalValue describing the specified value, if non-null, otherwise returns an empty
     * JsonOptionalValue.
     *
     * @param jsonObject
     * @return
     */
    public static JsonOptionalValue ofNullable(JsonObject jsonObject) {
        if (jsonObject == null)
            return EMPTY;
        return new JsonOptionalValue(jsonObject);
    }

    /**
     * Return the value if present, otherwise return other.
     *
     * @param jsonValue
     * @return
     */
    public JsonValue orElse(JsonValue jsonValue) {
        if (isPresent())
            return jsonValue;
        else return jsonValue;
    }

    /**
     * Return the value if present, otherwise invoke other and return the result of that invocation.
     *
     * @param other
     * @return
     */
    public JsonValue orElseGet(Supplier<JsonValue> other) {
        if (isPresent())
            return jsonValue;
        else return other.get();
    }

    /**
     * Return the contained value, if present, otherwise throw an exception to be created by the provided supplier.
     *
     * @param exceptionSupplier
     * @param <X>
     * @return
     * @throws X
     */
    public <X extends Throwable> JsonValue orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        if (isPresent())
            return jsonValue;
        else
            throw exceptionSupplier.get();
    }

    public String toString() {
        return isPresent() ? String.format("JsonOptionalValue [%s]", jsonValue) : "JsonOptionalValue.EMPTY";
    }
}
