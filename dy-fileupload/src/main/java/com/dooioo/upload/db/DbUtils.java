package com.dooioo.upload.db;

/**
 * Created with IntelliJ IDEA at 13-9-9 下午5:35.
 *
 * @author 焦义贵
 * @since 1.0
 *        To change this template use File | Settings | File Templates.
 */
public class DbUtils {

    private void initDataSource() {
        PoolProperties p = new PoolProperties();
        p.setUrl(this.dataSourceProperties.get("url"));
        p.setDriverClassName(this.dataSourceProperties.get("driver"));
        p.setUsername(this.dataSourceProperties.get("user"));
        p.setPassword(this.dataSourceProperties.get("password"));
        p.setJmxEnabled(true);
        p.setTestWhileIdle(false);
        p.setValidationQuery("SELECT 1");
        p.setTestOnReturn(false);
        p.setValidationInterval(30000);
        p.setTimeBetweenEvictionRunsMillis(30000);
        p.setMaxActive(100);
        p.setInitialSize(10);
        p.setMaxWait(300000);
        p.setMinEvictableIdleTimeMillis(30000);
        p.setMinIdle(10);
        p.setLogAbandoned(true);
        p.setRemoveAbandoned(true);
        p.setRemoveAbandonedTimeout(3600);
        p.setTestOnBorrow(true);
        p.setJdbcInterceptors("org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
        this.dataSource = new DataSource();
        this.dataSource.setPoolProperties(p);
    }

}
