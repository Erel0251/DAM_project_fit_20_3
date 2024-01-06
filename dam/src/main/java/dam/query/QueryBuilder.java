package dam.query;

import dam.recordConnection.ConnectionBuilder;
import java.sql.Connection;

/*
author: Tran Duc Anh
description: Class QueryBuilder is used to create a query,
create some basic query SELECT, FROM, WHERE, ORDER BY, LIMIT
then execute it to create a ResultSet
*/
public class QueryBuilder {
    private StringBuilder query;
    private Connection conn;

    public QueryBuilder(Connection conn){
        this.query = new StringBuilder();
        this.conn = conn;
    }

    public QueryBuilder select(String column){
        this.query.append("Select " + column + " ");
        return this;
    }

    public QueryBuilder from(String table){
        this.query.append("From " + table + " ");
        return this;
    }

    public QueryBuilder where(String condition){
        this.query.append("Where " + condition + " ");
        return this;
    }

    /*
    public QueryBuilder orderBy(String orderBy){
        this.query.append(`ORDER BY ${orderBy} `);
        return this;
    }

    public QueryBuilder limit(String limit){
        this.query.append(`LIMIT ${limit} `);
        return this;
    }
    */

    public String execute(){
        return query.toString();
    }
}