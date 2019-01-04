package com.hsys.common;

import java.text.DecimalFormat;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2019/01/04
 */
public class ExcelUtils {
	public static String getCellValue(Cell cell) {
		if (cell == null) {
			return "";
		}
		
		int cellType = cell.getCellType();
        switch (cellType) {
            case Cell.CELL_TYPE_NUMERIC: 
            	if (DateUtil.isCellDateFormatted(cell)) {
            		return getCellValue(cell, HsysDate.DATE_TIME_PATTERN);
            	} else {
            		return getCellValue(cell, "0.00000");
            	}
            default:
            	return getCellValue(cell, null);
        }
	}
	
	public static String getCellValue(Cell cell, String format) {
        String value = "";
        if (cell != null) {
            int cellType = cell.getCellType();
            switch (cellType) {
                case Cell.CELL_TYPE_NUMERIC:            //表示数值
                	double val = cell.getNumericCellValue();
                	if (DateUtil.isCellDateFormatted(cell)) {
                		Date date = DateUtil.getJavaDate(val);
                		value = HsysDate.format(date, format);
                	} else {
                		DecimalFormat df = new DecimalFormat(format);
	                    value =  df.format(val);
                	}
                    break;
                case Cell.CELL_TYPE_STRING:            //表示字符串
                    value = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_BLANK:            //表示空白
                    value = "";
                    break;
                case Cell.CELL_TYPE_BOOLEAN:            //表示boolean
                    value = cell.getBooleanCellValue() + "";
                    break;
                default:        //表示其他
                    value = "";
                    break;
            }
        } else {
            value = "";
        }
        return value.trim();
    }
}
