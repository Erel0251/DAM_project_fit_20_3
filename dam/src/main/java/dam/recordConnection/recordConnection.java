package dam.recordConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/*
author: Tran Duc Anh
description: This package is used to connect to the database
using design pattern Builder, Singleton
*/

/*
author: Tran Duc Anh
description: Class ConnectionBuilder is used to connect to the database
    using design pattern Builder to create a connection,
    using with Singleton to prevent creating multiple instances of the class
*/
class ConnectionBuilder(){
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
}

/* 
author: Tran Duc Anh
description: class Director read data from file ./configuration.xml
    and set data to ConnectionBuilder
*/
class Director{
    private ConnectionBuilder builder;

    public Director(){
        this.builder = ConnectionBuilder.getInstance();
    }

    public Director(ConnectionBuilder builder){
        this.builder = builder;
    }

    public void getConfiguration(String path = "./configuration.xml"){
        // read data from file xml
        try {
            File xmlFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            // get data from file xml
            NodeList nList = doc.getElementsByTagName("database-connection");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    // get data from file xml (tag: driver, url, user, password
                    Element eElement = (Element) nNode;

                    String driver = eElement.getElementsByTagName("driver").item(0).getTextContent();
                    String url = eElement.getElementsByTagName("url").item(0).getTextContent();
                    String user = eElement.getElementsByTagName("user").item(0).getTextContent();
                    String password = eElement.getElementsByTagName("password").item(0).getTextContent();
                    this.builder.setDriver(driver);
                    this.builder.setUrl(url);
                    this.builder.setUser(user);
                    this.builder.setPassword(password);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public setConnectionBuilder(ConnectionBuilder builder){
        this.builder = builder;
    }

    public Connection getConnection(){
        return this.builder.getConnection();
    }
}