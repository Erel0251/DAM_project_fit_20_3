package com.mtk.management;

import com.mtk.connect.ConnectConfiguration;
import com.mtk.connect.RecordConnection;
import com.mtk.connect.RecordConnectionFactory;
import com.mtk.exception.ConnectionException;
import com.mtk.exception.OutOfConnectionException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Persistence {

    public final static Integer MAX_EXISTS_CONNECTIONS = 4;

    public static List<RecordConnection> connectionPool = new ArrayList<>();

    public final static Map<Integer, RecordManagerFactory> used = new HashMap<>();

    private Persistence() {

    }

    public static void configureDatasource(ConnectConfiguration configuration)
            throws ConnectionException {
        createConnection(configuration);
    }

    public static void createConnection(ConnectConfiguration configuration)
            throws ConnectionException {
        for (int i = 0; i < MAX_EXISTS_CONNECTIONS; ++i) {
            connectionPool.add(RecordConnectionFactory.createRecordConnection(configuration));
        }
    }

    public static RecordManagerFactory createRecordManagerFactory(String factoryName)
            throws OutOfConnectionException {
        int connectionId = used.entrySet().stream()
                .filter(entry -> factoryName.equals(entry.getValue().getName()))
                .mapToInt(Map.Entry::getKey)
                .findFirst()
                .orElse(-1);
        if (connectionId != -1) {
            return used.get(connectionId);
        }
        int freeConnectionId = IntStream.range(0, MAX_EXISTS_CONNECTIONS)
                .filter(id -> !used.containsKey(id))
                .findFirst()
                .orElse(-1);
        if (freeConnectionId == -1) {
            throw new OutOfConnectionException("Out of database connections.");
        }
        RecordManagerFactory factory = new RecordManagerFactory(factoryName,
                connectionPool.get(freeConnectionId));
        used.put(freeConnectionId, factory);
        return factory;
    }

    public static void release(String factoryName) {
        int connectionId = used.entrySet().stream()
                .filter(entry -> factoryName.equals(entry.getValue().getName()))
                .mapToInt(Map.Entry::getKey)
                .findFirst()
                .orElse(-1);
        if (connectionId == -1) {
            return;
        }
        used.remove(connectionId);
    }
}
