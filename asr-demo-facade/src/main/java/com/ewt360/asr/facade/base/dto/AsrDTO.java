package com.ewt360.asr.facade.base.dto;

import com.ewt360.asr.facade.base.result.ToString;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: wangchao
 * @create: 2019/7/15 10:44
 */
@Data
public class AsrDTO extends ToString {
    @ApiModelProperty(value = "上传记录id")
    private Long uploadId;

    @ApiModelProperty(value = "文件名")
    private String fileName;

    @ApiModelProperty(value = "百度AI语音识别类型")
    private String productId;

    @ApiModelProperty(value = "扩展名")
    private String ext;

    @ApiModelProperty(value = "前缀名")
    private String pre;

    @ApiModelProperty(value = "百度AI平台上申请的appid")
    private String appId;

    @ApiModelProperty(value = "百度AI平台上申请的appkey")
    private String appKey;

    @ApiModelProperty(value = "百度AI平台上申请的appsecret")
    private String appSecret;

    @ApiModelProperty(value = "程序入口目录")
    private String appDir;
}
