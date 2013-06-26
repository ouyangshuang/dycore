package com.dooioo.upload;

/**
 * Created with IntelliJ IDEA at 13-6-25 上午9:36.
 *
 * @author 焦义贵
 * @since 1.0
 *        To change this template use File | Settings | File Templates.
 */
public class Upload {
    /**
     * 上传文件源名称
     */
    private String origiName;

    /**
     *系统生成后文件名
     */
    private String targetName;

    /**
     * 图片：宽
     */
    private int width;

    /**
     * 图片：高
     */
    private int height;

    /**
     * zip 获取中间.htm文件访问地址
     */
    private String htmPath;

    /**
     * zip 获取其中一张图片当作可预览地址
     */
    private String iconPath;


    public Upload(){
    }

    public String getOrigiName() {
        return origiName;
    }

    public Upload setOrigiName(String origiName) {
        this.origiName = origiName;
        return this;
    }

    public String getTargetName() {
        return targetName;
    }

    public Upload setTargetName(String targetName) {
        this.targetName = targetName;
        return this;
    }

    public int getWidth() {
        return width;
    }

    public Upload setWidth(int width) {
        this.width = width;
        return this;
    }

    public int getHeight() {
        return height;
    }

    public Upload setHeight(int height) {
        this.height = height;
        return this;
    }

    public String getHtmPath() {
        return htmPath;
    }

    public void setHtmPath(String htmPath) {
        this.htmPath = htmPath;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }
}
