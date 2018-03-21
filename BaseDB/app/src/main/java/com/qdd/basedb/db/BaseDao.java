package com.qdd.basedb.db;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by qdd
 * date 2018/3/21.
 * description:
 * modify by:
 */

public class BaseDao<T> implements IBaseDao {
    private SQLiteDatabase sqLiteDatabase;




    @Override
    public long insert(Object entity) {
        return 0;
    }
}
