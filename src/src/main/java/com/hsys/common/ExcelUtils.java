package com.hsys.common;

import java.text.DecimalFormat;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

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
	
	public static void copyRows(Sheet sheet, int rowIndexStart, int rowIndexEnd, Sheet sheetTo, int rowTo) {
		
		int rowDist = rowTo;
		
		//行的生成
		for(int r = rowIndexStart; r <= rowIndexEnd; r++) {
			Row row = sheet.getRow(r);
			Row createRow = sheetTo.createRow(rowDist++);
			createRow.setRowStyle(row.getRowStyle());
			createRow.setHeight(row.getHeight());
		}
		
		//行的结合
		for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
			CellRangeAddress region = sheet.getMergedRegion(i);
			if ((region.getFirstRow() >= rowIndexStart)  
				&& (region.getLastRow() <= rowIndexEnd)) {
 
				int RowToStart = region.getFirstRow() - rowIndexStart + rowTo;
				int RowToEnd = region.getLastRow() - rowIndexStart + rowTo;
 
				CellRangeAddress newRegion = region.copy();
				newRegion.setFirstRow(RowToStart);
				newRegion.setFirstColumn(region.getFirstColumn());
				newRegion.setLastRow(RowToEnd);
				newRegion.setLastColumn(region.getLastColumn());
				sheetTo.addMergedRegion(newRegion);
				
				System.out.println(newRegion.toString());
			}
		}

		//行的复制
		rowDist = rowTo;
		for(int r = rowIndexStart; r <= rowIndexEnd; r++) {
			Row row = sheet.getRow(r);
			Row createRow = sheetTo.getRow(rowDist++);
			
			int columnCount = row.getLastCellNum();
			for(int c = 0; c < columnCount; c++) {
				Cell cell = row.getCell(c);
				
				Cell createCell = createRow.createCell(c);
				copyCell(cell, createCell);
			}
		}
	}
	
	public static void copyCell(Cell cell, Cell createTo) {
		createTo.setCellStyle(cell.getCellStyle());

		if(cell.getCellComment() != null) {
			createTo.setCellComment(cell.getCellComment());
		}
		
		createTo.setCellType(cell.getCellType());
		
		switch(cell.getCellType()) {
			case Cell.CELL_TYPE_NUMERIC:
				if (DateUtil.isCellDateFormatted(cell)) {
					createTo.setCellValue(cell.getDateCellValue());
				} else {
					createTo.setCellValue(cell.getNumericCellValue());
				}
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				createTo.setCellValue(cell.getBooleanCellValue());
				break;
			case Cell.CELL_TYPE_STRING:
				createTo.setCellValue(cell.getStringCellValue());
				break;
			case Cell.CELL_TYPE_FORMULA:
				createTo.setCellFormula(cell.getCellFormula());
				break;
			case Cell.CELL_TYPE_ERROR:
				createTo.setCellErrorValue(cell.getErrorCellValue());
				break;
			default:
				break;
		}
	}
}
