package com.ewt360.asr.home.controller;

import com.ewt360.asr.biz.base.UploadFileService;
import com.ewt360.asr.facade.base.dto.IdsDTO;
import com.ewt360.asr.facade.base.dto.UploadFileDTO;
import com.ewt360.asr.facade.base.result.BaseResult;
import com.ewt360.asr.common.config.QueryResponseMaker;
import com.ewt360.asr.facade.base.result.ListResult;
import com.ewt360.asr.facade.base.vo.UploadInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/asr")
@Api(tags = "演示Demo", value = "演示Demo", description = "wangchao@mistong.com")
public class UploadController {

    @Autowired
    private UploadFileService uploadFileService;
    @Value("${asr.upload.dir}")
    private String uploadDir;

    @ApiOperation(value = "上传文件")
    @PostMapping("upload")
     /*@ModelAttribute把请求链接中的videoType、file属性的值赋给UploadFileDTO对象的videoType、file变量*/
    public BaseResult singleFileUpload(@ModelAttribute @Valid UploadFileDTO uploadFileDTO) throws IOException {
        log.info("<<-------进入上传文件接口------->>");
        uploadFileService.singleFileUpload(uploadFileDTO);
        return QueryResponseMaker.build(true);
    }

    @ApiOperation(value = "展示视频文件列表", response = UploadInfoVO.class)
    @GetMapping("listAll")
    public ListResult<UploadInfoVO> listAll(){
        List<UploadInfoVO> uploadInfoVOList = uploadFileService.listAll();
        return QueryResponseMaker.build(true,uploadInfoVOList);
    }

    @ApiOperation(value = "语音识别")
    @PostMapping
    public BaseResult asr(@Valid @RequestBody IdsDTO idsDTO){
        uploadFileService.asr(idsDTO.getIds());
        return QueryResponseMaker.build(true);
    }

    @ApiOperation(value = "下载文件")
    @GetMapping("download")
    public void download(@RequestParam(name = "file") String fileName,
                         HttpServletRequest request,
                         HttpServletResponse response) throws IOException {
        //下载
        try {
            String filePath = uploadDir+fileName;
            // path是指欲下载的文件的路径。
            File file = new File(filePath);
//            // 取得文件的后缀名。
//            String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();

            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(filePath));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("gb2312"),"ISO8859-1"));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}