package com.dooioo.upload;

/**
 * Created with IntelliJ IDEA at 13-6-25 下午4:27.
 *
 * @author 焦义贵
 * @since 1.0
 *        To change this template use File | Settings | File Templates.
 */
public enum Company {
    /**
     * 公司-德佑
     */
    DOOIOO("dooioo"),
    /**
     * 公司-德融
     */
    IDERONG("derong");

    private final String value;

    private Company(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
