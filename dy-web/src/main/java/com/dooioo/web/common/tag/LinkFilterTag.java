package com.dooioo.web.common.tag;

import com.dooioo.commons.Strings;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 列表页筛选组件
 * 如果URL的参数值跟当前的值相同时，显示为当选的样式
 * <app:linkFilterTag label="${usage.value}" key="usage" value="${usage.key}"/>
 * lable    : 链接文案名称
 * key      : 参数键
 * value    : 参数值
 * cssClass : 样式
 * onclick  : 点击事件
 *
 * User: kuang
 * Date: 12-10-23
 * Time: 下午5:17
 */
public class LinkFilterTag extends RequestContextAwareTag {
    private static final Log log = LogFactory.getLog(LinkFilterTag.class);
    private static String HREF_HTML = "<a href=\"#href#\" class=\"in_block #cssClass#\" #onclick#>#label#</a>";
    private static String CHECKED_HTML = "<b class=\"current in_block #cssClass#\" href=\"#\">#label#</b>";

    private String label = "";
    private String key = "";
    private String value = "";
    private String defaultChecked = "";
    private String cssClass = "";
    private String onclick = "";

    @Override
    protected int doStartTagInternal() throws Exception {
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        StringBuffer retHtml = new StringBuffer();

        Map<String, String> params = getParameterMap(request);

        //生成当选样式
        String paramsValue = Strings.defaultEmptyStr(params.get(getKey())).toLowerCase();
        if(getValue().toLowerCase().equals(paramsValue)
                || (Strings.isEmpty(paramsValue) && getDefaultChecked().equals(getValue()))) {
            retHtml.append(CHECKED_HTML.replaceAll("#label#", getLabel()));
        } else {
            StringBuffer queryString = new StringBuffer();
            if(Strings.isNotEmpty(getValue())) {
                queryString.append("?").append(buildParam(getKey(), getValue()));
            }

            for(Map.Entry<String, String> entry : params.entrySet()) {
                String _key = entry.getKey();
                String _value = entry.getValue();
                if(_key.equals(getKey())) {
                    continue;
                }

                //过滤掉翻页，因为新的筛选条件从第1页开始
                if(_key.toLowerCase().equals("pageno")) {
                    continue;
                }

                if(Strings.isEmpty(queryString.toString())) {
                    queryString.append("?");
                } else {
                    queryString.append("&");
                }

                queryString.append(buildParam(_key, _value));
            }

            StringBuffer strHref = new StringBuffer(request.getAttribute("javax.servlet.forward.request_uri") + "");
            if(!StringUtils.isEmpty(queryString.toString()))
                strHref.append(queryString);

            String hrefHmtl = HREF_HTML;
            if(Strings.isNotEmpty(getOnclick())) {
                hrefHmtl = hrefHmtl.replaceAll("#onclick#", "onclick=\"" + getOnclick() + "\"");
            } else {
                hrefHmtl = hrefHmtl.replaceAll("#onclick#", "");
            }

            retHtml.append(hrefHmtl.replaceAll("#href#", strHref.toString())
                                    .replaceAll("#label#", label)
                                    .replaceAll("#cssClass#", getCssClass()));

        }

        JspWriter out = pageContext.getOut();
        try {
            out.print(retHtml);
        } catch (IOException e) {
            log.error(e);
        }
        return SKIP_BODY;
    }


    private Map<String, String> getParameterMap(HttpServletRequest request) {
        Map<String, String> retMap = new HashMap<String, String>();
        Map<String, String[]> params = request.getParameterMap();

        for(String key : params.keySet()){
            retMap.put(key,  params.get(key)[0]);
        }
        return retMap;
    }

    private String buildParam(String key, String value) {
        try {
            return key + "=" + URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error(e);
        }
        return key + "=";
    }
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCssClass() {
        return cssClass;
    }

    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }

    public String getOnclick() {
        return onclick;
    }

    public void setOnclick(String onclick) {
        this.onclick = onclick;
    }

    public String getDefaultChecked() {
        return Strings.defaultEmptyStr(defaultChecked).toLowerCase();
    }

    public void setDefaultChecked(String defaultChecked) {
        this.defaultChecked = defaultChecked;
    }

}
