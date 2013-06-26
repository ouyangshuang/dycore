package com.dooioo.commons.ftp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool;

/**
 * FTP连接池
 *
 * User: kuang
 * Date: 13-3-22
 * Time: 下午5:46
 */
public class FTPPool {

    private static final Log log = LogFactory.getLog(FTPPool.class);
    private final GenericObjectPool<FTPClient> internalPool;


    public FTPPool(GenericObjectPool.Config poolConfig, PoolableObjectFactory factory) {
        this.internalPool = new GenericObjectPool<FTPClient>(factory, poolConfig);
    }

    /**
     * 从连接池获取一个资源（对象）
     * @return
     */
    public FTPClient getResource() {
        try {
            return this.internalPool.borrowObject();
        } catch (Exception e) {
            log.error("getResource => ", e);
            return null;
        }
    }

    /**
     * 对象使用完后，归还一个资源（对象）
     * @param resource
     */
    public void returnResource(FTPClient resource) {
        try {
            this.internalPool.returnObject(resource);
        } catch (Exception e) {
            log.error("returnResource => ", e);
        }
    }

    public void destroy() {
        try {
            this.internalPool.close();
        } catch (Exception e) {
            log.error("destroy => ", e);
        }
    }

    public int inPoolSize() {
        try {
            return this.internalPool.getNumIdle();
        } catch (Exception e) {
            log.error("inPoolSize => ", e);
            return 0;
        }
    }

    public int borrowSize() {
        try {
            return this.internalPool.getNumActive();
        } catch (Exception e) {
            log.error("borrowSize => ", e);
            return 0;
        }
    }

}