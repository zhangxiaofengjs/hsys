package com.hsys.schedule.jobs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hsys.common.HsysDate;

/**
 * 数据库备份计划
 * @author: zhangxiaofengjs@163.com
 * @version: 2019/07/23
 */
@Component
public class DataBaseBackupJob implements Job {
	@Autowired
	com.hsys.config.BackupDataBaseConfig backupDataBaseConfig;
	
	protected static final Logger logger = LoggerFactory.getLogger(DataBaseBackupJob.class);
	
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
    	if(!backupDataBaseConfig.isEnable()) {
    		return;
    	}
    	backup();
    }
    
    public boolean backup() {
    	logger.debug("database backup start");
    	
    	String backupPath = backupDataBaseConfig.getLocation();
    	File bkPath = new File(backupPath);
    	if (!bkPath.exists()) {
			//创建父目录文件
    		bkPath.mkdirs();
		}
    	backupPath += "hsys_" + HsysDate.format(HsysDate.now(), "yyyyMMddHHmmss") + ".backup";
    	
    	String cmdline = backupDataBaseConfig.getMysqlBin() + "/mysqldump" + 
    					" -h" + backupDataBaseConfig.getHost() + 
    					" -u" + backupDataBaseConfig.getUserName() + 
    					" -p" + backupDataBaseConfig.getPassword() + 
    					" " + backupDataBaseConfig.getName();
    	
    	logger.debug("backupPath:" + backupPath);
    	logger.debug("cmd:" + cmdline);
    	
    	PrintWriter printWriter = null;  
        BufferedReader bufferedReader = null;  
        
        try {  
             printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(backupPath), "utf8"));  
             Process process = Runtime.getRuntime().exec(cmdline);  
             InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream(), "utf8");  
             bufferedReader = new BufferedReader(inputStreamReader);  
             String line;  
             while((line = bufferedReader.readLine())!= null){  
                 printWriter.println(line);  
             }  
             printWriter.flush();  
             if(process.waitFor() == 0){//0 表示线程正常终止。  
            	 logger.debug("database backup end successfully");
                 return true;  
             }  
         }catch (IOException e) {  
             e.printStackTrace();  
         } catch (InterruptedException e) {
			e.printStackTrace();
		} finally {  
             try {  
                 if (bufferedReader != null) {  
                     bufferedReader.close();  
                 }  
                 if (printWriter != null) {  
                     printWriter.close();  
                 }  
             } catch (IOException e) {  
                 e.printStackTrace();  
             }  
         }
        
        logger.debug("database backup end exception");
    	return false;
    }
}
