package com.github.learndifferent.githubstars.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.learndifferent.githubstars.exception.ServiceException;

import java.io.IOException;


public class JsonUtil {
    private static final ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
    }

    public static String toJson(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new ServiceException("Fail to change to Json");
        }
    }

    public static <T> T toObject(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new ServiceException("Fail to change to Object");
        }
    }

    public static <T> T toObject(String json, TypeReference<T> valueTypeRef) {
        try {
            return mapper.readValue(json, valueTypeRef);
        } catch (IOException e) {
            throw new ServiceException("Fail to change to Object");
        }
    }

}