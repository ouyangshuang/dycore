package com.dooioo.commons.ftp;

import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.log4j.Logger;

/**
 * FTP连接池工厂类
 * User: kuang
 * Date: 13-3-22
 * Time: 下午5:53
 */
public class FTPFactory {
    private static final Logger log = Logger.getLogger(FTPFactory.class);

    private volatile static FTPPool ftpPool;

    private FTPFactory() {
    }

    /**
     * 获取FTP连接池
     * @return
     */
    public static FTPPool getInstance(GenericObjectPool.Config config, String host, String user, String pwd) {

        if (ftpPool != null) {
            return ftpPool;
        }

        log.info("start init FTP Pool ...");
//        GenericObjectPool.Config config = new GenericObjectPool.Config();
//        //最大池容量
//        config.maxActive = 3;
//
//        config.maxWait = 2;
//
//        //从池中取对象达到最大时,继续创建新对象.
//        config.whenExhaustedAction = GenericObjectPool.WHEN_EXHAUSTED_GROW;
//
//        //池为空时取对象等待的最大毫秒数.
//        config.maxWait = 60 * 1000;
//
//        //取出对象时验证(此处设置成验证ftp是否处于连接状态).
//        config.testOnBorrow = true;
//
//        //还回对象时验证(此处设置成验证ftp是否处于连接状态).
//        config.testOnReturn = true;
        ftpPool = new FTPPool(config, new FTPPoolableObjectFactory(host, user, pwd));

        return ftpPool;
    }
}
