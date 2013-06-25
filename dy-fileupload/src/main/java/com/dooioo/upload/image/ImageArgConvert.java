package com.dooioo.upload.image;

/**
 * Created with IntelliJ IDEA at 13-6-20 上午11:34.
 *
 * @author 焦义贵
 * @since 1.0
 * 图片生成参数
 */
public class ImageArgConvert {
    /**
     * 图片尺寸
     */
    private ImageSize imageSize;
    /**
     * 图片Logo水印
     */
    private Logo logo;
    /**
     * 图片Logo水印位置
     */
    private LogoPosition logoPosition;
    /**
     * 异步生成缩略图
     */
    private boolean async;
    /**
     * 是否需要生成镜像
     * 楼盘有镜像的概念
      */
    private boolean mirror = false;

    /**
     * @param imageSize 图片尺寸
     */
    public ImageArgConvert(ImageSize imageSize) {
        this(imageSize, Logo.None, LogoPosition.MiddleCenter,false);
    }

    /**
     * @param imageSize  图片尺寸
     * @param async 异步压缩
     */
    public ImageArgConvert(ImageSize imageSize,boolean async) {
        this(imageSize, Logo.None, LogoPosition.MiddleCenter,async);
    }

    /**
     * @param imageSize  图片尺寸
     * @param logo  水印设置
     */
    public ImageArgConvert(ImageSize imageSize, Logo logo) {
        this(imageSize, logo, LogoPosition.MiddleCenter,false);
    }

    /**
     *
     * @param imageSize  图片尺寸
     * @param logo  水印设置
     * @param async 异步压缩
     */
    public ImageArgConvert(ImageSize imageSize, Logo logo,boolean async) {
        this(imageSize, logo, LogoPosition.MiddleCenter,async);
    }

    /**
     *
     * @param imageSize  图片尺寸
     * @param logo  水印设置
     * @param logoPosition 水印位置
     */
    public ImageArgConvert(ImageSize imageSize, Logo logo, LogoPosition logoPosition) {
        this(imageSize,logo,logoPosition,false);
    }

    /**
     *
     * @param imageSize  图片尺寸
     * @param logo  水印设置
     * @param logoPosition 水印位置
     * @param async 异步压缩
     */
    public ImageArgConvert(ImageSize imageSize, Logo logo, LogoPosition logoPosition, boolean async) {
        this.imageSize = imageSize;
        this.logo = logo;
        this.logoPosition = logoPosition;
        this.async = async;
    }

    public ImageSize getImageSize() {
        return imageSize;
    }

    public void setImageSize(ImageSize imageSize) {
        this.imageSize = imageSize;
    }

    public Logo getLogo() {
        return logo;
    }

    public void setLogo(Logo logo) {
        this.logo = logo;
    }

    public LogoPosition getLogoPosition() {
        return logoPosition;
    }

    public void setLogoPosition(LogoPosition logoPosition) {
        this.logoPosition = logoPosition;
    }

    public boolean isAsync() {
        return async;
    }

    public void setAsync(boolean async) {
        this.async = async;
    }

    public boolean isMirror() {
        return mirror;
    }

    public void setMirror(boolean mirror) {
        this.mirror = mirror;
    }
}
