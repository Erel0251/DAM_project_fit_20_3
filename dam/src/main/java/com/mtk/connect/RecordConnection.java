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

    public Object execute(Query query) {
        try {
            Statement statement = connection.createStatement();
            String SQL = flatter.flat(query);
            switch (query.getType()) {
                case SELECT:
                    return statement.executeQuery(SQL);
                case INSERT:
                case UPDATE:
                case DELETE:
                    return statement.executeUpdate(SQL);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
