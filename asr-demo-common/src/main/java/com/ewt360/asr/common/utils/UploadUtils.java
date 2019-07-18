package com.ewt360.asr.common.utils;

import com.ewt360.asr.common.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

@Slf4j
@Component
public class UploadUtils {
    @Value("${asr.upload.dir}")
    private String fileDir;

    public String saveFile(MultipartFile multipartFile){
        String filename = getFileName(multipartFile.getOriginalFilename());
        String filePath = fileDir+filename;
        File targetFile = new File(filePath);
        OutputStream outStream = null;
        try {
            log.info("<<-------正在努力上传中...------->>");
            outStream = new FileOutputStream(targetFile);
            outStream.write(multipartFile.getBytes());
            outStream.flush();
            outStream.close();
            log.info("<<-------上传成功------->>");
            return filename;
        } catch (Exception e) {
            log.error("<<-------上传失败------->>", e);
            throw new ServiceException("上传失败，请联系管理员！", e);
        }
    }

    public String getFileName(String fileName){
        return System.currentTimeMillis() +fileName.substring(fileName.indexOf("."),fileName.length());
    }
}
