package com.mtk.management;

import com.mtk.annotation.Column;
import com.mtk.annotation.Id;
import com.mtk.annotation.Record;
import com.mtk.exception.UnsupportedActionException;
import com.mtk.query.Query;
import com.mtk.query.QueryType;
import com.mtk.query.builder.QueryBuilder;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

public class RecordMapper {

    public static <T> List<T> map(ResultSet rs, Class<T> clazz)
            throws SQLException, InstantiationException, IllegalAccessException,
            UnsupportedActionException, NoSuchMethodException, InvocationTargetException {
        List<T> data = new ArrayList<>();
        if (!clazz.isAnnotationPresent(Record.class)) {
            throw new UnsupportedActionException("Must be Record object");
        }
        ResultSetMetaData rsmd = rs.getMetaData();
        Field[] fields = clazz.getDeclaredFields();
        while (rs.next()) {
            T bean = clazz.getDeclaredConstructor().newInstance();
            for (int _iterator = 0; _iterator < rsmd.getColumnCount(); _iterator++) {
                String columnName = rsmd.getColumnName(_iterator + 1);
                Object columnValue = rs.getObject(_iterator + 1);
                for (Field field : fields) {
                    if (!field.isAnnotationPresent(Column.class)) {
                        continue;
                    }
                    field.setAccessible(true);
                    Column column = field.getAnnotation(Column.class);
                    if (column.name().equalsIgnoreCase(columnName) && columnValue != null) {
                        field.set(bean, columnValue);
                    }
                }
            }
            data.add(bean);
        }
        return data;
    }

    public static <T> QueryBuilder toInsertQuery(T object) {
        try {
            Class<T> clazz = (Class<T>) object.getClass();
            if (!clazz.isAnnotationPresent(Record.class)) {
                throw new RuntimeException("No valid record.");
            }
            String tableName = clazz.getAnnotation(Record.class).table();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
            }
            Map<Object, Object> values = new HashMap<>();
            for (Field field : fields) {
                if (!field.isAnnotationPresent(Column.class)) {
                    continue;
                }
                field.setAccessible(true);
                Column column = field.getAnnotation(Column.class);
                values.put(column.name(), field.get(object));
            }
            return QueryBuilder.insert(tableName).values(values);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Can't process query on record.");
        }
    }

    public static <T> QueryBuilder toUpdateQuery(T object) {
        try {
            Class<?> clazz = object.getClass();
            if (!clazz.isAnnotationPresent(Record.class)) {
                throw new RuntimeException("No valid record.");
            }
            Field[] fields = clazz.getDeclaredFields();
            Field idField = null;
            Map<Object, Object> setters = new HashMap<>();
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(Id.class)) {
                    idField = field;
                    continue;
                }

                if (!field.isAnnotationPresent(Column.class)) {
                    continue;
                }
                field.setAccessible(true);
                Column column = field.getAnnotation(Column.class);
                setters.put(column.name(), field.get(object));
            }
            if (idField == null) {
                throw new RuntimeException("haha");
            }
            String tableName = clazz.getAnnotation(Record.class).table();
            return QueryBuilder.update(tableName)
                    .setters(setters)
                    .where(idField.getAnnotation(Id.class).name()
                            + " = " + idField.get(object));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> QueryBuilder toDeleteQuery(T object) {
        try {
            Class<?> clazz = object.getClass();
            if (!clazz.isAnnotationPresent(Record.class)) {
                throw new RuntimeException("No valid record.");
            }
            Field[] fields = clazz.getDeclaredFields();
            Field idField = null;
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(Id.class)) {
                    idField = field;
                }
            }
            if (idField == null) {
                throw new RuntimeException("haha");
            }
            String tableName = clazz.getAnnotation(Record.class).table();
            return QueryBuilder.delete(tableName)
                    .where(idField.getAnnotation(Id.class).name()
                            + " = " + idField.get(object));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
