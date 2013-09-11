package com.dooioo.upload.image.factory;

import com.dooioo.upload.UploadResult;
import com.dooioo.upload.image.ImageArgConvert;
import com.dooioo.upload.image.ImageSize;
import com.dooioo.upload.utils.FileUtils;
import com.dooioo.upload.utils.UploadConfig;
import magick.*;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA at 13-6-24 下午1:52.
 *
 * @author 焦义贵
 * @since 1.0
 *        To change this template use File | Settings | File Templates.
 */
public class ImageMagick extends AbstractImage {
    static {
        System.setProperty("jmagick.systemclassloader", "no");
    }

    private ImageMagick() {
    }

//    @Override
    public static void generatesImageHandle(final String fileName,final ImageArgConvert imageArgConvert) throws Exception {
        generatesImageHandle(fileName, new ArrayList<ImageArgConvert>() {{
            add(imageArgConvert);
        }});
    }

//    @Override
    public static void generatesImageHandle(String fileName, List<ImageArgConvert> imageArgConverts) throws Exception {
        if (imageArgConverts == null || imageArgConverts.size() == 0) {
            return;
        }

        String relationPath = originalDirectory + FILE_SEPARATOR + fileName;
        String targetPath = tragetDirectory + FILE_SEPARATOR + fileName;

        if (!FileUtils.exists(relationPath)) {
            return;
        }

        MagickImage image = null;
        try {
            image = new MagickImage(new ImageInfo(relationPath));
            splitHandle(image,targetPath,imageArgConverts);
        } finally {
            if (image != null) {
                image.destroyImages();
            }
        }
    }

    /**
     * 根据图片类型拆分处理所有需要处理的图片
     *
     * @param image
     * @param relativepath
     * @throws Exception
     */
    private static void splitHandle(MagickImage image, String relativepath , List<ImageArgConvert> imageArgConverts) throws Exception {
        convert2RGB(image);
        //生成文件目录
        String relationPath = relativepath.substring(0, relativepath.lastIndexOf("/"));
        FileUtils.existsAndCreate(relationPath);

        // 根据图片类型获得需要生成的图片组
        for (ImageArgConvert imageArgConvert : imageArgConverts) {
            // 遍历生成图片
            imageConvert(image, relativepath, imageArgConvert);

            //TODO:未实现
//            if (imageArgConvert.isMirror()) {
//            }
        }
    }


    /**
     * 处理图片函数
     *
     * @throws Exception
     */
    private static void imageConvert(MagickImage image, String destfilename,ImageArgConvert imageArgConvert) throws Exception {
        String filename = generalImageExtName(imageArgConvert.getImageSize(), destfilename);
        if(new File(filename).exists()){
            return;
        }

        ImageSize retImageSize = scaleSize(new ImageSize((int) image.getDimension().getWidth(), (int) image.getDimension().getHeight()), imageArgConvert.getImageSize());
        // 定义新图的MagickImage对象
        MagickImage newimage = null;
        try {
            newimage = image.scaleImage(retImageSize.getWidth(), retImageSize.getHeight());
            buildWaterMaker(imageArgConvert, newimage);
            newimage = cutImage(imageArgConvert, newimage, retImageSize);
            newimage.setFileName(filename);
            ImageInfo imageInfo = new ImageInfo();
            imageInfo.setQuality(imageArgConvert.getQuality());
            newimage.writeImage(imageInfo);
        }finally {
            if(newimage != null){
                newimage.destroyImages();
            }
        }

    }

//    @Override
    public static UploadResult upload(byte[] data, String savePath) throws MagickException, FileUploadException {
        MagickImage image = null;
        try {
            // 写原图
            try {
                FileUtils.writeByteToFile(data, savePath);
            }catch (IOException e){
                LOGGER.info("图片写入失败", e);
            }

            //处理原图未上传的异常
            if(!FileUtils.exists(savePath)){
                throw new FileUploadException();
            }
            image = new MagickImage(new ImageInfo(), data);
            convert2RGB(image);
            return new UploadResult().setWidth((int) image.getDimension().getWidth()).setHeight((int) image.getDimension().getHeight());
        } finally {
            if (image != null)
                image.destroyImages();
        }
    }


//    @Override
    public static UploadResult write(FileItem fileItem, String savePath) throws MagickException, FileUploadException {
        MagickImage image = null;
        try {
            // 写原图
            try {
//                FileUtils.writeByteToFile(data, savePath);
                fileItem.write(new File(savePath));
            }catch (Exception e){
                LOGGER.info("图片写入失败", e);
            }

            //处理原图未上传的异常
            if(!FileUtils.exists(savePath)){
                throw new FileUploadException();
            }
            image = new MagickImage(new ImageInfo(),fileItem.get());
            convert2RGB(image);
            return new UploadResult().setWidth((int) image.getDimension().getWidth()).setHeight((int) image.getDimension().getHeight());
        } finally {
            if (image != null)
                image.destroyImages();
        }
    }


