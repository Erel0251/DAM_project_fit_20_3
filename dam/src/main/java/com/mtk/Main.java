package com.mtk;

import com.mtk.connect.ConnectConfiguration;
import com.mtk.connect.ConnectionType;
import com.mtk.exception.ConnectionException;
import com.mtk.exception.OutOfConnectionException;
import com.mtk.exception.UnsupportedActionException;
import com.mtk.exception.UnsupportedDatabaseException;
import com.mtk.management.Persistence;
import com.mtk.management.RecordManager;
import com.mtk.management.RecordManagerFactory;
import com.mtk.query.Query;
import com.mtk.query.QueryType;
import com.mtk.sample.User;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws OutOfConnectionException, SQLException, InstantiationException, IllegalAccessException, UnsupportedActionException, InvocationTargetException, NoSuchMethodException {
        Map<Object, Object> params = new HashMap<>();
        params.put("createDatabaseIfNotExist", "true");
        ConnectConfiguration configuration = ConnectConfiguration.builder(ConnectionType.MySQL)
                .hostName("localhost:3306/")
                .username("root")
                .password("auth-service-root-pass-123")
                .databaseName("auth_service_database")
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
//        User user = new User("1", "i");
//        recordManager.save(user);
//        recordManager.delete(user);
        Query query = recordManager.createQuery(QueryType.SELECT);
        query.setTable("user");
        List<User> users = recordManager.executeQuery(query, User.class);
        users.forEach(user -> {
            System.out.println(user.getId() + " " + user.getName());
        });
        Persistence.release("simple-dao");
    }
}
