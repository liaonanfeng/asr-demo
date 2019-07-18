package com.ewt360.asr.facade.base.dto;

import com.ewt360.asr.facade.base.result.ToString;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author: wangchao
 * @create: 2019/7/16 16:38
 */
@Data
public class IdsDTO extends ToString {
    @Size(min=1,message = "没有可识别的视频")
    private List<Long> ids;
}
