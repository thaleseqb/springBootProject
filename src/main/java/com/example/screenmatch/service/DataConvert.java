package com.example.screenmatch.service;

import com.example.screenmatch.model.DataSerie;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataConvert implements IDataConvert {
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T getData(String json, Class<T> customClass) {
        try {
            return mapper.readValue(json, customClass);
        } catch (JsonProcessingException exception) {
            throw new RuntimeException(exception);
        }
    }
}
