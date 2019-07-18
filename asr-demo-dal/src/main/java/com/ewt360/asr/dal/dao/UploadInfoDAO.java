package com.ewt360.asr.dal.dao;

import com.ewt360.asr.dal.mapper.UploadInfoMapper;
import com.ewt360.asr.dal.model.UploadInfoDO;
import com.ewt360.asr.facade.base.vo.UploadInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: wangchao
 * @create: 2019/7/12 11:45
 */
@Repository
@Slf4j
public class UploadInfoDAO {
    @Autowired
    UploadInfoMapper uploadInfoMapper;

    public void save(UploadInfoDO uploadInfoDO){
        uploadInfoMapper.save(uploadInfoDO);
    }

    public List<UploadInfoVO> listAll(){
        return uploadInfoMapper.listAll();
    }

    public List<UploadInfoDO> listByIds(List<Long> ids){
        return uploadInfoMapper.listByIds(ids);
    }

    public List<Long> listByCondition() {
        return uploadInfoMapper.listByCondition();
    }

    public void update(UploadInfoDO uploadInfoDO){
        uploadInfoMapper.update(uploadInfoDO);
    }
}
