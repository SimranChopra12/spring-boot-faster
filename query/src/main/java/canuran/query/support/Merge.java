package canuran.query.support;

import com.querydsl.core.QueryMetadata;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.SubQueryExpression;
import com.querydsl.sql.RelationalPath;

import java.beans.Expression;
import java.util.List;

public class Merge {
    private RelationalPath<?> entity;
    private QueryMetadata md;
    private List<Path<?>> keys;
    private List<Path<?>> columns;
    private List<Expression> values; // non-generic type
    private SubQueryExpression<?> subQuery;

    public void setEntity(RelationalPath<?> entity) {
        this.entity = entity;
    }

    public void setMd(QueryMetadata md) {
        this.md = md;
    }

    public void setKeys(List<Path<?>> keys) {
        this.keys = keys;
    }

    public void setColumns(List<Path<?>> columns) {
        this.columns = columns;
    }

    public void setValues(List<Expression> values) {
        this.values = values;
    }

    public void setSubQuery(SubQueryExpression<?> subQuery) {
        this.subQuery = subQuery;
    }

    public RelationalPath<?> getEntity() {
        return entity;
    }

    public QueryMetadata getMd() {
        return md;
    }

    public List<Path<?>> getKeys() {
        return keys;
    }

    public List<Path<?>> getColumns() {
        return columns;
    }

    public List<Expression> getValues() {
        return values;
    }

    public SubQueryExpression<?> getSubQuery() {
        return subQuery;
    }
}
