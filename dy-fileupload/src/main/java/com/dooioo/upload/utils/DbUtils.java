package com.dooioo.upload.utils;

import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created with IntelliJ IDEA at 13-9-9 下午5:35.
 *
 * @author 焦义贵
 * @since 1.0
 *        To change this template use File | Settings | File Templates.
 */
public class DbUtils {

    private static DataSource dataSource;
    private static DbUtils dbUtils;

    private DbUtils() {
        dataSource = initDataSource();
    }

    public synchronized static DbUtils getInstance() {
        if(dbUtils == null) {
            dbUtils = new DbUtils();
        }
        return dbUtils;
    }

    public boolean insertTask() {
        String sql = "";
        Connection connection= null;
        Statement s = null;
        try {
            connection = dataSource.getConnection();

            s = connection.createStatement();
            return s.executeUpdate(sql) > 0;
        } catch (SQLException e) {

        } finally {
            if(connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
            if(s != null) {
                try {
                    s.close();
                } catch (SQLException e) {

                }
            }
        }
        return false;
    }

    private BasicDataSource initDataSource() {
        BasicDataSource p = new BasicDataSource();
        p.setUrl("jdbc:sqlserver://10.8.1.142:1433;DatabaseName=activeMQ");
        p.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        p.setUsername("dooiooadmin");
        p.setPassword("5a2eff87efec511e");
        p.setTestWhileIdle(false);
        p.setValidationQuery("SELECT 1");
        p.setTestOnReturn(false);
        p.setTimeBetweenEvictionRunsMillis(30000);
        p.setMaxActive(50);
        p.setInitialSize(10);
        p.setMaxWait(300000);
        p.setMinEvictableIdleTimeMillis(30000);
        p.setMinIdle(10);
        p.setLogAbandoned(true);
        p.setRemoveAbandoned(true);
        p.setRemoveAbandonedTimeout(3600);
        p.setTestOnBorrow(true);
        return p;
    }

}
