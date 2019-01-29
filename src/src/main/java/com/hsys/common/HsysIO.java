package com.hsys.common;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.hsys.exception.HsysException;

/**
 * @author: zhangxiaofengjs@163.com
 * @version: 2019/01/29
 */
public class HsysIO {
	public static void save(MultipartFile file, String filePath) {
		if(file == null) {
			throw new HsysException("文件为null。");
		}
		
        File uploadedFile = new File(filePath);

        if(uploadedFile.exists()) {
        	//如果存在先删除
        	uploadedFile.delete();
        }
        
        File fileParent = uploadedFile.getParentFile();
        //判断是否存在
        if (!fileParent.exists()) {
        	//创建父目录文件
        	fileParent.mkdirs();
        }
        
        try {
			file.transferTo(uploadedFile);
		} catch (IllegalStateException e) {
			throw new HsysException(e);
		} catch (IOException e) {
			throw new HsysException(e);
		}
    }

	public static void move(String tempPath, String attendacePath) {
		File file = new File(tempPath);
		File atFile = new File(attendacePath);
		
		File fileParent = atFile.getParentFile();
		//判断是否存在
		if (!fileParent.exists()) {
			//创建父目录文件
			fileParent.mkdirs();
		}
	        
		file.renameTo(atFile);
	}

	public static void delete(String path) {
		File file = new File(path);
		file.delete();
	}
}
