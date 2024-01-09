package com.mtk.query.builder;

import java.util.Arrays;
import java.util.List;

public class SelectQueryBuilder extends QueryBuilder {

    private List<String> columns;

    private List<String> joins;

    public SelectQueryBuilder join(String table) {
        joins.add(table);
        return this;
    }

    public SelectQueryBuilder from(String table) {
        this.table = table;
        return this;
    }

    public SelectQueryBuilder(String... columns) {
        this.columns = Arrays.asList(columns);
    }

    @Override
    public String build() {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT ")
                .append(columns.get(0));
        if (columns.size() > 1) {
            for (String column : columns) {
                builder.append(",").append(column);
            }
        }
        builder.append(" FROM ");
        builder.append(this.table);
        for (int i = 1; i < joins.size(); ++i) {
            builder.append(" JOIN ").append(joins.get(i));
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
