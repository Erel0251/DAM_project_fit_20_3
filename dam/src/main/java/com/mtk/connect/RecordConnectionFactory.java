package com.mtk.connect;

import com.mtk.exception.ConnectionException;
import com.mtk.exception.UnsupportedDatabaseException;
import com.mtk.flatter.*;

import java.sql.Connection;
import java.sql.DriverManager;

public class RecordConnectionFactory {

    public static RecordConnection createRecordConnection(ConnectConfiguration configuration)
            throws ConnectionException {
        String connectionString = configuration.toConnectString();
        String driver;
        try {
            switch (configuration.getType()) {
                case MySQL:
                    driver= "com.mysql.cj.jdbc.Driver";
                    break;
                case PostgresSQL:
                    driver = "org.postgresql.Driver";
                    break;
                case MSSQL:
                    driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
                    break;
                case Oracle:
                    driver = "oracle.jdbc.driver.OracleDriver";
                    break;
                default:
                    throw new UnsupportedDatabaseException("Currently no support for this database type: " + configuration.getType());
            }
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(connectionString,
                    configuration.getUsername(), configuration.getPassword());
            return new RecordConnection(getFlatter(configuration), connection);
        } catch (Exception ex) {
            System.err.println(ex);
        }
        throw new ConnectionException("Can't connect to database");
    }

    public static QueryFlatter getFlatter(ConnectConfiguration configuration) throws UnsupportedDatabaseException {
        switch (configuration.getType()) {
            case MySQL:
                return new MySqlFlatter();
            case PostgresSQL:
                return new PostgresSqlFlatter();
            case MSSQL:
                return new MsSqlFlatter();
            case Oracle:
                return new OracleFlatter();
        }
        throw new UnsupportedDatabaseException("Currently no support for this database type: " + configuration.getType());
    }
}
