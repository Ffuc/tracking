package com.ruixun.tracking.common.check;

import lombok.Data;

/**
 * Program: tracking
 * <p>
 * Description:
 *
 * @Date: 2020-03-30 00:52
 **/
@Data
public class PageCheck {

    private Integer total;
    private Integer size;
    private Integer current;
    private Integer pages;

    public PageCheck(int count, int page, int size) {
        this.pages = count % size == 0 ? count / size : count / size + 1;
        this.current = page;
        this.total = count;
        this.size = size;
    }
    private PageCheck(){

    }
}
