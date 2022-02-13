package com.treeleaf.task.response;

import lombok.Data;

import java.util.List;

/**
 * Created by User on 2/12/2022.
 */
@Data
public class PageResponse<T> {

    private int pageNo;

    private int pageSize;

    private long totalElement;

    private int totalPage;

    private List<T> data;

    public PageResponse(int page_no, int page_size, long total_element, int total_page, List<T> data) {
        this.pageNo = page_no;
        this.pageSize = page_size;
        this.totalElement = total_element;
        this.totalPage = total_page;
        this.data = data;
    }
}
