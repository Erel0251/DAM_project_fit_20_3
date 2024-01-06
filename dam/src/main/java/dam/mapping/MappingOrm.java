package dam.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

/*
author: Tran Duc Anh
description: Class MappingOrm is used to map data from ResultSet to Object
that is defined by user

example main:
    public static void main(String[] args) {
        Director director = new Director();
        director.getConfiguration();
        ConnectionBuilder builder = director.getConnectionBuilder();
        Connection conn = builder.getConnection();
        QueryBuilder query = new QueryBuilder(conn);
        String sql = query.select("*").from("student").where("id = 1").execute();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        MappingOrm<Student> mapping = new MappingOrm<Student>(new Student(), rs);
        Student student = mapping.map();
        System.out.println(student);
    }
*/

public class MappingOrm {
    private ResultSet rs;
}
