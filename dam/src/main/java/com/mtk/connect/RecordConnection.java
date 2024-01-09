package com.mtk.connect;

import com.mtk.flatter.QueryFlatter;
import com.mtk.query.Query;
import lombok.Getter;
import lombok.Setter;

import java.sql.*;

@Getter
@Setter
public class RecordConnection {

    private final QueryFlatter flatter;

    private final Connection connection;

    public RecordConnection(QueryFlatter flatter, Connection connection) {
        this.connection = connection;
        this.flatter = flatter;
    }

    public Object executeQuery(String query) {
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void executeUpdate(String query) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
