package com.ewt360.asr.facade.base.result;

import com.ewt360.asr.facade.base.enumeration.BaseResultCodeEnum;
import com.ewt360.asr.facade.base.enumeration.EnumBase;
import com.ewt360.asr.facade.base.enumeration.EnumBaseResult;
import org.springframework.util.StringUtils;

public class BaseResult extends ToString {
    private static final long serialVersionUID = 1545989841537897712L;
    protected boolean success = false;
    protected String code;
    protected String message;
    protected Integer ncode;

    public BaseResult(){}
    public BaseResult(boolean success) {
        this.code = BaseResultCodeEnum.SYSTEM_ERROR.getCode();
        this.message = BaseResultCodeEnum.SYSTEM_ERROR.getMessage();
        this.ncode = BaseResultCodeEnum.SYSTEM_ERROR.getNCode();
        if(success) {
            this.success = true;
            this.code = BaseResultCodeEnum.SUCCESS.getCode();
            this.message = BaseResultCodeEnum.SUCCESS.getMessage();
            this.ncode = BaseResultCodeEnum.SUCCESS.getNCode();
        }

    }

    public void markResult(boolean success, EnumBase resultCode) {
        this.markResult(Boolean.valueOf(success), resultCode, null, null);
    }

    public void markResult(boolean success, EnumBase resultCode, Integer NCode) {
        this.markResult(Boolean.valueOf(success), resultCode, null, NCode);
    }

    public void markResult(boolean success, EnumBase resultCode, String message) {
        this.markResult(Boolean.valueOf(success), resultCode, message, null);
    }

    public void markResult(EnumBase resultCode) {
        this.markResult(null, resultCode, null, null);
    }

    public void markResult(EnumBase resultCode, Integer NCode) {
        this.markResult(null, resultCode, null, NCode);
    }

    public void markResult(EnumBase resultCode, String message) {
        this.markResult(null, resultCode, message, null);
    }

    public void markResult(Boolean success, EnumBase resultCode, String message, Integer NCode) {
        if(null != success) {
            this.success = success.booleanValue();
        }

        if(null != resultCode) {
            this.code = resultCode.name();
            this.message = resultCode.message();
            if(resultCode instanceof EnumBaseResult) {
                this.ncode = ((EnumBaseResult)resultCode).getNCode();
            }
        }

        if(!StringUtils.isEmpty(message)) {
            this.message = message;
        }

        if(null != NCode) {
            this.ncode = NCode;
        }

    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "[success:" + this.success + ",code:" + this.code + ",ncode" + this.ncode + ",message:" + this.message + "]";
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Integer getNcode() {
        return ncode;
    }
    public void setNcode(Integer ncode) {
        this.ncode = ncode;
    }
}