package com.hsys.common;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class HsysTimeSerializer extends JsonSerializer<Date>{
    public static final SimpleDateFormat format = new SimpleDateFormat("HH:mm");

    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException,JsonProcessingException
    {
        jsonGenerator.writeString(format.format(date));
    }
}
