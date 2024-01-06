package dam.recordConnection;

import dam.recordConnection.ConnectionBuilder;

import java.sql.Connection;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
/* 
author: Tran Duc Anh
description: class Director read data from file ./configuration.xml
    and set data to ConnectionBuilder
*/
public class Director{
    private ConnectionBuilder builder;

    public Director(){
        this.builder = ConnectionBuilder.getInstance();
    }

    public Director(ConnectionBuilder builder){
        this.builder = builder;
    }

    public void getConfiguration(String path){
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
                    String hostName = eElement.getElementsByTagName("hostName").item(0).getTextContent();
                    String port = eElement.getElementsByTagName("port").item(0).getTextContent();
                    String database = eElement.getElementsByTagName("database").item(0).getTextContent();
                    String user = eElement.getElementsByTagName("user").item(0).getTextContent();
                    String password = eElement.getElementsByTagName("password").item(0).getTextContent();
                    this.builder.setDriver(driver)
                                .setHostName(hostName)
                                .setPort(port)
                                .setDatabase(database)
                                .setUser(user)
                                .setPassword(password);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setConnectionBuilder(ConnectionBuilder builder){
        this.builder = builder;
    }

    public Connection getConnection(){
        return this.builder.getConnection();
    }

    public void closeConnection(Connection conn){
        this.builder.closeConnection(conn);
    }
}