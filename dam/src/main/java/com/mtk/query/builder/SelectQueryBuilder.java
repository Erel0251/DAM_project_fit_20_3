package com.mtk.query.builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SelectQueryBuilder extends QueryBuilder {

    private List<String> columns;

    private List<String> joins;

    public SelectQueryBuilder join(String table) {
        if (joins == null) {
            joins = new ArrayList<>();
        }
        joins.add(table);
        return this;
    }

    public SelectQueryBuilder from(String table) {
        this.table = table;
        return this;
    }

    public SelectQueryBuilder(String... columns) {
        this.columns = Arrays.asList(columns);
        this.joins = new ArrayList<>();
    }

    @Override
    public String build() {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT");
        if (!columns.isEmpty()) {
            builder.append(columns.get(0));
            for (String column : columns) {
                builder.append(",").append(column);
            }
        } else {
            builder.append(" * ");
        }
        builder.append(" FROM ");
        builder.append(this.table);
        if (!joins.isEmpty()) {
            for (int i = 1; i < joins.size(); ++i) {
                builder.append(" JOIN ").append(joins.get(i));
            }
        }
        if (!this.wheres.isEmpty()) {
            builder.append(" WHERE ");
            for (String where : this.wheres) {
                builder.append(where);
            }
        }
        return builder.toString();
    }
}
