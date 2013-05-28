/**
 *  SpyMemcachedManager.java  2012-11-2
 *  Administrator	
 */
package com.dooioo.commons.memcache;

/**
 * @author Administrator
 *
 */
import com.dooioo.commons.memcache.spy.AddrUtil;
import com.dooioo.commons.memcache.spy.ConnectionObserver;
import com.dooioo.commons.memcache.spy.MemcachedClient;
import com.dooioo.commons.memcache.spy.transcoders.Transcoder;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketAddress;
import java.util.*;
import java.util.concurrent.Future;

  
public class Memcache {  
  
	private static Memcache dyMemcache = null;
	
	private static Object obj = new Object();
	
    private List<MemcacheAddr> servers;  
  
    private MemcachedClient memClient;  
    
    private static Logger log =Logger.getLogger( Memcache.class.getName() );
  
    private Memcache(List<MemcacheAddr> servers) {  
        this.servers = servers;  
    }  
    
    /**
	 * 一个	memcacheAddr  192.168.0.133:11713
	 * 多个	memcacheAddr  192.168.0.133:11713,192.168.0.133:11813
	 * @param memcacheAddrs
	 * @return
	 */
    @Deprecated
    public static Memcache getDyMemcache(String memcacheAddrs){
    	String[] memcacheAddrArray = memcacheAddrs.split(",");
    	return getDyMemcache(memcacheAddrArray);
    }
    
    @Deprecated
    public static Memcache getDyMemcache(String[] memcacheAddrArray){
    	if(dyMemcache == null){
    		synchronized (obj) {
    			if(dyMemcache == null){
    				dyMemcache = create(memcacheAddrArray);
    			}
			}
    	}
    	return dyMemcache;
    }
    
    public static Memcache getMemcache(String memcacheAddrs){
    	String[] memcacheAddrArray = memcacheAddrs.split(",");
    	return getMemcache(memcacheAddrArray);
    }
    
    public static Memcache getMemcache(String[] memcacheAddrArray){
    	if(dyMemcache == null){
    		synchronized (obj) {
    			if(dyMemcache == null){
    				dyMemcache = create(memcacheAddrArray);
    			}
			}
    	}
    	return dyMemcache;
    }
    
    private static Memcache create(String[] memcacheAddrArray){
        List<MemcacheAddr> servers = new ArrayList<MemcacheAddr>();  
        for (int i = 0; i < memcacheAddrArray.length; i++) {  
        	String[] ipAndPort = memcacheAddrArray[i].split(":"); 
            MemcacheAddr server = new MemcacheAddr();  
            server.setIp(ipAndPort[0]);  
            server.setPort(Integer.parseInt(ipAndPort[1]));  
            servers.add(server);  
        }  
        dyMemcache = new Memcache(servers);  
        
        try {
			dyMemcache.connect();
		} catch (IOException e) {
			log.error("connect memcache server error");
		}  
//        ConnectionObserver obs = new ConnectionObserver() {  
//            public void connectionEstablished(SocketAddress sa, int reconnectCount) {  
//            	log.info("Established " + sa.toString());  
//            }  
//            public void connectionLost(SocketAddress sa) {  
//            	log.info("Lost " + sa.toString());  
//            }  
//        };  
//        dyMemcache.addObserver(obs);
        return dyMemcache;
    }
    
 
    
  
    private void connect() throws IOException {  
        if (memClient != null) {  
            return;  
        }  
        StringBuffer buf = new StringBuffer();  
        for (int i = 0; i < servers.size(); i++) {  
            MemcacheAddr server = servers.get(i);  
            buf.append(server.toString()).append(" ");  
        }  
        memClient = new MemcachedClient(  
                AddrUtil.getAddresses(buf.toString()));  
    }  
  
    public void disConnect() {  
        if (memClient == null) {  
            return;  
        }  
        memClient.shutdown();  
    }  
      
    private void addObserver(ConnectionObserver obs) {  
        memClient.addObserver(obs);  
    }  
      
    private void removeObserver(ConnectionObserver obs) {  
        memClient.removeObserver(obs);  
    }  
      
    //---- Basic Operation Start ----//  
    public boolean set(String key, Object value, int expire) {  
        Future<Boolean> f = memClient.set(key, expire, value);  
        return getBooleanValue(f);  
    }  
  
    public Object get(String key) throws MemcacheNotFoundValueExecption {  
    	Object obj = null;
    	try {
			obj = memClient.get(key);
		} catch (Exception e) {
			dyMemcache.disConnect();
			dyMemcache = null;
			throw new MemcacheNotFoundValueExecption();
		}
    	if(obj == null){
    		throw new MemcacheNotFoundValueExecption();
    	}
        return obj;  
    }  
  
