package com.qdd.basedb.db;

import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.qdd.basedb.annotation.DBField;
import com.qdd.basedb.annotation.DBTable;

import java.lang.reflect.Field;

/**
 * Created by qdd
 * date 2018/3/21.
 * description:
 * modify by:
 */

public class BaseDao<T> implements IBaseDao {
    private SQLiteDatabase sqLiteDatabase;
    private Class<T> tClz;
    private boolean initFirst = false;
    private String tabName;

    protected boolean init(SQLiteDatabase database, Class<T> tClz) {
        this.sqLiteDatabase = database;
        this.tClz = tClz;
        if (!initFirst) {
            if (tClz.getAnnotation(DBTable.class) == null || TextUtils.isEmpty(tClz.getAnnotation(DBTable.class).value())) {
                tabName = tClz.getSimpleName();
            } else {
                tabName = tClz.getAnnotation(DBTable.class).value();
            }
            String sql = getCreateTableSql();
            sqLiteDatabase.execSQL(sql);
            initFirst = true;
        }
        return initFirst;
    }

    private String getCreateTableSql() {
        //create tab if not exists tab_name(_id INTEGER, name TEXT, age INTEGER)
        StringBuffer sb = new StringBuffer();
        sb.append("create tab if not exists ");
        sb.append(tabName);
        sb.append("(");

        Field[] fields = tClz.getFields();
        for (Field field : fields) {
            Class type = field.getType();
            String fieldName;
            if (field.getAnnotation(DBField.class) == null || TextUtils.isEmpty(field.getAnnotation(DBField.class).value())) {
                fieldName = field.getName();
            } else {
                fieldName = field.getAnnotation(DBField.class).value();
            }
            if (type == String.class) {
                sb.append(fieldName + " TEXT,");
            } else if (type == Integer.class) {
                sb.append(fieldName + " INTEGER,");
            } else if (type == Long.class) {
                sb.append(fieldName + " BIGINT,");
            } else if (type == Double.class) {
                sb.append(fieldName + " DOUBLE,");
            } else if (type == byte[].class) {
                sb.append(fieldName + " BLOB,");
            }
        }
        if (sb.charAt(sb.length() - 1) == ',') {
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public long insert(Object entity) {
        return 0;
    }
}
