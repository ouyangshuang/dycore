package com.dooioo.web.controller;

import com.dooioo.web.helper.JsonResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created with IntelliJ IDEA at 13-7-11 下午2:30.
 *
 * @author 焦义贵
 * @since 1.0
 *        To change this template use File | Settings | File Templates.
 */
@Controller
public class PingController extends BaseController{
    @RequestMapping(value = {"/it/ping","/api/it/ping"})
    @ResponseBody
    public JsonResult ping(){
        return ok();
    }
}
