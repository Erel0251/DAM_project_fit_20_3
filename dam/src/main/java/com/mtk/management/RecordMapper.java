package com.mtk.management;

import com.mtk.annotation.Column;
import com.mtk.annotation.Id;
import com.mtk.annotation.Record;
import com.mtk.exception.UnsupportedActionException;
import com.mtk.query.Query;
import com.mtk.query.QueryType;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public static <T> Query toQuery(QueryType type, T object) {
        try {
            Class<?> clazz = object.getClass();
            Field[] fields = clazz.getDeclaredFields();
            Object id = null;
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(Id.class)) {
                    id = field.get(object);
                }
            }
            if (id == null) {
                throw new RuntimeException("haha");
            }
            switch (type) {
                case SELECT:

                    break;
                case INSERT:
                    break;
                case UPDATE:
                    break;
                case DELETE:
                    break;
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        throw new RuntimeException("Can't process query on record.");
    }
}
