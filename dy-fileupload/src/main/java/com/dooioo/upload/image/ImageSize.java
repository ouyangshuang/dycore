package com.dooioo.upload.image;

/**
 * Created with IntelliJ IDEA at 13-6-20 上午11:10.
 *
 * @author 焦义贵
 * @since 1.0
 * 生成缩略图的宽和高
 */
public class ImageSize {
    /**
     * 100x75小图
     */
    public static final ImageSize IMAGE_SIZE_100x75 = new ImageSize(100,75);
    /**
     * 200x150小图
     */
    public static final ImageSize IMAGE_SIZE_200x150 = new ImageSize(200,150);
    /**
     * 300x225小图
     */
    public static final ImageSize IMAGE_SIZE_300x225 = new ImageSize(300,225);
    /**
     * 400x300小图
     */
    public static final ImageSize IMAGE_SIZE_400x300 = new ImageSize(400,300);
    /**
     * 500x375小图
     */
    public static final ImageSize IMAGE_SIZE_500x375 = new ImageSize(500,375);
    /**
     * 600x450小图
     */
    public static final ImageSize IMAGE_SIZE_600x450 = new ImageSize(600,450);
    /**
     * 700x525小图
     */
    public static final ImageSize IMAGE_SIZE_700x525 = new ImageSize(700,525);
    /**
     * 800x600小图
     */
    public static final ImageSize IMAGE_SIZE_800x600 = new ImageSize(800,600);
    /**
     * 900x675小图
     */
    public static final ImageSize IMAGE_SIZE_900x675 = new ImageSize(900,675);
    /**
     * 楼盘看大图
     * 960x960小图
     */
    public static final ImageSize IMAGE_SIZE_960x960 = new ImageSize(960,960);
    /**
     * 房源委托书
     * 0x0原图
     */
    public static final ImageSize IMAGE_SIZE_0x0 = new ImageSize(0,0);
    /**
     * 原图
     */
    public static final ImageSize IMAGE_SIZE_SOURCE = new ImageSize(-1,-1);
    /**
     * 图片宽
     */
    private int width;
    /**
     * 图片高
     */
    private int height;

    private int x;

    private int y;

    public ImageSize() {
    }

    public ImageSize(int width, int height) {
       this.width = width;
       this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
