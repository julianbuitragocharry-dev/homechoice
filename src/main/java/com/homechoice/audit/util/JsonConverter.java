package com.homechoice.audit.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converter class for converting Java objects to JSON strings and vice versa.
 * This class uses Jackson's ObjectMapper for the conversion.
 */
@Converter
public class JsonConverter implements AttributeConverter<Object, String> {
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Converts a Java object to a JSON string for storing in the database.
     *
     * @param attribute the Java object to convert
     * @return the JSON string representation of the object
     * @throws RuntimeException if an error occurs during conversion
     */
    @Override
    public String convertToDatabaseColumn(Object attribute) {
        try {
            return mapper.writeValueAsString(attribute);
        } catch (Exception e) {
            throw new RuntimeException("Error converting to JSON");
        }
    }

    /**
     * Converts a JSON string from the database to a Java object.
     *
     * @param dbData the JSON string from the database
     * @return the Java object represented by the JSON string
     * @throws RuntimeException if an error occurs during conversion
     */
    @Override
    public Object convertToEntityAttribute(String dbData) {
        try {
            return mapper.readValue(dbData, Object.class);
        } catch (Exception e) {
            throw new RuntimeException("Error converting from JSON");
        }
    }
}
