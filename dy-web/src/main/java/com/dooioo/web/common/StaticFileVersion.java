package com.dooioo.web.common;

import com.dooioo.commons.memcache.Memcache;
import com.dooioo.commons.memcache.MemcacheNotFoundValueExecption;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA at 13-5-22 下午5:24.
 *
 * @author 焦义贵
 * @since 1.0
 *        To change this template use File | Settings | File Templates.
 */
public class  StaticFileVersion{
    private final static Logger log = Logger.getLogger(StaticFileVersion.class);

    /**
     * memcache地址 192.168.0.133:11213
     * 多个地址 192.168.0.133:11213,192.168.0.133:11713,192.168.0.133:11813
     * @return Memcache memcache缓存
     */
    private static Memcache getMemcache(){
        return Memcache.getMemcache("192.168.0.133:11513");
    }

    public static Map<String,String> findStaticFileVersions(){
        Map<String,String> versions = null;
        try {
            versions = (Map<String, String>)getMemcache().get("StaticVersionNo");
            if (versions == null) {
                versions = new HashMap<String, String>();
            }
        } catch (MemcacheNotFoundValueExecption e) {
            log.error(e);
            versions = new HashMap<String, String>();
        } catch (Exception e) {
            log.error(e);
            versions = new HashMap<String, String>();
        }
        return  versions;
    }

    public static Map<String,String> refresh(String file){
        Map<String,String> versions = findStaticFileVersions();
        versions.put(file,System.currentTimeMillis()+"");
        return refresh(versions);
    }

    public static Map<String,String> refreshAll(){
        Map<String,String> versions = findStaticFileVersions();
        String versionNo = System.currentTimeMillis()+"";
        for(String key : versions.keySet()){
            versions.put(key,versionNo);
        }
        return refresh(versions);
    }

    public  static Map<String,String> refresh(Map<String,String> versions){
        try{
            getMemcache().set("StaticVersionNo", versions, 0);
        }catch (Exception e){
            log.error(e);
        }
        return versions;
    }
}
