package dam.recordManager;

import dam.recordManager.*;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;


/*
author: Tran Duc Anh
description: Class RecordManager is used to manage almost everything
after get connection to the database, 
include create, read, update, delete,
basic query and some advanced query
*/

class RecordManager {
    private Connection conn;
    private QueryBuilder queryBuilder;

    public RecordManager(Connection conn){
        this.conn = conn;
    }

    public RecordManager(Connection conn, QueryBuilder queryBuilder){
        this.conn = conn;
        this.queryBuilder = queryBuilder;
    }

    public void setQueryBuilder(QueryBuilder queryBuilder){
        this.queryBuilder = queryBuilder;
    }

    public ResultSet executeQuery(String query){
        ResultSet rs = null;
        try{
            Statement stmt = this.conn.createStatement();
            rs = stmt.executeQuery(query);
        }catch(Exception e){
            e.printStackTrace();
        }
        return rs;
    }

    public create(String table, String[] columns, String[] values){
        String query = `INSERT INTO ${table} (${columns.join(", ")}) VALUES (${values.join(", ")})`;
        try{
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(query);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public read(String table, String[] columns, String condition){
        String query = `SELECT ${columns.join(", ")} FROM ${table} WHERE ${condition}`;
        return this.executeQuery(query);
    }

    public update(String table, String[] columns, String[] values, String condition){
        String query = `UPDATE ${table} SET `;
        for(int i = 0; i < columns.length; i++){
            query += `${columns[i]} = ${values[i]}, `;
        }
        query = query.substring(0, query.length() - 2);
        query += ` WHERE ${condition}`;
        try{
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(query);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public delete(String table, String condition){
        String query = `DELETE FROM ${table} WHERE ${condition}`;
        try{
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(query);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}