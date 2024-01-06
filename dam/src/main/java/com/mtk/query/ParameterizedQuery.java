package com.mtk.query;

import java.util.HashMap;
import java.util.Map;

public class ParameterizedQuery extends Query {

    protected Query query;

    public ParameterizedQuery(Query query) {
        this.query = query;
    }

    private final Map<String, String> parameters = new HashMap<>();

    public void bind(String token, String value) {
        parameters.put(token, value);
    }

    public void unbind(String token) {
        if (!parameters.containsKey(token)) {
            return;
        }
        parameters.remove(token);
    }

    @Override
    public String toString() {
        String SQL = query.toString();
        for(Map.Entry<String, String> entry : parameters.entrySet()) {
            SQL = SQL.replace(entry.getKey(), entry.getValue());
        }
        return SQL;
    }
}
