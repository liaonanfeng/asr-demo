package com.ewt360.asr.biz.base.impl;

import com.ewt360.asr.biz.base.UploadFileService;
import com.ewt360.asr.common.pool.AsrTask;
import com.ewt360.asr.common.pool.ExecutorPool;
import com.ewt360.asr.common.utils.CharacterUtils;
import com.ewt360.asr.common.utils.UploadUtils;
import com.ewt360.asr.dal.dao.UploadInfoDAO;
import com.ewt360.asr.dal.model.UploadInfoDO;
import com.ewt360.asr.facade.base.dto.AsrDTO;
import com.ewt360.asr.facade.base.dto.UploadFileDTO;
import com.ewt360.asr.facade.base.enumeration.VideoTypeEnum;
import com.ewt360.asr.facade.base.vo.UploadInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

/**
 * @author: wangchao
 * @create: 2019/7/11 15:11
 */
@Service
@Slf4j
public class UploadFileServiceImpl implements UploadFileService {
    @Autowired
    private UploadUtils uploadUtils;
    @Autowired
    private UploadInfoDAO uploadInfoDAO;
    @Autowired
    private ExecutorPool executorPool;
    @Value("${asr.app.id}")
    private String appId;
    @Value("${asr.app.key}")
    private String appKey;
    @Value("${asr.app.secret}")
    private String appSecret;
    @Value("${asr.app.dir}")
    private String appDir;
    @Value("${asr.upload.dir}")
    private String fileDir;
    @Value("${asr.log.dir}")
    private String logDir;

    //定义在构造方法完毕后，执行这个初始化方法
    @PostConstruct
    public void init(){
        //启动应用时识别所有状态为'识别中的'记录
        List<Long> ids = listByCondition();
        if(!CollectionUtils.isEmpty(ids))
            asr(ids);
    }

    @Override
    public void singleFileUpload(UploadFileDTO fileDTO) throws IOException {
        MultipartFile multipartFile = fileDTO.getFile();
        Assert.isTrue(!multipartFile.isEmpty(),"请选择文件");
        Assert.isTrue(CharacterUtils.matcher(multipartFile.getOriginalFilename(),".+(.mp4|.f4v|.wmv|.rmvb|.mkv|.avi|.rm|.mid|.mov|.mpeg)$"),"文件格式不正确");
        String filename = uploadUtils.saveFile(multipartFile);

        UploadInfoDO uploadInfoDO = new UploadInfoDO();
        uploadInfoDO.setOrginName(multipartFile.getOriginalFilename());
        uploadInfoDO.setFileName(filename);
        uploadInfoDO.setStatus(0);
        uploadInfoDO.setVideoType(fileDTO.getVideoType());
        uploadInfoDO.setCreateBy("2109");
        uploadInfoDAO.save(uploadInfoDO);
    }

    @Override
    public List<UploadInfoVO> listAll() {
        return uploadInfoDAO.listAll();
    }

    @Override
    public List<Long> listByCondition() {
        return uploadInfoDAO.listByCondition();
    }

    @Override
    public void asr(List<Long> ids) {
        List<UploadInfoDO> uploadInfoDOList = uploadInfoDAO.listByIds(ids);
        for (UploadInfoDO uploadInfoDO:uploadInfoDOList) {
            Assert.isTrue(uploadInfoDO.getStatus()!=1,"识别中的视频不能重复识别！");

            AsrDTO asrDTO = new AsrDTO();
            String fileName = uploadInfoDO.getFileName();
            //文件名
            asrDTO.setFileName(fileName);
            //百度AI语音识别类型
            asrDTO.setProductId(VideoTypeEnum.getProductId(uploadInfoDO.getVideoType()));
            String ext = fileName.substring(fileName.lastIndexOf(".")+1,fileName.length());
            String pre = fileName.substring(0,fileName.lastIndexOf("."));
            //扩展名
            asrDTO.setExt(ext);
            //前缀名
            asrDTO.setPre(pre);
            //百度AI平台上申请的appid
            asrDTO.setAppId(appId);
            //百度AI平台上申请的appkey
            asrDTO.setAppKey(appKey);
            //百度AI平台上申请的appsecret
            asrDTO.setAppSecret(appSecret);
            //程序入口目录
            asrDTO.setAppDir(appDir);
            //上传记录id
            asrDTO.setUploadId(uploadInfoDO.getId());

            UploadInfoDO up = new UploadInfoDO();
            up.setId(uploadInfoDO.getId());
            up.setStatus(1);
            up.setSrtName("");
            //更新识别状态为识别中
            uploadInfoDAO.update(up);

            //初始化语音识别任务
            AsrTask asrTask = new AsrTask(asrDTO,fileDir,logDir,uploadInfoDAO);
            //向线程池提交任务
            executorPool.execute(asrTask);
        }
    }

    public static void main(String[] args){
        String fileName = "1562911371613.f4v";
        String ext = fileName.substring(fileName.lastIndexOf(".")+1,fileName.length());
        String pre = fileName.substring(0,fileName.lastIndexOf("."));
        System.out.println(pre);
        System.out.println(ext);
        System.out.println(fileName.replace("."+ext,".srt"));
        System.out.println(String.format("执行shell耗时%ds",5600/1000));
        System.out.println(String.format("asrEwt_%s.log","1562911371613"));
    }
}
