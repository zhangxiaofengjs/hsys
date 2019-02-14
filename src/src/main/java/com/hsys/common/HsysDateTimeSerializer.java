package com.hsys.common;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2019/01/03
 */
public class HsysDateTimeSerializer extends JsonSerializer<Date> {
    public static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat format2 = new SimpleDateFormat("HH:mm:ss");

    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException,JsonProcessingException
    {
    	String str = format.format(date) + "T" + format2.format(date);
        jsonGenerator.writeString(str);
    }
}
