package com.mtk.management;

import com.mtk.connect.RecordConnection;
import lombok.Getter;

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
