package com.mtk.management;

import com.mtk.connect.RecordConnection;
import com.mtk.exception.UnsupportedActionException;
import com.mtk.query.QueryType;
import com.mtk.query.builder.QueryBuilder;
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

    private final List<Object> records = new ArrayList<>();

    public RecordManager(RecordConnection connection) {
        this.recordConnection = connection;
    }

    public QueryBuilder createQuery(QueryType type) {
        return createQuery(type, null);
    }

    public QueryBuilder createQuery(QueryType type, Object record) {
        switch (type) {
            case INSERT:
                return RecordMapper.toInsertQuery(record);
            case UPDATE:
                return RecordMapper.toUpdateQuery(record);
            case DELETE:
                return RecordMapper.toDeleteQuery(record);
        }
        return QueryBuilder.newQuery(type);
    }

    public <T> List<T> executeQuery(QueryBuilder query, Class<T> clazz)
            throws SQLException, InstantiationException, IllegalAccessException, UnsupportedActionException, InvocationTargetException, NoSuchMethodException {
        ResultSet resultSet = (ResultSet) recordConnection.executeQuery(query.build());
        return RecordMapper.map(resultSet, clazz);
    }

    public void executeUpdate(QueryBuilder query) {
        recordConnection.executeUpdate(query.build());
    }

    public void insert(Object record) {
        QueryBuilder query = createQuery(QueryType.INSERT, record);
        executeUpdate(query);
    }

    public void update(Object record) {
        QueryBuilder query = createQuery(QueryType.UPDATE, record);
        executeUpdate(query);
    }

    public void delete(Object record) {
        QueryBuilder query = createQuery(QueryType.DELETE, record);
        executeUpdate(query);
    }
}
