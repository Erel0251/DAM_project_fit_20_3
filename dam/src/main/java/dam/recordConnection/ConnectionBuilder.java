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
public class ConnectionBuilder(){
    private String driver;
    private String url;
    private String user;
    private String password;

    private static ConnectionBuilder instance = null;

    private ConnectionBuilder(){
        this.driver = "";
        this.url = "";
        this.user = "";
        this.password = "";
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

    public ConnectionBuilder setUrl(String url){
        this.url = url;
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

