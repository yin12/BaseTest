package com.example.basetest.db;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;

import java.util.List;
import java.util.Map;

public interface CommonDao<T> {
	Dao<T, Object> getDao();
	QueryBuilder<T,Object> getQueryBuilder();
	UpdateBuilder<T,Object> getUpdateBuilder();

	boolean insertList(List<T> list);

	boolean insertItem(T a);

	boolean deleteItem(T a);

	boolean deleteList(List<T> list);

	boolean updateList(List<T> list);

	boolean updateItem(T a);

	List<T> queryForAll();

	boolean createOrUpdate(T a);

	boolean createOrUpdate(List<T> list);

	List<T> queryForMatchingArgs(T matchObj);

	List<T> queryForFieldValuesArgs(Map<String, Object> fieldValues);
	List<T> queryForFieldValuesArgsWithOr(Map<String, Object> fieldValues);
	List<T> queryForFieldValuesArgsWithISNoNullOr(Map<Integer, String> fieldValues);
	List<T> queryForFieldValuesArgsWithOrderBy(Map<String, Object> fieldValues, Map<String, Boolean> orderBy);
	List<T> queryForFieldValuesArgsWithLike(Map<String, Object> fieldValues);
	List<T> queryForFieldValuesArgsWithLikeOrderBy(Map<String, Object> fieldValues, Map<String, Boolean> orderBy);
	public List<T> queryForFieldValuesArgsAndNot(Map<String, Object> fieldValues, Map<String, Object> notfieldValues);

	T queryForId(String id);
	T queryFirstWithOrderBy(Map<String, Boolean> orderBy);

	void clearTable(Signal signal);
	
	void deleteForFieldValuesArgs(Map<String, Object> fieldValues);

	List<String[]> queryRaw(String statement, String... arguments);
	int updateRaw(String statement, String... arguments);

    void deleteById(String id);
	List<T> queryForFieldValuesArgsAndWithOrderBy(Map<String, Object> fieldValues, Map<String, Boolean> orderBy);
}
