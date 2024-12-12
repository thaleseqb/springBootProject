package com.example.screenmatch.service;

public interface IDataConvert {
    <T> T getData(String json, Class<T> customClass);
}
