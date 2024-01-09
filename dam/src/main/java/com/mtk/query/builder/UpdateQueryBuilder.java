package com.mtk.query.builder;

import java.util.List;
import java.util.Map;

public class UpdateQueryBuilder extends QueryBuilder {

    private String table;

    private Map<Object, Object> setters;

    public UpdateQueryBuilder(String table) {
        this.table = table;
    }

    public UpdateQueryBuilder setter(Object key, Object value) {
        setters.put(key, value);
        return this;
    }

    public UpdateQueryBuilder setters(Map<Object, Object> setters) {
        this.setters = setters;
        return this;
    }

    @Override
    public String build() {
        StringBuilder builder = new StringBuilder();
        builder.append("UPDATE ")
                .append(this.table);
        builder.append(" SET ");
        for (Map.Entry<Object, Object> setter : setters.entrySet()) {
            builder.append(setter.getKey())
                    .append("=")
                    .append(setter.getValue())
                    .append(" ");
        }
        if (this.wheres.size() > 0) {
            builder.append(" WHERE ");
            for (String where : this.wheres) {
                builder.append(where);
            }
        }
        return builder.toString();
    }
}
