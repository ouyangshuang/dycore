package com.dooioo.web.common.tag;

import com.dooioo.commons.Strings;
import com.dooioo.web.common.Paginate;
import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.FileTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.tags.RequestContextAwareTag;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@SuppressWarnings("serial")
public class PaginateTag extends RequestContextAwareTag {
    private static Boolean boolLoad = false;
    private FreeMarkerConfigurer freeMarkerConfigurer;
	
	private String nextLabel;
	private String previousLabel;
    private String template;

    private JspWriter out;
	private String url;

    public void setTemplate(String template) {
        this.template = template;
    }

	@Override
	protected int doStartTagInternal() throws Exception {
		initData();
		Paginate paginate = (Paginate) pageContext.findAttribute("paginate");
		adjustPageNo(paginate);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("paginate", paginate);
		map.put("nextLabel", nextLabel);
		map.put("previousLabel", previousLabel);
		map.put("url", url);

        if(Strings.isEmpty(template)) {
            template = "pagenate";
        }
        Template t =  freeMarkerConfigurer.getConfiguration().getTemplate(template + ".ftl");
        try {
            String template = FreeMarkerTemplateUtils.processTemplateIntoString(t, map);
            out.print(template);
        } catch (TemplateException e) {
            e.printStackTrace();
        }
		return EVAL_PAGE;
	}

	private void initData(){
		freeMarkerConfigurer = (FreeMarkerConfigurer) getRequestContext().getWebApplicationContext().getBean("freeMarkerConfigurer");
        if(!boolLoad){
            ClassTemplateLoader ctl = new ClassTemplateLoader(this.getClass(), "/conf/template");
            TemplateLoader tl = freeMarkerConfigurer.getConfiguration().getTemplateLoader();
            TemplateLoader[] loaders = new TemplateLoader[] { tl, ctl };
            MultiTemplateLoader mtl = new MultiTemplateLoader(loaders);
            freeMarkerConfigurer.getConfiguration().setTemplateLoader(mtl);
            boolLoad = true;
        }

		nextLabel = "上一页";
		previousLabel = "下一页";
		out = pageContext.getOut();
		url = getURL();
	}
	
	private String getURL(){
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		String baseUrl = (String)request.getAttribute("javax.servlet.forward.request_uri");
		if(baseUrl == null) baseUrl = (String) request.getRequestURI();
		String q = getQueryString(request);
		return baseUrl + q;
	}
	
	private String getQueryString(HttpServletRequest request){
		StringBuffer q = new StringBuffer();
		Map<String, String[]>params = request.getParameterMap();
		q.append("?");
		for(String key : params.keySet()){
			if(key.equalsIgnoreCase("pageNo")){
				continue;
			}
			String [] values = params.get(key);
			for(int i = 0; i< values.length; i++) {
				q.append(key);
				q.append("=");
				q.append(Strings.encodeUTF(values[i]));
				q.append("&");
			}
		}
		q.append("pageNo=<1>");
		return q.toString();
	}

    /**
     * 设置分页参数
     * @param paginate
     */
	private void adjustPageNo(Paginate paginate){
		if(paginate.getPageNo() > paginate.getTotalPage()){
			paginate.setPageNo(paginate.getTotalPage());
		}
		if(paginate.getPageNo() <= 0){
			paginate.setPageNo(1);
		}
	}
}
