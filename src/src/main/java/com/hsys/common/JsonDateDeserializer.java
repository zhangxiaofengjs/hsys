package com.hsys.common;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2019/01/03
 */
public class JsonDateDeserializer extends JsonDeserializer<Date> {
	public static final SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException,JsonProcessingException
    {
        try
        {
            return format.parse(jsonParser.getText());
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
