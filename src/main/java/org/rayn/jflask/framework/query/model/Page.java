package org.rayn.jflask.framework.query.model;

import java.util.List;

/**
 * Page
 * Created by Raynxxx on 2016/06/05.
 */
public interface Page<T> {

    long getTotalElements();

    int getNumberOfElements();

    int getTotalPages();

    int getPageNumber();

    List<T> getContent();

    boolean hasNext();

    boolean hasPrevious();

    boolean	isFirst();

    boolean	isLast();

    PageRequest nextPageable();

    PageRequest previousPageable();
}
