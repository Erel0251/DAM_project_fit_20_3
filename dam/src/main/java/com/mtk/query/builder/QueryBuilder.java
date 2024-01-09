package com.mtk.query.builder;

import java.util.List;

public abstract class QueryBuilder {

    protected String table;

    protected List<String> wheres;

    public QueryBuilder where(String clause) {
        wheres.add(clause);
        return this;
    }

    public QueryBuilder and(String clause) {
        wheres.add(" AND " + clause);
        return this;
    }

    public QueryBuilder or(String clause) {
        wheres.add(" OR " + clause);
        return this;
    }

    public QueryBuilder table(String table) {
        this.table = table;
        return this;
    }

    public static SelectQueryBuilder select(String...columns) {
        return new SelectQueryBuilder(columns);
    }

    public static InsertQueryBuilder insert(String table) {
        return new InsertQueryBuilder(table);
    }

    public static UpdateQueryBuilder update(String table) {
        return new UpdateQueryBuilder(table);
    }

    public static DeleteQueryBuilder delete(String table) {
        return new DeleteQueryBuilder(table);
    }

    public abstract String build();
}
