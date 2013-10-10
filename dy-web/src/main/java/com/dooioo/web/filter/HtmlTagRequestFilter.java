package com.dooioo.web.filter;

import com.dooioo.web.common.WebUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * 对请求参数的值，做HTML表做过滤
 *
 */
public class HtmlTagRequestFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //HTML 过滤
        boolean filterHtmlTag = WebUtils.findParamBool(request,"filterHtmlTag",true);
        if(filterHtmlTag){
            HttpServletRequest _request = new HtmlTagRequestWrapper(request);
            filterChain.doFilter(_request,response);
        }else{
            filterChain.doFilter(request,response);
        }
    }

    private static class HtmlTagRequestWrapper extends HttpServletRequestWrapper {
        public HtmlTagRequestWrapper(HttpServletRequest request) {
            super(request);
        }

        @Override
        public String getParameter(String name) {
            String queryStringValue = super.getParameter(name);
            if(null == queryStringValue){
                return null;
            }
            return queryStringValue.replaceAll("<([^>]*)>","");
        }

        @Override
        public Map<String, String[]> getParameterMap() {
            Map<String, String[]> result = new LinkedHashMap<String, String[]>();
            Enumeration<String> names = this.getParameterNames();
            while (names.hasMoreElements()) {
                String name = names.nextElement();
                result.put(name, this.getParameterValues(name));
            }
            return result;
        }

        @Override
        public String[] getParameterValues(String name) {
            String[] queryStringValues = super.getParameterValues(name);
            if(null == queryStringValues){
                return null;
            }
            for(int i =0 ;i < queryStringValues.length; i++){
                if(null != queryStringValues[i]){
                    queryStringValues[i] = queryStringValues[i].replaceAll("<([^>]*)>","");
                }
            }
            return queryStringValues;
        }
    }
}
