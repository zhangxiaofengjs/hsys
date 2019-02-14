package com.hsys.common;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
	
	public static byte[] readToByteArr(String filePath) throws IOException {
        File f = new File(filePath);
 
        ByteArrayOutputStream bos = new ByteArrayOutputStream((int) f.length());
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(f));
            int buf_size = 1024;
            byte[] buffer = new byte[buf_size];
            int len = 0;
            while (-1 != (len = in.read(buffer, 0, buf_size))) {
                bos.write(buffer, 0, len);
            }
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            bos.close();
        }
    }
	
	public static ResponseEntity<byte[]> downloadHttpFile(String filePath) {
		String fileName = "";
        try {
        	File file = new File(filePath);
        	if(!file.exists()) {
        		return null;
        	}
            fileName = new String(file.getName().getBytes("UTF-8"), "iso-8859-1");// 为了解决中文名称乱码问题
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 下载文件
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", fileName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        try {
            return new ResponseEntity<byte[]>(
            		readToByteArr(filePath), headers,
                    HttpStatus.CREATED);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
	}
}
