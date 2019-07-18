package com.ewt360.asr.biz.base;


import com.ewt360.asr.facade.base.dto.UploadFileDTO;
import com.ewt360.asr.facade.base.vo.UploadInfoVO;

import java.io.IOException;
import java.util.List;


/**
 *
 * @author liaozhanggen@mistong.com
 * @date 2018/05/07
 */
public interface UploadFileService {


    /**
     *  上传附件文件
     *
     * @param fileDTO
     * @return
     */
    void singleFileUpload(UploadFileDTO fileDTO) throws IOException;

    /**
     * 展示视频文件列表
     * @return
     */
    List<UploadInfoVO> listAll();

    /**
     * 获取所有status=1的视频id列表
     * @return
     */
    List<Long> listByCondition();

    /**
     * 语音识别
     * @param ids
     */
    void asr(List<Long> ids);

}
