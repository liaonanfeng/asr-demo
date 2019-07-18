package com.ewt360.asr.common.config;

import com.ewt360.asr.facade.base.enumeration.BaseResultCodeEnum;
import com.ewt360.asr.facade.base.result.ListResult;
import com.ewt360.asr.facade.base.result.PageQueryResult;
import com.ewt360.asr.facade.base.result.QueryResult;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * Description:
 */
public class QueryResponseMaker {
//  public final static boolean FAILED = false;
//  public final static boolean SUCCESS = true;

    public static QueryResult build(boolean isSuccess) {
        return new QueryResult(isSuccess);
    }

    public static <T> QueryResult<T> build(boolean isSuccess, T data) {
        QueryResult<T> result = build(isSuccess);
        result.setResultObject(data);
        return result;
    }
    /*public static <T> QueryResult<T> build(boolean isSuccess, T data,String token) {
        QueryResult<T> result = build(isSuccess);
        result.setResultObject(data);
        result.setAccessToken(token);
        return result;
    }*/

    public static QueryResult build(boolean isSuccess, BaseResultCodeEnum codeEnum) {

        QueryResult result = build(isSuccess);
        result.setCode(codeEnum.getCode());
        result.setMessage(codeEnum.getMessage());
        return result;
    }

    public static <T> QueryResult<T> build(boolean isSuccess, BaseResultCodeEnum codeEnum, T data) {
        QueryResult result = build(isSuccess, codeEnum);
        result.setResultObject(data);
        return result;
    }
    /*public static <T> QueryResult<T> build(boolean isSuccess, BaseResultCodeEnum codeEnum, T data,String token) {
        QueryResult result = build(isSuccess, codeEnum);
        result.setResultObject(data);
        result.setAccessToken(token);
        return result;
    }*/

    public static <T> ListResult<T> build(boolean isSuccess, List<T> data) {
        ListResult result = new ListResult(isSuccess);
        result.setResultList(data);
        return result;
    }

    public static <T> ListResult<T> build(boolean isSuccess, BaseResultCodeEnum codeEnum, List<T> data) {
        ListResult result = build(isSuccess, data);
        result.setCode(codeEnum.getCode());
        result.setMessage(codeEnum.getMessage());
        return result;
    }

    public static <T> PageQueryResult<T> build(boolean isSuccess, PageInfo info) {
        PageQueryResult<T> result = new PageQueryResult<T>(isSuccess);
        if (null != info) {
            result.setResultList(info.getList());
            result.setCurrentPage(info.getPageNum());
            result.setItemsPerPage(info.getPageSize());
            result.setTotalItems((int) info.getTotal());
            result.setTotalPages(info.getPages());
        }
        return result;
    }

}
