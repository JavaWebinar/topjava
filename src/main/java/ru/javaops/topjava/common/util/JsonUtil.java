package ru.javaops.topjava.common.util;

import lombok.experimental.UtilityClass;
import tools.jackson.core.JacksonException;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.ObjectReader;

import java.util.List;
import java.util.Map;

@UtilityClass
public class JsonUtil {
    private static ObjectMapper mapper;

    public static void setMapper(ObjectMapper mapper) {
        JsonUtil.mapper = mapper;
    }

    public static <T> List<T> readValues(String json, Class<T> clazz) {
        ObjectReader reader = mapper.readerFor(clazz);
        try {
            return reader.<T>readValues(json).readAll();
        } catch (JacksonException e) {
            throw new IllegalArgumentException("Invalid read array from JSON:\n'" + json + "'", e);
        }
    }

    public static <T> T readValue(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (JacksonException e) {
            throw new IllegalArgumentException("Invalid read from JSON:\n'" + json + "'", e);
        }
    }

    public static <T> String writeValue(T obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JacksonException e) {
            throw new IllegalStateException("Invalid write to JSON:\n'" + obj + "'", e);
        }
    }

    public static <T> String writeAdditionProps(T obj, String addName, Object addValue) {
        return writeAdditionProps(obj, Map.of(addName, addValue));
    }

    public static <T> String writeAdditionProps(T obj, Map<String, Object> addProps) {
        Map<String, Object> map = mapper.convertValue(obj, new TypeReference<>() {});
        map.putAll(addProps);
        return writeValue(map);
    }
}