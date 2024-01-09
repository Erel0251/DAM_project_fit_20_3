package com.mtk.query.builder;

public class DeleteQueryBuilder extends QueryBuilder {

    public DeleteQueryBuilder(String table) {
        this.table = table;
    }

    @Override
    public String build() {
        StringBuilder builder = new StringBuilder();
        builder.append("DELETE FROM ").append(this.table);
        if (this.wheres.size() > 0) {
            builder.append(" WHERE ");
            for (String where : this.wheres) {
                builder.append(where);
            }
        }
        return builder.toString();
    }
}
