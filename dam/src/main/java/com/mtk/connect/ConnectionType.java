package com.mtk.connect;

public enum ConnectionType {

    MySQL("mysql"),
    PostgresSQL("postgressql"),
    MSSQL("mssql"),
    Oracle("oracle");

    private String value;

    ConnectionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
