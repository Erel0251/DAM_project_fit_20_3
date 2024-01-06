package com.mtk.connect;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ConnectConfiguration {

    private ConnectionType type;

    private String hostName;

    private String username;

    private String password;

    private String databaseName;

    private Map<Object, Object> params;

    public String toConnectString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("jdbc:");
        stringBuilder.append(type.getValue());
        stringBuilder.append("://");
        stringBuilder.append(hostName);
        stringBuilder.append(databaseName);
//        if (params.size() > 0) {
//            stringBuilder.append("?");
//            for (Map.Entry<Object, Object> entry : params.entrySet()) {
//                stringBuilder.append(entry.getKey());
//                stringBuilder.append("=");
//                stringBuilder.append(entry.getValue());
//                stringBuilder.append("&");
//            }
//            stringBuilder.
//        }
        return stringBuilder.toString();
    }

    public static Builder builder(ConnectionType type) {
        return new Builder(type);
    }

    public static class Builder {

        private final ConnectConfiguration configuration = new ConnectConfiguration();

        public Builder(ConnectionType type) {
            configuration.setType(type);
        }

        public Builder hostName(String hostName) {
            configuration.setHostName(hostName);
            return this;
        }

        public Builder username(String username) {
            configuration.setUsername(username);
             return this;
        }

        public Builder password(String password) {
            configuration.setPassword(password);
            return this;
        }

        public Builder databaseName(String databaseName) {
            configuration.setDatabaseName(databaseName);
            return this;
        }

        public Builder params(Map<Object, Object> params) {
            configuration.setParams(params);
            return this;
        }

        public ConnectConfiguration build() {
            return configuration;
        }
    }
}
