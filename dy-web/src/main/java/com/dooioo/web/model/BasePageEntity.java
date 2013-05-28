package com.dooioo.web.model;

/**
 * 分页通用基础类
 * User: kuang
 * Date: 12-7-5
 * Time: 下午12:42
 */
public class BasePageEntity extends BaseEntity {

    private String columns;
    private String table;
    private String where;
    //order 用 orderBy 替代

    public String getColumns() {
        return columns;
    }

    public void setColumns(String columns) {
        this.columns = columns;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }
}