    /**
     * jmagick 将所有图片色彩统一为RGB
     *
     * @param fromImage
     * @return
     */
    private static boolean convert2RGB(MagickImage fromImage) {
        try {
            if (fromImage.getColorspace() != ColorspaceType.RGBColorspace) {
                return fromImage.transformRgbImage(fromImage.getColorspace());
            }
        } catch (MagickException e) {
            e.printStackTrace();
            LOGGER.info("图片转换RGB失败", e);
        }
        return true;
    }

    /**
     * 为图片打水印
     *
     * @param imageArgConvert
     * @param newimage
     * @throws MagickException
     */
    private static void buildWaterMaker(ImageArgConvert imageArgConvert, MagickImage newimage) throws MagickException {
        if (!hasWaterMaker(imageArgConvert)) {
            return;
        }
        ImageInfo logoinfo = null;
        // 判断是否打平铺水印
        switch (imageArgConvert.getLogoPosition()) {
            case Tile:
                logoinfo = new ImageInfo(UploadConfig.getInstance().getTileLogoPath());
                break;
            default:
                logoinfo = new ImageInfo(UploadConfig.getInstance().getLogoHuge());
                break;
        }

        //宽大于600，使用大图水印
        if( (imageArgConvert.getImageSize().getWidth() > 600 || imageArgConvert.getImageSize().getWidth() <= 0 ) && newimage.getDimension().getWidth() > 600){
            logoinfo = new ImageInfo(UploadConfig.getInstance().getSuperLogoPath());
        }

        MagickImage logoimage = new MagickImage(logoinfo);
        Dimension newimagedimension = newimage.getDimension();
        Dimension logodimension = logoimage.getDimension();
        logoimage.setDelay(0);
        switch (imageArgConvert.getLogoPosition()) {
            case MiddleCenter: // 正中
                newimage.compositeImage(CompositeOperator.AtopCompositeOp, logoimage, ((int) newimagedimension.getWidth() - (int) logodimension.getWidth()) / 2, ((int) newimagedimension.getHeight() - (int) logodimension.getHeight()) / 2);
                break;
            case LeftTop: // 左上
                newimage.compositeImage(CompositeOperator.AtopCompositeOp, logoimage, 0, 0);
                break;
            case RightTop: // 右上
                newimage.compositeImage(CompositeOperator.AtopCompositeOp, logoimage, ((int) newimagedimension.getWidth() - (int) logodimension.getWidth()), 0);
                break;
            case LeftBottom: // 左下
                newimage.compositeImage(CompositeOperator.AtopCompositeOp, logoimage, 0, ((int) newimagedimension.getHeight() - (int) logodimension.getHeight()));
                break;
            case RightBottom: // 右下
                newimage.compositeImage(CompositeOperator.AtopCompositeOp, logoimage, ((int) newimagedimension.getWidth() - (int) logodimension.getWidth()), ((int) newimagedimension.getHeight() - (int) logodimension.getHeight()));
                break;
            case Tile: //平铺
                int startX = 0;
                int startY = 0;
                int width = newimagedimension.width;
                int height = newimagedimension.height;

                while (startY <= height) {
                    newimage.compositeImage(CompositeOperator.AtopCompositeOp, logoimage, startX, startY);
                    startX += (logodimension.width * 1);
                    if (startX >= width) {
                        startY += logodimension.height * 1;
                        startX = 0;
                    }
                }
                break; // 平铺
        }
        logoimage.destroyImages();
    }

    /**
     * 剪裁图片尺寸
     *
     * @param imageArgConvert
     * @param newimage
     * @param imageSize
     */
    private static MagickImage cutImage(ImageArgConvert imageArgConvert, MagickImage newimage, ImageSize imageSize) throws MagickException {
        if (imageArgConvert.getImageSize().getWidth() <=0 || imageArgConvert.getImageSize().getWidth() > 200){
            return newimage;
        }
        Rectangle rect = new Rectangle(imageSize.getX(), imageSize.getY(), imageArgConvert.getImageSize().getWidth(), imageArgConvert .getImageSize().getHeight());
        return newimage.cropImage(rect);
    }
}
