package com.dooioo.web.common.tag;

import com.dooioo.commons.Strings;
import com.dooioo.web.common.StaticFileVersion;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 读取服务公共的javascript,css文件
 * 并根据服务器文件的版本号，自动打上版本号标记
 * 导入多个文件时忆逗号分隔
 * e.g. <app:static_include_tag files="/public/css/main.css"/>
 * User: kuang
 * Date: 11-9-23
 * Time: 下午1:37
 */
public class StaticIncludeTag extends BodyTagSupport {
    private static final Log log = LogFactory.getLog(StaticIncludeTag.class);

    private String files;
    private Map<String, String> versionNos;

    public int doEndTag() throws JspException {
        if (Strings.isEmpty(files)) {
            return SKIP_BODY;
        }
        findStaticFileVersionNos();//初始所有静态文件版本号
        boolean hasVersionChanged = false;
        String _versionNo = System.currentTimeMillis() + "";

        for (String file : files.split(",")) {
            if (Strings.isEmpty(file))
                continue;

            file = file.trim();
            StringBuffer versionFileName = new StringBuffer();
            versionFileName.append(STATIC_FILE_URL);
            if (!file.startsWith("/")) {
                versionFileName.append("/");
            }
            versionFileName.append(file);
            String versionNo = versionNos.get(getFileName(file));//versionProps.getProperty(getFileName(file), "");

            if (Strings.isEmpty(versionNo)) {
                versionNo = _versionNo;
                versionNos.put(getFileName(file), versionNo);
                hasVersionChanged = true;
            }
            versionFileName.append("?v=").append(versionNo);

            String retHtml = TEMPLATE_MAP.get(getFileSuffix(file)).replaceAll("#SRC#", versionFileName.toString());
            JspWriter out = pageContext.getOut();
            try {
                out.print(retHtml);
            } catch (IOException e) {
                log.error(e);
            }
        }

        if (hasVersionChanged) {
            resetStaticFileVersionNos();
        }
        return SKIP_BODY;
    }

    //重新设置所有静态文件版本号
    private void resetStaticFileVersionNos() {
        StaticFileVersion.refresh(versionNos);
        pageContext.setAttribute("StaticVersionNo", versionNos, PageContext.PAGE_SCOPE);
    }

    //获取所有静态文件版本号
    private void findStaticFileVersionNos() {
        versionNos = (Map<String, String>) pageContext.getAttribute("StaticVersionNo", PageContext.PAGE_SCOPE);
        if (versionNos == null) {
            versionNos = StaticFileVersion.findStaticFileVersions();
            pageContext.setAttribute("StaticVersionNo", versionNos, PageContext.PAGE_SCOPE);
        }
    }

    private String getFileName(String filePath) {
        return Strings.substringAfterLast(filePath, "/");
    }

    private String getFileSuffix(String filePath) {
        return Strings.substringAfterLast(filePath, ".").toLowerCase().trim();
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    private static final String STATIC_FILE_URL = "http://dui.dooioo.com";
    private static final String JAVASCRIPT_TEMPLATE = "<script type=\"text/javascript\" language=\"javascript\" src=#SRC#></script>";
    private static final String CSS_TEMPLATE = "<link type=\"text/css\" rel=\"stylesheet\" href=\"#SRC#\"/>";
    private static final Map<String, String> TEMPLATE_MAP = new HashMap<String, String>() {{
        put("js", JAVASCRIPT_TEMPLATE);
        put("css", CSS_TEMPLATE);
    }};
}
