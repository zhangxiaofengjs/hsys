package com.hsys.io;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Component;
import com.hsys.common.HsysDate;
import com.hsys.exception.HsysException;
import com.hsys.models.ExtraTimeModel;
import com.hsys.models.enums.ExtraTimeType;

@Component
public class ExtraTimeWriter {
	private InputStream templateFileIs;
	
	public void setTemplateFileStream(InputStream is) {
		this.templateFileIs = is;
	}

	public void write(String filePath, List<ExtraTimeModel> extraTimes) {
		OutputStream out = null;
		int rowNo = 0;
		int i = 0;		
		try {
			Workbook wb = WorkbookFactory.create(this.templateFileIs);
			Sheet sheet = null;
			for(ExtraTimeModel ex :extraTimes) {
				sheet = wb.getSheet(ex.getUser().getNo());
				if(sheet == null) {
					sheet = wb.cloneSheet(0);  //克隆sheet(0)					
					i = wb.getSheetIndex(sheet);
					wb.setSheetName(i, ex.getUser().getNo());
					sheet.setFitToPage(true);
				}						
				for(int j = 6 ; j < 32 ; j++) {
					Row row02 = sheet.getRow(j);
					Cell cell01 = row02.getCell(0);
					String m = cell01.getStringCellValue();
					if(m.equals("") || m.isEmpty()) {
						rowNo = j;	
						break;
					}
					
				}
				/*
				Row row02 = sheet.getRow(1);
				Cell cell01 = row02.createCell(9);					
				cell01.setCellValue(HsysDate.format(ex.getDate(), HsysDate.DATE_PATTERN).substring(0, 4));				
				Cell cell02 = row02.createCell(13);					
				cell02.setCellValue(HsysDate.format(ex.getDate(), HsysDate.DATE_PATTERN).substring(5, 7));
				
				Row row04 = sheet.getRow(3);
				//Cell cell07 = row04.createCell(2);
				//cell07.setCellValue(ex.getUser().getGroup().getName());
				Cell cell03 = row04.createCell(8);					
				cell03.setCellValue(ex.getUser().getNo());	
				Cell cell04 = row04.createCell(14);					
				cell04.setCellValue(ex.getUser().getName());	
				Cell cell05 = row04.createCell(21);					
				cell05.setCellValue(HsysDate.format(ex.getDate(), HsysDate.DATE_PATTERN).substring(5, 7));				
				Cell cell06 = row04.createCell(25);					
				cell06.setCellValue("0"+(Integer.parseInt(HsysDate.format(ex.getDate(), HsysDate.DATE_PATTERN).substring(5, 7))+1));
				*/
				Row row01 = sheet.getRow(rowNo);//读多条数据。要给一个rowNo。来指定第几行
				
				row01.getCell(0).setCellValue(HsysDate.format(ex.getDate(), HsysDate.DATE_PATTERN));//日期
				String b = HsysDate.format(ex.getStartTime(), HsysDate.TIME_PATTERN).substring(0, 5)+"~"+HsysDate.format(ex.getEndTime(), HsysDate.TIME_PATTERN).substring(0, 5);
				row01.getCell(4).setCellValue(b);//预定时间
				row01.getCell(8).setCellValue(ex.getLength());//预定时数				
				if(ex.getType()==ExtraTimeType.Normal) {			
					row01.getCell(10).setCellValue("平时");
				}else if(ex.getType()==ExtraTimeType.Weekend) {
					row01.getCell(10).setCellValue("周末");
				}else if(ex.getType()==ExtraTimeType.Holiday) {
					row01.getCell(10).setCellValue("节假");
				}				
				row01.getCell(12).setCellValue(ex.getComment());//加班内容
				//批准
				row01.getCell(23).setCellValue(ex.getLength());//实际时数
				row01.getCell(25).setCellValue(b);//实际时间
				//确认					
			}	
			wb.removeSheetAt(0);
			out = new FileOutputStream(filePath);
			wb.write(out);			
		} catch(Exception e) {
			throw new HsysException(e.getMessage() + " \n错误行:" + (rowNo + 1), e);
		} finally {			
			if(out != null) {
				try {					
					out.flush();
					out.close();
				} catch(Exception e) {
					System.out.println(e);
				}
			}
		}
	}
}
