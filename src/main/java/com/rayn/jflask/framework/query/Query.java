package com.rayn.jflask.framework.query;

import java.util.List;

/**
 * Query
 * Created by Raynxxx on 2016/07/19.
 */
public interface Query<T> {

    <PK> T find(PK primaryKey);

    boolean exist(String conditions);
}
