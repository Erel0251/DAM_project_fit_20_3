package dam.recordConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
author: Tran Duc Anh
description: Class ConnectionBuilder is used to connect to the database
    using design pattern Builder to create a connection,
    using with Singleton to prevent creating multiple instances of the class
*/
public class ConnectionBuilder {
    private String driver;
    private String hostName;
    private String port;
    private String database;
    private String user;
    private String password;
    private String url;

    private static ConnectionBuilder instance = null;

    private ConnectionBuilder(){
        this.driver = "";
        this.hostName = "";
        this.port = "";
        this.database = "";
        this.user = "root";
        this.password = "root";
    }

    public static ConnectionBuilder getInstance(){
        if(instance == null){
            instance = new ConnectionBuilder();
        }
        return instance;
    }

    public ConnectionBuilder setDriver(String driver){
        this.driver = driver;
        return this;
    }

    public ConnectionBuilder setHostName(String hostName){
        this.hostName = hostName;
        return this;
    }

    public ConnectionBuilder setPort(String port){
        this.port = port;
        return this;
    }

    public ConnectionBuilder setDatabase(String database){
        this.database = database;
        return this;
    }

    public ConnectionBuilder setUser(String user){
        this.user = user;
        return this;
    }

    public ConnectionBuilder setPassword(String password){
        this.password = password;
        return this;
    }

    public Connection getConnection(){
        Connection conn = null;
        // depending on the database that you use, you need to change the driver
        // and the url
        switch(this.driver){
            case "com.mysql.jdbc.Driver":
                this.url = "jdbc:mysql://" + this.hostName + ":" + this.port + "/" + this.database;
                break;
            case "oracle.jdbc.driver.OracleDriver":
                this.url = "jdbc:oracle:thin:@" + this.hostName + ":" + this.port + ":" + this.database;
                break;
            case "com.microsoft.sqlserver.jdbc.SQLServerDriver":
                this.url = "jdbc:sqlserver://" + this.hostName + ":" + this.port + ";databaseName=" + this.database;
                break;
            default:
                System.out.println("Driver is not defined");
                break;
        }


        try{
            Class.forName(this.driver);
            conn = DriverManager.getConnection(this.url, this.user, this.password);
        }catch(ClassNotFoundException e){
            System.out.println("Class not found");
        }catch(SQLException e){
            System.out.println("SQL Exception");
        }
        return conn;
    }

    // Close connection
    public void closeConnection(Connection conn){
        try{
            conn.close();
        }catch(SQLException e){
            System.out.println("SQL Exception");
        }
    }
}

