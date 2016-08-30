package com.example.basetest.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.basetest.bean.User;
import com.example.basetest.util.LogUtil;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * 创建数据库
 * Created by Administrator on 2016/8/26.
 */
public class DBDataHelper extends OrmLiteSqliteOpenHelper {
    private static final String TAG = "DBDataHelper";
    public static String DATABASE_NAME = "database.db";
    private static final int DATABASE_VERSION = 6;


    public DBDataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        createAllTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

        LogUtil.d(TAG, "oldVersion:" + oldVersion);
        LogUtil.d(TAG, "newVersion:" + newVersion);

        dropAllTable();
        createAllTable();

    }

    private void dropAllTable() {
        try {
             /*用户数据*/
            TableUtils.dropTable(connectionSource, User.class, true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createAllTable() {
        try {
              /*用户数据*/
            TableUtils.createTableIfNotExists(connectionSource, User.class);

        } catch (SQLException e) {
            LogUtil.d(TAG, "创建数据库成功");
            e.printStackTrace();
        }
    }

    public void clearTable(Signal signal) throws SQLException {
        switch (signal) {
            case USER:
                TableUtils.clearTable(connectionSource, User.class);
                break;

            default:
                break;
        }
    }

    public void clearTables() throws SQLException {
          /*用户数据*/
        TableUtils.clearTable(connectionSource, User.class);
    }

    public <T> Dao<T, Object> getMyDao(Class<?> c) {
        try {
            return (Dao<T, Object>) getDao(c);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getDBName(Context context) {
        return DATABASE_NAME;
    }

    public void close() {
        super.close();
    }
}
