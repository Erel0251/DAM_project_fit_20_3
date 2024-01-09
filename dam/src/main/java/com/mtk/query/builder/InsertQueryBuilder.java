package com.mtk.query.builder;

import java.util.Map;

public class InsertQueryBuilder extends QueryBuilder {

    private Map<Object, Object> values;

    public InsertQueryBuilder() {

    }

    public InsertQueryBuilder(String table) {
        this.table = table;
    }

    public InsertQueryBuilder value(Object key, Object value) {
        values.put(key, value);
        return this;
    }

    public InsertQueryBuilder values(Map<Object, Object> values) {
        this.values = values;
        return this;
    }

    @Override
    public String build() {
        StringBuilder builder = new StringBuilder();
        builder.append("INSERT INTO ")
                .append(this.table)
                .append(" (");
        for (Object column : values.keySet()) {
            builder.append(column).append(",");
        }
        builder.append(") VALUES (");
        for (Object value : values.values()) {
            builder.append(value).append(", ");
        }
        builder.append(") ");
        if (this.wheres.size() > 0) {
            builder.append(" WHERE ");
            for (String where : this.wheres) {
                builder.append(where);
            }
        }
        return builder.toString();
    }
}
