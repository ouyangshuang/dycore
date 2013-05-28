package com.dooioo.web.model;

import com.dooioo.web.common.Constants;

import java.io.Serializable;

/**
 * 所有实体类的父类都得从这儿继承
 *
 * 说明：id（主键） 未放入其中，因为房友系统中很多表主键名不是id
 * 各系统可以在此类中再扩展
 *
 * User: kuang
 * Date: 12-4-25
 * Time: 下午12:07
 */
public class BaseEntity implements Serializable {
    protected static final long serialVersionUID = -3837567657603261711L;

    private int pageNo = 0;
    private int pageSize = Constants.WEB_PAGE_SIZE;
    private String sql;
    private String orderBy;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        if(pageNo <= 0 )
            this.pageNo = 0;
        else
            this.pageNo = pageNo - 1;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
}
