package com.dooioo.upload.image.factory;

/**
 * Created with IntelliJ IDEA at 13-6-24 下午1:54.
 *
 * @author 焦义贵
 * @since 1.0
 *        To change this template use File | Settings | File Templates.
 */
public class ImageFactory {
    private static AbstractImage image = null;

    private ImageFactory(){}

    public static synchronized AbstractImage newInstance(){
        if(image == null){
            image = new ImageMagick();
        }
        return image;
    }
}
