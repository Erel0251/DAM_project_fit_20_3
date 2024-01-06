package com.mtk.query;

public class QueryFactory {

    public static Query createQuery(QueryType type) {
        switch (type) {
            case SELECT:
                return new SelectQuery();

            case INSERT:
                break;
            case UPDATE:
                break;
            case DELETE:
                break;
        }
        return null;
    }
}
