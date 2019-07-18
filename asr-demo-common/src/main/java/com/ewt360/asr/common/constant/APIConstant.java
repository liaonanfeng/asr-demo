package com.ewt360.asr.common.constant;

/**
 * @author 王超 on 2018/4/23.
 * @desc
 */
public interface APIConstant {
    String CHARSET_UTF8 = "UTF-8";

    String HTTP_CONTENT_TYPE_KEY = "Content-type";

    //语音识别线程数
    Integer THREAD_MAX = 3;

    //语音识别的shell脚本名称
    String SHELL_NAME = "run.sh";

    //日志格式名称
    String LOG_FORMAT_NAME = "asrEwt_%s.log";

    //语音识别超时时长（单位：s）
    Long TIMEOUT = 1800L;
}
