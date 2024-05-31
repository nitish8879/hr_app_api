package com.hr.hr_management.utils;

import java.sql.Time;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.Converter;

@Converter
public class TimeListConverter implements jakarta.persistence.AttributeConverter<List<Time>, String> {

    private static final String SEPARATOR = ",";

    @Override
    public String convertToDatabaseColumn(List<Time> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return "";
        }
        return attribute.stream()
                .map(Time::toString)
                .collect(Collectors.joining(SEPARATOR));
    }

    @Override
    public List<Time> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }
        return Arrays.stream(dbData.split(SEPARATOR))
                .map(Time::valueOf)
                .collect(Collectors.toList());
    }
}
