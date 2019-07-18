package com.ewt360.asr.dal.mapper;

import com.ewt360.asr.dal.model.UploadInfoDO;
import com.ewt360.asr.facade.base.vo.UploadInfoVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: wangchao
 * @create: 2019/7/12 11:55
 */
public interface UploadInfoMapper {
    void save(UploadInfoDO uploadInfoDO);
    List<UploadInfoVO> listAll();
    List<UploadInfoDO> listByIds(@Param("list") List<Long> ids);
    void update(UploadInfoDO uploadInfoDO);
    List<Long> listByCondition();
}
