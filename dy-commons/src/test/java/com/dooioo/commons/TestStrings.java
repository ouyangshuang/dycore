package com.dooioo.commons;

/**
 * Created by IntelliJ IDEA.
 * User: kuang
 * Date: 13-1-25
 * Time: 上午11:47
 */
public class TestStrings {


    public static void main(String [] args) {
        System.out.println(Strings.isEmpty(" "));
        System.out.println(Strings.isNotEmpty(" "));
        System.out.println(Strings.isEmpty("null"));
        System.out.println(Strings.isNotEmpty("null"));
        System.out.println(Strings.isEmpty(null));
        System.out.println(Strings.isNotEmpty(null));
    }

}
