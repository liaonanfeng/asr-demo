package com.ewt360.asr.dal.model;

import com.ewt360.asr.facade.base.result.ToString;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author: wangchao
 * @create: 2019/7/12 11:47
 */
@Data
public class UploadInfoDO extends ToString {
    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "视频文件原始名")
    private String orginName;

    @ApiModelProperty(value = "视频文件名")
    private String fileName;

    @ApiModelProperty(value = "状态（0:待语音识别,1:语音识别中,2:语音识别成功,3:语音识别失败）")
    private Integer status;

    @ApiModelProperty(value = "字幕文件名")
    private String srtName;

    @ApiModelProperty(value = "视频类型(ENG:英语,OTHER:其他)")
    private String videoType;

    @ApiModelProperty(value = "创建人工号")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date ctime;

    @ApiModelProperty(value = "更新时间")
    private Date utime;
}
