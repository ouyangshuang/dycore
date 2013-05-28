/**
 *  SpyMemcachedServer.java  2012-11-2
 *  Administrator	
 */
package com.dooioo.commons.memcache;

/**
 * @author Administrator
 *
 */
public class MemcacheAddr {  
    
    private String ip;  
    private int port;  
      
    public void setIp(String ip) {  
        this.ip = ip;  
    }  
      
    public String getIp() {  
        return ip;  
    }  
      
    public void setPort(int port) {  
        if (port < 0 || port > 65535) {  
            throw new IllegalArgumentException("Port number must be between 0 to 65535");  
        }  
        this.port = port;  
    }  
      
    public int getPort() {  
        return port;  
    }  
      
    public String toString() {  
        return getIp() + ":" + getPort();  
    }  
}  
