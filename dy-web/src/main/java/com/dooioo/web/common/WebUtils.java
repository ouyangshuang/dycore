package com.dooioo.web.common;

import com.dooioo.commons.Strings;
import com.dooioo.web.model.BaseEntity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: kuang
 * Date: 12-4-25
 * Time: 下午2:59
 */
public class WebUtils {

    private static final Log log = LogFactory.getLog(WebUtils.class);
    /** Name suffixes in case of image buttons */
    public static final String[] SUBMIT_IMAGE_SUFFIXES = {".x", ".y"};

    /**
     * 通过名字从 request parameters 中获取值，并返回字符串
     * 如果值为null，则默认返回空字符串
     * @param request
     * @param name
     * @return
     */
    public static String findParamStr(ServletRequest request, String name, String defaultStr) {
        return Strings.defaultStr(findParameterValue(request, name), defaultStr);
    }

    public static String findParamStr(ServletRequest request, String name) {
        return findParamStr(request, name, "");
    }

    /**
     * 通过名字从 request parameters 中获取值，并返回 int
     * 如果值为空，默认返回 0
     * @param request
     * @param name
     * @return
     */
    public static int findParamInt(ServletRequest request, String name, int defaultInt) {
        return Strings.defaultInteger(findParamStr(request, name), defaultInt);
    }

    public static int findParamInt(ServletRequest request, String name) {
        return findParamInt(request, name, 0);
    }

    /**
     * 通过名字从 request parameters 中获取值，并返回 Integer
     * 如果值为空，默认返回 null
     * @param request
     * @param name
     * @return
     */
    public static Integer findParamInteger(ServletRequest request, String name) {
        String paramValue = findParamStr(request, name);
        Integer paramValueInt = null;
        try{
            paramValueInt = Integer.parseInt(paramValue);
            return Strings.isEmpty(paramValue) ? null : paramValueInt;
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }

    /**
     * 通过名字从 request parameters 中获取值，并返回 double
     * 如果值为空，默认返回 0
     * @param request
     * @param name
     * @return
     */
    public static double findParamDoub(ServletRequest request, String name, double defaultDouble) {
        return Strings.defaultDouble(findParamStr(request, name), defaultDouble);
    }

    public static double findParamDoub(ServletRequest request, String name) {
        return findParamDoub(request, name, 0);
    }

    /**
     * 通过名字从 request parameters 中获取值，并返回 Double
     * 如果值为空，默认返回 null
     * @param request
     * @param name
     * @return
     */
    public static Double findParamDouble(ServletRequest request, String name) {
        String paramValue = findParamStr(request, name);
        Double paramValueInt = null;
        try{
            paramValueInt = Double.parseDouble(paramValue);
            return Strings.isEmpty(paramValue) ? null : paramValueInt;
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }

    /**
     * 通过名字从 request parameters 中获取值，并返回 boolean
     * 如果值为空，默认返回 0
     * @param request
     * @param name
     * @return
     */
    public static boolean findParamBool(ServletRequest request, String name, boolean defaultBoolean) {
        Boolean retBoolean = findParamBoolean(request, name);
        return retBoolean == null ? defaultBoolean : retBoolean;
    }

    public static boolean findParamBool(ServletRequest request, String name) {
        return findParamBool(request, name, false);
    }

    /**
     * 通过名字从 request parameters 中获取值，并返回 Boolean
     * 如果值为空，默认返回 null
     * @param request
     * @param name
     * @return
     */
    public static Boolean findParamBoolean(ServletRequest request, String name) {
        String paramValue = findParamStr(request, name);
        Boolean paramValueBoolean = null;
        try{
            paramValueBoolean = Boolean.parseBoolean(paramValue);
            return Strings.isEmpty(paramValue) ? null : paramValueBoolean;
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }

    public static Object getSessionAttribute(HttpServletRequest request, String name) {
        Assert.notNull(request, "Request must not be null");
        HttpSession session = request.getSession(false);
        return (session != null ? session.getAttribute(name) : null);
    }

    /**
     * 得到用户的session
     * 返回 Object，每个子系统强转换类型
     * @param request
     * @return
     */
    public Object getSession(HttpServletRequest request) {
        Assert.notNull(request, "Request must not be null");
        HttpSession session = request.getSession(false);
        return session != null ? (BaseEntity) session.getAttribute(Constants.SESSION_USER) : null;
    }

    public static void setSessionAttribute(HttpServletRequest request, String name, Object value) {
        Assert.notNull(request, "Request must not be null");
        if (value != null) {
            request.getSession().setAttribute(name, value);
        }
        else {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.removeAttribute(name);
            }
        }
    }

    /**
     * 设置用户session
     * session存储的为Employee 对象
     * @param request
     * @param baseEntity
     * @see com.dooioo.web.model.BaseEntity
     */
    public void setSession(HttpServletRequest request, BaseEntity baseEntity) {
        Assert.notNull(request, "Request must not be null");
        setSessionAttribute(request, Constants.SESSION_USER, baseEntity);
    }

    public static String findParameterValue(ServletRequest request, String name) {
        return findParameterValue(request.getParameterMap(), name);
    }

    public static String findParameterValue(Map<String, ?> parameters, String name) {
        // First try to get it as a normal name=value parameter
        Object value = parameters.get(name);
        if (value instanceof String[]) {
            String[] values = (String[]) value;
            return (values.length > 0 ? values[0] : null);
        }
        else if (value != null) {
            return value.toString();
        }
        // If no value yet, try to get it as a name_value=xyz parameter
        String prefix = name + "_";
        for (String paramName : parameters.keySet()) {
            if (paramName.startsWith(prefix)) {
                // Support images buttons, which would submit parameters as name_value.x=123
                for (String suffix : SUBMIT_IMAGE_SUFFIXES) {
                    if (paramName.endsWith(suffix)) {
                        return paramName.substring(prefix.length(), paramName.length() - suffix.length());
                    }
                }
                return paramName.substring(prefix.length());
            }
        }
        // We couldn't find the parameter value...
        return null;
    }
}
