package com.daypaytechnologies.digichitfund.infrastructure.pagination;

import java.io.Serializable;
import java.util.List;

public class Page<E> implements Serializable {

    private final int totalRecord;
    private final List<E> data;
    private final int currentPage; //size
    private final int recordPerPage; //limit
    private final int totalPages;



    public Page(final List<E> data, final int totalRecord, final int currentPage, final int recordPerPage, final int totalPages) {
        this.data = data;
        this.totalRecord = totalRecord;
        this.currentPage = currentPage;
        this.recordPerPage = recordPerPage;
        this.totalPages = totalPages;
    }

    public int getTotalRecord() {
        return this.totalRecord;
    }

    public List<E> getData() {
        return this.data;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getRecordPerPage() {
        return recordPerPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

}
