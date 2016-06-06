package org.rayn.jflask.framework.query.model;

import java.io.Serializable;

/**
 * PageRequest
 * Created by Raynxxx on 2016/06/06.
 */
public class PageRequest implements Serializable {

    private final int page;
    private final int size;
    private final Sort sort;

    PageRequest(int page, int size) {
        this.page = page;
        this.size = size;
        this.sort = null;
    }

    PageRequest(int page, int size, Sort sort) {
        this.page = page;
        this.size = size;
        this.sort = sort;
    }

    public int getPageSize() {
        return size;
    }

    public int getPageNumber() {
        return page;
    }

    public int getOffset() {
        return page * size;
    }

    public boolean hasPrevious() {
        return page > 0;
    }

    public PageRequest first() {
        return null;
    }
}
