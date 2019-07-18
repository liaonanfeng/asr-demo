package com.ewt360.asr.facade.base.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class UploadFileDTO implements Serializable{
    @ApiModelProperty(value = "视频类型(ENG:英语,OTHER:其他)")
    @NotBlank(message = "视频类型不能为空")
    private String videoType;

    @ApiModelProperty(value = "上传文件")
    @NotNull(message = "上传文件不能为空")
    private MultipartFile file;
}