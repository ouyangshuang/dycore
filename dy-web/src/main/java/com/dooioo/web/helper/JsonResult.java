package com.dooioo.web.helper;

import com.dooioo.commons.Strings;
import com.dooioo.web.model.BaseEntity;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;

/**
 * 返回json的标准格式
 * {status:'ok', mssage:'test', data:{}}
 *
 * User: kuang
 * Date: 12-8-25
 * Time: 下午3:15
 */
public class JsonResult extends HashMap<String,Object>{
    public static enum STATUS {OK, FAIL};
    private JsonResult() {}

    public JsonResult(STATUS status) {
        this(status, "");
    }

    public JsonResult(STATUS status, String message) {
          this.put("status",status.name().toLowerCase()).put("message",message);
    }

    public JsonResult put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public JsonResult put(BaseEntity baseEntity) {
        super.put(Strings.uncapitalize(baseEntity.getClass().getSimpleName()), baseEntity);
        return this;
    }

    public String toJson()  {
        StringWriter jsonWriter = new StringWriter();
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(jsonWriter, this);
            return jsonWriter.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 借跨域回调使用
     * @param callback
     * @return
     */
    public String toJson(String callback)  {
        if(Strings.isEmpty(callback))
            return toJson();

        return callback + "(" + toJson() + ")";
    }

    public JsonResult clone() {
        return (JsonResult) super.clone();
    }

    public String toString()  {
        return toJson();
    }
}
