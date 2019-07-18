package com.ewt360.asr.facade.base.result;

import lombok.Data;

@Data
public class QueryResult<T> extends BaseResult {
    private static final long serialVersionUID = 4114715063829909323L;
    private T resultObject;

    //private String accessToken;
    //private long consumeTime;

    public QueryResult(){}
    public QueryResult(boolean success) {
        super(success);
    }

    public void markResult(boolean success, String code, String message, T reusltObj) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.setResultObject(reusltObj);
    }

}
