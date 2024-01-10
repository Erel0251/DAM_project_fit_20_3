package com.mtk;

import com.mtk.connect.ConnectConfiguration;
import com.mtk.connect.ConnectionType;
import com.mtk.exception.ConnectionException;
import com.mtk.exception.OutOfConnectionException;
import com.mtk.exception.UnsupportedActionException;
import com.mtk.management.Persistence;
import com.mtk.management.RecordManager;
import com.mtk.management.RecordManagerFactory;
import com.mtk.query.builder.QueryBuilder;
import com.mtk.sample.User;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws OutOfConnectionException, SQLException, InstantiationException, IllegalAccessException, UnsupportedActionException, InvocationTargetException, NoSuchMethodException {
        Map<Object, Object> params = new HashMap<>();
        params.put("createDatabaseIfNotExist", "true");
        ConnectConfiguration configuration = ConnectConfiguration.builder(ConnectionType.MySQL)
                .hostName("localhost:3306")
                .username("benjamin")
                .password("benjamin")
                .databaseName("mtk-demo")
                .params(params)
                .build();
        try {
            Persistence.configureDatasource(configuration);
        } catch (ConnectionException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        RecordManagerFactory factory = Persistence.createRecordManagerFactory("simple-dao");
        RecordManager recordManager = factory.createRecordManager();
        QueryBuilder query = QueryBuilder.update("user")
                .setter("username", "hehe")
                .where("id = 3");
        recordManager.executeUpdate(query);
        QueryBuilder insertQuery = QueryBuilder.insert("user")
                .value("username", "test");
        recordManager.executeUpdate(insertQuery);

        User user = new User();
        recordManager.insert(user);
//        List<User> users = recordManager.executeQuery(query, User.class);
//        users.forEach(user -> System.out.println(user.getId() + " " + user.getName()));
        Persistence.release("simple-dao");
    }
}
