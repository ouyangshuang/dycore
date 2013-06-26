package com.dooioo.commons.ftp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.pool.BasePoolableObjectFactory;

/**
 * FTP连接池对象创建工厂
 * 采用 commons-pool
 *
 * User: kuang
 * Date: 13-3-22
 * Time: 下午5:40
 */
public class FTPPoolableObjectFactory extends BasePoolableObjectFactory {

    private static final Log log = LogFactory.getLog(FTPPoolableObjectFactory.class);
    private String host;
    private String user;
    private String pwd;

    public FTPPoolableObjectFactory(String host, String user, String pwd) {
        this.host = host;
        this.user = user;
        this.pwd = pwd;
    }

    @Override
    public Object makeObject() throws Exception {
        FTPClient ftpClient = new FTPClient();
        ftpClient.connect(host);
        ftpClient.setConnectTimeout(60); //ms
        ftpClient.setKeepAlive(true); // 系统默认会存活2个小时
        ftpClient.login(user, pwd);
        ftpClient.enterLocalActiveMode();
        log.debug("FTPPool makeObject => " + ftpClient.toString());
        return ftpClient;
    }

    @Override
    public void destroyObject(Object obj) throws Exception {
        if (obj instanceof FTPClient) {
            log.debug("FTPPool destroyObject => " + obj.toString());
            FTPClient ftpClient = (FTPClient) obj;
            if (!ftpClient.isConnected())
                return;
            try {
                ftpClient.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean validateObject(Object obj) {
        boolean isConnected = false;
        if (obj instanceof FTPClient) {
            FTPClient ftpClient = (FTPClient) obj;
            try {
                isConnected = ftpClient.isConnected() && ftpClient.sendNoOp();
            } catch (Exception e) {
                isConnected = false;
            }
        }
        log.debug("FTPPool validateObject => " + obj.toString() + " => " + isConnected);
        return isConnected;
    }

}

