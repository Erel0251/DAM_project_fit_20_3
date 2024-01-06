package com.mtk.flatter;

import com.mtk.query.Query;

public class MySqlFlatter implements QueryFlatter {

    @Override
    public String flat(Query query) {
        String SQL = "";
        switch (query.getType()) {

            case SELECT:
                return createSelectQuery(query);
            case INSERT:
                break;
            case UPDATE:
                break;
            case DELETE:
                break;
        };
        return SQL;
    }

    private String createSelectQuery(Query query) {
        String SQL = "SELECT * FROM " + query.getTable();
        if (query.getWhere() != null) {
            SQL += " WHERE " + query.getWhere();
        }
        if (query.getGroupBy() != null) {
            SQL += " GROUP BY " + query.getGroupBy();
        }
        if (query.getHaving() != null) {
            SQL += " HAVING " + query.getHaving();
        }
        if (query.getOrderBy() != null) {
            SQL += " ORDER BY " + query.getOrderBy();
        }
        return SQL;
    }
}
