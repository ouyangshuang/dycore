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

    private int quality = 100;

    public ImageArgConvert(){
        this.quality = 100;
    }

    /**
     * @param imageSize 图片尺寸
     */
    public ImageArgConvert(ImageSize imageSize) {
        this(imageSize, Logo.Logo, LogoPosition.MiddleCenter,false);
    }

    /**
     * @param imageSize  图片尺寸
     * @param async 异步压缩
     */
    public ImageArgConvert(ImageSize imageSize,boolean async) {
        this(imageSize, Logo.Logo, LogoPosition.MiddleCenter,async);
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
        this(imageSize,logo,logoPosition,async,100);
    }

    public ImageArgConvert(ImageSize imageSize, Logo logo, LogoPosition logoPosition, boolean async , int quality) {
        this.imageSize = imageSize;
        this.logo = logo;
        this.logoPosition = logoPosition;
        this.async = async;
        this.quality = quality;
    }

    public ImageSize getImageSize() {
        return imageSize;
    }

    public ImageArgConvert setImageSize(ImageSize imageSize) {
        this.imageSize = imageSize;
        return this;
    }

    public Logo getLogo() {
        return logo;
    }

    public ImageArgConvert setLogo(Logo logo) {
        this.logo = logo;
        return this;
    }

    public LogoPosition getLogoPosition() {
        return logoPosition;
    }

    public ImageArgConvert setLogoPosition(LogoPosition logoPosition) {
        this.logoPosition = logoPosition;
        return this;
    }

    public boolean isAsync() {
        return async;
    }

    public ImageArgConvert setAsync(boolean async) {
        this.async = async;
        return this;
    }

    public boolean isMirror() {
        return mirror;
    }

    public ImageArgConvert setMirror(boolean mirror) {
        this.mirror = mirror;
        return this;
    }

    public int getQuality() {
        return quality;
    }

    public ImageArgConvert setQuality(int quality) {
        this.quality = quality;
        return this;
    }
}
