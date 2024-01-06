package com.mtk.management;

import com.mtk.annotation.Record;
import com.mtk.connect.ConnectConfiguration;
import com.mtk.connect.RecordConnection;
import com.mtk.exception.UnsupportedDatabaseException;
import com.mtk.flatter.*;
import com.mtk.query.Query;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.UUID;

@Getter
public class RecordManagerFactory {

    private final String name;

    private final RecordConnection recordConnection;

    public RecordManagerFactory(String name, RecordConnection recordConnection) {
        this.name = name;
        this.recordConnection = recordConnection;
    }

    public RecordManager createRecordManager() {
        return new RecordManager(recordConnection);
    }
}
