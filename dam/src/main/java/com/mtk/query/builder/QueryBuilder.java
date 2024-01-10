package com.mtk.query.builder;

import com.mtk.query.QueryType;

import java.util.ArrayList;
import java.util.List;

public abstract class QueryBuilder {

    protected String table;

    protected List<String> wheres = new ArrayList<>();

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

    public static QueryBuilder newQuery(QueryType type) {
        switch (type) {
            case SELECT:
                return select();
            case INSERT:
                return new InsertQueryBuilder();
            case UPDATE:
                return new UpdateQueryBuilder();
            case DELETE:
                return new DeleteQueryBuilder();
        }
        throw new RuntimeException("Please use valid query type.");
    }

    public abstract String build();
}