    private Object asyncGet(String key) {  
        Object obj = null;  
        Future<Object> f = memClient.asyncGet(key);  
        try {  
            obj = f.get(MemcachedConstants.DEFAULT_TIMEOUT,  
                    MemcachedConstants.DEFAULT_TIMEUNIT);  
        } catch (Exception e) {  
            f.cancel(false);  
        }  
        return obj;  
    }  
  
    private boolean add(String key, Object value, int expire) {  
        Future<Boolean> f = memClient.add(key, expire, value);  
        return getBooleanValue(f);  
    }  
  
    private boolean replace(String key, Object value, int expire) {  
        Future<Boolean> f = memClient.replace(key, expire, value);  
        return getBooleanValue(f);  
    }  
  
    public boolean delete(String key) {  
        Future<Boolean> f = memClient.delete(key);  
        return getBooleanValue(f);  
    }  
  
    public boolean flush() {  
        Future<Boolean> f = memClient.flush();  
        return getBooleanValue(f);  
    }  
  
    private Map<String, Object> getMulti(Collection<String> keys) {  
        return memClient.getBulk(keys);  
    }  
  
    private Map<String, Object> getMulti(String[] keys) {  
        return memClient.getBulk(keys);  
    }  
  
    private Map<String, Object> asyncGetMulti(Collection<String> keys) {  
        Map map = null;  
        Future<Map<String, Object>> f = memClient.asyncGetBulk(keys);  
        try {  
            map = f.get(MemcachedConstants.DEFAULT_TIMEOUT,  
                    MemcachedConstants.DEFAULT_TIMEUNIT);  
        } catch (Exception e) {  
            f.cancel(false);  
        }  
        return map;  
    }  
  
    private Map<String, Object> asyncGetMulti(String keys[]) {  
        Map map = null;  
        Future<Map<String, Object>> f = memClient.asyncGetBulk(keys);  
        try {  
            map = f.get(MemcachedConstants.DEFAULT_TIMEOUT,  
                    MemcachedConstants.DEFAULT_TIMEUNIT);  
        } catch (Exception e) {  
            f.cancel(false);  
        }  
        return map;  
    }  
    //---- Basic Operation End ----//  
  
          
    //---- increment & decrement Start ----//  
    private long increment(String key, int by, long defaultValue, int expire) {  
        return memClient.incr(key, by, defaultValue, expire);  
    }  
      
    private long increment(String key, int by) {  
        return memClient.incr(key, by);  
    }  
      
    private long decrement(String key, int by, long defaultValue, int expire) {  
        return memClient.decr(key, by, defaultValue, expire);  
    }  
      
    private long decrement(String key, int by) {  
        return memClient.decr(key, by);  
    }  
      
    private long asyncIncrement(String key, int by) {  
        Future<Long> f = memClient.asyncIncr(key, by);  
        return getLongValue(f);  
    }  
      
    private long asyncDecrement(String key, int by) {  
        Future<Long> f = memClient.asyncDecr(key, by);  
        return getLongValue(f);  
    }  
    //  ---- increment & decrement End ----//  
      
    public void printStats() throws IOException {  
        printStats(null);  
    }  
      
    public void printStats(OutputStream stream) throws IOException {  
        Map<SocketAddress, Map<String, String>> statMap =   
            memClient.getStats();  
        if (stream == null) {  
            stream = System.out;  
        }  
        StringBuffer buf = new StringBuffer();  
        Set<SocketAddress> addrSet = statMap.keySet();  
        Iterator<SocketAddress> iter = addrSet.iterator();  
        while (iter.hasNext()) {  
            SocketAddress addr = iter.next();  
            buf.append(addr.toString() + "/n");  
            Map<String, String> stat = statMap.get(addr);  
            Set<String> keys = stat.keySet();  
            Iterator<String> keyIter = keys.iterator();  
            while (keyIter.hasNext()) {  
                String key = keyIter.next();  
                String value = stat.get(key);  
                buf.append("  key=" + key + ";value=" + value + "/n");  
            }  
            buf.append("/n");  
        }  
        stream.write(buf.toString().getBytes());  
        stream.flush();  
    }  
      
    private Transcoder getTranscoder() {  
        return memClient.getTranscoder();  
    }  
      
    private long getLongValue(Future<Long> f) {  
        try {  
            Long l = f.get(MemcachedConstants.DEFAULT_TIMEOUT,  
                    MemcachedConstants.DEFAULT_TIMEUNIT);  
            return l.longValue();  
        } catch (Exception e) {  
            f.cancel(false);  
        }  
        return -1;  
    }  
  
    private boolean getBooleanValue(Future<Boolean> f) {  
        try {  
            Boolean bool = f.get(MemcachedConstants.DEFAULT_TIMEOUT,  
                    MemcachedConstants.DEFAULT_TIMEUNIT);  
            return bool.booleanValue();  
        } catch (Exception e) {  
            f.cancel(false);  
            return false;  
        }  
    }  
  
}  
