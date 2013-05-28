package com.dooioo.web.controller;

import com.dooioo.web.helper.JsonResult;
import com.dooioo.web.model.BaseEntity;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: kuang
 * Date: 12-9-7
 * Time: 上午10:05
 */
public class TestBaseController extends BaseController {

    public static void main(String [] args) throws IOException {

        Map<String, Object> map = new HashMap<String, Object>(){{
            put("key1", "value1");
            put("key2", "value2");
        }};
        List<String> list = list("xx", "oo");

        Post post = new Post();
        post.setName("kuang");
        post.setName("123456");

        System.out.println(OK.put("root1", map).put("root1_list1", list).put(post).toJson());

        System.out.println(ok("test2").put("root2", map).put("post", post).toJson());

        System.out.println(ok("test3").put("root3", list).put("map", map).toJson());

        System.out.println(ok("test4").put("root4", list).put("map", map).toJson());

        JsonResult jsonResult = new JsonResult(JsonResult.STATUS.OK, "test5");
        System.out.println(jsonResult.put("list", list));
    }

    private static class Post extends BaseEntity {
        String name;
        String password;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
