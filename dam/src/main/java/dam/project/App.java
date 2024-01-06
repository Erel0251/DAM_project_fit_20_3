package dam.project;

import dam.recordConnection.*;

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
            .setUrl("jdbc:mysql://localhost:3306/record")
            .setUser("root")
            .setPassword("root")
            .getConnection();

        // Example using properties in preset config file
        Director director = new Director();
        director.getConfiguration("./configuration.xml");
        Connection connection = director.getConnection();
    }
}
