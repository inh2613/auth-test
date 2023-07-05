package com.inhee.jwt.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
	private static final ObjectMapper objectMapper = new ObjectMapper();

	public static <T> String toJson(T object) throws JsonProcessingException {
		return objectMapper.writeValueAsString(object);
	}

	public static <T> T fromJson(String json, Class<T> clazz) throws JsonProcessingException {
		return objectMapper.readValue(json, clazz);
	}
}