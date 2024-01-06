package com.mtk.management;

import com.mtk.connect.RecordConnection;
import com.mtk.exception.UnsupportedActionException;
import com.mtk.query.Query;
import com.mtk.query.QueryType;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RecordManager {

    private RecordConnection recordConnection;

    private final List<Query> queries = new ArrayList<>();

    private final List<Object> records = new ArrayList<>();

    public RecordManager(RecordConnection connection) {
        this.recordConnection = connection;
    }

    public Query createQuery(QueryType type) {
        Query query = Query.builder(type).build();
        queries.add(query);
        return query;
    }

    public <T> List<T> executeQuery(Query query, Class<T> clazz)
            throws SQLException, InstantiationException, IllegalAccessException, UnsupportedActionException, InvocationTargetException, NoSuchMethodException {
        ResultSet resultSet = (ResultSet) recordConnection.execute(query);
        return RecordMapper.map(resultSet, clazz);
    }

    public void executeUpdate(Query query) {
        recordConnection.execute(query);
    }

    public void insert(Object record) {
        Query newQuery = RecordMapper.toQuery(QueryType.INSERT, record);
        executeUpdate(newQuery);
        records.add(record);
    }

    public void update(Object record) {
        Query newQuery = createQuery(QueryType.UPDATE);
        executeUpdate(newQuery);
    }

    public void delete(Object record) {
        Query newQuery = createQuery(QueryType.DELETE);
        executeUpdate(newQuery);
    }
}
