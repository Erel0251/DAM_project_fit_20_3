package com.mtk.query;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Query {

    private QueryType type;

    private Class clazz;

    private String table;

    private String where;

    private String groupBy;

    private String having;

    private String orderBy;

    public static Builder builder(QueryType type) {
        return new Builder(type);
    }

    public static class Builder {

        private final Query query = new Query();

        public Builder(QueryType type) {
            query.setType(type);
        }

        public Query build() {
            return query;
        }
    }
}
