package com.dooioo.web.controller;

import com.dooioo.commons.Lang;
import com.dooioo.commons.Strings;
import com.dooioo.web.exception.FunctionException;
import com.dooioo.web.exception.IllegalOperationException;
import com.dooioo.web.exception.NotFoundException;
import com.dooioo.web.exception.NullException;
import com.dooioo.web.helper.JsonResult;
import com.dooioo.web.model.BaseEntity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Controller 通用父类，提供一些公共方法
 * 例如：获取session
 * User: kuang
 * Date: 12-4-25
 * Time: 上午11:05
 */
public abstract class BaseController {
    private static final Log log = LogFactory.getLog(BaseController.class);
    /**
     * @since 1.7
     */
    @Deprecated
    protected final static String SUCCESS_JSON = "jsonView";
    /**
     * @since 1.7
     */
    @Deprecated
    protected final static String SUCCESS = "success";
    /**
     * @since 1.7
     */
    @Deprecated
    protected final static String ERROR = "error";

    protected final static String ERROR_PAGE = "/error/error";
    protected final static JsonResult OK = new JsonResult(JsonResult.STATUS.OK);
    protected final static JsonResult FAIL = new JsonResult(JsonResult.STATUS.FAIL);
    protected final static String EMPTY_STR = "";

    //各种工具方法
    public static <T> List<T> list(T... arrays) {
        return Lang.list(arrays);
    }

    public static Map map(Object... arrays) {
        return Lang.map(arrays);
    }

    public static <T> Set<T> set(T... arrays) {
        return Lang.set(arrays);
    }

    public String redirect(String page) {
        return "redirect:" + page;
    }

    /**
     * 跳转页面，并且将model里面的值清除
     * @param page
     * @param modelMap
     * @return
     */
    public String redirect(String page, ModelMap modelMap) {
        modelMap.clear();
        return "redirect:" + page;
    }

    public String forward(String page) {
        return "forward:" + page;
    }

    //ok
    public JsonResult ok() {
        return ok("");
    }

    public static JsonResult ok(String message) {
        return new JsonResult(JsonResult.STATUS.OK, message);
    }

    public static JsonResult ok(BaseEntity value) {
        return ok(EMPTY_STR, value);
    }

    public static JsonResult ok(String message, BaseEntity value) {
        return ok(message).put(value);
    }

    public static JsonResult ok(String message, String key, Object value) {
        return new JsonResult(JsonResult.STATUS.OK, message).put(key, value);
    }


    //fail
    public static JsonResult fail() {
        return fail("");
    }

    public static JsonResult fail(String message) {
        return new JsonResult(JsonResult.STATUS.FAIL, message);
    }

    public static JsonResult fail(BaseEntity value) {
        return fail(EMPTY_STR, value);
    }

    public static JsonResult fail(String key, Object value) {
        return fail(EMPTY_STR, key, value);
    }

    public static JsonResult fail(String message, String key, Object value) {
        return new JsonResult(JsonResult.STATUS.FAIL, message).put(key, value);
    }

    /**
     * 拦截NotFoundException
     * 跳转至指定的页面
     *
     * @see com.dooioo.web.exception.NotFoundException
     * @param ex
     * @param request
     * @return
     * @throws java.io.IOException
     */
    @ExceptionHandler(NotFoundException.class)
    public RedirectView notFoundException(NotFoundException ex, HttpServletRequest request) throws IOException {
        log.error("NotFoundException => ", ex);
        RedirectView redirectView = new RedirectView(Strings.isEmpty(ex.getUrl()) ? "/404" : ex.getUrl());
        redirectView.addStaticAttribute("message", ex.getMessage());
        return redirectView;
    }

    /**
     * 拦截参数异常，返回jsonResult
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public JsonResult illegalArgumentException(IllegalArgumentException ex, HttpServletRequest request) {
        log.error("IllegalArgumentException => ", ex);
        return fail(ex.getMessage());
    }

    /**
     * 拦截方法异常，返回jsonResult，http请求返回500
     *
     * @see com.dooioo.web.exception.FunctionException
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(FunctionException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public JsonResult functionException(FunctionException ex, HttpServletRequest request) {
        log.error("FunctionException => ", ex);
        return fail(ex.getMessage());
    }

    /**
     * 拦截NullException，返回jsonResult，http请求返回500
     *
     * @see com.dooioo.web.exception.NullException
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(NullException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public JsonResult nullException(NullException ex, HttpServletRequest request) {
        log.error("NullException => ", ex);
        return fail(ex.getMessage());
    }

    /**
     * 拦截非法操作异常，返回jsonResult，http请求返回500
     *
     * @see com.dooioo.web.exception.IllegalOperationException
     * @param ex
     * @return
     */
    @ExceptionHandler(IllegalOperationException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public JsonResult illegalOperationException(IllegalOperationException ex) {
        log.error("IllegalOperationException => ", ex);
        return fail(ex.getMessage());
    }

    /**
     * 拦截数据库操作异常，返回jsonResult，http请求返回500
     * @param ex
     * @return
     */
    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public JsonResult dataAccessException(DataAccessException ex) {
        log.error("DataAccessException => ", ex);
        return fail(ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public JsonResult runtimeException(RuntimeException ex) {
        log.error("RuntimeException => ", ex);
        return fail(ex.getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public JsonResult nullPointerException(NullPointerException ex) {
        log.error("NullPointerException => ", ex);
        return fail(ex.getMessage());
    }


    /**
     * 获取客户端的请求IP
     * @param request
     * @return
     */
    protected String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("Cdn-Src-Ip");//增加CDN获取ip
        if (Strings.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (Strings.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (Strings.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (Strings.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (Strings.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (Strings.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
