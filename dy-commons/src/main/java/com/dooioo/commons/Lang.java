package com.dooioo.commons;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;


/**
 *
 *
 * User: kuang
 * Date: 11-3-10
 * Time: 下午5:36
 */
public class Lang {

	/**
	 * 传入的参数是对象， 则返回空字符串
	 * @param obj
	 * @return
	 */
    public static String defaultEmptyStr(Object obj) {
        if (obj == null)
            return "";
        return defaultStr(obj.toString(), "");
    }

    public static String defaultStr(Object obj, String defaultStr) {
        if (obj == null)
            return defaultStr;
        return defaultStr(obj.toString(), defaultStr);
    }

    //各种工具方法
    public static <T> List<T> list(T... arrays) {
        List<T> list = new ArrayList<T>(arrays.length);
        for (T t : arrays) {
            list.add(t);
        }
        return list;
    }

    public static Map map(Object... arrays) {
        Map maps = new HashMap();
        if (arrays.length % 2 != 0) throw new RuntimeException("arrays 长度 必须为偶数");
        for (int i = 0; i < arrays.length; i++) {
            maps.put(arrays[i], arrays[++i]);
        }
        return maps;
    }

    public static <T> Set<T> set(T... arrays) {
        Set<T> sets = new HashSet<T>(arrays.length);
        for (T t : arrays) {
            sets.add(t);
        }
        return sets;
    }

    public static Class getSuperClassGenricType(Class clazz) {
        return getSuperClassGenricType(clazz, 0);  
    }

    /**
     *  获取超类型
     * @param clazz
     * @param index
     * @return
     */
    public static Class getSuperClassGenricType(Class clazz, int index) {  
        Type genType = clazz.getGenericSuperclass();  
  
        if (!(genType instanceof ParameterizedType)) {  
            return Object.class;  
        }  
  
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();  
  
        if (index >= params.length || index < 0) {  
            return Object.class;  
        }  
        if (!(params[index] instanceof Class)) {  
            return Object.class;  
        }  
        return (Class) params[index];  
    }


    /**
     * 通过beanId获取容器里的一个bean对象
     * @param beanId
     * @return
     */
    public static Object getSpringBean(String beanId) {
        WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
        return context.getBean(beanId);
    }


    /**
     * 根据格式化字符串，生成运行时异常
     *
     * @param format
     *            格式
     * @param args
     *            参数
     * @return 运行时异常
     */
    public static RuntimeException makeThrow(String format, Object... args) {
        return new RuntimeException(String.format(format, args));
    }

    /**
     * 将抛出对象包裹成运行时异常，并增加自己的描述
     *
     * @param e
     *            抛出对象
     * @param fmt
     *            格式
     * @param args
     *            参数
     * @return 运行时异常
     */
    public static RuntimeException wrapThrow(Throwable e, String fmt, Object... args) {
        return new RuntimeException(String.format(fmt, args), e);
    }

}
