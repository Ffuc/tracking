package com.ruixun.tracking.common.check;

/**
 * Program: tracking
 * <p>
 * Description:
 *
 * @Date: 2020-03-30 00:52
 **/
public class PageCheck {

    private Integer total;
    private Integer size;
    private Integer current;
    private Integer pages;


    public void calculate(int count, int total, int page, int size) {
        this.pages = count % size == 0 ? count / size : count / size + 1;
        this.current = page;
        this.total = count;
        this.size = size;
    }

}
