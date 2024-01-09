package com.mtk.management;

import com.mtk.connect.RecordConnection;
import com.mtk.exception.UnsupportedActionException;
import com.mtk.query.Query;
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

    private final List<Query> queries = new ArrayList<>();

    private final List<Object> records = new ArrayList<>();

    public RecordManager(RecordConnection connection) {
        this.recordConnection = connection;
    }

    public QueryBuilder createQuery(QueryType type, Object record) {
        return null;
    }

    public <T> List<T> executeQuery(QueryBuilder query, Class<T> clazz)
            throws SQLException, InstantiationException, IllegalAccessException, UnsupportedActionException, InvocationTargetException, NoSuchMethodException {
        ResultSet resultSet = (ResultSet) recordConnection.executeQuery(query.toString());
        return RecordMapper.map(resultSet, clazz);
    }

    public void executeUpdate(QueryBuilder query) {
        recordConnection.executeUpdate(query.toString());
    }

    public void insert(Object record) {
        QueryBuilder query = RecordMapper.toInsertQuery(record);
        recordConnection.executeUpdate(query.build());
    }

    public void update(Object record) {
        QueryBuilder query = RecordMapper.toUpdateQuery(record);
        recordConnection.executeUpdate(query.build());
    }

    public void delete(Object record) {
        QueryBuilder query = RecordMapper.toDeleteQuery(record);
        recordConnection.executeUpdate(query.build());
    }
}
