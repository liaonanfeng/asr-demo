package com.ewt360.asr.facade.base.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: wangchao
 * @create: 2019/7/15 10:23
 */
@Getter
@AllArgsConstructor
public enum VideoTypeEnum {
    ENG("1737"),
    OTHER("15372");

    //百度AI语音识别类型
    private String productId;

    public static String getProductId(String name){
        return VideoTypeEnum.valueOf(name).getProductId();
    }
}
