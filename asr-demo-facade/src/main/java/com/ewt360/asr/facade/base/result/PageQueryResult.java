package com.ewt360.asr.facade.base.result;

import lombok.Data;

import java.util.List;

@Data
public class PageQueryResult<T> extends BaseResult {
    private static final long serialVersionUID = 704571612892315998L;
    private int totalItems = 0;
    private int totalPages = 0;
    private int currentPage = 1;
    private int itemsPerPage = 20;
    private List<T> resultList;

    public PageQueryResult(boolean success) {
        super(success);
    }

//    public static <T> PageQueryResult<T> resResult(List<T> resultList) {
//        PageQueryResult<T> result = new PageQueryResult(true);
//        result.setResultList(resultList);
//        return result;
//    }

    public int getTotalPages() {
        return this.totalItems > 0 && this.itemsPerPage > 0?(this.totalItems % this.itemsPerPage > 0?this.totalItems / this.itemsPerPage + 1:this.totalItems / this.itemsPerPage):this.totalPages;
    }

}
