package com.hsys.common;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class HsysTimeDeserializer extends JsonDeserializer<Date>{
	public static final SimpleDateFormat format = new SimpleDateFormat("HH:mm");

	public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException,JsonProcessingException
    {
		String strDate = jsonParser.getText();
		if(strDate.contains("T")) {
    		strDate = strDate.replace("T", " ");
		}
		
        try
        {
        	return format.parse(strDate);
        }
        catch(Exception e)
        {
    		System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
