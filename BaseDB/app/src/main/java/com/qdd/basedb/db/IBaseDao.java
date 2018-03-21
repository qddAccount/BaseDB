package com.qdd.basedb.db;

/**
 * Created by qdd
 * date 2018/3/21.
 * description:
 * modify by:
 */

public interface IBaseDao<T> {
    long insert(T entity);
}
