package com.ewt360.asr.facade.base.enumeration;

public enum BaseResultCodeEnum implements EnumBaseResult {
    SUCCESS("SUCCESS", "操作成功", 1000001),
    SYSTEM_ERROR("SYSTEM_ERROR", "系统异常，请联系管理员！", 1000002),
    INTERFACE_SYSTEM_ERROR("INTERFACE_SYSTEM_ERROR", "外部接口调用异常，请联系管理员！", 1000003),
    CONNECT_TIME_OUT("CONNECT_TIME_OUT", "系统超时，请稍后再试!", 1000004),
    SYSTEM_FAILURE("SYSTEM_FAILURE", "系统错误", 1000005),
    NULL_ARGUMENT("NULL_ARGUMENT", "参数为空", 1000006),
    ILLEGAL_ARGUMENT("ILLEGAL_ARGUMENT", "非法参数", 1000007),
    ILLEGAL_STATE("ILLEGAG_STATE", "非法状态", 1000008),
    ENUM_CODE_ERROR("ENUM_CODE_ERROR", "错误的枚举编码", 1000009),
    LOGIC_ERROR("LOGIC_ERROR", "逻辑错误", 1000010),
    CONCURRENT_ERROR("CONCURRENT_ERROR", "并发异常", 1000011),
    ILLEGAL_OPERATION("ILLEGAL_OPERATION", "非法操作", 1000012),
    REPETITIVE_OPERATION("REPETITIVE_OPERATION", "重复操作", 1000013),
    NO_OPERATE_PERMISSION("NO_OPERATE_PERMISSION", "无操作权限", 1000014),
    DATA_ERROR("DATA_ERROR", "数据异常", 1000015),
    // add code by liaozhanggen 2018-05-09 15:00
    TIMEOUT("SESSION_TIMEOUT", "会话过期，请重新登陆",100001),
    WAIT_THREAD_ERROR("WAIT_THREAD_ERROR", "等待进程结束出现错误！", 1000016),
    SCRIPT_INNER_ERROR("SCRIPT_INNER_ERROR", "调用脚本内部异常", 1000017),
    EXISTS_ERROR("EXISTS_ERROR", "该表已配置其他任务！", 1000018),
    ALLOW_ERROR("ALLOW_ERROR", "只允许运行当天的周、月任务！", 1000019),
    NOTEXISTS_ERROR("NOTEXISTS_ERROR", "该表已不存在！", 1000020);


    private String code;
    private String message;
    private Integer NCode;

    BaseResultCodeEnum(String code, String message, int NCode) {
        this.code = code;
        this.message = message;
        this.NCode = Integer.valueOf(NCode);
    }

    public static BaseResultCodeEnum getResultCodeEnumByCode(String code) {
        BaseResultCodeEnum[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            BaseResultCodeEnum param = var1[var3];
            if(param.getCode().equals(code)) {
                return param;
            }
        }

        return null;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public String message() {
        return this.message;
    }

    public Integer getNCode() {
        return this.NCode;
    }
}