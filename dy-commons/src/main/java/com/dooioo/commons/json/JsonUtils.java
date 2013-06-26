package com.dooioo.commons.json;

import com.dooioo.commons.Strings;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * json字符串转map
 *
 * 依赖jackson
 * 
 * @author liuxing
 * 
 */
public class JsonUtils {

    private static final Log log = LogFactory.getLog(JsonUtils.class);

	public static Map<String, Object> parserToMap(String s) {
		return readToMap(s);
	}
	
	/**
	 * 仅限json格式只有一级目录
	 * @param jsonStr
	 * @return
	 */
    @Deprecated
	public static Map<String, String> parserToStringMap(String jsonStr) {
		Map<String, Object> map = parserToMap(jsonStr);
		Map<String, String> stringMap = new HashMap<String, String>();

		for (String key : map.keySet()) {
			stringMap.put(key, String.valueOf(map.get(key)));
		}
		return stringMap;
	}
	
    /**
     * 将json格式转换成一个javabean
     * @param json
     * @return
     */
    @Deprecated
    public static DynaBean toBean(String json) {
        JSONObject jsonObject = JSONObject.fromObject(json);
        return (DynaBean) JSONObject.toBean(jsonObject);
    }

    /**
     * 将json格式转换成指写的javabean
     * @param json
     * @param beanName name
     * @return
     */
    @Deprecated
    public static Object toBean(String json, String beanName) {
        try {
            return toBean(json, Class.forName(beanName));
        } catch (ClassNotFoundException e) {
            log.error(e);
        }
        return null;
    }

    /**
     * 将json格式转换成指写的javabean
     * @param json
     * @param clazz
     * @return
     */
    @Deprecated
    public static Object toBean(String json, Class clazz) {
       return readValueToObj(json, clazz);
    }

    /**
     * 将javabean转换成json
     * 并且在json中添加clazz属性，用于在再转换成javabean时自动再组装
     * e.g. {"clazz":"com.dooioo.property.model.Property"}
     * @param obj
     * @param clazz
     * @return
     */
    @Deprecated
    public static String toJson(Object obj, Class clazz) {
        JSONObject jsonObject = JSONObject.fromObject(obj);
        jsonObject.element("clazz", clazz.getName());
        return jsonObject.toString();
    }

    /**
     * 将javabean转换成json
     * @param obj
     * @return
     */
    @Deprecated
    public static String toJson(Object obj) {
        try {
            return write(obj);
        } catch (IOException e) {
            log.error(e);
            return "";
        }
    }

    /**
	 * 将map转换成json格式
	 * @param obj
	 * @return
	 */
    public static String objectToJson(Object obj){
        try {
            return write(obj);
        } catch (IOException e) {
            log.error(e);
            return "";
        }
    }

    public static Map<String, Object> readToMap(String str) {
        Map<String, Object> emptyMap = new HashMap<String, Object>();
        if(Strings.isEmpty(str)) {
            return emptyMap;
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(str, Map.class);
        } catch (IOException e) {
            log.error(e);
        }
        return emptyMap;
    }

    public static Map<String, Object> readToMap(File file) {
        Map<String, Object> emptyMap = new HashMap<String, Object>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(file, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return emptyMap;
    }

    public static Object readValueToObj(String str, Class clss) {
        Map<String, Object> emptyMap = new HashMap<String, Object>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(str, clss);
        } catch (IOException e) {
            log.error(e);
        }
        return null;
    }

    public static Object readValueToObj(File file, Class clss) {
        Map<String, Object> emptyMap = new HashMap<String, Object>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(file, clss);
        } catch (IOException e) {
            log.error(e);
        }
        return null;
    }

    /**
     * 将一个对象，转换成josn格式
     * 并且返回json字符串
     * @param obj
     * @return
     * @throws IOException
     */
    public static String write(Object obj) throws IOException {
        StringWriter stringWriter = new StringWriter();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(stringWriter, obj);
        return stringWriter.toString();
    }

    /**
     * 将一个对象转换成josn格式，写入文件中
     * @param file
     * @param obj
     * @throws IOException
     */
    public static void write(File file, Object obj) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(file, obj);
    }
    
	public static void main(String[] args) {
//		Map<String, Object> map = new HashMap<String, Object>();
//		map = parserToMap("{name:\"刘先生\",mobile:\"1380000000\",contactName:\"测试人\",tel:\"其他联系信息"+
//						"其他联系信息2\n"+
//						"其他联系信息2\n\",nationality:\"香港\",checkStatus:\"已购\",followContent:\"fsdfadfs\"}");
//		System.out.println(map.get("name"));
//		String str = "{\"descVote\":{'descVoteId': 2},\"descVoteLine\":\"{'1':'a','2':'b','3':'c'}\"}";
//		Map<String, Object> map = parserToMap(str);
//		System.out.println(map.get("descVote"));
//		System.out.println(map.get("descVoteLine"));
		
		String jsonStr = "{'propertyId':'propertyId','contactName':'contactName','relationship':'relationship','residence':'residence','phones':'18663545,4884545,4545545'}";
		Map<String, String> map = parserToStringMap(jsonStr);
	}
	
}
