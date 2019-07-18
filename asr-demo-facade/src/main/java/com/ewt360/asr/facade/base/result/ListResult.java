package com.ewt360.asr.facade.base.result;

import java.util.List;

public class ListResult<T> extends BaseResult {
    private static final long serialVersionUID = -9131807024640695592L;
    private List<T> resultList;

    public ListResult(boolean success) {
        super(success);
    }

    public List<T> getResultList() {
        return this.resultList;
    }

    public void setResultList(List<T> resultList) {
        this.resultList = resultList;
    }
}
