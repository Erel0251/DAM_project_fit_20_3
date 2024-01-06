package dam.project;

import dam.recordConnection.*;
import dam.recordManager.*;
import dam.query.*;
import dam.mapping.*;

import java.sql.Connection;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        // Example manual, using builder pattern
        ConnectionBuilder connBuilder = ConnectionBuilder.getInstance();
        Connection connection = connBuilder
            .setDriver("com.mysql.jdbc.Driver")
            .setHostName("localhost")
            .setPort("3306")
            .setDatabase("dam")
            .setUser("root")
            .setPassword("root")
            .getConnection();

        // Example using properties in preset config file
        /*
        Director director = new Director();
        director.getConfiguration("./configuration.xml");
        Connection connection = director.getConnection();
        */
    }
}
