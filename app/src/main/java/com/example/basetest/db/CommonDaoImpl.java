package com.example.basetest.db;

import android.text.TextUtils;

import com.example.basetest.util.ListUtils;
import com.example.basetest.util.LogUtil;
import com.example.basetest.util.Utils;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;

public class CommonDaoImpl<T> implements CommonDao<T> {

    private Dao<T, Object> dao;
    private QueryBuilder<T, Object> queryBuilder;
    private UpdateBuilder<T, Object> updateBuilder;

    private DBDataHelper dataHelper;

    public CommonDaoImpl(DBDataHelper dataHelper, Class<?> clazz) {
        this.dataHelper = dataHelper;
        dao = dataHelper.getMyDao(clazz);
        queryBuilder = dao.queryBuilder();
        updateBuilder = dao.updateBuilder();
    }

    @Override
    public boolean insertList(final List<T> list) {
        if (Utils.collectionIsEmpty(list)) {
            return false;
        }
        try {
            dao.callBatchTasks(new Callable<Void>() {

                @Override
                public Void call() throws Exception {
                    for (T item : list) {
                        insertItem(item);
                    }
                    return null;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean insertItem(T a) {
        if (a == null) {
            return false;
        }
        try {
            dao.createOrUpdate(a);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateList(final List<T> list) {
        if (Utils.collectionIsEmpty(list)) {
            return false;
        }
        try {
            dao.callBatchTasks(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    long current = System.currentTimeMillis();
                    for (T item : list) {
                        dao.update(item);
                    }
                    LogUtil.d("updateList----list--------=" + list.size());
                    LogUtil.d("updateList----time--------=" + (System.currentTimeMillis() - current));
                    return null;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<T> queryForFieldValuesArgsWithISNoNullOr(Map<Integer, String> fieldValues) {
        List<T> list = new ArrayList<T>();
        if (fieldValues == null) {
            return list;
        }
        try {
            QueryBuilder<T, Object> queryBuilder;
            queryBuilder = dao.queryBuilder();
            Iterator<Entry<Integer, String>> iterator = fieldValues.entrySet().iterator();
            Where<T, Object> where = null;
            while (iterator.hasNext()) {
                Entry<Integer, String> entry = iterator.next();
                if (where == null) {
                    where = queryBuilder.where().isNotNull(entry.getValue());
                } else {
                    where.or().isNotNull(entry.getValue());
                }
            }
            if (where != null) {
                list = dao.query(queryBuilder.prepare());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean updateItem(T t) {
        if (t == null) {
            return false;
        }
        try {
            dao.update(t);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<T> queryForAll() {
        List<T> list = new ArrayList<T>();
        try {
            list = dao.queryForAll();
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean deleteItem(T a) {
        if (a == null) {
            return false;
        }
        try {
            return dao.delete(a) == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean deleteList(List<T> list) {
        if (Utils.collectionIsEmpty(list)) {
            return false;
        }
        try {
            return dao.delete(list) == list.size();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean createOrUpdate(T a) {
        if (a == null) {
            return false;
        }
        try {
            dao.createOrUpdate(a);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean createOrUpdate(final List<T> list) {
        if (Utils.collectionIsEmpty(list)) {
            return false;
        }
        try {
            dao.callBatchTasks(new Callable<Void>() {

                @Override
                public Void call() throws Exception {
                    for (T item : list) {
                        createOrUpdate(item);
                    }
                    return null;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<T> queryForMatchingArgs(T matchObj) {
        List<T> list = new ArrayList<T>();
        try {
            list = dao.queryForMatchingArgs(matchObj);
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<T> queryForFieldValuesArgs(Map<String, Object> fieldValues) {
        List<T> list = new ArrayList<T>();
        try {
            list = dao.queryForFieldValuesArgs(fieldValues);
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public T queryForId(String id) {
        if (TextUtils.isEmpty(id)) {
            return null;
        }
        try {
            return dao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public T queryFirstWithOrderBy(Map<String, Boolean> orderBy) {
        List<T> list = new ArrayList<T>();
        try {
            QueryBuilder<T, Object> queryBuilder;
            queryBuilder = dao.queryBuilder();

            if (orderBy != null) {
                Iterator<Entry<String, Boolean>> orderSQl = orderBy.entrySet().iterator();
                while (orderSQl.hasNext()) {
                    Entry<String, Boolean> entry = orderSQl.next();
                    queryBuilder.orderBy(entry.getKey(), entry.getValue());
                }
            }
            list = dao.query(queryBuilder.prepare());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ListUtils.isEmpty(list) ? null : list.get(0);
    }

    @Override
    public void clearTable(Signal signal) {
        try {
            dataHelper.clearTable(signal);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<T> queryForFieldValuesArgsWithOr(Map<String, Object> fieldValues) {
        List<T> list = new ArrayList<T>();
        if (fieldValues == null) {
            return list;
        }
        try {
            QueryBuilder<T, Object> queryBuilder;
            queryBuilder = dao.queryBuilder();
            Iterator<Entry<String, Object>> iterator = fieldValues.entrySet().iterator();
            Where<T, Object> where = null;
            while (iterator.hasNext()) {
                Entry<String, Object> entry = iterator.next();
                if (where == null) {
                    where = queryBuilder.where().eq(entry.getKey(), entry.getValue());
                } else {
                    where.or().eq(entry.getKey(), entry.getValue());
                }
            }
            if (where != null) {
                list = dao.query(queryBuilder.prepare());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<T> queryForFieldValuesArgsWithOrderBy(
            Map<String, Object> fieldValues, Map<String, Boolean> orderBy) {
        List<T> list = new ArrayList<T>();
        if (fieldValues == null) {
            return list;
        }
        try {
            QueryBuilder<T, Object> queryBuilder;
            queryBuilder = dao.queryBuilder();

            Iterator<Entry<String, Object>> iterator = fieldValues.entrySet().iterator();
            Where<T, Object> where = null;
            while (iterator.hasNext()) {
                Entry<String, Object> entry = iterator.next();
                if (where == null) {
                    where = queryBuilder.where().eq(entry.getKey(), entry.getValue());
                } else {
                    where.or().eq(entry.getKey(), entry.getValue());
                }
            }
            if (orderBy != null) {
                Iterator<Entry<String, Boolean>> orderSQl = orderBy.entrySet().iterator();
                while (orderSQl.hasNext()) {
                    Entry<String, Boolean> entry = orderSQl.next();
                    queryBuilder.orderBy(entry.getKey(), entry.getValue());
                }
            }
            if (where != null) {
                list = dao.query(queryBuilder.prepare());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void deleteForFieldValuesArgs(Map<String, Object> fieldValues) {
        if (fieldValues == null) {
            return;
        }
        try {
            DeleteBuilder<T, Object> deleteBuilder = dao.deleteBuilder();
            Iterator<Entry<String, Object>> iterator = fieldValues.entrySet().iterator();
            Where<T, Object> where = null;
            while (iterator.hasNext()) {
                Entry<String, Object> entry = iterator.next();
                if (where == null) {
                    where = deleteBuilder.where().eq(entry.getKey(), entry.getValue());
                } else {
                    where = where.and().eq(entry.getKey(), entry.getValue());
                }
            }
            if (where != null) {
                deleteBuilder.delete();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<T> queryForFieldValuesArgsWithLike(
            Map<String, Object> fieldValues) {
        List<T> list = new ArrayList<T>();
        if (fieldValues == null) {
            return list;
        }
        try {
            QueryBuilder<T, Object> queryBuilder;
            queryBuilder = dao.queryBuilder();

            Iterator<Entry<String, Object>> iterator = fieldValues.entrySet().iterator();
            Where<T, Object> where = null;
            while (iterator.hasNext()) {
                Entry<String, Object> entry = iterator.next();
                if (where == null) {
                    where = queryBuilder.where().like(entry.getKey(), entry.getValue() + "%");
                } else {
                    where.and().like(entry.getKey(), entry.getValue() + "%");
                }
            }
            if (where != null) {
                list = dao.query(queryBuilder.prepare());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<T> queryForFieldValuesArgsWithLikeOrderBy(Map<String, Object> fieldValues, Map<String, Boolean>
            orderBy) {
        List<T> list = new ArrayList<T>();
        if (fieldValues == null) {
            return list;
        }
        try {
            QueryBuilder<T, Object> queryBuilder;
            queryBuilder = dao.queryBuilder();

            Iterator<Entry<String, Object>> iterator = fieldValues.entrySet().iterator();
            Where<T, Object> where = null;
            while (iterator.hasNext()) {
                Entry<String, Object> entry = iterator.next();
                if (where == null) {
                    where = queryBuilder.where().like(entry.getKey(), entry.getValue() + "%");
                } else {
                    where.and().like(entry.getKey(), entry.getValue() + "%");
                }
            }
            if (orderBy != null) {
                Iterator<Entry<String, Boolean>> orderSQl = orderBy.entrySet().iterator();
                while (orderSQl.hasNext()) {
                    Entry<String, Boolean> entry = orderSQl.next();
                    queryBuilder.orderBy(entry.getKey(), entry.getValue());
                }
            }
            if (where != null) {
                list = dao.query(queryBuilder.prepare());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<T> queryForFieldValuesArgsAndNot(Map<String, Object> fieldValues, Map<String, Object> notfieldValues) {
        List<T> list = new ArrayList<T>();
        if (fieldValues == null) {
            return list;
        }
        try {
            QueryBuilder<T, Object> queryBuilder;
            queryBuilder = dao.queryBuilder();

            Iterator<Entry<String, Object>> iterator = fieldValues.entrySet().iterator();
            Where<T, Object> where = null;
            while (iterator.hasNext()) {
                Entry<String, Object> entry = iterator.next();
                if (where == null) {
                    where = queryBuilder.where().eq(entry.getKey(), entry.getValue());
                } else {
                    where.and().eq(entry.getKey(), entry.getValue());
                }
            }
            if (notfieldValues != null) {
                Iterator<Entry<String, Object>> orderSQl = notfieldValues.entrySet().iterator();
                while (orderSQl.hasNext()) {
                    Entry<String, Object> entry = orderSQl.next();
                    where.and().not().eq(entry.getKey(), entry.getValue());
                }
            }
            if (where != null) {
                list = dao.query(queryBuilder.prepare());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            LogUtil.d("queryForFieldValuesArgsAndNot=" + e.getMessage());
        }
        return list;
    }


    @Override
    public Dao<T, Object> getDao() {
        return dao;
    }

    @Override
    public QueryBuilder<T, Object> getQueryBuilder() {
        queryBuilder.reset();
        return queryBuilder;
    }

    @Override
    public UpdateBuilder<T, Object> getUpdateBuilder() {
        updateBuilder.reset();
        return updateBuilder;
    }

    @Override
    public List<String[]> queryRaw(String statement, String... arguments) {
        List<String[]> list = new ArrayList<String[]>();
        try {
            GenericRawResults<String[]> rawResults = dao.queryRaw(statement);
            for (Iterator<String[]> iterator = rawResults.iterator(); iterator.hasNext(); ) {
                String[] result = iterator.next();
                list.add(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int updateRaw(String statement, String... arguments) {
        try {
            return dao.updateRaw(statement, arguments);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public void deleteById(String id) {
        DeleteBuilder deleteBuilder = dao.deleteBuilder();
        try {
            deleteBuilder.where().eq("id", id);
            deleteBuilder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<T> queryForFieldValuesArgsAndWithOrderBy(Map<String, Object> fieldValues, Map<String, Boolean>
            orderBy) {
        List<T> list = new ArrayList<T>();
        if (fieldValues == null) {
            return list;
        }
        try {
            QueryBuilder<T, Object> queryBuilder;
            queryBuilder = dao.queryBuilder();

            Iterator<Entry<String, Object>> iterator = fieldValues.entrySet().iterator();
            Where<T, Object> where = null;
            while (iterator.hasNext()) {
                Entry<String, Object> entry = iterator.next();
                if (where == null) {
                    where = queryBuilder.where().eq(entry.getKey(), entry.getValue());
                } else {
                    where.and().eq(entry.getKey(), entry.getValue());
                }
            }
            if (orderBy != null) {
                Iterator<Entry<String, Boolean>> orderSQl = orderBy.entrySet().iterator();
                while (orderSQl.hasNext()) {
                    Entry<String, Boolean> entry = orderSQl.next();
                    queryBuilder.orderBy(entry.getKey(), entry.getValue());
                }
            }
            if (where != null) {
                list = dao.query(queryBuilder.prepare());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public DBDataHelper getDataHelper() {
        return dataHelper;
    }

    public void setDataHelper(DBDataHelper dataHelper) {
        this.dataHelper = dataHelper;
    }


}